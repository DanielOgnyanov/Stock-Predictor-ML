package com.daniel.stockpredictorml.models.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictResponseDTO {
    private String symbol;
    private BigDecimal predicted_close;
}
