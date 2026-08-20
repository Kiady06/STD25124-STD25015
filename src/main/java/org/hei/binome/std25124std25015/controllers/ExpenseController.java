package org.hei.binome.std25124std25015.controllers;

import org.hei.binome.std25124std25015.models.Expense;
import org.hei.binome.std25124std25015.services.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        Expense createdExpense = expenseService.createExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }

    // Gestion des erreurs de validation (ex: utilisateur inexistant ou données manquantes)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}