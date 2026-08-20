package org.hei.binome.std25124std25015.models;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class ClashFlow {
    private String id;
    private Instant createdAt;
    private BigDecimal amount;
    private User user;
}
