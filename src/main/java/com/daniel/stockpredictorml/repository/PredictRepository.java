package com.daniel.stockpredictorml.repository;


import com.daniel.stockpredictorml.models.entities.PredictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictRepository  extends JpaRepository<PredictEntity, Long> {
}
