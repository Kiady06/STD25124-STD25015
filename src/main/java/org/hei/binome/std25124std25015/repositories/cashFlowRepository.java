package org.hei.binome.std25124std25015.repositories;

import org.hei.binome.std25124std25015.config.DatabaseConnection;
import org.hei.binome.std25124std25015.models.CashFlow;
import org.hei.binome.std25124std25015.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class cashFlowRepository {
    private final DatabaseConnection databaseConnection;

    public cashFlowRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public List<CashFlow> findByType(String type) throws SQLException {
        if ("donation".equalsIgnoreCase(type)) {
            return findDonations();
        }
        if ("expense".equalsIgnoreCase(type)) {
            return findExpenses();
        }
        throw new IllegalArgumentException("Type inconnu: " + type);
    }

    private List<CashFlow> findDonations() throws SQLException {
        String sql = "SELECT id, created_at, amount, id_user, comment "
                + "FROM donation ORDER BY created_at DESC";

        List<CashFlow> donations = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Donation donation = new Donation();
                donation.setId(rs.getString("id"));
                donation.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                donation.setAmount(rs.getBigDecimal("amount"));
                donation.setIdUser(rs.getString("id_user"));
                donation.setComment(rs.getString("comment"));
                donations.add(donation);
            }
        }

        return donations;
    }

    private List<CashFlow> findExpenses() throws SQLException {
        String sql = "SELECT id, created_at, amount, id_user, reason, frequency "
                + "FROM expense ORDER BY created_at DESC";

        List<CashFlow> expenses = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Expense expense = new Expense();
                expense.setId(rs.getString("id"));
                expense.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                expense.setAmount(rs.getBigDecimal("amount"));
                expense.setIdUser(rs.getString("id_user"));
                expense.setReason(rs.getString("reason"));
                expense.setFrequency(ExpenseFrequency.valueOf(rs.getString("frequency")));
                expenses.add(expense);
            }
        }

        return expenses;
    }
}
