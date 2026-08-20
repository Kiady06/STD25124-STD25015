package org.hei.binome.std25124std25015.repositories;

import org.hei.binome.std25124std25015.config.DatabaseConnection;
import org.hei.binome.std25124std25015.dto.BalanceDto;
import org.hei.binome.std25124std25015.models.CashFlow;
import org.hei.binome.std25124std25015.models.User;
import org.hei.binome.std25124std25015.models.Donation;
import org.hei.binome.std25124std25015.models.Expense;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CashFlowRepository {
    private final DatabaseConnection databaseConnection;

    public CashFlowRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public List<CashFlow> getByUser(String userId) {
        List<CashFlow> cashFlows = new ArrayList<>();

        String sql = """
            SELECT c.id, c.created_at, c.amount, c.id_user,
                   u.ref, u.first_name, u.last_name, u.email, u.phone,
                   d.comment,
                   e.reason, e.frequency
            FROM cashflow c
            INNER JOIN "user" u ON c.id_user = u.id
            LEFT JOIN donation d ON c.id = d.id
            LEFT JOIN expense e ON c.id = e.id
            WHERE c.id_user = ?
            ORDER BY c.created_at DESC
        """;

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    cashFlows.add(mapResultSetToCashFlow(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des cashflows pour l'utilisateur: " + userId, e);
        }

        return cashFlows;
    }

    private CashFlow mapResultSetToCashFlow(ResultSet rs) throws SQLException {
        User user = User.builder()
                .id(rs.getString("id_user"))
                .ref(rs.getString("ref"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .email(rs.getString("email"))
                .phone(rs.getString("phone"))
                .build();

        String comment = rs.getString("comment");
        String reason = rs.getString("reason");

        if (comment != null) {
            return new Donation(
                    rs.getString("id"),
                    rs.getTimestamp("created_at").toInstant(),
                    rs.getBigDecimal("amount"),
                    user,
                    comment
            );
        }
        else if (reason != null) {
            String frequencyStr = rs.getString("frequency");
            Expense.ExpenseFrequency frequency = frequencyStr != null
                    ? Expense.ExpenseFrequency.valueOf(frequencyStr)
                    : null;

            return new Expense(
                    rs.getString("id"),
                    rs.getTimestamp("created_at").toInstant(),
                    rs.getBigDecimal("amount"),
                    user,
                    reason,
                    frequency
            );
        }
        else {
            return new CashFlow(
                    rs.getString("id"),
                    rs.getTimestamp("created_at").toInstant(),
                    rs.getBigDecimal("amount"),
                    user
            );
        }

    }
    public List<CashFlow> findAll(String type) {
        List<CashFlow> cashFlows = new ArrayList<>();

        String sql = """
        SELECT c.id, c.created_at, c.amount, c.id_user,
               u.ref, u.first_name, u.last_name, u.email, u.phone,
               d.comment,
               e.reason, e.frequency
        FROM cashflow c
        INNER JOIN "user" u ON c.id_user = u.id
        LEFT JOIN donation d ON c.id = d.id
        LEFT JOIN expense e ON c.id = e.id
        WHERE (? IS NULL)\s
           OR (? = 'donation' AND d.id IS NOT NULL)\s
           OR (? = 'expense' AND e.id IS NOT NULL)
        ORDER BY c.created_at DESC
   \s""";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            String cleanType = (type != null && !type.trim().isEmpty()) ? type.trim().toLowerCase() : null;

            preparedStatement.setString(1, cleanType);
            preparedStatement.setString(2, cleanType);
            preparedStatement.setString(3, cleanType);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    cashFlows.add(mapResultSetToCashFlow(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des cashflows", e);
        }

        return cashFlows;
    }

    public BalanceDto getBalance() {
        String sql = """
        SELECT 
            COALESCE(SUM(CASE WHEN d.id IS NOT NULL THEN c.amount ELSE 0 END), 0) AS total_inflow,
            COALESCE(SUM(CASE WHEN e.id IS NOT NULL THEN c.amount ELSE 0 END), 0) AS total_outflow
        FROM cashflow c
        LEFT JOIN donation d ON c.id = d.id
        LEFT JOIN expense e ON c.id = e.id
    """;

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                BigDecimal totalInflow = resultSet.getBigDecimal("total_inflow");
                BigDecimal totalOutflow = resultSet.getBigDecimal("total_outflow");
                BigDecimal balance = totalInflow.subtract(totalOutflow);

                return BalanceDto.builder()
                        .totalInflow(totalInflow)
                        .totalOutflow(totalOutflow)
                        .balance(balance)
                        .build();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du calcul de la solde", e);
        }

        return new BalanceDto(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
