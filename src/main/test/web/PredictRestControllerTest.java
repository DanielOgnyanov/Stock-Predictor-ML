package web;

import com.daniel.stockpredictorml.models.dto.PredictResponseDTO;
import com.daniel.stockpredictorml.service.PredictService;
import com.daniel.stockpredictorml.service.StockService;
import com.daniel.stockpredictorml.web.PredictRestController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PredictRestController.class)

public class PredictRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PredictService predictService;

    @MockBean
    private StockService stockService;

    @Test
    @DisplayName("POST /api/predict/new should call predictService and return OK")
    void predictAndSave_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/predict/new")
                        .param("symbol", "AAPL"))
                .andExpect(status().isOk())
                .andExpect(content().string("Prediction requested and saved for symbol: AAPL"));

        verify(predictService, times(1)).predictAndSave("AAPL");
    }

    @Test
    @DisplayName("POST /api/predict/new should handle service exception gracefully")
    void predictAndSave_ShouldHandleException() throws Exception {
        doThrow(new RuntimeException("Service failed"))
                .when(predictService).predictAndSave(anyString());

        mockMvc.perform(post("/api/predict/new")
                        .param("symbol", "TSLA"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error processing prediction for symbol: TSLA"));
    }

    @Test
    @DisplayName("GET /api/predict/{symbol}/latest should return latest prediction when found")
    void getLatestPrediction_ShouldReturnPrediction() throws Exception {
        PredictResponseDTO dto = new PredictResponseDTO("GOOGL", new BigDecimal("184.52"));

        when(predictService.getLatestPrediction("GOOGL")).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/api/predict/GOOGL/latest")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                          "symbol": "GOOGL",
                          "predicted_close": 184.52
                        }
                """));
    }

    @Test
    @DisplayName("GET /api/predict/{symbol}/latest should return 404 when not found")
    void getLatestPrediction_ShouldReturnNotFound() throws Exception {
        when(predictService.getLatestPrediction("AMZN")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/predict/AMZN/latest"))
                .andExpect(status().isNotFound());
    }
}
