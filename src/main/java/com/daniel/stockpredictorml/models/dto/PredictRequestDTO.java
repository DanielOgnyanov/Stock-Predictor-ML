package com.daniel.stockpredictorml.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictRequestDTO {
    private String symbol;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Long volume;
    private String currency;
    private String name;
}
