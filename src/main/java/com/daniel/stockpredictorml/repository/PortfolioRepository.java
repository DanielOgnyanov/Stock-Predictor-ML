package com.daniel.stockpredictorml.repository;



import com.daniel.stockpredictorml.models.entities.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Long> {
}
