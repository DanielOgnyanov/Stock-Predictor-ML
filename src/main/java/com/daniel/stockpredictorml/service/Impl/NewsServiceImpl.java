package com.daniel.stockpredictorml.service.Impl;

import com.daniel.stockpredictorml.models.dto.NewsResponseDTO;
import com.daniel.stockpredictorml.models.entities.NewsEntity;
import com.daniel.stockpredictorml.repository.NewsRepositories;
import com.daniel.stockpredictorml.service.NewsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepositories newsRepositories;
    private final RestTemplate restTemplate = new RestTemplate();


    private static final String API_KEY = "qLaO07X8EYUf7BP6hZ35noxVT42yeJMPOwW5bAsU";
    private static final String BASE_URL = "https://api.marketaux.com/v1/news/all?symbols=AAPL,MSFT,NVDA,GOOGL,AMZN&filter_entities=true&language=en&api_token=";
    private static String url = BASE_URL + API_KEY;

    public NewsServiceImpl(NewsRepositories newsRepositories) {
        this.newsRepositories = newsRepositories;
    }


    @Override
    public void fetchAndStoreNews() {

        try {

            String response = restTemplate.getForObject(url, String.class);

            if (response != null) {

                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response);

                JsonNode dataArray = root.path("data");

                for (JsonNode newsNode : dataArray) {

                    String title = newsNode.path("title").asText(null);
                    String description = newsNode.path("description").asText(null);
                    String snippet = newsNode.path("snippet").asText(null);

                    JsonNode entities = newsNode.path("entities");
                    String symbol = null;
                    if (entities.isArray() && entities.size() > 0) {
                        symbol = entities.get(0).path("symbol").asText(null);
                    }


                    NewsEntity newsEntity = NewsEntity.builder()

                            .title(title)
                            .description(description)
                            .snippet(snippet)
                            .symbol(symbol)
                            .build();

                    newsRepositories.save(newsEntity);

                }


            }

        } catch (Exception e) {
            System.err.println("Error fetching/saving symbol: " + e.getMessage());
        }


    }


    @Override
    public List<NewsEntity> getAllTheNewsOrderedByDate() {

        return newsRepositories.findAll();
    }
}
