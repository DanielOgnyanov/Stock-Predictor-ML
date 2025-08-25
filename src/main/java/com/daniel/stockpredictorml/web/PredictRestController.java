package com.daniel.stockpredictorml.web;


import com.daniel.stockpredictorml.models.dto.PredictResponseDTO;
import com.daniel.stockpredictorml.service.PredictService;
import com.daniel.stockpredictorml.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/predict")
public class PredictRestController {

    private final PredictService predictService;
    private final StockService stockService;

    public PredictRestController(PredictService predictService, StockService stockService) {
        this.predictService = predictService;
        this.stockService = stockService;
    }

    @PostMapping("/new")
    public ResponseEntity<String> predictAndSave(@RequestParam String symbol) {
        try {
            predictService.predictAndSave(symbol);
            return ResponseEntity.ok("Prediction requested and saved for symbol: " + symbol);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing prediction for symbol: " + symbol);
        }
    }


    @GetMapping("/{symbol}/latest")
    public ResponseEntity<PredictResponseDTO> getLatestPrediction(@PathVariable String symbol) {
        return predictService.getLatestPrediction(symbol)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
