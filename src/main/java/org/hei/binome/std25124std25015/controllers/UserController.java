package org.hei.binome.std25124std25015.controllers;

import org.hei.binome.std25124std25015.models.CashFlow;
import org.hei.binome.std25124std25015.services.CashFlowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CashFlowService cashFlowService;

    public UserController(CashFlowService cashFlowService) {
        this.cashFlowService = cashFlowService;
    }

    @GetMapping("/{id}/cash-flows")
    public ResponseEntity<List<CashFlow>> getUserCashFlows(@PathVariable("id") String userId) {
        List<CashFlow> cashFlows = cashFlowService.getCashFlowsByUserId(userId);
        return ResponseEntity.ok(cashFlows);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleUserNotFound(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}