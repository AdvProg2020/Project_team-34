package database;


import cart.ShippingInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static database.DataBase.connect;

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
        if (doesShippingInfoAlreadyExists(shippingInfo)) {
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
    private static boolean doesShippingInfoAlreadyExists(ShippingInfo shippingInfo) {
        ArrayList<ShippingInfo> list = getAllScores();
        if (list == null)
            return false;
        for (ShippingInfo eachShippingInfo : list) {
            if (eachShippingInfo.getIdentifier().equals(shippingInfo.getIdentifier()))
                return true;
        }
        return false;
    }

    public static void update(ShippingInfo shippingInfo) {
        delete(shippingInfo.getIdentifier());
        add(shippingInfo);
    }

    public static void delete(String identifier) {
        DataBase.delete("ShippingInfos", "identifier",identifier);
    }

    public static ArrayList<ShippingInfo> getAllScores() {
        String sql = "SELECT *  FROM ShippingInfos";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            ArrayList<ShippingInfo> shippingInfos = new ArrayList<>();
            while (resultSet.next()) {
                String identifier = resultSet.getString("identifier");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String city = resultSet.getString("city");
                String address = resultSet.getString("address");
                String postalCode = resultSet.getString("postalCode");
                String phoneNumber = resultSet.getString("phoneNumber");



                shippingInfos.add(new ShippingInfo(identifier,firstName,lastName,city,address, postalCode,phoneNumber));
            }
            return shippingInfos;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
