package database;

import account.Account;
import account.Customer;
import cart.Cart;
import discount.CodedDiscount;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Date;

import static database.DataBase.*;

public class CodedDiscountDataBase {
    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("start" ,"long");
        content.put("end" , "long");
        content.put("percent","int");
        content.put("discountCode" , "String");
        content.put("maxDiscountAmount" , "int");
        content.put("usedDiscountPerCustomer" , "String");
        content.put("listOfCustomers" , "String");

        DataBase.createNewTable("CodedDiscounts", content);
    }

    public static void add(CodedDiscount codedDiscount) {
        if (doesCodedDiscountAlreadyExists(codedDiscount)) {
            return;
        }
        String sql = "INSERT into DiscountCodes (start , end , percent, discountCode, maxDiscountAmount,usedDiscountPerCustomer , listOfCustomers) " +
                "VALUES (?,?, ? , ? , ? , ?,?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1,codedDiscount.getStart().getTime());
            statement.setLong(2,codedDiscount.getEnd().getTime());
            statement.setInt(3, codedDiscount.getPercent());
            statement.setString(4,codedDiscount.getDiscountCode());
            statement.setInt(5, codedDiscount.getMaxDiscountAmount());
            statement.setString(6, convertObjectToJsonString(convertCustomerHashMapToStringHashMap(codedDiscount.getUsedDiscountPerCustomer())));
            statement.setString(7, convertObjectToJsonString(convertCustomerArrayListToStringArrayList(codedDiscount.getCustomers())));

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static HashMap<String,Integer> convertCustomerHashMapToStringHashMap(HashMap<Customer,Integer> customerHashMap){
        HashMap<String ,Integer> stringHashMap = new HashMap<>();
        for (Customer customer : customerHashMap.keySet()) {
            stringHashMap.put(customer.getUserName(),customerHashMap.get(customer));
        }
        return stringHashMap;
    }

    private static HashMap<Customer,Integer> convertStringHashMapToCustomerHashMap(HashMap<String,Integer> stringHashMap){
        HashMap<Customer,Integer> customerHashMap = new HashMap<>() ;
        for (String username : stringHashMap.keySet()) {
            customerHashMap.put((Customer) Account.getAccountByUsername(username),stringHashMap.get(username));
        }
        return customerHashMap;
    }

    private static ArrayList<String> convertCustomerArrayListToStringArrayList(ArrayList<Customer> supplierArrayList){
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Customer customer : supplierArrayList) {
            stringArrayList.add(customer.getUserName());
        }
        return stringArrayList;
    }

    private static ArrayList<Customer> convertStringArrayListToCustomerArrayList(ArrayList<String > stringArrayList){
        ArrayList<Customer> supplierArrayList = new ArrayList<>();
        for (String username : stringArrayList) {
            supplierArrayList.add((Customer) Account.getAccountByUsername(username));
        }
        return supplierArrayList;
    }

    private static boolean doesCodedDiscountAlreadyExists(CodedDiscount codedDiscount) {
        return !(CodedDiscount.getCodedDiscountByCode(codedDiscount.getDiscountCode())==null);
    }

    public static void update(CodedDiscount codedDiscount) {
        delete(codedDiscount.getDiscountCode());
        add(codedDiscount);
    }

    public static void delete(String discountCode) {
        DataBase.delete("CodedDiscounts" , "discountCode",discountCode);
    }

    public static void  importAllCodedDiscounts() {
        String sql = "SELECT *  FROM CodedDiscounts";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Date start = new Date(resultSet.getLong("start"));
                Date end =  new Date(resultSet.getLong("end"));
                int percent = resultSet.getInt("percent");
                String discountCode = resultSet.getString("discountCode");
                int maxDiscountAmount = resultSet.getInt("maxDiscountAmount");
                HashMap<Customer , Integer> usedDiscountPerCustomer = convertStringHashMapToCustomerHashMap(convertJsonToHashMap(resultSet.getString("usedDiscountPerCustomer")));
                ArrayList<Customer> listOfCustomers =convertStringArrayListToCustomerArrayList(convertJsonToArrayList("listOfCustomers"));




                new CodedDiscount(start,end,percent,discountCode,maxDiscountAmount,usedDiscountPerCustomer,listOfCustomers);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
