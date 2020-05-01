package database;

import account.Account;
import account.Customer;
import feedback.Comment;
import feedback.CommentState;
import product.Product;

import java.sql.*;
import java.util.ArrayList;

public class CommentDataBase {

    public static void createNewTable() {
        String url = "jdbc:sqlite:.\\src\\main\\java\\DataBase.db";

        String sql = "CREATE TABLE IF NOT EXISTS Comments (\n"
                + " commentId String,\n"
                + "	customerUsername String,\n"
                + "	productId String, \n"
                + "title String, \n"
                + "content String, \n"
                + "	commentState String , \n"
                + "customerBoughtThisProduct boolean \n"
                + ");";
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static Connection connect() {
        String url = "jdbc:sqlite:.\\src\\main\\java\\DataBase.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void add(Comment comment) {
        if (doesCommentAlreadyExists(comment)) {
            return;
        }
        String sql = "INSERT into Comments (commentId , customerUsername, productId, title, content, commentState,customerBoughtThisProduct " +
                "VALUES (?,?, ? , ? , ? , ?,?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, comment.getCustomer().getUserName());
            statement.setString(2, comment.getProduct().getProductId());
            statement.setString(3, comment.getTitle());
            statement.setString(4, comment.getContent());
            statement.setString(5, String.valueOf(comment.getState()));
            statement.setBoolean(6, comment.didCustomerBoughtThisProduct());


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
        String sql = "DELETE FROM Comments WHERE commentId= ?";

        try (Connection connect = connect();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {

            preparedStatement.setString(1, commentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static ArrayList<Comment> getAllComments() {
        String sql = "SELECT *  FROM Comments";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            ArrayList<Comment> comments = new ArrayList<>();
            while (resultSet.next()) {
                String commentId = resultSet.getString("commentId");
                Customer customer = (Customer) (Account.getAccountByUsername(resultSet.getString("customerId")));
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
