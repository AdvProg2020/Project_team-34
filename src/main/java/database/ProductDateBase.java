package database;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import product.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductDateBase {

    public static void createNewTable() {
        String url = "jdbc:sqlite:.\\src\\main\\java\\DataBase.db";

        String sql = "CREATE TABLE IF NOT EXISTS Products (\n"
                + "	numberOfViews int,\n"
                + "	productId String, \n"
                + "	productState String, \n"
                +  "name String, \n"
                +  "nameOfCompany String, \n"
                + "	price int, \n"
                + "	remainedNumber int , \n"
                + " categoryId String ,\n"
                + "	description String, \n"
                + " productCommentsId String, \n"
                + "	specification String \n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        String url = "jdbc:sqlite:.\\src\\main\\java\\DataBase.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public void add(Product product) {
        String sql = "INSERT into Products (numberOfViews,productId ,productState, name, nameOfCompany, price,"+
                "remainedNumber,categoryId, description,productCommentsId , specification)" +
                "VALUES (?, ? , ? , ? , ?, ? ,?, ?, ? ,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1,product.getNumberOfViews());
            statement.setString(2, product.getProductId());
            statement.setString(3,String.valueOf(product.getProductState()));
            statement.setString(4, product.getName());
            statement.setString(5, product.getNameOfCompany());
            statement.setInt(6, product.getPrice());
            statement.setInt(7, product.getRemainedNumber());
            statement.setString(8,product.getCategoryId());
            statement.setString(9, product.getDescription());
            statement.setString(10, String.valueOf(product.getComments()));
            statement.setString(11,convertSpecificationToJson(product.getSpecification()));

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private String convertSpecificationToJson  (HashMap<String, String> specification){
        Gson gson = new Gson();
        return gson.toJson(specification);
    }

    public void update(Product product){

    }

    public ArrayList<Product> getAllProducts(){
        return null;
    }

    public ArrayList<Product> getAllFilteredProducts(Gson gson){
        return null;
    }


}
