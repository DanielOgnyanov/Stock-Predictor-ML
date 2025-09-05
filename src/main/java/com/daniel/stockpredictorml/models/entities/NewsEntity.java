package com.daniel.stockpredictorml.models.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "news_articles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NewsEntity extends BaseEntity{

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    @Column(nullable = false, length = 255)
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Column(nullable = false)
    private String description;

    @Size(max = 500, message = "Snippet cannot exceed 500 characters")
    @Column(length = 500)
    private String snippet;

    @NotBlank(message = "Symbol is required")
    @Size(max = 20, message = "Symbol cannot exceed 20 characters")
    @Column(nullable = false, length = 20)
    private String symbol;

}
