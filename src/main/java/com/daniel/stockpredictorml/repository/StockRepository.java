package com.daniel.stockpredictorml.repository;




import com.daniel.stockpredictorml.models.entities.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
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


    @Query(value = """
    SELECT 
        s.symbol,
        JSON_ARRAYAGG(
            JSON_OBJECT(
                'date', s.created_at,
                'open', s.open
            )
        ) AS open_prices
    FROM (
        SELECT symbol, open, created_at
        FROM stocks
        ORDER BY symbol, created_at
    ) AS s
    GROUP BY s.symbol
    ORDER BY s.symbol;
    """, nativeQuery = true)
    List<Map<String, Object>> findAllSymbolsWithOpenPriceHistory();

}
