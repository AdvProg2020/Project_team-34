package database;

import account.*;
import exceptionalMassage.ExceptionalMassage;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class AccountDataBase {

    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("username", "String");
        content.put("name" , "String");
        content.put("familyName", "String");
        content.put("email", "String");
        content.put("phoneNumber" , "TEXT");
        content.put("credit", "int");
        content.put("cartId", "String");
        content.put("nameOfCompany", "String");
        content.put("isAvailable", "boolean");
        content.put("bankAccountNumber", "int");
        content.put("type", "String");

        DataBase.createNewTable("Accounts", content);
    }


    public static void add(Account account) {
        if (DataBase.doesIdAlreadyExist("Accounts", "username", account.getUserName())){
            return;
        }
        String sql = "INSERT into Accounts (username,name,familyName, email, phoneNumber, credit, " +
                " cartId, nameOfCompany,isAvailable,bankAccountNumber , type)"+
                "VALUES (?, ? , ? , ? , ?, ? ,?, ?, ?,?, ?)";
        try (PreparedStatement statement = DataBase.getConnection().prepareStatement(sql)) {
            statement.setString(1, account.getUserName());
            statement.setString(2,account.getName());
            statement.setString(3, account.getFamilyName());
            statement.setString(4, account.getEmail());
            statement.setString(5, account.getPhoneNumber());
            statement.setInt(6, account.getCredit());
            statement.setBoolean(9,account.getIsAvailable());
            statement.setInt(10, account.getBankAccountNumber());
            statement.setString(11, account.getAccountType());

            if(account instanceof Customer){
                statement.setString(7,((Customer)account).getCart().getIdentifier());
                statement.setString(8,null);
            }
            else if(account instanceof Supplier){
                statement.setString(7,null);
                statement.setString(8,((Supplier) account).getNameOfCompany());
            }
            else{
                statement.setString(7,null);
                statement.setString(8,null);
            }

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void update(Account account) {
        delete(account.getUserName());
        add(account);
    }

    public static void delete(String username) {
        DataBase.delete("Accounts", "username",username);
    }


    public static void importAllAccounts() {
        String sql = "SELECT *  FROM Accounts";

        try (Statement statement = DataBase.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String name = resultSet.getString("name");
                String familyName = resultSet.getString("familyName");
                String email = resultSet.getString("email");
                String phoneNumber  = resultSet.getString("phoneNumber");
                int credit =resultSet.getInt("credit");
                String cartId = resultSet.getString("cartId");
                String nameOfCompany = resultSet.getString("nameOfCompany");
                boolean isAvailable = resultSet.getBoolean("isAvailable");
                int bankAccountNumber = resultSet.getInt("bankAccountNumber");
                String type = resultSet.getString("type");


                if(type.equals("Customer")){
                    Customer customer = new Customer(username,name,familyName,email,phoneNumber,credit,
                             cartId, isAvailable,bankAccountNumber);
                }
                else if(type.equals("Supplier")){
                    Supplier supplier = new Supplier(username,name,familyName,email,phoneNumber,credit,isAvailable,nameOfCompany, bankAccountNumber);
                }
                else if(type.equals("Supervisor")){
                    Supervisor supervisor = new Supervisor(username,name, familyName, email,phoneNumber,credit,isAvailable , bankAccountNumber);
                }else {
                    Supporter supporter = new Supporter(username,name, familyName, email, phoneNumber, credit, isAvailable);
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Account> sortAccount(String field , ArrayList<String> whatToShow) throws ExceptionalMassage{
        if(!field.equals("username") && !field.equals("credit"))
            throw  new ExceptionalMassage("Invalid field to sort with");
        String sql = "SELECT username FROM Accounts ORDER BY " + field + " ASC;";
        ArrayList<Account> result = new ArrayList<>();
        try (Statement statement = DataBase.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            String username ;
            while (resultSet.next()) {
                username = resultSet.getString("username");
                if(whatToShow.contains(username))
                    result.add(Account.getAccountByUsernameWithinAvailable(username));
            }
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
















