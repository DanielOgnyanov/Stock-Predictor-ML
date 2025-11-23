package service;

import com.daniel.stockpredictorml.models.dto.PredictResponseDTO;
import com.daniel.stockpredictorml.models.entities.PredictEntity;
import com.daniel.stockpredictorml.models.entities.StockEntity;
import com.daniel.stockpredictorml.repository.PredictRepository;
import com.daniel.stockpredictorml.repository.StockRepository;
import com.daniel.stockpredictorml.service.Impl.PredictServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PredictServiceImplTest {

    private final StockRepository stockRepository = mock(StockRepository.class);
    private final PredictRepository predictRepository = mock(PredictRepository.class);
    private final RestTemplate restTemplate = mock(RestTemplate.class);

    private final PredictServiceImpl predictService =
            new PredictServiceImpl(stockRepository, predictRepository, restTemplate);

    @Test
    void predictAndSave_ShouldDoNothing_WhenStockDoesNotExist() {
        when(stockRepository.findTopBySymbolOrderByUpdatedAtDesc("AAPL"))
                .thenReturn(Optional.empty());

        predictService.predictAndSave("AAPL");

        verify(restTemplate, never()).postForObject(anyString(), any(), any());
        verify(predictRepository, never()).save(any());
    }

    @Test
    void predictAndSave_ShouldSavePrediction_WhenPythonReturnsData() {

        StockEntity stock = new StockEntity();
        stock.setSymbol("AAPL");
        stock.setOpen(BigDecimal.TEN);
        stock.setHigh(BigDecimal.TEN);
        stock.setLow(BigDecimal.TEN);
        stock.setClose(BigDecimal.TEN);
        stock.setVolume(1000);
        stock.setCurrency("USD");
        stock.setName("Apple");

        when(stockRepository.findTopBySymbolOrderByUpdatedAtDesc("AAPL"))
                .thenReturn(Optional.of(stock));


        List<Map<String, Object>> pythonResponse = List.of(
                Map.of(
                        "symbol", "AAPL",
                        "predicted_close", new BigDecimal("150.55")
                )
        );

        when(restTemplate.postForObject(
                eq("http://127.0.0.1:5000/predict"),
                any(),
                eq(List.class)
        )).thenReturn((List) pythonResponse);

        predictService.predictAndSave("AAPL");

        ArgumentCaptor<PredictEntity> captor = ArgumentCaptor.forClass(PredictEntity.class);
        verify(predictRepository, times(1)).save(captor.capture());

        PredictEntity saved = captor.getValue();

        assertEquals("AAPL", saved.getSymbol());
        assertEquals(new BigDecimal("150.55"), saved.getPredictedClose());
    }

    @Test
    void predictAndSave_ShouldNotSave_WhenPythonReturnsEmpty() {
        StockEntity stock = new StockEntity();
        stock.setSymbol("AAPL");

        when(stockRepository.findTopBySymbolOrderByUpdatedAtDesc("AAPL"))
                .thenReturn(Optional.of(stock));

        when(restTemplate.postForObject(anyString(), any(), eq(List.class)))
                .thenReturn(List.of()); // empty response

        predictService.predictAndSave("AAPL");

        verify(predictRepository, never()).save(any());
    }

    @Test
    void getLatestPrediction_ShouldReturnDTO_WhenExists() {
        PredictEntity entity = new PredictEntity();
        entity.setSymbol("TSLA");
        entity.setPredictedClose(new BigDecimal("123.45"));

        when(predictRepository.findTopBySymbolOrderByUpdatedAtDesc("TSLA"))
                .thenReturn(Optional.of(entity));

        Optional<PredictResponseDTO> result = predictService.getLatestPrediction("TSLA");

        assertTrue(result.isPresent());
        assertEquals("TSLA", result.get().getSymbol());
        assertEquals(new BigDecimal("123.45"), result.get().getPredicted_close());
    }

    @Test
    void getLatestPrediction_ShouldReturnEmpty_WhenNoPrediction() {
        when(predictRepository.findTopBySymbolOrderByUpdatedAtDesc("NFLX"))
                .thenReturn(Optional.empty());

        Optional<PredictResponseDTO> result = predictService.getLatestPrediction("NFLX");

        assertTrue(result.isEmpty());
    }
}
