package com.daniel.stockpredictorml.models.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table
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
}
