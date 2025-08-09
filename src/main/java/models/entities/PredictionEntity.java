package models.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "predictions", indexes = {
        @Index(name = "idx_symbol_prediction_date", columnList = "symbol, prediction_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PredictionEntity extends BaseEntity{


    @Column(nullable = false, length = 10)
    private String symbol;


    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal predictedPrice;


    @Column(length = 10)
    private String signal;


    @Column(precision = 5, scale = 4)
    private BigDecimal confidence;


    @Column(name = "prediction_date", nullable = false)
    private Instant predictionDate;
}
