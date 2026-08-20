package org.hei.binome.std25124std25015.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDto {
    private BigDecimal totalInflow;
    private BigDecimal totalOutflow;
    private BigDecimal balance;
}