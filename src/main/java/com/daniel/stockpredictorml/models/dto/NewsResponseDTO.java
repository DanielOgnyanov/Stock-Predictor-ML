package com.daniel.stockpredictorml.models.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsResponseDTO {
    private String title;
    private String description;
    private String snippet;
    private String symbol;

}
