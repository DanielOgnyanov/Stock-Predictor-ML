package com.daniel.stockpredictorml.service;

import com.daniel.stockpredictorml.models.dto.PredictResponseDTO;

import java.util.Optional;

public interface PredictService {

    void predictAndSave(String symbol);
    Optional<PredictResponseDTO> getLatestPrediction(String symbol);
}
