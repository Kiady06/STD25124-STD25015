package org.hei.binome.std25124std25015.controllers;

import org.hei.binome.std25124std25015.dto.BalanceDto;
import org.hei.binome.std25124std25015.services.CashFlowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    private final CashFlowService cashFlowService;

    public BalanceController(CashFlowService cashFlowService) {
        this.cashFlowService = cashFlowService;
    }

    @GetMapping
    public ResponseEntity<BalanceDto> getBalance() {
        BalanceDto balance = cashFlowService.getBalance();
        return ResponseEntity.ok(balance);
    }
}