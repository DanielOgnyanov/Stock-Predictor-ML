package models.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull
    @Size(max = 10)
    @Column(name = "symbol", nullable = false, length = 10)
    private String symbol;

    @Size(max = 100)
    @Column(name = "name")
    private String name;

    @Size(max = 10)
    @Column(name = "exchange")
    private String exchange;

    @Size(max = 10)
    @Column(name = "mic_code")
    private String micCode;

    @Size(max = 5)
    @Column(name = "currency")
    private String currency;

    @Column(name = "datetime", nullable = false)
    private Instant datetime;

    @Column(name = "last_quote_at")
    private Instant lastQuoteAt;

    @Column(name = "open", precision = 19, scale = 6)
    private BigDecimal open;

    @Column(name = "high", precision = 19, scale = 6)
    private BigDecimal high;

    @Column(name = "low", precision = 19, scale = 6)
    private BigDecimal low;

    @Column(name = "close", precision = 19, scale = 6)
    private BigDecimal close;

    @Column(name = "volume")
    private Long volume;

    @Column(name = "previous_close", precision = 19, scale = 6)
    private BigDecimal previousClose;

    @Column(name = "change_value", precision = 19, scale = 6)
    private BigDecimal changeValue;

    @Column(name = "percent_change", precision = 8, scale = 4)
    private BigDecimal percentChange;

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
