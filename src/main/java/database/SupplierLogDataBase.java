package database;

import account.Account;
import account.Supplier;
import log.CustomerLog;
import log.SupplierLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static database.DataBase.connect;

public class SupplierLogDataBase {
    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("identifier", "String");
        content.put("earnedMoney", "int");
        content.put("discountAmount", "int");
        content.put("totalPurchase" , "int");
        content.put("customerLogId", "String");
        content.put("supplierUsername", "String");


        DataBase.createNewTable("SupplierLogs", content);
    }

    public static void add(SupplierLog supplierLog) {
        if (doesSupplierLogAlreadyExists(supplierLog)) {
            return;
        }
        String sql = "INSERT into SupplierLogs (identifier,earnedMoney, discountAmount, totalPurchase, customerLogId, supplierUsername) " +
                "VALUES (?,?, ? ,?, ?,?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1,supplierLog.getIdentifier());
            statement.setInt(2,supplierLog.getEarnedMoney());
            statement.setInt(3,supplierLog.getDiscountAmount());
            statement.setInt(4,supplierLog.getTotalPurchase());
            statement.setString(5, supplierLog.getCustomerLog().getIdentifier());
            statement.setString(6,supplierLog.getSupplier().getUserName() );

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean doesSupplierLogAlreadyExists(SupplierLog supplierLog) {
        ArrayList<SupplierLog> list = getAllSupplierLogs();
        if (list == null)
            return false;
        for (SupplierLog eachSupplierLog : list) {
            if (eachSupplierLog.getIdentifier().equals(supplierLog.getIdentifier()))
                return true;
        }
        return false;
    }

    public static void update(SupplierLog supplierLog) {
        delete(supplierLog.getIdentifier());
        add(supplierLog);
    }

    public static void delete(String supplierLogId) {
        DataBase.delete("SupplierLogs", "identifier",supplierLogId);
    }

    public static ArrayList <SupplierLog> getAllSupplierLogs() {
        String sql = "SELECT *  FROM SupplierLogs";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            ArrayList<SupplierLog> supplierLogs = new ArrayList<>();
            while (resultSet.next()) {
                String supplierLogId = resultSet.getString("identifier");
                int earnedMoney = resultSet.getInt("earnedMoney");
                int discountAmount = resultSet.getInt("discountAmount");
                int totalPurchase = resultSet.getInt("totalPurchase");
                CustomerLog customerLog = CustomerLog.getCustomerLogById(resultSet.getString("customerLogId"));
                Supplier supplier = (Supplier)Account.getAccountByUsername(resultSet.getString("supplierUsername"));

                supplierLogs.add(new SupplierLog(supplierLogId,earnedMoney,discountAmount,totalPurchase,customerLog,supplier));

            }
            return supplierLogs;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
