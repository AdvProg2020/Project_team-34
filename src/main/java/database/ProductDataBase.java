package database;

import account.Account;
import account.Supplier;
import cart.Cart;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import feedback.Comment;
import product.Category;
import product.Product;
import state.State;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static database.DataBase.*;

public class ProductDataBase {

    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("numberOfViews", "int");
        content.put("productId" , "String");
        content.put("productState", "String");
        content.put("name", "String");
        content.put("priceForEachSupplier" , "String");
        content.put("listOfSuppliers", "String");
        content.put("remainedNumberForEachSupplier", "String");
        content.put("description", "String");
        content.put("specification", "String");
        content.put("rootProductId" , "String");

        DataBase.createNewTable("Products", content);
    }


    public static void add(Product product) {
        if (doesProductAlreadyExists(product)) {
           return;
        }
        String sql = "INSERT into Products (numberOfViews,productId ,productState, name, nameOfCompany, priceForEachSupplier," +
                "listOfSuppliers, remainedNumberForEachSupplier description , specification, rootProductId)" +
                "VALUES (?, ? , ? , ? , ?, ? ,?, ?, ? ,?)";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, product.getNumberOfViews());
            statement.setString(2, product.getProductId());
            statement.setString(3, String.valueOf(product.getProductState()));
            statement.setString(4, product.getName());
            statement.setString(5, product.getNameOfCompany());
            statement.setString(6, convertObjectToJsonString(convertSupplierHashMapToStringHashMap(product.getPriceForEachSupplier())));
            statement.setString(7, convertObjectToJsonString(convertSupplierArrayListToStringArrayList(product.getListOfSuppliers())));
            statement.setString(8, convertObjectToJsonString(convertSupplierHashMapToStringHashMap(product.getRemainedNumberForEachSupplier())));
            statement.setString(9, product.getDescription());
            statement.setString(10, convertObjectToJsonString(product.getSpecification()));
            statement.setString(11, product.getRootProductId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static HashMap<String,Integer> convertSupplierHashMapToStringHashMap(HashMap<Supplier,Integer> supplierHashMap){
        HashMap<String ,Integer> stringHashMap = new HashMap<>();
        for (Supplier supplier : supplierHashMap.keySet()) {
            stringHashMap.put(supplier.getUserName(),supplierHashMap.get(supplier));
        }
        return stringHashMap;
    }

    private static HashMap<Supplier,Integer> convertStringHashMapToSupplierHashMap(HashMap<String,Integer> stringHashMap){
        HashMap<Supplier,Integer> supplierHashMap = new HashMap<>() ;
        for (String username : stringHashMap.keySet()) {
            supplierHashMap.put((Supplier) Account.getAccountByUsername(username),stringHashMap.get(username));
        }
        return supplierHashMap;
    }

    private static ArrayList<String> convertSupplierArrayListToStringArrayList(ArrayList<Supplier> supplierArrayList){
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Supplier supplier : supplierArrayList) {
            stringArrayList.add(supplier.getUserName());
        }
        return stringArrayList;
    }

    private static ArrayList<Supplier> convertStringArrayListToSupplierArrayList(ArrayList<String > stringArrayList){
        ArrayList<Supplier> supplierArrayList = new ArrayList<>();
        for (String username : stringArrayList) {
            supplierArrayList.add((Supplier) Account.getAccountByUsername(username));
        }
        return supplierArrayList;
    }

    private static ArrayList<String> covertCommentArrayListToStringArrayList(ArrayList<Comment> commentArrayList){
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Comment comment : commentArrayList) {
            //stringArrayList.add(comment.getId);
        }
        return stringArrayList;
    }

    private static ArrayList<Comment> convertStringArrayListToCommentArrayList(ArrayList<String> stringArrayList){
        ArrayList<Comment> commentArrayList = new ArrayList<>();
        for (String id : stringArrayList) {
            //commentArrayList.add(Comment.getCommentById(id));
        }
        return commentArrayList;
    }

    private static boolean doesProductAlreadyExists(Product product) {
        return !(Product.getProductById(product.getProductId())==null);
    }


    public static void update(Product product) {
        delete(product.getProductId());
        add(product);
    }

    public static void delete(String productId) {
            DataBase.delete("Products", "productId",productId);
    }

    public static ArrayList<Product> getAllProducts() {
        String sql = "SELECT *  FROM Products";

        try (Connection connection = connect();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
            ArrayList<Product> products = new ArrayList<>();
            while (resultSet.next()) {

                String name = resultSet.getString("name");
                String nameOfCompany = resultSet.getString("nameOfCompany");
                HashMap<Supplier,Integer> priceForEachSupplier = convertStringHashMapToSupplierHashMap(convertJsonToHashMap(resultSet.getString("priceForEachSupplier")));
                ArrayList<Supplier> listOfSuppliers = convertStringArrayListToSupplierArrayList(convertJsonToArrayList(resultSet.getString("listOfSuppliers")));
                HashMap<Supplier,Integer> remainedNumberForEachSupplier = convertStringHashMapToSupplierHashMap(convertJsonToHashMap(resultSet.getString("remainedNumberForEachSupplier")));
                String description = resultSet.getString("description");
                int numberOfViews = resultSet.getInt("numberOfViews");
                String productId = resultSet.getString("productId");
                State state = State.valueOf(resultSet.getString("productState"));
                String rootProductId = resultSet.getString("rootProductId");
                Product product = new Product(name,nameOfCompany,priceForEachSupplier,listOfSuppliers,
                        remainedNumberForEachSupplier,description,numberOfViews,productId,state, rootProductId);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }



    public static ArrayList<Product> getAllFilteredProducts(Gson gson) {
        return null;
    }


}
