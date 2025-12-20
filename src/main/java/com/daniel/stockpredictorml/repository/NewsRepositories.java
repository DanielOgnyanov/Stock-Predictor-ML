package com.daniel.stockpredictorml.repository;


import com.daniel.stockpredictorml.models.entities.NewsEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepositories extends JpaRepository<NewsEntity, Long> {
    List<NewsEntity> findByCreatedAtAfterOrderByCreatedAtDesc(LocalDateTime createdAt);

}
