package com.daniel.stockpredictorml.service;

import java.util.List;

public interface StockService {
    void fetchAndStoreQuotes(List<String> symbols);
}
