package org.hei.binome.std25124std25015.dto;

import lombok.Getter;
import lombok.Setter;
import org.hei.binome.std25124std25015.models.User;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter

public class Donnationdto {
    private String id;
    private Instant createdAt;
    private BigDecimal amount;
    private String comment;
}
