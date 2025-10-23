package com.daniel.stockpredictorml.service;

import com.daniel.stockpredictorml.models.entities.StockEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StockService {
    void fetchAndStoreQuotes();
    Optional<StockEntity> getLatestStockBySymbol(String symbol);
    List<StockEntity> getAllStocksLatestPricesPerSymbol();

    List<Map<String, Object>>  getPriceHistoryBySymbol(String symbol);
}
