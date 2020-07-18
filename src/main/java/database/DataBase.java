package database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import product.Category;
import product.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class DataBase {

    private  final static String url = "jdbc:sqlite:.\\src\\main\\java\\DataBase.db";
    private final static Connection connection = connect();

    private static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void createNewTable(String nameOfTable, HashMap<String,String> content) {

        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sql.append(nameOfTable).append("(\n");
        for (String columnName : content.keySet()) {
            sql.append(columnName).append(" ").append(content.get(columnName)).append(",\n");
        }
        sql.delete(sql.length()-2,sql.length());
        sql.append(" \n )");
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

        try (PreparedStatement preparedStatement = DataBase.getConnection().prepareStatement(String.valueOf(sql))) {

            preparedStatement.setString(1, identifier);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteAll(String nameOfTable){
        String sql = "DELETE FROM " + nameOfTable;
        try (PreparedStatement preparedStatement = DataBase.getConnection().prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static boolean doesIdAlreadyExist(String nameOfTable, String nameOfColumn , String identifier){
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(nameOfColumn).append(" FROM ").append(nameOfTable);

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(String.valueOf(sql))) {
            while (resultSet.next()) {
                if(resultSet.getString(nameOfColumn).equals(identifier))
                    return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static String convertObjectToJsonString(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static HashMap<String, ArrayList<String>> convertJsonToSpecialHashMap(String string ){
        Gson gson = new Gson();
        return (HashMap<String, ArrayList<String>>) gson.fromJson(string, new TypeToken<HashMap<String, ArrayList<String>>>() {
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
        if(categoryArrayList == null)
            return null;
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Category eachCategory : categoryArrayList) {
            stringArrayList.add(eachCategory.getName());
        }
        return stringArrayList;
    }


    public static ArrayList<String> convertProductArrayListToStringArrayList(ArrayList<Product> productArrayList){
        if(productArrayList == null )
            return null;
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Product eachProduct : productArrayList) {
            stringArrayList.add(eachProduct.getProductId());
        }
        return stringArrayList;
    }

    public static ArrayList<Product> convertStringArrayListToProductArrayList(ArrayList<String> stringArrayList){
        if(stringArrayList == null)
            return null;
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

    public static void createNewTablesToStart(){
        AccountDataBase.createNewTable();
        ProductDataBase.createNewTable();
        CategoryDataBase.createNewTable();
        CommentDataBase.createNewTable();
        ScoreDataBase.createNewTable();
        CodedDiscountDataBase.createNewTable();
        SaleDataBase.createNewTable();
        ProductInCartDataBase.createNewTable();
        ShippingInfoDataBase.createNewTable();
        CartDataBase.createNewTable();
        CustomerLogDataBase.createNewTable();
        WageDataBase.createNewTable();
    }
}
