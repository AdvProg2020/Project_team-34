package database;

import account.Account;
import account.Customer;
import feedback.Score;
import product.Product;

import java.sql.*;
import java.util.HashMap;

import static database.DataBase.connect;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class ScoreDataBase {

    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("scoreId", "String");
        content.put("customerUsername" , "String");
        content.put("productId", "String");
        content.put("score", "float");

        DataBase.createNewTable("Scores", content);
    }

    public static void add(Score score) {
        if (DataBase.doesIdAlreadyExist("Scores", "scoreId", score.getIdentifier())) {
            return;
        }
        String sql = "INSERT into Scores (scoreId, customerUsername, productId,score) " +
                "VALUES (?,?, ? ,?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1,score.getIdentifier());
            statement.setString(2,score.getCustomer().getUserName());
            statement.setString(3, score.getProduct().getProductId());
            statement.setFloat(4, score.getScore());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void update(Score score) {
        delete(score.getIdentifier());
        add(score);
    }

    public static void delete(String scoreId) {
        DataBase.delete("Scores", "scoreId",scoreId);
    }


    public static void importAllScores() {
        String sql = "SELECT *  FROM Scores";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                String scoreId = resultSet.getString("scoreId");
                Customer customer = (Customer) (Account.getAccountByUsername(resultSet.getString("customerUsername")));
                Product product = Product.getProductById(resultSet.getString("productId"));
                Float score = resultSet.getFloat("score");

                new Score(scoreId,customer,product,score);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
