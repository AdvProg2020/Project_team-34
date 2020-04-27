package database;

import account.Account;
import account.Supplier;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import feedback.Comment;
import product.Category;
import product.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductDateBase {

    public static void createNewTable() {
        String url = "jdbc:sqlite:.\\src\\main\\java\\DataBase.db";

        String sql = "CREATE TABLE IF NOT EXISTS Products (\n"
                + "	numberOfViews int,\n"
                + "	productId String, \n"
                + "	productState String, \n"
                + "name String, \n"
                + "nameOfCompany String, \n"
                + "	priceForEachSupplier String , \n"
                + " listOfSuppliers String ,\n"
                + "	remainedNumberForEachSupplier String , \n"
                + " categoryName String ,\n"
                + "	description String, \n"
                + " productCommentsId String, \n"
                + "	specification String \n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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

    public void add(Product product) {
        if (doesProductAlreadyExists(product)) {
           return;
        }
        String sql = "INSERT into Products (numberOfViews,productId ,productState, name, nameOfCompany, priceForEachSupplier," +
                "listOfSuppliers, remainedNumber,categoryId, description,productCommentsId , specification)" +
                "VALUES (?, ? , ? , ? , ?, ? ,?, ?, ? ,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, product.getNumberOfViews());
            statement.setString(2, product.getProductId());
            statement.setString(3, String.valueOf(product.getProductState()));
            statement.setString(4, product.getName());
            statement.setString(5, product.getNameOfCompany());
            statement.setString(6, convertObjectToJsonString(convertSupplierHashMapToStringHashMap(product.getPriceForEachSupplier())));
            statement.setString(7, convertObjectToJsonString(covertSupplierArrayListToStringArrayList(product.getListOfSuppliers())));
            statement.setString(8, convertObjectToJsonString(convertSupplierHashMapToStringHashMap(product.getRemainedNumberForEachSupplier())));
            statement.setString(9, product.getCategory().getName());
            statement.setString(10, product.getDescription());
            statement.setString(11, String.valueOf(product.getComments()));
            statement.setString(12, convertObjectToJsonString(product.getSpecification()));

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private HashMap<String,Integer> convertSupplierHashMapToStringHashMap(HashMap<Supplier,Integer> supplierHashMap){
        HashMap<String ,Integer> stringHashMap = new HashMap<>();
        for (Supplier supplier : supplierHashMap.keySet()) {
            stringHashMap.put(supplier.getUserName(),supplierHashMap.get(supplier));
        }
        return stringHashMap;
    }

    private HashMap<Supplier,Integer> convertStringHashMapToSupplierHashMap(HashMap<String,Integer> stringHashMap){
        HashMap<Supplier,Integer> supplierHashMap = new HashMap<>() ;
        for (String username : stringHashMap.keySet()) {
            supplierHashMap.put((Supplier)Account.getAccountByUsername(username),stringHashMap.get(username));
        }
        return supplierHashMap;
    }

    private ArrayList<String> covertSupplierArrayListToStringArrayList(ArrayList<Supplier> supplierArrayList){
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Supplier supplier : supplierArrayList) {
            stringArrayList.add(supplier.getUserName());
        }
        return stringArrayList;
    }

    private ArrayList<Supplier> convertStringArrayListToSupplierArrayList(ArrayList<String > stringArrayList){
        ArrayList<Supplier> supplierArrayList = new ArrayList<>();
        for (String username : stringArrayList) {
            supplierArrayList.add((Supplier)Account.getAccountByUsername(username));
        }
        return supplierArrayList;
    }

    private ArrayList<String> covertCommentArrayListToStringArrayList(ArrayList<Comment> commentArrayList){
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Comment comment : commentArrayList) {
            //stringArrayList.add(comment.getId);
        }
        return stringArrayList;
    }

    private ArrayList<Comment> convertStringArrayListToCommentArrayList(ArrayList<String> stringArrayList){
        ArrayList<Comment> commentArrayList = new ArrayList<>();
        for (String id : stringArrayList) {
            //commentArrayList.add(Comment.getCommentById(id));
        }
        return commentArrayList;
    }

    private boolean doesProductAlreadyExists(Product product) {
        ArrayList<Product> list = getAllProducts();
        for (Product eachProduct : list) {
            if(eachProduct.getProductId().equals(product.getProductId()))
                return true;
        }
        return false;
    }


    private String convertObjectToJsonString(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
    private ArrayList<String> convertJsonToArrayList(String string) {
        Gson gson = new Gson();
        return (ArrayList<String>) gson.fromJson(string, new TypeToken<ArrayList<String>>() {
        }.getType());
    }

    private HashMap<String,Integer> convertJsonToHashMap(String string){
        Gson gson = new Gson();
        return (HashMap<String, Integer>) gson.fromJson(string, new TypeToken<HashMap<String,Integer>>() {
        }.getType());
    }

    public void update(Product product) {

    }

    public ArrayList<Product> getAllProducts() {
        String sql = "SELECT *  FROM Products";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
            ArrayList<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getString("name"), resultSet.getString("nameOfCompany"),
                        convertStringHashMapToSupplierHashMap(convertJsonToHashMap(resultSet.getString("priceForEachSupplier"))),
                        convertStringArrayListToSupplierArrayList(convertJsonToArrayList(resultSet.getString("listOfSuppliers"))),
                        convertStringHashMapToSupplierHashMap(convertJsonToHashMap(resultSet.getString("remainedNumberForEachSupplier"))),
                        Category.getCategoryByName(resultSet.getString("categoryName")),resultSet.getString("description"),
                        convertStringArrayListToCommentArrayList(convertJsonToArrayList(resultSet.getString("ProductCommentsId")))
                        , resultSet.getInt("numberOfViews"),resultSet.getString("productId"),resultSet.getString("productState"));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }



    public ArrayList<Product> getAllFilteredProducts(Gson gson) {
        return null;
    }


}
