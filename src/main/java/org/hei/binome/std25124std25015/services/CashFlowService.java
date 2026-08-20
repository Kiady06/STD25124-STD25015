package org.hei.binome.std25124std25015.services;

import org.hei.binome.std25124std25015.dto.BalanceDto;
import org.hei.binome.std25124std25015.models.CashFlow;
import org.hei.binome.std25124std25015.repositories.CashFlowRepository;
import org.hei.binome.std25124std25015.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CashFlowService {

    private final CashFlowRepository cashFlowRepository;
    private final UserRepository userRepository;

    public CashFlowService(CashFlowRepository cashFlowRepository, UserRepository userRepository) {
        this.cashFlowRepository = cashFlowRepository;
        this.userRepository = userRepository;
    }

    public List<CashFlow> getCashFlowsByUserId(String userId) {
        boolean userExists = userRepository.existsById(userId);

        if (!userExists) {
            throw new IllegalArgumentException("L'utilisateur avec l'ID " + userId + " n'existe pas.");
        }

        return cashFlowRepository.getByUser(userId);
    }

    public List<CashFlow> getAllCashFlows(String type) {
        if (type != null && !type.trim().isEmpty()) {
            String cleanType = type.trim().toLowerCase();
            if (!cleanType.equals("donation") && !cleanType.equals("expense")) {
                throw new IllegalArgumentException("Le type de cashflow invalide. Valeurs acceptées : 'donation' ou 'expense'.");
            }
        }

        return cashFlowRepository.findAll(type);
    }

    public BalanceDto getBalance() {
        return cashFlowRepository.getBalance();
    }
}