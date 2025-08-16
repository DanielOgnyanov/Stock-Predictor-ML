package com.daniel.stockpredictorml.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "stocks", indexes = {
        @Index(name = "idx_symbol", columnList = "symbol")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockEntity extends BaseEntity {

    @NotNull(message = "Symbol is required")
    @Size(max = 10, message = "Symbol max length is 10")
    @Column(name = "symbol", nullable = false, length = 10)
    private String symbol;

    @Size(max = 100, message = "Name max length is 100")
    @Column(name = "name")
    private String name;

    @Size(max = 5, message = "Currency max length is 5")
    @Column(name = "currency")
    private String currency;

    @DecimalMin(value = "0.0", inclusive = false, message = "Open price must be positive")
    @Column(name = "open", precision = 19, scale = 6)
    private double open;

    @DecimalMin(value = "0.0", inclusive = false, message = "High price must be positive")
    @Column(name = "high", precision = 19, scale = 6)
    private double high;

    @DecimalMin(value = "0.0", inclusive = false, message = "Low price must be positive")
    @Column(name = "low", precision = 19, scale = 6)
    private double low;

    @DecimalMin(value = "0.0", inclusive = false, message = "Close price must be positive")
    @Column(name = "close", precision = 19, scale = 6)
    private double close;

    @PositiveOrZero(message = "Volume cannot be negative")
    @Column(name = "volume")
    private int volume;
}
