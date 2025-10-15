package com.daniel.stockpredictorml.web;


import com.daniel.stockpredictorml.models.entities.StockEntity;
import com.daniel.stockpredictorml.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stocks")
public class StockRestController {

    private final StockService stockService;

    public StockRestController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/latest")
    public ResponseEntity<List<StockEntity>> getAllStocks() {
        List<StockEntity> stocks = stockService.getAllStocksLatestPricesPerSymbol();

        if (stocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/price/history/open")
    public List<Map<String, Object>> getAllSymbolsOpenPriceHistory() {
        return stockService.getAllSymbolsWithOpenPriceHistory();
    }
}
