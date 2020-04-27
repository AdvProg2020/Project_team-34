package product;

import exceptionalMassage.ExceptionalMassage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Aryan Ahadinia
 */

public class Category {
    public static final Category superCategory = new Category();

    private String name;
    private Category parentCategory;
    private ArrayList<Product> allProductsIn;
    private ArrayList<Category> allCategoriesIn;
    private HashMap<String, ArrayList<String>> specificationFilter;

    //Constructors:
    private Category() {
        this.name = "All Products";
        this.parentCategory = null;
        this.allProductsIn = null;
        this.allCategoriesIn = new ArrayList<>();
    }

    public Category(String name, boolean isParentCategory, Category parentCategory) throws ExceptionalMassage {
        if (getCategoryByName(name) != null) {
            throw new ExceptionalMassage("Category with name " + name + " has already created.");
        }
        this.name = name;
        if (isParentCategory) {
            allCategoriesIn = new ArrayList<>();
            allProductsIn = null;
        } else {
            allProductsIn = new ArrayList<>();
            allCategoriesIn = null;
        }
        //exception might occur, parent category is a product saver;
        if (parentCategory == null) {
            parentCategory = superCategory;
        }
        this.parentCategory = parentCategory;
        this.parentCategory.addSubCategory(this);
        //file modifications required
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
    public void addProduct(Product product) throws ExceptionalMassage {
        if (allProductsIn == null) {
            throw new ExceptionalMassage("Cannot Add Product to this category.");
        }
        if (allProductsIn.contains(product)) {
            throw new ExceptionalMassage("This product has already added to this category.");
        }
        allProductsIn.add(product);
        //file modification required
    }

    public void removeProduct(Product product) throws ExceptionalMassage {
        if (allProductsIn == null) {
            throw new ExceptionalMassage("This is not a product category.");
        }
        if (!allProductsIn.contains(product)) {
            throw new ExceptionalMassage("This product is not in this category.");
        }
        allProductsIn.remove(product);
        //file modification required
    }

    public boolean isProductIn(Product product) {
        return allProductsIn.contains(product);
    }

    public boolean canBeParentCategory() {
        return allProductsIn == null;
    }

    public void addSubCategory(Category subCategory) throws ExceptionalMassage {
        if (allCategoriesIn == null) {
            throw new ExceptionalMassage("Cannot add a subcategory to this category.");
        }
        if (allCategoriesIn.contains(subCategory)) {
            throw new ExceptionalMassage("This subcategory has already added.");
        }
        allCategoriesIn.add(subCategory);
        //file modifications required
    }

    public void removeSubCategory(Category subCategory) throws ExceptionalMassage {
        if (!allCategoriesIn.contains(subCategory)) {
            throw new ExceptionalMassage("This subcategory is not in this category.");
        }
        allCategoriesIn.remove(subCategory);
        //file modification required
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

    public ArrayList<Product> getAllProductInAllSubCategories() {
        ArrayList<Product> allProductsInAll = new ArrayList<>();
        if (allCategoriesIn == null) {
            allProductsInAll.addAll(allProductsIn);
        } else {
            for (Category category : allCategoriesIn) {
                allProductsInAll.addAll(category.getAllProductInAllSubCategories());
            }
        }
        return allProductsInAll;
    }

    public boolean isProductInSubCategories(Product product) {
        return getAllProductInAllSubCategories().contains(product);
    }

    public Category getCategoryByNameIn(String name) {
        for (Category category : allCategoriesIn) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        for (Category category : allCategoriesIn) {
            Category temp = category.getCategoryByNameIn(name);
            if (temp != null) {
                return temp;
            }
        }
        return null;
    }

    public static Category getCategoryByName(String name) {
        return superCategory.getCategoryByNameIn(name);
    }
}
