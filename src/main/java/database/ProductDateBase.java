package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductDateBase {
    public static void createNewTable() {
        String url = "jdbc:sqlite:.\\src\\main\\java\\recourses";

        String sql = "CREATE TABLE IF NOT EXISTS Products (\n"
                + "	id int,\n"
                + "	name String \n"
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
}
