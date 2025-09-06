package com.daniel.stockpredictorml.web;


import com.daniel.stockpredictorml.models.entities.NewsEntity;
import com.daniel.stockpredictorml.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsRestController {

    private final NewsService newsService;

    public NewsRestController(NewsService newsService) {
        this.newsService = newsService;
    }



    @GetMapping("/all")

    public ResponseEntity<List<NewsEntity>> getAllTheNewsOrderedByDate() {
        List<NewsEntity> news = newsService.getAllTheNewsOrderedByDate();

        if(news.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(news);
    }
}
