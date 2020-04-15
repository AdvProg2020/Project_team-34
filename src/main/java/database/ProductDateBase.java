package database;

import java.sql.*;

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

    public void add(int id, String name) {
        String sql = "INSERT into Students (id, name) VALUES (?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(2, name);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
