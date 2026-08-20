package org.hei.binome.std25124std25015.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Expense {
    public enum ExpenseFrequency{
        NONE,
        MONTHLY,
        WEEKLY,
        YEARLY
    }

    private String reason;
    private ExpenseFrequency frequency;
}
