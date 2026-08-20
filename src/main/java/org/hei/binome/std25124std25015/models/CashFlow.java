package org.hei.binome.std25124std25015.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@SuperBuilder
public class CashFlow {
    private String id;
    private Instant createdAt;
    private BigDecimal amount;
    private User user;

    public CashFlow(String id, Instant createdAt, BigDecimal amount, User user) {
        this.id = id;
        this.createdAt = createdAt;
        this.amount = amount;
        this.user = user;
    }
}