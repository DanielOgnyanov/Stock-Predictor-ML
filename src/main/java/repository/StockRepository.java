package main.java.repository;



import main.java.models.entities.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository  extends JpaRepository<StockEntity, Long> {
}
