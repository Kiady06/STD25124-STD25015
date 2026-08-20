package org.hei.binome.std25124std25015.repositories;

import org.hei.binome.std25124std25015.config.DatabaseConnection;
import org.hei.binome.std25124std25015.models.Expense;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Repository
public class ExpenseRepository {

    private final DatabaseConnection databaseConnection;

    public ExpenseRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Expense save(Expense expense) {
        String sql = """
            INSERT INTO expense (id, created_at, amount, id_user, reason, frequency)
            VALUES (?, ?, ?, ?, ?, ?::expense_frequency)
        """;

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, expense.getId());
            preparedStatement.setTimestamp(2, Timestamp.from(expense.getCreatedAt()));
            preparedStatement.setBigDecimal(3, expense.getAmount());
            preparedStatement.setString(4, expense.getUser().getId());
            preparedStatement.setString(5, expense.getReason());
            preparedStatement.setString(6, expense.getFrequency() != null ? expense.getFrequency().name() : null);

            preparedStatement.executeUpdate();
            return expense;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création de la dépense", e);
        }
    }
}