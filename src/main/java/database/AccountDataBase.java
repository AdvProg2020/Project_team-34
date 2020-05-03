package database;

import account.Account;
import account.Customer;
import account.Supervisor;
import account.Supplier;
import cart.Cart;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class AccountDataBase {

    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("username", "String");
        content.put("name" , "String");
        content.put("familyName", "String");
        content.put("email", "String");
        content.put("phoneNumber" , "String");
        content.put("password", "String");
        content.put("credit", "int");
        content.put("cartId", "String");
        content.put("nameOfCompany", "String");

        DataBase.createNewTable("Accounts", content);
    }


    public static void add(Account account) {
        if (doesAccountAlreadyExists(account)){
            return;
        }
        String sql = "INSERT into Accounts (username,name,familyName, email, phoneNumber, password, credit, " +
                " cartId, nameOfCompany)"+
                "VALUES (?, ? , ? , ? , ?, ? ,?, ?, ? )";
        try (Connection connection = DataBase.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

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

    private static boolean doesAccountAlreadyExists(Account account) {
        ArrayList<Account> list = getAllAccounts();
        if(list == null)
            return false;
        for (Account eachAccount: list) {
            if(eachAccount.getUserName().equals(account.getUserName()))
                return true;
        }
        return false;
    }

    public static void update(Account account) {
        delete(account.getUserName());
        add(account);
    }

    public static void delete(String username) {
        String sql = "DELETE FROM Accounts WHERE username= ?";

        try (Connection connect = DataBase.connect();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static ArrayList<Account> getAllAccounts() {
        String sql = "SELECT *  FROM Accounts";

        try (Connection connection = DataBase.connect();
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
                String cartId = resultSet.getString("cartId");
                String nameOfCompany = resultSet.getString("nameOfCompany");


                if(cartId!= null){
                    Customer customer = new Customer(username,name,familyName,email,phoneNumber,password,credit,
                             cartId);
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















