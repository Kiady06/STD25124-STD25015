package org.hei.binome.std25124std25015.repositories;

import org.hei.binome.std25124std25015.config.DatabaseConnection;
import org.hei.binome.std25124std25015.models.CashFlow;
import org.hei.binome.std25124std25015.models.User;
import org.hei.binome.std25124std25015.models.Donation;
import org.hei.binome.std25124std25015.models.Expense;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class cashFlowRepository {
    private final DatabaseConnection databaseConnection;

    public cashFlowRepository(DatabaseConnection databaseConnection) {
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
}
