package ru.netology.database;

import lombok.val;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseInfo {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
//        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/app", "app", "pass");
    }

    public static String getPaymentId() throws SQLException {
        String paymentId = null;
        val idSQL = "select payment_id from order_entity order by created desc limit 1 ";
        try (val conn = getConnection();
             val statusStatement = conn.prepareStatement(idSQL)) {
            try (val rs = statusStatement.executeQuery()) {
                if (rs.next()) {
                    paymentId = rs.getString("payment_id");

                }
            }
        }
        return paymentId;
    }

    public static String getStatusFromCredit(String paymentId) throws SQLException {
        String status = null;
        val statusSQL = "select status from credit_request_entity where bank_id =? ";
        try (val conn = getConnection();
             val statusStatement = conn.prepareStatement(statusSQL)) {
            statusStatement.setString(1, paymentId);
            try (val rs = statusStatement.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("status");
                }
            }
        }
        return status;
    }

    public static String getStatusFromDebit(String paymentId) throws SQLException {
        String status = null;
        val statusSQL = "select status from payment_entity where transaction_id =?";
        try (val conn = getConnection();
             val statusStatement = conn.prepareStatement(statusSQL)) {
            statusStatement.setString(1, paymentId);
            try (val rs = statusStatement.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("status");
                }
            }
        }
        return status;
    }

    public static String getAmount(String paymentId) throws SQLException {
        String amount = null;
        val amountSQL = "select amount from payment_entity where transaction_id =?";
        try (val conn = getConnection();
             val amountStatement = conn.prepareStatement(amountSQL)) {
            amountStatement.setString(1, paymentId);
            try (val rs = amountStatement.executeQuery()) {
                if (rs.next()) {
                    amount = rs.getString("amount");
                }
            }
        }
        return amount;
    }

    public static void cleanDB() {
        val deleteOrdersEntity = "delete from order_entity;";
        val deleteCreditRequests = "delete from credit_request_entity;";
        val deletePaymentRequests = "delete from payment_entity;";

        try (val conn = getConnection();
             val deleteOrdersSt = conn.createStatement();
             val deleteCreditStmt = conn.createStatement();
             val deletePaymentStmt = conn.createStatement()) {
            deleteOrdersSt.executeUpdate(deleteOrdersEntity);
            deleteCreditStmt.executeUpdate(deleteCreditRequests);
            deletePaymentStmt.executeUpdate(deletePaymentRequests);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
