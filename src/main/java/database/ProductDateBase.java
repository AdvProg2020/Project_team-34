package database;

import product.Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductDateBase {

    public static void createNewTable() {
        String url = "jdbc:sqlite:.\\src\\main\\java\\recourses";

        String sql = "CREATE TABLE IF NOT EXISTS Products (\n"
                + "	numberOfViews int,\n"
                + "	productId String, \n"
                +  "name String, \n"
                +  "nameOfCompany String, \n"
                + "	price int, \n"
                + "	remainedNumber int , \n"
                + "	description String \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        String url = "jdbc:sqlite:.\\src\\main\\java\\recourses";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public void add(Product product) {
        String sql = "INSERT into Students (numbeerOfViews,productId , name, nameOfCompany, price, remainedNumbers, description)" +
                " VALUES (?, ? , ? , ? , ?, ? ,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1,product.getNumberOfViews());
            pstmt.setString(2, product.getProductId());
            pstmt.setString(3, product.getName());
            pstmt.setString(4, product.getNameOfCompany());
            pstmt.setInt(5, product.getPrice());
            pstmt.setInt(6, product.getRemainedNumber());
            pstmt.setString(7, product.getDescription());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Product product){

    }

    public ArrayList<Product> getAllProducts(){
        return null;
    }

    public ArrayList<Product> getAllFilteredProducts(){
        return null;
    }


}
