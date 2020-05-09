package database;

import discount.Sale;
import product.Product;
import state.State;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static database.DataBase.*;

public class SaleDataBase {
    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("start" ,"Date");
        content.put("end" , "Date");
        content.put("percent","int");
        content.put("offId" , "String");
        content.put("listOfProductIds" , "String");
        content.put("state" , "String");

        DataBase.createNewTable("Sales", content);
    }

    public static void add(Sale sale) {
        if (doesScoreAlreadyExists(sale)) {
            return;
        }
        String sql = "INSERT into Sales (start , end , percent, offId , listOfProductIds, state) " +
                "VALUES (?,?, ? , ? , ? , ?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1,String.valueOf(sale.getStart()));
            statement.setString(2,String.valueOf(sale.getEnd()));
            statement.setInt(3, sale.getPercent());
            statement.setString(4, sale.getOffId());
            statement.setString(5, convertObjectToJsonString(convertProductArrayListToStringArrayList(sale.getProducts())));
            statement.setString(6, String.valueOf(sale.getState()));

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean doesScoreAlreadyExists(Sale sale) {
        ArrayList<Sale> list = getAllSales();
        if (list == null)
            return false;
        for (Sale eachSale : list) {
            if (eachSale.getOffId().equals(sale.getOffId()))
                return true;
        }
        return false;
    }

    public static void update(Sale sale) {
        delete(sale.getOffId());
        add(sale);
    }

    public static void delete(String offId) {
        DataBase.delete("Sales" , "offId",offId);
    }

    public static ArrayList<Sale> getAllSales() {
        String sql = "SELECT *  FROM Sales";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            ArrayList<Sale> sales = new ArrayList<>();
            while (resultSet.next()) {
                Date start = Date.valueOf(resultSet.getString("start"));
                Date end = Date.valueOf(resultSet.getString("end"));
                int percent = resultSet.getInt("percent");
                String offId = resultSet.getString("OffId");
                ArrayList<Product> products = convertStringArrayListToProductArrayList(convertJsonToArrayList(resultSet.getString("listOfProductIds")));
                State state = State.valueOf(resultSet.getString("state"));


                sales.add(new Sale(start,end,percent,offId,products, state));

            }
            return sales;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
