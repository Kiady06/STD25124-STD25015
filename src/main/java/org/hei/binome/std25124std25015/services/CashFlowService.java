package org.hei.binome.std25124std25015.services;

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
}