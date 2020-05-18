package database;

import discount.Sale;
import product.Product;
import state.State;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import static database.DataBase.*;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class SaleDataBase {
    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("start" ,"long");
        content.put("end" , "long");
        content.put("percent","int");
        content.put("offId" , "String");
        content.put("listOfProductIds" , "String");
        content.put("state" , "String");

        DataBase.createNewTable("Sales", content);
    }

    public static void add(Sale sale) {
        if (DataBase.doesIdAlreadyExist("Sales", "offId", sale.getOffId())) {
            return;
        }
        String sql = "INSERT into Sales (start , end , percent, offId , listOfProductIds, state) " +
                "VALUES (?,?, ? , ? , ? , ?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1,sale.getStart().getTime());
            statement.setLong(2,sale.getEnd().getTime());
            statement.setInt(3, sale.getPercent());
            statement.setString(4, sale.getOffId());
            statement.setString(5, convertObjectToJsonString(convertProductArrayListToStringArrayList(sale.getProducts())));
            statement.setString(6, String.valueOf(sale.getState()));

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void update(Sale sale) {
        delete(sale.getOffId());
        add(sale);
    }

    public static void delete(String offId) {
        DataBase.delete("Sales" , "offId",offId);
    }

    public static void importAllSales() {
        String sql = "SELECT *  FROM Sales";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Date start = new Date(resultSet.getLong("start"));
                Date end =  new Date(resultSet.getLong("end"));
                int percent = resultSet.getInt("percent");
                String offId = resultSet.getString("OffId");
                ArrayList<Product> products = convertStringArrayListToProductArrayList(convertJsonToArrayList(resultSet.getString("listOfProductIds")));
                State state = State.valueOf(resultSet.getString("state"));


                new Sale(start,end,percent,offId,products, state);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
