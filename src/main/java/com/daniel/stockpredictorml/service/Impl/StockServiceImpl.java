package com.daniel.stockpredictorml.service.Impl;

import com.daniel.stockpredictorml.models.dto.StockQuoteResponseDTO;
import com.daniel.stockpredictorml.models.entities.StockEntity;
import com.daniel.stockpredictorml.models.enums.TopStockSymbol;
import com.daniel.stockpredictorml.repository.StockRepository;
import com.daniel.stockpredictorml.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final RestTemplate restTemplate = new RestTemplate();


    private static final String API_KEY = "b78dab5f38a54cfcae5ee920eeeda8ed";
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

    @Override
    public Optional<StockEntity> getLatestStockBySymbol(String symbol) {


        return  stockRepository.findTopBySymbolOrderByUpdatedAtDesc(symbol);
    }

    private StockEntity mapToEntity(StockQuoteResponseDTO response) {
        return StockEntity.builder()
                .symbol(response.getSymbol())
                .name(response.getName())
                .currency(response.getCurrency())
                .open(response.getOpen())
                .high(response.getHigh())
                .low(response.getLow())
                .close(response.getClose())
                .volume(response.getVolume())

                .build();
    }





}
