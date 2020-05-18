package database;

import account.Account;
import account.Customer;
import feedback.Comment;
import feedback.CommentState;
import product.Product;

import java.sql.*;
import java.util.HashMap;


/**
 * @author rpirayadi
 * @since 0.0.1
 */
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
        if (DataBase.doesIdAlreadyExist("Comments", "commentId" ,comment.getCommentId())) {
            return;
        }
        String sql = "INSERT into Comments (commentId , customerUsername, productId, title, content, commentState,customerBoughtThisProduct) " +
                "VALUES (?,?, ? , ? , ? , ?,?)";
        try (PreparedStatement statement = DataBase.getConnection().prepareStatement(sql)) {

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


    public static void update(Comment comment) {
        delete(comment.getCommentId());
        add(comment);
    }

    public static void delete(String commentId) {
        DataBase.delete("Comments", "commentId", commentId);
    }


    public static void importAllComments() {
        String sql = "SELECT *  FROM Comments";

        try (Statement statement = DataBase.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                String commentId = resultSet.getString("commentId");
                Customer customer = (Customer) (Account.getAccountByUsername(resultSet.getString("customerUsername")));
                Product product = Product.getProductById(resultSet.getString("productId"));
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");

                CommentState commentState = CommentState.valueOf(resultSet.getString("commentState"));
                boolean customerBoughtThisProduct = resultSet.getBoolean("customerBoughtThisProduct");
                new Comment(customer, product, title, content, commentState, customerBoughtThisProduct, commentId);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
