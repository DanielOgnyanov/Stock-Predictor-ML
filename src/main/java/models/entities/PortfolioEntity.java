package models.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "portfolios", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioEntity extends BaseEntity{


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @NotNull
    @Column(name = "symbol", nullable = false, length = 10)
    private String symbol;

    @NotNull
    @Min(value = 0, message = "Quantity must be non-negative")
    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @NotNull
    @Min(value = 0, message = "Average price must be non-negative")
    @Column(name = "average_price", nullable = false, precision = 19, scale = 6)
    private BigDecimal averagePrice;

    @Column(name = "last_updated")
    private Instant lastUpdated;
}
