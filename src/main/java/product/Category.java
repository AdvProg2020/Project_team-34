package product;

import exceptionalMassage.ExceptionalMassage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author Aryan Ahadinia
 */

public class Category {
    public static ArrayList<Category> allCategories = new ArrayList<>();
    public static final Category superCategory = new Category();

    private String name;
    private String parentCategoryName;
    private ArrayList<Product> allProductsIn;
    private ArrayList<String> allCategoriesInName;
    private HashMap<String, ArrayList<String>> specificationFilter;
    private Comparator<Product> sortType;


    //Constructors:
    private Category() {
        this.name = "All Products";
        this.parentCategoryName = null;
        this.allProductsIn = null;
        this.allCategoriesInName = new ArrayList<>();
        //DataBase Banned
    }

    private Category(String name, boolean isParentCategory, Category parentCategory) {
        this.name = name;
        if (parentCategory == null) {
            parentCategory = superCategory;
        }
        this.parentCategoryName = parentCategory.getName();
        if (isParentCategory) {
            allCategoriesInName = new ArrayList<>();
            allProductsIn = null;
        } else {
            allProductsIn = new ArrayList<>();
            allCategoriesInName = null;
        }
        specificationFilter = new HashMap<>();
        //file modifications required
    }

    public static Category getInstance(String name, boolean isParentCategory, Category parentCategory) throws ExceptionalMassage {
        if (getCategoryByName(name) != null)
            throw new ExceptionalMassage("Category with name " + name + " has already created.");
        if (!parentCategory.canBeParentCategory())
            throw new ExceptionalMassage("Category " + parentCategory.getName() + " is a product classifier.");
        Category category = new Category(name, isParentCategory, parentCategory);
        parentCategory.addSubCategory(category);
        return category;
    }

    //Getters:
    public String getName() {
        return name;
    }

    public Category getParentCategory() {
        return getCategoryByName(parentCategoryName);
    }

    public ArrayList<Product> getAllProductsIn() {
        return allProductsIn;
    }

    public ArrayList<Category> getAllCategoriesIn() {
        ArrayList<Category> allCategoriesIn = new ArrayList<>();
        for (String name : allCategoriesInName) {
            allCategoriesIn.add(getCategoryByName(name));
        }
        return allCategoriesIn;
    }

    //Setters:
    public void setName(String name) throws ExceptionalMassage {
        if (getCategoryByName(name) != null) {
            throw new ExceptionalMassage("Category with this name has already initialized.");
        }
        this.name = name;
        //file modification required
    }

    //Modeling methods:
    public void addProduct(Product product) throws ExceptionalMassage {
        if (allProductsIn == null)
            throw new ExceptionalMassage("Cannot Add Product to this category.");
        if (allProductsIn.contains(product))
            throw new ExceptionalMassage("This product has already added to this category.");
        allProductsIn.add(product);
        //file modification required
    }

    public void removeProduct(Product product) throws ExceptionalMassage {
        if (allProductsIn == null)
            throw new ExceptionalMassage("This is not a product category.");
        if (!allProductsIn.contains(product))
            throw new ExceptionalMassage("This product is not in this category.");
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
        if (allCategoriesInName == null)
            throw new ExceptionalMassage("Cannot add a subcategory to this category.");
        if (allCategoriesInName.contains(subCategory.getName()))
            throw new ExceptionalMassage("This subcategory has already added.");
        allCategoriesInName.add(subCategory.getName());
        //file modifications required
    }

    public void removeSubCategory(Category subCategory) throws ExceptionalMassage {
        if (allCategoriesInName == null)
            throw new ExceptionalMassage("This category is a product classifier.");
        if (!allCategoriesInName.contains(subCategory.getName()))
            throw new ExceptionalMassage("This subcategory is not in this category.");
        if (subCategory.allProductsIn != null) {
            if (subCategory.allProductsIn.size() != 0)
                throw new ExceptionalMassage("You must remove all products in before.");
        }
        if (subCategory.allCategoriesInName != null) {
            if (subCategory.allCategoriesInName.size() != 0)
                throw new ExceptionalMassage("You must remove all categories in before.");
        }
        allCategoriesInName.remove(subCategory.getName());
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

    public void addSpecialFilter(String key, ArrayList<String> values) throws ExceptionalMassage {
        if (specificationFilter.containsKey(key))
            throw new ExceptionalMassage("This filter has already added.");
        specificationFilter.put(key, values);
        //file modification
    }

    public void removeSpecialFilter(String key) throws ExceptionalMassage {
        if (!specificationFilter.containsKey(key))
            throw new ExceptionalMassage("This filter is not in this category.");
        specificationFilter.remove(key);
        //file modification
    }
}
