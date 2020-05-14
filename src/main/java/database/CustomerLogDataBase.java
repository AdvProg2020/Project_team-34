package database;

import account.Account;
import account.Customer;
import cart.Cart;
import log.CustomerLog;
import log.LogStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

import static database.DataBase.connect;

public class CustomerLogDataBase {
    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("identifier", "String");
        content.put("date" , "long");
        content.put("deliveryStatus", "String");
        content.put("cartId", "String");


        DataBase.createNewTable("CustomerLogs", content);
    }

    public static void add(CustomerLog customerLog) {
        if (DataBase.doesIdAlreadyExist("CustomerLogs", "identifier", customerLog.getIdentifier())) {
            return;
        }
        String sql = "INSERT into CustomerLogs (identifier, date, deliveryStatus, cartId) " +
                "VALUES (?,?, ? ,?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1,customerLog.getIdentifier());
            statement.setLong(2,customerLog.getDate().getTime());
            statement.setString(5, String.valueOf(customerLog.getDeliveryStatus()));
            statement.setString(6, customerLog.getCart().getIdentifier());

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

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            ArrayList<CustomerLog> customerLogs = new ArrayList<>();
            while (resultSet.next()) {
                String customerLogId = resultSet.getString("identifier");
                Date date = new Date(resultSet.getLong("date"));
                LogStatus deliveryStatus = LogStatus.valueOf(resultSet.getString("deliveryStatus"));
                Cart cart = Cart.getCartById(resultSet.getString("cartId"));

                customerLogs.add(new CustomerLog(customerLogId,date,deliveryStatus,cart));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }













}
