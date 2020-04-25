package database;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.reflect.TypeToken;
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
                + " listOfSuppliersUsername String ,\n"
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
                "listOfSuppliersUsername, remainedNumber,categoryId, description,productCommentsId , specification)" +
                "VALUES (?, ? , ? , ? , ?, ? ,?, ?, ? ,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1,product.getNumberOfViews());
            statement.setString(2, product.getProductId());
            statement.setString(3,String.valueOf(product.getProductState()));
            statement.setString(4, product.getName());
            statement.setString(5, product.getNameOfCompany());
            statement.setInt(6, product.getPrice());
            statement.setString(7, convertObjectToJsonString(product.getListOfSuppliersUsername()));
            statement.setInt(8, product.getRemainedNumber());
            statement.setString(9,product.getCategoryId());
            statement.setString(10, product.getDescription());
            statement.setString(11, String.valueOf(product.getComments()));
            statement.setString(12, convertObjectToJsonString(product.getSpecification()));

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private String convertObjectToJsonString  (Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public void update(Product product){

    }

    public ArrayList<Product> getAllProducts(){
        String sql = "SELECT numberOfViews,productId ,productState, name, nameOfCompany, price," +
                "listOfSuppliersUsername, remainedNumber,categoryId, description,productCommentsId , specification  FROM Products";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet resultSet    = stmt.executeQuery(sql)){
            ArrayList<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getString("name"),resultSet.getString("nameOfCompany"),
                        resultSet.getInt("price"),convertJsonToArrayList(resultSet.getString("listOfSuppliersUsername")),
                        resultSet.getInt("remainedNumber"),resultSet.getString("categoryId"),
                        resultSet.getString("description"),convertJsonToArrayList(resultSet.getString("ProductCommentsId"))
                ,resultSet.getInt("numberOfViews"));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private ArrayList<String> convertJsonToArrayList(String string){
        Gson gson = new Gson();
        return (ArrayList<String>) gson.fromJson(string,new TypeToken<ArrayList<String>>() {}.getType());
    }

    public ArrayList<Product> getAllFilteredProducts(Gson gson){
        return null;
    }


}
