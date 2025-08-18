package com.daniel.stockpredictorml.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockQuoteResponseDTO {

   private String symbol;
   private String name;
   private String currency;
   private BigDecimal open;
   private BigDecimal high;
   private BigDecimal low;
   private BigDecimal close;
   private int volume;
}
