package com.daniel.stockpredictorml.repository;




import com.daniel.stockpredictorml.models.entities.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository  extends JpaRepository<StockEntity, Long> {

    Optional<StockEntity> findTopBySymbolOrderByUpdatedAtDesc(String symbol);

    @Query("""
    SELECT s FROM StockEntity s
    WHERE s.updatedAt = (
        SELECT MAX(s2.updatedAt)
        FROM StockEntity s2
        WHERE s2.symbol = s.symbol)""")
    List<StockEntity> findLatestPricesPerSymbol();
}
