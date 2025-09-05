package com.daniel.stockpredictorml.service.Impl;

import com.daniel.stockpredictorml.repository.NewsRepositories;
import com.daniel.stockpredictorml.service.NewsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepositories newsRepositories;
    private final RestTemplate restTemplate = new RestTemplate();


    private static final String API_KEY = "";
    private static final String BASE_URL = "https://api.marketaux.com/v1/news/all?symbols=";

    public NewsServiceImpl(NewsRepositories newsRepositories) {
        this.newsRepositories = newsRepositories;
    }


    @Override
    public void fetchAndStoreNews() {

    }
}
