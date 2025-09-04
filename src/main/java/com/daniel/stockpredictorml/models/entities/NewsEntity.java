package com.daniel.stockpredictorml.models.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "news_articles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NewsEntity {

    private String title;
    private String description;
    private String snippet;
    private String symbol;

}
