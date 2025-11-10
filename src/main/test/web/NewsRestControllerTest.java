package web;


import com.daniel.stockpredictorml.models.entities.NewsEntity;
import com.daniel.stockpredictorml.service.NewsService;
import com.daniel.stockpredictorml.web.NewsRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NewsRestController.class)
public class NewsRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService newsService;

    @Test
    void getAllTheNewsOrderedByDate_ShouldReturnOk_WhenNewsExist() throws Exception {
        NewsEntity news = NewsEntity.builder()
                .title("News Title")
                .description("News Description")
                .snippet("News Snippet")
                .symbol("AAPL")
                .build();

        when(newsService.getAllTheNewsOrderedByDate()).thenReturn(List.of(news));

        mockMvc.perform(get("/api/news/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("News Title"))
                .andExpect(jsonPath("$[0].symbol").value("AAPL"));
    }

    @Test
    void getAllTheNewsOrderedByDate_ShouldReturnNoContent_WhenEmpty() throws Exception {
        when(newsService.getAllTheNewsOrderedByDate()).thenReturn(List.of());

        mockMvc.perform(get("/api/news/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
