package service;

import com.daniel.stockpredictorml.models.dto.StockQuoteResponseDTO;
import com.daniel.stockpredictorml.models.entities.StockEntity;
import com.daniel.stockpredictorml.models.enums.TopStockSymbol;
import com.daniel.stockpredictorml.repository.StockRepository;
import com.daniel.stockpredictorml.service.Impl.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private StockServiceImpl stockService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);


        ReflectionTestUtils.setField(stockService, "restTemplate", restTemplate);
    }


    @Test
    void fetchAndStoreQuotes_ShouldSaveQuote_WhenApiReturnsValidResponse() {

        StockQuoteResponseDTO mockResponse = new StockQuoteResponseDTO();
        mockResponse.setSymbol("AAPL");
        mockResponse.setName("Apple Inc");
        mockResponse.setCurrency("USD");
        mockResponse.setOpen(BigDecimal.ONE);
        mockResponse.setHigh(BigDecimal.ONE);
        mockResponse.setLow(BigDecimal.ONE);
        mockResponse.setClose(BigDecimal.ONE);
        mockResponse.setVolume(1000000);

        when(restTemplate.getForObject(anyString(), eq(StockQuoteResponseDTO.class)))
                .thenReturn(mockResponse);

        stockService.fetchAndStoreQuotes();

        verify(stockRepository, atLeastOnce()).save(any(StockEntity.class));
    }


    @Test
    void getLatestStockBySymbol_ShouldReturnEntity_WhenExists() {

        StockEntity mockEntity = StockEntity.builder()
                .symbol("TSLA")
                .build();

        when(stockRepository.findTopBySymbolOrderByUpdatedAtDesc("TSLA"))
                .thenReturn(Optional.of(mockEntity));

        Optional<StockEntity> result = stockService.getLatestStockBySymbol("TSLA");

        assertTrue(result.isPresent());
        assertEquals("TSLA", result.get().getSymbol());
    }


    @Test
    void getAllStocksLatestPricesPerSymbol_ShouldReturnList() {

        StockEntity entity = StockEntity.builder().symbol("AAPL").build();

        when(stockRepository.findLatestPricesPerSymbol())
                .thenReturn(List.of(entity));

        List<StockEntity> result = stockService.getAllStocksLatestPricesPerSymbol();

        assertEquals(1, result.size());
        assertEquals("AAPL", result.get(0).getSymbol());
    }


    @Test
    void getPriceHistoryBySymbol_ShouldReturnHistory() {

        Map<String, Object> row = new HashMap<>();
        row.put("price", 150);

        when(stockRepository.findPriceHistoryBySymbol("AAPL"))
                .thenReturn(List.of(row));

        List<Map<String, Object>> result = stockService.getPriceHistoryBySymbol("AAPL");

        assertEquals(1, result.size());
        assertEquals(150, result.get(0).get("price"));
    }
}
