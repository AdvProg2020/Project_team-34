package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DataBase {

    private  final static String url = "jdbc:sqlite:.\\src\\main\\java\\DataBase.db";

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void createNewTable(String nameOfTable, HashMap<String,String> content) {

        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS");
        sql.append(nameOfTable).append("(\n");
        for (String columnName : content.keySet()) {
            sql.append(columnName).append(" ").append(content.get(columnName)).append(",\n");
        }
        sql.delete(sql.length()-2,sql.length());
        sql.append(" \n )");
        System.out.println(sql);
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(String.valueOf(sql));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
