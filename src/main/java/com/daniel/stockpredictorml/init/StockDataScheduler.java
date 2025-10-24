package com.daniel.stockpredictorml.init;

import com.daniel.stockpredictorml.service.NewsService;
import com.daniel.stockpredictorml.service.StockService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StockDataScheduler {

    private final StockService stockService;
    private final NewsService newsService;
    private boolean initialRunDone = false;

    public StockDataScheduler(StockService stockService, NewsService newsService) {
        this.stockService = stockService;
        this.newsService = newsService;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        stockService.fetchAndStoreQuotes();
        newsService.fetchAndStoreNews();
        initialRunDone = true;
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void scheduledFetch() {
        if (initialRunDone) {
            stockService.fetchAndStoreQuotes();
            newsService.fetchAndStoreNews();
        }
    }
}
