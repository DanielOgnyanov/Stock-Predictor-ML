package service;

import com.daniel.stockpredictorml.models.entities.NewsEntity;
import com.daniel.stockpredictorml.repository.NewsRepositories;
import com.daniel.stockpredictorml.service.Impl.NewsServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsServiceImplTest {

    private NewsRepositories newsRepository;
    private NewsServiceImpl newsServiceSpy;
    private RestTemplate mockRestTemplate;

    @BeforeEach
    void setUp() throws Exception {

        newsRepository = mock(NewsRepositories.class);


        newsServiceSpy = Mockito.spy(new NewsServiceImpl(newsRepository));


        mockRestTemplate = mock(RestTemplate.class);
        var restTemplateField = NewsServiceImpl.class.getDeclaredField("restTemplate");
        restTemplateField.setAccessible(true);
        restTemplateField.set(newsServiceSpy, mockRestTemplate);
    }

    @Test
    void fetchAndStoreNews_ShouldSaveParsedNews() {

        String fakeResponse = """
                    {
                      "data": [
                        {
                          "title": "Test News Title",
                          "description": "Test Description",
                          "snippet": "Test Snippet",
                          "entities": [{ "symbol": "AAPL" }]
                        }
                      ]
                    }
                """;


        when(mockRestTemplate.getForObject(anyString(), eq(String.class))).thenReturn(fakeResponse);


        newsServiceSpy.fetchAndStoreNews();


        verify(newsRepository, times(1)).save(any(NewsEntity.class));
    }

    @Test
    void fetchAndStoreNews_ShouldHandleEmptyData() {

        String emptyResponse = "{ \"data\": [] }";
        when(mockRestTemplate.getForObject(anyString(), eq(String.class))).thenReturn(emptyResponse);


        newsServiceSpy.fetchAndStoreNews();


        verify(newsRepository, never()).save(any(NewsEntity.class));
    }

    @Test
    void fetchAndStoreNews_ShouldHandleNullResponse() {

        when(mockRestTemplate.getForObject(anyString(), eq(String.class))).thenReturn(null);


        newsServiceSpy.fetchAndStoreNews();


        verify(newsRepository, never()).save(any(NewsEntity.class));
    }
}
