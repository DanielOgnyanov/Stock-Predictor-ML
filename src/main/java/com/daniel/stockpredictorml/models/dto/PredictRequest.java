package com.daniel.stockpredictorml.models.dto;

import lombok.Data;

@Data
public class PredictRequest {
    private String symbol;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Long volume;
    private String currency;
    private String name;
}
