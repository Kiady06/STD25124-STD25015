package org.hei.binome.std25124std25015.controllers;

import org.hei.binome.std25124std25015.models.CashFlow;
import org.hei.binome.std25124std25015.services.CashFlowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cash-flows")
public class CashFlowController {

    private final CashFlowService cashFlowService;

    public CashFlowController(CashFlowService cashFlowService) {
        this.cashFlowService = cashFlowService;
    }

    @GetMapping
    public ResponseEntity<List<CashFlow>> getCashFlows(
            @RequestParam(name = "type", required = false) String type) {

        List<CashFlow> cashFlows = cashFlowService.getAllCashFlows(type);
        return ResponseEntity.ok(cashFlows);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleInvalidType(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}