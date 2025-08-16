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
   private double open;
   private double high;
   private double low;
   private double close;
   private int volume;
}
