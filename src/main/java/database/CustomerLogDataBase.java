package database;

import account.Account;
import cart.Cart;
import exceptionalMassage.ExceptionalMassage;
import log.CustomerLog;
import log.LogStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;


/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class CustomerLogDataBase {
    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("identifier", "String");
        content.put("date" , "long");
        content.put("deliveryStatus", "String");
        content.put("cartId", "String");
        content.put("amount", "int");
        content.put("isAuction", "boolean");


        DataBase.createNewTable("CustomerLogs", content);
    }

    public static void add(CustomerLog customerLog) {
        if (DataBase.doesIdAlreadyExist("CustomerLogs", "identifier", customerLog.getIdentifier())) {
            return;
        }
        String sql = "INSERT into CustomerLogs (identifier, date, deliveryStatus, cartId,amount, isAuction) " +
                "VALUES (?,?, ? ,?, ?, ?)";
        try (PreparedStatement statement = DataBase.getConnection().prepareStatement(sql)) {

            statement.setString(1,customerLog.getIdentifier());
            statement.setLong(2,customerLog.getDate().getTime());
            statement.setString(3, String.valueOf(customerLog.getDeliveryStatus()));
            statement.setString(4, customerLog.getCart().getIdentifier());
            statement.setInt(5,customerLog.getCart().getBill());
            statement.setBoolean(6, customerLog.isAuction());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void update(CustomerLog customerLog) {
        delete(customerLog.getIdentifier());
        add(customerLog);
    }

    public static void delete(String customerLogId) {
        DataBase.delete("CustomerLogs", "identifier",customerLogId);
    }

    public static void importAllCustomerLogs() {
        String sql = "SELECT *  FROM CustomerLogs";

        try (Statement statement = DataBase.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String customerLogId = resultSet.getString("identifier");
                Date date = new Date(resultSet.getLong("date"));
                LogStatus deliveryStatus = LogStatus.valueOf(resultSet.getString("deliveryStatus"));
                Cart cart = Cart.getCartById(resultSet.getString("cartId"));
                boolean isAuction = resultSet.getBoolean("isAuction");
                new CustomerLog(customerLogId,date,deliveryStatus,cart, isAuction);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static ArrayList<CustomerLog> sortCustomerLog(String field , ArrayList<String> whatToShow) throws ExceptionalMassage {
        if(!field.equals("date") && !field.equals("amount"))
            throw  new ExceptionalMassage("Invalid field to sort with");
        String sql = "SELECT identifier FROM CustomerLogs ORDER BY " + field + " ASC;";
        ArrayList<CustomerLog> result = new ArrayList<>();
        try (Statement statement = DataBase.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            String identifier ;
            while (resultSet.next()) {
                identifier = resultSet.getString("identifier");
                if(whatToShow.contains(identifier))
                    result.add(CustomerLog.getCustomerLogById(identifier));
            }
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }












}
