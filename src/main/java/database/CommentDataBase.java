package database;

import account.Account;
import account.Customer;
import feedback.Comment;
import feedback.CommentState;
import product.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static database.DataBase.connect;

public class CommentDataBase {

    public static void createNewTable() {

        HashMap<String, String> content = new HashMap<>();
        content.put("commentId", "String");
        content.put("name" , "String");
        content.put("customerUsername", "String");
        content.put("productId", "String");
        content.put("title" , "String");
        content.put("content", "String");
        content.put("commentState", "String");
        content.put("customerBoughtThisProduct", "boolean");

        DataBase.createNewTable("Comments", content);
    }


    public static void add(Comment comment) {
        if (doesCommentAlreadyExists(comment)) {
            return;
        }
        String sql = "INSERT into Comments (commentId , customerUsername, productId, title, content, commentState,customerBoughtThisProduct) " +
                "VALUES (?,?, ? , ? , ? , ?,?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, comment.getCommentId());
            statement.setString(2, comment.getCustomer().getUserName());
            statement.setString(3, comment.getProduct().getProductId());
            statement.setString(4, comment.getTitle());
            statement.setString(5, comment.getContent());
            statement.setString(6, String.valueOf(comment.getState()));
            statement.setBoolean(7, comment.didCustomerBoughtThisProduct());


            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean doesCommentAlreadyExists(Comment comment) {
        ArrayList<Comment> list = getAllComments();
        if (list == null)
            return false;
        for (Comment eachComment : list) {
            if (eachComment.getCommentId().equals(comment.getCommentId()))
                return true;
        }
        return false;
    }

    public static void update(Comment comment) {
        delete(comment.getCommentId());
        add(comment);
    }

    public static void delete(String commentId) {
        DataBase.delete("Comments", "commentId", commentId);
    }


    public static ArrayList<Comment> getAllComments() {
        String sql = "SELECT *  FROM Comments";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            ArrayList<Comment> comments = new ArrayList<>();
            while (resultSet.next()) {
                String commentId = resultSet.getString("commentId");
                Customer customer = (Customer) (Account.getAccountByUsername(resultSet.getString("customerUsername")));
                Product product = Product.getProductById(resultSet.getString("productId"));
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");

                CommentState commentState = CommentState.valueOf(resultSet.getString("commentState"));
                boolean customerBoughtThisProduct = resultSet.getBoolean("customerBoughtThisProduct");
                comments.add(new Comment(customer, product, title, content, commentState, customerBoughtThisProduct, commentId));
            }
            return comments;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
