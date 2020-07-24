package database;

import exceptionalMassage.ExceptionalMassage;
import log.SupplierLog;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import static database.DataBase.doesIdAlreadyExist;

public class SupplierLogDataBase {
    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("identifier", "String");
        content.put("earnedMoney", "int");
        content.put("discountAmount", "int");
        content.put("totalPurchase", "int");
        content.put("date", "long");

        DataBase.createNewTable("SupplierLogs", content);
    }

    public static void add(SupplierLog supplierLog) {
        if (doesIdAlreadyExist("SupplierLogs", "identifier", supplierLog.getIdentifier())) {
            return;
        }
        String sql = "INSERT into SupplierLogs (identifier,earnedMoney, discountAmount, totalPurchase , date) " +
                "VALUES (?,?, ? ,?, ?)";
        try (PreparedStatement statement =DataBase.getConnection().prepareStatement(sql)) {

            statement.setString(1, supplierLog.getIdentifier());
            statement.setInt(2, supplierLog.getEarnedMoney());
            statement.setInt(3, supplierLog.getDiscountAmount());
            statement.setInt(4, supplierLog.getTotalPurchase());
            statement.setLong(5, supplierLog.getDate().getTime());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void update(SupplierLog supplierLog) {
        delete(supplierLog.getIdentifier());
        add(supplierLog);
    }

    public static void delete(String supplierLogId) {
        DataBase.delete("SupplierLogs", "identifier", supplierLogId);
    }

    public static ArrayList<SupplierLog> sortSupplierLog(String field , ArrayList<String> whatToShow) throws ExceptionalMassage {
        if(!field.equals("earnedMoney") && !field.equals("discountAmount") && !field.equals("totalPurchase") && !field.equals("date" ))
            throw  new ExceptionalMassage("Invalid field to sort with");
        String sql = "SELECT username FROM SupplierLogs ORDER BY " + field + " ASC;";
        ArrayList<SupplierLog> result = new ArrayList<>();
        try (Statement statement = DataBase.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            String identifier ;
            while (resultSet.next()) {
                identifier = resultSet.getString("identifier");
                if(whatToShow.contains(identifier))
                    result.add(SupplierLog.getSupplierLogById(identifier));
            }
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
