package product;

import java.util.ArrayList;

public class Category {
    public static final Category superCategory = new Category("All Products", true, null);

    private String name;
    private Category parentCategory;
    private ArrayList<Product> allProductsIn;
    private ArrayList<Category> allCategoriesIn;

    //Constructors:
    public Category(String name, boolean isParentCategory, Category parentCategory) {

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
    }

    private void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    private void setAllProductsIn(ArrayList<Product> allProductsIn) {
        this.allProductsIn = allProductsIn;
    }

    private void setAllCategoriesIn(ArrayList<Category> allCategoriesIn) {
        this.allCategoriesIn = allCategoriesIn;
    }

    //Modeling methods:
    public void switchType() {
        if (allProductsIn == null) {
            allCategoriesIn = null;
            allProductsIn = new ArrayList<>();
        } else {
            allCategoriesIn = new ArrayList<>();
            allProductsIn = null;
        }
    }

    public boolean addProduct(Product product) {
        if (allProductsIn != null) {
            allProductsIn.add(product);
            return true;
        }
        return false;
    }

    public boolean addSubCategory(Category subCategory) {
        return false;
    }

    public ArrayList<Category> getReference() {
        return null;
    }

    public ArrayList<Product> allProductInAllSubCategories() {
        return null;
    }

    public static boolean isProductInSubCategories(Product product) {
        return false;
    }
}
