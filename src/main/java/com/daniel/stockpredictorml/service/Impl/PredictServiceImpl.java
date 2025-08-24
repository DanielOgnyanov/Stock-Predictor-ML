package com.daniel.stockpredictorml.service.Impl;


import com.daniel.stockpredictorml.models.dto.PredictRequestDTO;
import com.daniel.stockpredictorml.models.dto.PredictResponseDTO;
import com.daniel.stockpredictorml.models.entities.PredictEntity;
import com.daniel.stockpredictorml.models.entities.StockEntity;
import com.daniel.stockpredictorml.repository.PredictRepository;
import com.daniel.stockpredictorml.repository.StockRepository;
import com.daniel.stockpredictorml.service.PredictService;
import com.daniel.stockpredictorml.service.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class PredictServiceImpl implements PredictService {

    private static final Logger logger = LoggerFactory.getLogger(PredictServiceImpl.class);

    private final StockRepository stockRepository;
    private final PredictRepository predictRepository;
    private final RestTemplate restTemplate;

    public PredictServiceImpl(StockRepository stockRepository, PredictRepository predictRepository, RestTemplate restTemplate) {
        this.stockRepository = stockRepository;
        this.predictRepository = predictRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public void predictAndSave(String symbol) {
        Optional<StockEntity> stockOpt = stockRepository.findTopBySymbolOrderByDateTimeDesc(symbol);

        if (stockOpt.isEmpty()) {
            logger.warn("Stock with symbol {} does not exist. Nothing sent to Python service.", symbol);
            return;
        }

        StockEntity stock = stockOpt.get();


        PredictRequestDTO stockRequest = new PredictRequestDTO(
                stock.getSymbol(),
                stock.getOpen(),
                stock.getHigh(),
                stock.getLow(),
                stock.getClose(),
                stock.getVolume(),
                stock.getCurrency(),
                stock.getName()
        );

        List<PredictResponseDTO> responseList = restTemplate.postForObject(
                "http://127.0.0.1:5000/predict",
                List.of(stockRequest),
                List.class
        );

        if (responseList == null || responseList.isEmpty()) {
            logger.warn("No prediction returned from Python service for symbol {}", symbol);
            return;
        }

        for (Object obj : responseList) {

            Map<String, Object> map = (Map<String, Object>) obj;

            PredictEntity prediction = new PredictEntity();
            prediction.setSymbol((String) map.get("symbol"));
            prediction.setPredictedClose(((BigDecimal) map.get("predicted_close")));

            predictRepository.save(prediction);
            logger.info("Prediction saved for symbol {} with predicted_close {}", prediction.getSymbol(), prediction.getPredictedClose());
        }
    }
}
