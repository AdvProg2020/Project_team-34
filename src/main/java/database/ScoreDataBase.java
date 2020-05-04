package database;

import account.Account;
import account.Customer;
import feedback.Comment;
import feedback.CommentState;
import feedback.Score;
import product.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static database.DataBase.connect;

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
        if (doesScoreAlreadyExists(score)) {
            return;
        }
        String sql = "INSERT into scores (scoreId, customerUsername, productId,score) " +
                "VALUES (?,?, ? )";
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

    private static boolean doesScoreAlreadyExists(Score score) {
        ArrayList<Score> list = getAllScores();
        if (list == null)
            return false;
        for (Score eachScore : list) {
            if (eachScore.getIdentifier().equals(score.getIdentifier()))
                return true;
        }
        return false;
    }

    public static void update(Score score) {
        delete(score.getIdentifier());
        add(score);
    }

    public static void delete(String scoreId) {
        DataBase.delete("Scores", "scoreId",scoreId);
    }


    public static ArrayList<Score> getAllScores() {
        String sql = "SELECT *  FROM Scores";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            ArrayList<Score> scores = new ArrayList<>();
            while (resultSet.next()) {
                String scoreId = resultSet.getString("scoreId");
                Customer customer = (Customer) (Account.getAccountByUsername(resultSet.getString("customerUsername")));
                Product product = Product.getProductById(resultSet.getString("productId"));
                Float score = resultSet.getFloat("score");

                scores.add(new Score(scoreId,customer,product,score));
            }
            return scores;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
