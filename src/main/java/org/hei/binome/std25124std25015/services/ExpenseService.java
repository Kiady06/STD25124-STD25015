package org.hei.binome.std25124std25015.services;

import org.hei.binome.std25124std25015.models.Expense;
import org.hei.binome.std25124std25015.repositories.ExpenseRepository;
import org.hei.binome.std25124std25015.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public Expense createExpense(Expense expense) {
        // 1. Vérification que l'utilisateur associé existe
        if (expense.getUser() == null || expense.getUser().getId() == null) {
            throw new IllegalArgumentException("L'identifiant de l'utilisateur est obligatoire.");
        }

        boolean userExists = userRepository.existsById(expense.getUser().getId());
        if (!userExists) {
            throw new IllegalArgumentException("L'utilisateur avec l'ID " + expense.getUser().getId() + " n'existe pas.");
        }

        // 2. Génération de l'ID si non renseigné (limité à 5 caractères selon votre schéma VARCHAR(5))
        if (expense.getId() == null || expense.getId().trim().isEmpty()) {
            expense.setId(UUID.randomUUID().toString().substring(0, 5));
        }

        // 3. Définition de la date de création si elle n'est pas fournie
        if (expense.getCreatedAt() == null) {
            expense.setCreatedAt(Instant.now());
        }

        return expenseRepository.save(expense);
    }
}