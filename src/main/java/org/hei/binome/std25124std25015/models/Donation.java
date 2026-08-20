package org.hei.binome.std25124std25015.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Donation extends CashFlow {
    private String comment;

    public Donation(String id, Instant createdAt, BigDecimal amount, User user, String comment) {
        super(id, createdAt, amount, user);
        this.comment = comment;
    }
}