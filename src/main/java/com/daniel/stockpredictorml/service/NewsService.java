package com.daniel.stockpredictorml.service;

import com.daniel.stockpredictorml.models.entities.NewsEntity;

import java.util.List;

public interface NewsService {

    void fetchAndStoreNews();
    List<NewsEntity> getAllTheNewsOrderedByDate();
}
