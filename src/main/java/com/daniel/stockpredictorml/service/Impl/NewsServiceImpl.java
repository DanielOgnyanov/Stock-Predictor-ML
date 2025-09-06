package com.daniel.stockpredictorml.service.Impl;

import com.daniel.stockpredictorml.models.dto.NewsResponseDTO;
import com.daniel.stockpredictorml.models.entities.NewsEntity;
import com.daniel.stockpredictorml.repository.NewsRepositories;
import com.daniel.stockpredictorml.service.NewsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepositories newsRepositories;
    private final RestTemplate restTemplate = new RestTemplate();



    private static final String API_KEY = "";
    private static final String BASE_URL = "https://api.marketaux.com/v1/news/all?symbols=TSLA,AMZN,MSFT&filter_entities=true&language=en&api_token=";
    private static String url = BASE_URL + API_KEY;

    public NewsServiceImpl(NewsRepositories newsRepositories) {
        this.newsRepositories = newsRepositories;
    }


    @Override
    public void fetchAndStoreNews() {

        try{

            NewsResponseDTO response = restTemplate.getForObject(url, NewsResponseDTO.class);

            if (response != null) {

                NewsEntity newsEntity = mapToEntity(response);
                newsRepositories.save(newsEntity);


            }

        } catch (Exception e) {
            System.err.println("Error fetching/saving symbol: " + " - " + e.getMessage());
        }



    }

    private NewsEntity mapToEntity(NewsResponseDTO response) {
        return NewsEntity.builder()
                .title(response.getTitle())
                .snippet(response.getSnippet())
                .symbol(response.getSymbol())
                .description(response.getDescription())
                .build();
    }
}
