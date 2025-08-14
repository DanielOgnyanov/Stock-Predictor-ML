package com.daniel.stockpredictorml.service.Impl;


import com.daniel.stockpredictorml.models.dto.StockQuoteResponseDTO;
import com.daniel.stockpredictorml.models.entities.StockEntity;
import com.daniel.stockpredictorml.repository.StockRepository;
import com.daniel.stockpredictorml.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;


@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void fetchAndStoreQuotes(List<String> symbols) {

        String apiKey = "";
        String baseUrl = "https://api.twelvedata.com/quote";

        int maxSymbols = Math.min(symbols.size(), 50);

        for (int i = 0; i < maxSymbols; i++) {
            String symbol = symbols.get(i);
            String url = baseUrl + "?symbol=" + symbol + "&apikey=" + apiKey;

            try {
                StockQuoteResponseDTO response = restTemplate.getForObject(url, StockQuoteResponseDTO.class);
                if (response != null) {
                    StockEntity stockEntity = getStockEntity(response);

                    stockRepository.save(stockEntity);
                    System.out.println("Saved: " + symbol);
                }
            } catch (Exception e) {
                System.err.println("Error fetching/saving symbol: " + symbol + " - " + e.getMessage());
            }
        }

    }

    private static StockEntity getStockEntity(StockQuoteResponseDTO response) {
        StockEntity stockEntity = new StockEntity();
        stockEntity.setSymbol(response.getSymbol());
        stockEntity.setName(response.getName());
        stockEntity.setOpen(new BigDecimal(response.getOpen()));
        stockEntity.setHigh(new BigDecimal(response.getHigh()));
        stockEntity.setLow(new BigDecimal(response.getLow()));
        stockEntity.setClose(new BigDecimal(response.getClose()));
        stockEntity.setVolume(Long.parseLong(response.getVolume()));
        return stockEntity;
    }
}
