package database;

import cart.ProductInCart;
import cart.ShippingInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import feedback.Score;
import log.CustomerLog;
import product.Category;
import product.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBase {

    private  final static String url = "jdbc:sqlite:.\\src\\main\\java\\DataBase.db";

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void createNewTable(String nameOfTable, HashMap<String,String> content) {

        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS");
        sql.append(nameOfTable).append("(\n");
        for (String columnName : content.keySet()) {
            sql.append(columnName).append(" ").append(content.get(columnName)).append(",\n");
        }
        sql.delete(sql.length()-2,sql.length());
        sql.append(" \n )");
        System.out.println(sql);
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(String.valueOf(sql));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void delete(String nameOfTable, String nameOfColumn , String identifier) {
        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(nameOfTable).append(" WHERE ").append(nameOfColumn).append("=?");

        try (Connection connect = connect();
             PreparedStatement preparedStatement = connect.prepareStatement(String.valueOf(sql))) {

            preparedStatement.setString(1, identifier);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String convertObjectToJsonString(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static HashMap<String, ArrayList<String>> convertJsonToSpecialHashMap(String string ){
        Gson gson = new Gson();
        return (HashMap<String, ArrayList<String>>) gson.fromJson(string, new TypeToken<ArrayList<String>>() {
        }.getType());
    }
    public static ArrayList<String> convertJsonToArrayList(String string) {
        Gson gson = new Gson();
        return (ArrayList<String>) gson.fromJson(string, new TypeToken<ArrayList<String>>() {
        }.getType());
    }

    public static HashMap<String,Integer> convertJsonToHashMap(String string){
        Gson gson = new Gson();
        return (HashMap<String, Integer>) gson.fromJson(string, new TypeToken<HashMap<String,Integer>>() {
        }.getType());
    }

    public static HashMap<String, String> convertJsonToStringStringHashMap(String string){
        Gson gson= new Gson();
        return (HashMap<String, String>) gson.fromJson(string,new TypeToken<HashMap<String,String>>(){}.getType());
    }



    public static ArrayList<String> convertCategoryArrayListToStringArrayList(ArrayList<Category> categoryArrayList){
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Category eachCategory : categoryArrayList) {
            stringArrayList.add(eachCategory.getName());
        }
        return stringArrayList;
    }


    public static ArrayList<String> convertProductArrayListToStringArrayList(ArrayList<Product> productArrayList){
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Product eachProduct : productArrayList) {
            stringArrayList.add(eachProduct.getProductId());
        }
        return stringArrayList;
    }

    public static ArrayList<Product> convertStringArrayListToProductArrayList(ArrayList<String> stringArrayList){
        ArrayList<Product> productArrayList = new ArrayList<>();
        for (String eachId : stringArrayList) {
            productArrayList.add(Product.getProductById(eachId));
        }
        return productArrayList;
    }

    public static void importAllData(){
        AccountDataBase.importAllAccounts();
        ProductDataBase.importAllProducts();
        CategoryDataBase.importAllCategories();
        CommentDataBase.importAllComments();
        ScoreDataBase.importAllScores();
        CodedDiscountDataBase.importAllCodedDiscounts();
        SaleDataBase.importAllSales();
        ProductInCartDataBase.importAllProductInCarts();
        ShippingInfoDataBase.importAllShippingInfos();
        CartDataBase.importAllCarts();
        CustomerLogDataBase.importAllCustomerLogs();
    }



}
