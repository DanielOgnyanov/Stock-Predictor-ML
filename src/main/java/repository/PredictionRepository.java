package repository;


import models.entities.PredictionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<PredictionEntity, Long> {
}
