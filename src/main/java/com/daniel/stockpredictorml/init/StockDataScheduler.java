package com.daniel.stockpredictorml.init;

import com.daniel.stockpredictorml.service.StockService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StockDataScheduler {

    private final StockService stockService;
    private boolean initialRunDone = false;

    public StockDataScheduler(StockService stockService) {
        this.stockService = stockService;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        stockService.fetchAndStoreQuotes();
        initialRunDone = true;
    }


    @Scheduled(fixedRate = 40 * 60 * 1000)
    public void scheduledFetch() {
        if (initialRunDone) {
            stockService.fetchAndStoreQuotes();
        }
    }
}
