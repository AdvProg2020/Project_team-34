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
    /*

    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("identifier", "String");
        content.put("earnedMoney", "int");
        content.put("discountAmount", "int");
        content.put("totalPurchase", "int");

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

            statement.setString(1, supplierLog.getIdentifier());
            statement.setInt(2, supplierLog.getEarnedMoney());
            statement.setInt(3, supplierLog.getDiscountAmount());
            statement.setInt(4, supplierLog.getTotalPurchase());
            statement.setString(5, supplierLog.getCustomerLog().getIdentifier());
            statement.setString(6, supplierLog.getSupplier().getUserName());

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
        DataBase.delete("SupplierLogs", "identifier", supplierLogId);
    }

     */
}
