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
        content.put("filters", "String");

        DataBase.createNewTable("Categories", content);
    }

    public static void add(Category category) {
        if (doesCategoryAlreadyExists(category)) {
            return;
        }
        String sql = "INSERT into scores (name , parentCategoryName, listOfAllProductsId, listOfCategoriesInName, filters) " +
                "VALUES (?,?, ? )";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1,category.getName());
            statement.setString(2,category.getParentCategory().getName());
            statement.setString(3,convertObjectToJsonString(convertProductArrayListToStringArrayList( category.getAllProductsIn())));
            statement.setString(4,convertObjectToJsonString(convertCategoryArrayListToStringArrayList(category.getAllCategoriesIn())));
            statement.setString(5,convertObjectToJsonString(category.getFilters()));

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
            ArrayList<Category> categories = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String parentCategoryName = resultSet.getString("parentCategoryName");
                ArrayList<Product> allProductsIn= convertStringArrayListToProductArrayList(convertJsonToArrayList(resultSet.getString("listOfAllProductsId")));
                ArrayList<String> allCategoriesInName = convertJsonToArrayList(resultSet.getString("listOfCategoriesInName"));
                HashMap<String,ArrayList<String>> filters = convertJsonToSpecialHashMap(resultSet.getString("filters"));

                new Category(name,parentCategoryName,allCategoriesInName,allProductsIn,filters);
            }
            return categories;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;


    }


}
