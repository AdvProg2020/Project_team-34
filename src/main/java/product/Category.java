package product;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class Category {
    private String name;
    private String parentCategoryName;
    private ArrayList<String> allCategoriesInName;
    private ArrayList<Product> allProductsIn;
    private HashMap<String, ArrayList<String>> specialFields;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public ArrayList<String> getAllCategoriesInName() {
        return allCategoriesInName;
    }

    public void setAllCategoriesInName(ArrayList<String> allCategoriesInName) {
        this.allCategoriesInName = allCategoriesInName;
    }

    public ArrayList<Product> getAllProductsIn() {
        return allProductsIn;
    }

    public void setAllProductsIn(ArrayList<Product> allProductsIn) {
        this.allProductsIn = allProductsIn;
    }

    public HashMap<String, ArrayList<String>> getSpecialFields() {
        return specialFields;
    }

    public void setSpecialFields(HashMap<String, ArrayList<String>> specialFields) {
        this.specialFields = specialFields;
    }
}
