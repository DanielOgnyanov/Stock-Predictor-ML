
import com.daniel.stockpredictorml.service.PredictService;
import com.daniel.stockpredictorml.web.StockRestController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(StockRestController.class)
public class StockRestControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private PredictService predictService;

    @Test
    void testPredictStock() throws Exception {
        String symbol = "AAPL";


        Mockito.doNothing().when(predictService).predictAndSave(symbol);

        mockMvc.perform(post("/api/stocks/predict")
                        .param("symbol", symbol))
                .andExpect(status().isOk());
    }
}
