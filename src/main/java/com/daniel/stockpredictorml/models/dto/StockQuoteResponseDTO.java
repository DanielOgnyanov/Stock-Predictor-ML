package com.daniel.stockpredictorml.models.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockQuoteResponseDTO {

    private String symbol;
    private String name;
    private String exchange;
    private String micCode;
    private String currency;

    private Instant datetime;
    private Instant lastQuoteAt;

    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;

    private Long volume;
    private BigDecimal previousClose;
    private BigDecimal changeValue;
    private BigDecimal percentChange;
    private Long averageVolume;

    private Boolean isMarketOpen;

    private BigDecimal extendedChange;
    private BigDecimal extendedPercentChange;
    private BigDecimal extendedPrice;
    private Instant extendedTimestamp;


}
