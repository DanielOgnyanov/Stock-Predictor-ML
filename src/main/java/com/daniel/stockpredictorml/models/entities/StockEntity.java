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

@Entity
@Table(name = "stocks", indexes = {
        @Index(name = "idx_symbol", columnList = "symbol"),
        @Index(name = "idx_datetime", columnList = "datetime")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockEntity extends BaseEntity{

    @NotNull(message = "Symbol is required")
    @Size(max = 10, message = "Symbol max length is 10")
    @Column(name = "symbol", nullable = false, length = 10)
    private String symbol;

    @Size(max = 100, message = "Name max length is 100")
    @Column(name = "name")
    private String name;

    @Size(max = 10, message = "Exchange max length is 10")
    @Column(name = "exchange")
    private String exchange;

    @Size(max = 10, message = "MIC code max length is 10")
    @Column(name = "mic_code")
    private String micCode;

    @Size(max = 5, message = "Currency max length is 5")
    @Column(name = "currency")
    private String currency;

    @NotNull(message = "Datetime is required")
    @Column(name = "datetime", nullable = false)
    private Instant datetime;

    @Column(name = "last_quote_at")
    private Instant lastQuoteAt;

    @DecimalMin(value = "0.0", inclusive = false, message = "Open price must be positive")
    @Column(name = "open", precision = 19, scale = 6)
    private BigDecimal open;

    @DecimalMin(value = "0.0", inclusive = false, message = "High price must be positive")
    @Column(name = "high", precision = 19, scale = 6)
    private BigDecimal high;

    @DecimalMin(value = "0.0", inclusive = false, message = "Low price must be positive")
    @Column(name = "low", precision = 19, scale = 6)
    private BigDecimal low;

    @DecimalMin(value = "0.0", inclusive = false, message = "Close price must be positive")
    @Column(name = "close", precision = 19, scale = 6)
    private BigDecimal close;

    @PositiveOrZero(message = "Volume cannot be negative")
    @Column(name = "volume")
    private Long volume;

    @DecimalMin(value = "0.0", inclusive = false, message = "Previous close price must be positive")
    @Column(name = "previous_close", precision = 19, scale = 6)
    private BigDecimal previousClose;

    @Column(name = "change_value", precision = 19, scale = 6)
    private BigDecimal changeValue;

    @DecimalMin(value = "-100.0", message = "Percent change cannot be less than -100%")
    @DecimalMax(value = "100.0", message = "Percent change cannot be greater than 100%")
    @Column(name = "percent_change", precision = 8, scale = 4)
    private BigDecimal percentChange;

    @PositiveOrZero(message = "Average volume cannot be negative")
    @Column(name = "average_volume")
    private Long averageVolume;

    @Column(name = "is_market_open")
    private Boolean isMarketOpen;

    @Column(name = "extended_change", precision = 19, scale = 6)
    private BigDecimal extendedChange;

    @Column(name = "extended_percent_change", precision = 8, scale = 4)
    private BigDecimal extendedPercentChange;

    @Column(name = "extended_price", precision = 19, scale = 6)
    private BigDecimal extendedPrice;

    @Column(name = "extended_timestamp")
    private Instant extendedTimestamp;
}
