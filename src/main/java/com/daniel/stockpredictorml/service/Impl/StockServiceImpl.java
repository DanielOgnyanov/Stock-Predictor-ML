package com.daniel.stockpredictorml.service.impl;

import com.daniel.stockpredictorml.models.dto.StockQuoteResponseDTO;
import com.daniel.stockpredictorml.models.entities.StockEntity;
import com.daniel.stockpredictorml.models.enums.TopStockSymbol;
import com.daniel.stockpredictorml.repository.StockRepository;
import com.daniel.stockpredictorml.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final RestTemplate restTemplate = new RestTemplate();


    private static final String API_KEY = "";
    private static final String BASE_URL = "https://api.twelvedata.com/quote";

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void fetchAndStoreQuotes() {
        for (TopStockSymbol stockSymbol : TopStockSymbol.values()) {
            String symbol = stockSymbol.getSymbol();
            String url = BASE_URL + "?symbol=" + symbol + "&apikey=" + API_KEY;

            try {
                StockQuoteResponseDTO response = restTemplate.getForObject(url, StockQuoteResponseDTO.class);

                if (response != null) {
                    StockEntity stockEntity = mapToEntity(response);
                    stockRepository.save(stockEntity);
                    System.out.println("Saved: " + symbol);
                }
            } catch (Exception e) {
                System.err.println("Error fetching/saving symbol: " + symbol + " - " + e.getMessage());
            }
        }
    }

    private StockEntity mapToEntity(StockQuoteResponseDTO response) {
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
