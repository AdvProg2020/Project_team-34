package database;

import account.Account;
import account.Customer;
import account.Supplier;
import feedback.Comment;

import java.sql.*;

public class CommentDataBase {

    public static void createNewTable() {
        String url = "jdbc:sqlite:.\\src\\main\\java\\DataBase.db";

        String sql = "CREATE TABLE IF NOT EXISTS Comments (\n"
                + "	customerId String,\n"
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

    /*
    public static void add(Comment comment) {
        if (doesAccountAlreadyExists(comment)){
            return;
        }
        String sql = "INSERT into Comments (customerId, productId, title, title, content, commentState,customerBoughtThisProduct " +
                "VALUES (?, ? , ? , ? , ?, ?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1,comment.);
            statement.setString(2,account.getName());
            statement.setString(3, account.getFamilyName());
            statement.setString(4, account.getEmail());
            statement.setString(5, account.getPhoneNumber());
            statement.setString(6, account.getPassword());
            statement.setInt(7, account.getCredit());

            if(account instanceof Customer){
                statement.setString(8,((Customer)account).getCart().getIdentifier());
                statement.setString(9,null);
            }
            else if(account instanceof Supplier){
                statement.setString(8,null);
                statement.setString(9,((Supplier) account).getNameOfCompany());
            }
            else{
                statement.setString(8,null);
                statement.setString(9,null);
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
     */
}
