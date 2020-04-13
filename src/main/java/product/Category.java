package product;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Aryan Ahadinia
 */

public class Category {
    public static Category superCategory = new Category("All Products", true, null);

    private String name;
    private Category parentCategory;
    private ArrayList<Product> allProductsIn;
    private ArrayList<Category> allCategoriesIn;
    private HashMap<String, ArrayList<String>> specificationFilter;

    //Constructors:
    public Category(String name, boolean isParentCategory, Category parentCategory) {
        this.name = name;
        if (isParentCategory) {
            allCategoriesIn = new ArrayList<>();
            allProductsIn = null;
        } else {
            allProductsIn = new ArrayList<>();
            allCategoriesIn = null;
        }
        this.parentCategory = parentCategory;
        //exception might occur, parent category is a product saver;
        if (parentCategory != null) {
            parentCategory.addSubCategory(this);
        }
    }

    //Getters:
    public String getName() {
        return name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public ArrayList<Product> getAllProductsIn() {
        return allProductsIn;
    }

    public ArrayList<Category> getAllCategoriesIn() {
        return allCategoriesIn;
    }

    //Setters:
    public void setName(String name) {
        this.name = name;
        //file modification required
    }

    private void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
        //file modification required
    }

    private void setAllProductsIn(ArrayList<Product> allProductsIn) {
        this.allProductsIn = allProductsIn;
        //file modification required
    }

    private void setAllCategoriesIn(ArrayList<Category> allCategoriesIn) {
        this.allCategoriesIn = allCategoriesIn;
        //file modification required
    }

    //Modeling methods:
    public boolean addProduct(Product product) {
        if (allProductsIn != null) {
            allProductsIn.add(product);
            return true;
        }
        return false;
    }

    public boolean removeProduct(Product product) {
        return false;
    }

    public boolean canBeParentCategory() {
        return false;
    }

    public void addSubCategory(Category subCategory) {
    }

    public boolean removeSubCategory(Category subCategory) {
        return false;
    }

    public ArrayList<Category> getReference() {
        ArrayList<Category> previousReference;
        if (this == superCategory) {
            previousReference = new ArrayList<>();
            previousReference.add(superCategory);
            return previousReference;
        }
        previousReference = parentCategory.getReference();
        previousReference.add(this);
        return previousReference;
        //completed
    }

    public ArrayList<Product> allProductInAllSubCategories() {
        return null;
    }

    public static boolean isProductInSubCategories(Product product) {
        return false;
    }
}
