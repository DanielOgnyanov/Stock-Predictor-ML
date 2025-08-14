package com.daniel.stockpredictorml.init;


import com.daniel.stockpredictorml.service.StockService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StockDataScheduler {

    private final StockService stockService;

    public StockDataScheduler(StockService stockService) {
        this.stockService = stockService;
    }


}
