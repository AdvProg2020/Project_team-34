package database;

import product.Category;
import product.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static database.DataBase.*;

public class CategoryDataBase {

    public static void createNewTable() {
        HashMap<String, String> content = new HashMap<>();
        content.put("name", "String");
        content.put("parentCategoryName" , "String");
        content.put("listOfAllProductsId", "String");
        content.put("listOfCategoriesInName", "String");
        content.put("specialFields", "String");

        DataBase.createNewTable("Categories", content);
    }

    public static void add(Category category) {
        if (DataBase.doesIdAlreadyExist("Categories", "name", category.getName())) {
            return;
        }
        String sql = "INSERT into Categories (name , parentCategoryName, listOfAllProductsId, listOfCategoriesInName, specialFields) " +
                "VALUES (?,?, ? ,?,?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1,category.getName());
            statement.setString(2,category.getParentCategoryName());
            statement.setString(3,convertObjectToJsonString(convertProductArrayListToStringArrayList( category.getAllProductsIn())));
            statement.setString(4,convertObjectToJsonString(category.getAllCategoriesInName()));
            statement.setString(5,convertObjectToJsonString(category.getSpecialFields()));

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    

    public static void update(Category category) {
        delete(category.getName());
        add(category);
    }

    public static void delete(String categoryName) {
        DataBase.delete("Categories", "name", categoryName);
    }

    public static void importAllCategories() {
        String sql = "SELECT *  FROM Categories";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String parentCategoryName = resultSet.getString("parentCategoryName");
                ArrayList<Product> allProductsIn= convertStringArrayListToProductArrayList(convertJsonToArrayList(resultSet.getString("listOfAllProductsId")));
                ArrayList<String> allCategoriesInName = convertJsonToArrayList(resultSet.getString("listOfCategoriesInName"));
                HashMap<String,ArrayList<String>> filters = convertJsonToSpecialHashMap(resultSet.getString("specialFields"));

                new Category(name,parentCategoryName,allCategoriesInName,allProductsIn,filters);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


}
