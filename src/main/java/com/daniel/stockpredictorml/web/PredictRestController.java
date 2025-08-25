package com.daniel.stockpredictorml.web;


import com.daniel.stockpredictorml.models.dto.PredictResponseDTO;
import com.daniel.stockpredictorml.service.PredictService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/predict")
public class PredictRestController {

    private final PredictService predictService;

    public PredictRestController(PredictService predictService) {
        this.predictService = predictService;
    }

    @GetMapping("/{symbol}/latest")
    public ResponseEntity<PredictResponseDTO> getLatestPrediction(@PathVariable String symbol) {
        return predictService.getLatestPrediction(symbol)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
