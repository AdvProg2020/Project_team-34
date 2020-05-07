package database;

import feedback.Comment;
import feedback.CommentState;
import product.Category;
import product.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static database.DataBase.connect;
import static database.DataBase.convertObjectToJsonString;

public class CategoryDataBase {

    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("name", "String");
        content.put("parentCategoryName" , "String");
        content.put("listOfAllProductsId", "String");
        content.put("listOfCategoriesInName", "String");
        content.put("specification", "String");

        DataBase.createNewTable("Categories", content);
    }

    /*
    public static void add(Category category) {
        if (doesScoreAlreadyExists(category)) {
            return;
        }
        String sql = "INSERT into scores (name , parentCategoryName, listOfAllProductsId, listOfCategoriesInName, specification) " +
                "VALUES (?,?, ? )";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1,category.getName());
            statement.setString(2,category.getParentCategory().getName();
            statement.setString(3,convertObjectToJsonString(convertProductArrayListToStringArrayList( category.getAllProductsIn())));
            statement.setString(4,convertObjectToJsonString(category.getAllCategoriesInName()));
            statement.setString(5,convertObjectToJsonString(category.getSpecificationFilter()));

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean doesCategoryAlreadyExists(Category category) {
        ArrayList<Category> list = getAllCategories();
        if (list == null)
            return false;
        for (Category eachCategory : list) {
            if (eachCategory.getName().equals(category.getName()))
                return true;
        }
        return false;
    }

    private static ArrayList<String> convertProductArrayListToStringArrayList(ArrayList<Product> productArrayList){
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Product eachProduct : productArrayList) {
            stringArrayList.add(eachProduct.getProductId());
        }
        return stringArrayList;
    }

    private static ArrayList<Product> convertStringArrayListToProductArrayList(ArrayList<String> stringArrayList){
        ArrayList<Product> productArrayList = new ArrayList<>();
        for (String eachId : stringArrayList) {
            productArrayList.add(Product.getProductById(eachId));
        }
        return productArrayList;
    }

    public static void update(Category category) {
        delete(category.getName());
        add(category);
    }

    public static void delete(String categoryName) {
        DataBase.delete("Categories", "name", categoryName);
    }

    public static ArrayList<Category> getAllCategories() {
        String sql = "SELECT *  FROM Categories";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            ArrayList<Comment> comments = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String parentCategoryName = resultSet.getString("parentCategoryName");
                ArrayList<Product> allProductsIn= resultSet.getString("listOfAllProductsId")
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");

                CommentState commentState = CommentState.valueOf(resultSet.getString("commentState"));
                boolean customerBoughtThisProduct = resultSet.getBoolean("customerBoughtThisProduct");
                comments.add(new Comment(customer, product, title, content, commentState, customerBoughtThisProduct, commentId));
            }
            return comments;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;


    }

     */


















}
