package database;

import cart.ShippingInfo;

import java.sql.*;
import java.util.HashMap;

import static database.DataBase.connect;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class ShippingInfoDataBase {
    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("identifier", "String");
        content.put("firstName" , "String");
        content.put("lastName", "String");
        content.put("city", "String");
        content.put("address", "String");
        content.put("postalCode", "String");
        content.put("phoneNumber", "String");

        DataBase.createNewTable("ShippingInfos", content);
    }

    public static void add(ShippingInfo shippingInfo) {
        if (DataBase.doesIdAlreadyExist("ShippingInfos", "identifier", shippingInfo.getIdentifier())) {
            return;
        }
        String sql = "INSERT into ShippingInfos (identifier, firstName, lastName , city , address , postalCode, phoneNumber) " +
                "VALUES (?,?, ? ,?,?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1,shippingInfo.getIdentifier());
            statement.setString(2,shippingInfo.getFirstName());
            statement.setString(3,shippingInfo.getLastName());
            statement.setString(4,shippingInfo.getCity());
            statement.setString(5,shippingInfo.getAddress());
            statement.setString(6,shippingInfo.getPostalCode());
            statement.setString(7,shippingInfo.getPhoneNumber());


            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void update(ShippingInfo shippingInfo) {
        delete(shippingInfo.getIdentifier());
        add(shippingInfo);
    }

    public static void delete(String identifier) {
        DataBase.delete("ShippingInfos", "identifier",identifier);
    }

    public static void importAllShippingInfos() {
        String sql = "SELECT *  FROM ShippingInfos";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                String identifier = resultSet.getString("identifier");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String city = resultSet.getString("city");
                String address = resultSet.getString("address");
                String postalCode = resultSet.getString("postalCode");
                String phoneNumber = resultSet.getString("phoneNumber");


                new ShippingInfo(identifier,firstName,lastName,city,address, postalCode,phoneNumber);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
