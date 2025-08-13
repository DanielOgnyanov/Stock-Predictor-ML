package com.daniel.stockpredictorml.models.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "predict", indexes = {
        @Index(name = "idx_symbol_prediction_date", columnList = "symbol, prediction_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PredictEntity extends BaseEntity{


    @Column(nullable = false, length = 10)
    @NotBlank(message = "Symbol must not be blank")
    @Size(max = 10, message = "Symbol length must be at most 10 characters")
    private String symbol;

    @Column(nullable = false, precision = 19, scale = 4)
    @NotNull(message = "Predicted price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Predicted price must be positive")
    private BigDecimal predictedPrice;

    @Column(name = "signal_code", length = 10)
    @Size(max = 10, message = "Signal length must be at most 10 characters")
    private String signal;

    @Column(precision = 5, scale = 4)
    @DecimalMin(value = "0.0", inclusive = true, message = "Confidence must be at least 0")
    @DecimalMax(value = "1.0", inclusive = true, message = "Confidence cannot be greater than 1")
    private BigDecimal confidence;

    @Column(name = "prediction_date", nullable = false)
    @NotNull(message = "Prediction date is required")
    private Instant predictionDate;
}
