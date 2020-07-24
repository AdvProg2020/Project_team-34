package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * @author rpirayadi
 * @since 0.0.3
 */
public class WageDataBase {
    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("wage" ,"int");
        content.put("minimum" , "int");

        DataBase.createNewTable("Wage", content);
    }

    public static void update(int wage , int minimum ) {
        delete();
        String sql = "INSERT into Wage (wage , minimum) " +
                "VALUES (?,?)";
        try (PreparedStatement statement = DataBase.getConnection().prepareStatement(sql)) {

            statement.setInt(1, wage);
            statement.setInt(2, minimum);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void delete() {
        DataBase.deleteAll("Wage");
    }

    public static int getWage() {
        int wage = 0;
        String sql = "SELECT wage  FROM Wage";
        try (Statement statement = DataBase.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                wage  = resultSet.getInt("wage");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return wage;
    }

    public static int getMinimum() {
        int minimum = 0;
        String sql = "SELECT minimum FROM Wage";
        try (Statement statement = DataBase.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                minimum  = resultSet.getInt("minimum");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return minimum;
    }
}
