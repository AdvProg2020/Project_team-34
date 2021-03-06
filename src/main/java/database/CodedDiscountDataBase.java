package database;

import account.Account;
import account.Customer;
import discount.CodedDiscount;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static database.DataBase.*;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class CodedDiscountDataBase {
    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("start" ,"long");
        content.put("end" , "long");
        content.put("percent","int");
        content.put("discountCode" , "String");
        content.put("maxDiscountAmount" , "int");
        content.put("usedDiscountPerCustomer" , "String");
        content.put("maximumNumberOfUsagePerCustomer" , "String");

        DataBase.createNewTable("CodedDiscounts", content);
    }

    public static void add(CodedDiscount codedDiscount) {
        if (DataBase.doesIdAlreadyExist("CodedDiscounts","discountCode", codedDiscount.getDiscountCode())) {
            return;
        }
        String sql = "INSERT into CodedDiscounts (start , end , percent, discountCode, maxDiscountAmount,usedDiscountPerCustomer , maximumNumberOfUsagePerCustomer) " +
                "VALUES (?,?, ? , ? , ? , ?,?)";
        try (PreparedStatement statement = DataBase.getConnection().prepareStatement(sql)) {

            statement.setLong(1,codedDiscount.getStart().getTime());
            statement.setLong(2,codedDiscount.getEnd().getTime());
            statement.setInt(3, codedDiscount.getPercent());
            statement.setString(4,codedDiscount.getDiscountCode());
            statement.setInt(5, codedDiscount.getMaxDiscountAmount());
            statement.setString(6, convertObjectToJsonString(convertCustomerHashMapToStringHashMap(codedDiscount.getUsedDiscountPerCustomer())));
            statement.setString(7, convertObjectToJsonString(convertCustomerHashMapToStringHashMap(codedDiscount.getMaximumNumberOfUsagePerCustomer())));

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
            customerHashMap.put((Customer) Account.getAccountByUsernameWithinAll(username),stringHashMap.get(username));
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
            supplierArrayList.add((Customer) Account.getAccountByUsernameWithinAvailable(username));
        }
        return supplierArrayList;
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

        try (Statement statement = DataBase.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Date start = new Date(resultSet.getLong("start"));
                Date end =  new Date(resultSet.getLong("end"));
                int percent = resultSet.getInt("percent");
                String discountCode = resultSet.getString("discountCode");
                int maxDiscountAmount = resultSet.getInt("maxDiscountAmount");
                HashMap<Customer , Integer> usedDiscountPerCustomer = convertStringHashMapToCustomerHashMap(convertJsonToHashMap(resultSet.getString("usedDiscountPerCustomer")));
                HashMap<Customer,Integer> maximumNumberOfUsagePerCustomer = convertStringHashMapToCustomerHashMap(convertJsonToHashMap(resultSet.getString("maximumNumberOfUsagePerCustomer")));




                new CodedDiscount(start,end,percent,discountCode,maxDiscountAmount,usedDiscountPerCustomer,maximumNumberOfUsagePerCustomer);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<CodedDiscount> sortCodedDiscount(String field , ArrayList<String> whatToShow) {
        String sql = "SELECT discountCode FROM CodedDiscounts ORDER BY " + field + " ASC;";
        ArrayList<CodedDiscount> result = new ArrayList<>();
        try (Statement statement = DataBase.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            String discountCode ;
            while (resultSet.next()) {
                discountCode = resultSet.getString("discountCode");
                if(whatToShow.contains(discountCode))
                    result.add(CodedDiscount.getCodedDiscountByCode(discountCode));
            }
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
