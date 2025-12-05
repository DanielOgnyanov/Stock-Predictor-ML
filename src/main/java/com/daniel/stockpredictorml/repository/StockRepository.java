package com.daniel.stockpredictorml.repository;




import com.daniel.stockpredictorml.models.entities.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface StockRepository  extends JpaRepository<StockEntity, Long> {

    Optional<StockEntity> findTopBySymbolOrderByUpdatedAtDesc(String symbol);

    List<StockEntity> findByTimestampAfterOrderByTimestampDesc(LocalDateTime timestamp);



    @Query(value = """
    SELECT s.created_at AS date, s.open AS price
    FROM stocks s
    WHERE s.symbol = :symbol
    ORDER BY s.created_at ASC, s.id ASC
    """, nativeQuery = true)
    List<Map<String, Object>> findPriceHistoryBySymbol(@Param("symbol") String symbol);



}
