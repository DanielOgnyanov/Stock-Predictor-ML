package web;

import com.daniel.stockpredictorml.models.entities.StockEntity;
import com.daniel.stockpredictorml.service.StockService;
import com.daniel.stockpredictorml.web.StockRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = com.daniel.stockpredictorml.StockPredictorML.class)
class StockRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    @Test
    void testGetAllStocks_returnsOk_withStockList() throws Exception {
        StockEntity stock = new StockEntity();
        stock.setId(1L);
        stock.setSymbol("AAPL");
        stock.setOpen(BigDecimal.valueOf(150.50));

        List<StockEntity> stockList = List.of(stock);

        when(stockService.getAllStocksLatestPricesPerSymbol()).thenReturn(stockList);

        mockMvc.perform(get("/api/stocks/latest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol").value("AAPL"))
                .andExpect(jsonPath("$[0].open").value(150.50));
    }

    @Test
    void testGetAllStocks_returnsNoContent_whenEmpty() throws Exception {
        when(stockService.getAllStocksLatestPricesPerSymbol()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/stocks/latest"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetPriceHistoryBySymbol() throws Exception {
        Map<String, Object> priceRecord = new HashMap<>();
        priceRecord.put("timestamp", "2024-01-01T10:00:00");
        priceRecord.put("open", 123.45);

        when(stockService.getPriceHistoryBySymbol("AAPL"))
                .thenReturn(List.of(priceRecord));

        mockMvc.perform(get("/api/stocks/price/history/open/AAPL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].open").value(123.45))
                .andExpect(jsonPath("$[0].timestamp").value("2024-01-01T10:00:00"));
    }
}
