package database;

import account.Account;
import account.Customer;
import account.Supervisor;
import account.Supplier;
import cart.Cart;
import log.CustomerLog;
import log.SupplierLog;
import product.Category;
import product.Product;

import java.sql.*;
import java.util.ArrayList;

public class AccountDataBase {
    public  void createNewTable() {
        String url = "jdbc:sqlite:.\\src\\main\\java\\DataBase.db";

        String sql = "CREATE TABLE IF NOT EXISTS Accounts (\n"
                + "	username String,\n"
                + "	name String, \n"
                + "	familyName String, \n"
                + "email String, \n"
                + "	phoneNumber String , \n"
                + "password String ,\n"
                + "credit int ,\n"
                + "cartId String ,\n"
                + "nameOfCompany String ,\n"
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
        if (doesAccountAlreadyExists(account)){
            return;
        }
        String sql = "INSERT into Accounts (username,name,familyName, email, phoneNumber, password, credit, " +
                " cartId, nameOfCompany)"+
                "VALUES (?, ? , ? , ? , ?, ? ,?, ?, ? ,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, account.getUserName());
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

    private boolean doesAccountAlreadyExists(Account account) {
        ArrayList<Account> list = getAllAccounts();
        for (Account eachAccount: list) {
            if(eachAccount.getUserName().equals(account.getUserName()))
                return true;
        }
        return false;
    }

    public void update(Account account) {
        delete(account.getUserName());
        add(account);
    }

    public void delete(String username) {
        String sql = "DELETE FROM Accounts WHERE username= ?";

        try (Connection connect = this.connect();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public ArrayList<Account> getAllAccounts() {
        String sql = "SELECT *  FROM Accounts";

        try (Connection connection = this.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            ArrayList<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String name = resultSet.getString("name");
                String familyName = resultSet.getString("familyName");
                String email = resultSet.getString("email");
                String phoneNumber  = resultSet.getString("phoneNumber");
                String password = resultSet.getString("password");
                int credit =resultSet.getInt("credit");
                String customerLogId = resultSet.getString("customerLogId");
                String cartId = resultSet.getString("cartId");
                String nameOfCompany = resultSet.getString("nameOfCompany");
                String supplierLogId = resultSet.getString("supplierLogId");


                if(cartId!= null){
                    Customer customer = new Customer(username,name,familyName,email,phoneNumber,password,credit,
                             Cart.getCartById(cartId));
                    accounts.add(customer);
                }
                else if(nameOfCompany != null){
                    Supplier supplier = new Supplier(username,name,familyName,email,phoneNumber,password,credit,nameOfCompany);
                    accounts.add(supplier);
                }
                else{
                    Supervisor supervisor = new Supervisor(username,name, familyName, email,phoneNumber,password,credit);
                    accounts.add(supervisor);
                }

            }
            return accounts;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
















