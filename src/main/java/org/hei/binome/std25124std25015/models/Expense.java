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
public class Expense extends CashFlow {

    public enum ExpenseFrequency {
        NONE,
        MONTHLY,
        WEEKLY,
        YEARLY
    }

    private String reason;
    private ExpenseFrequency frequency;

    public Expense(String id, Instant createdAt, BigDecimal amount, User user, String reason, ExpenseFrequency frequency) {
        super(id, createdAt, amount, user);
        this.reason = reason;
        this.frequency = frequency;
    }
}