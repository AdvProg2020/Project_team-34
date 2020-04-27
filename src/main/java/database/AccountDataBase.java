package database;

import account.Account;
import account.Customer;
import account.Supplier;
import product.Product;

import java.sql.*;

public class AccountDataBase {
    public static void createNewTable() {
        String url = "jdbc:sqlite:.\\src\\main\\java\\DataBase.db";

        String sql = "CREATE TABLE IF NOT EXISTS Accounts (\n"
                + "	username String,\n"
                + "	name String, \n"
                + "	familyName String, \n"
                + "email String, \n"
                + "	phoneNumber String , \n"
                + "password String ,\n"
                + "credit int ,\n"
                + "customerLogId String ,\n"
                + "cartId String ,\n"
                + "nameOfCompany String ,\n"
                + "supplierLogId String ,\n"
                + ");";
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private Connection connect() {
        String url = "jdbc:sqlite:.\\src\\main\\java\\DataBase.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public void add(Account account) {
        /*if (doesProductAlreadyExists(product)) {
            return;
        }*/
        String sql = "INSERT into Accounts (username,name,familyName, email, phoneNumber, password, credit, " +
                "customerLogId, cartId, nameOfCompany, supplierLogId)"+
                "VALUES (?, ? , ? , ? , ?, ? ,?, ?, ? ,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, account.getUserName());
            statement.setString(2, account.getFamilyName());
            statement.setString(3, account.getEmail());
            statement.setString(4, account.getPhoneNumber());
            statement.setString(5, account.getPassword());
            statement.setInt(6, account.getCredit());

            if(account instanceof Customer){
                statement.setString(7,((Customer)account).getCustomerLog().getIdentifier());
                statement.setString(8,((Customer)account).getCart().getIdentifier());
                statement.setString(9,null);
                statement.setString(10,null);
            }
            else if(account instanceof Supplier){
                statement.setString(7,null);
                statement.setString(8,null);
                statement.setString(9,((Supplier) account).getNameOfCompany());
                statement.setString(10,((Supplier) account).getSupplierLog().getIdentifier());
            }
            else{
                statement.setString(7,null);
                statement.setString(8,null);
                statement.setString(9,null);
                statement.setString(10,null);
            }



            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
















