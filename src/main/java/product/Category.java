package product;

import database.CategoryDataBase;
import exceptionalMassage.ExceptionalMassage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class Category {
    public static final ArrayList<Category> allCategories = new ArrayList<>();
    public static final Category superCategory = new Category();

    private String name;
    private final String parentCategoryName;
    private final ArrayList<String> allCategoriesInName;
    private final ArrayList<Product> allProductsIn;
    private final HashMap<String, ArrayList<String>> specialFields;


    //Constructors:
    private Category() {
        this.name = "All Products";
        this.parentCategoryName = null;
        this.allProductsIn = null;
        this.allCategoriesInName = new ArrayList<>();
        this.specialFields = null;
        allCategories.add(this);
        //DataBase Banned
    }

    private Category(String name, boolean isParentCategory, String parentCategoryName) {
        this.name = name;
        if (parentCategoryName == null) {
            parentCategoryName = superCategory.getName();
        }
        this.parentCategoryName = parentCategoryName;
        if (isParentCategory) {
            this.allCategoriesInName = new ArrayList<>();
            this.allProductsIn = null;
            this.specialFields = null;
        } else {
            this.allProductsIn = new ArrayList<>();
            this.specialFields = new HashMap<>();
            this.allCategoriesInName = null;
        }
        allCategories.add(this);
        CategoryDataBase.add(this);
    }

    public static Category getInstance(String name, boolean isParentCategory, String parentCategoryName) throws ExceptionalMassage {
        if (getCategoryByName(name) != null)
            throw new ExceptionalMassage("Category with name " + name + " has already created.");
        Category parentCategory = getCategoryByName(parentCategoryName);
        if (parentCategory == null)
            throw new ExceptionalMassage("Parent category not found.");
        if (!parentCategory.isCategoryClassifier())
            throw new ExceptionalMassage("Category " + parentCategoryName + " is a product classifier.");
        Category addingCategory = new Category(name, isParentCategory, parentCategoryName);
        parentCategory.addToSubCategoryList(addingCategory);
        return addingCategory;
    }

    public Category(String name, String parentCategoryName, ArrayList<String> allCategoriesInName,
                    ArrayList<Product> allProductsIn, HashMap<String, ArrayList<String>> filters) {
        this.name = name;
        this.parentCategoryName = parentCategoryName;
        this.allCategoriesInName = allCategoriesInName;
        this.allProductsIn = allProductsIn;
        this.specialFields = filters;
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
        if (allCategoriesInName != null) {
            for (String name : allCategoriesInName) {
                allCategoriesIn.add(getCategoryByName(name));
            }
        }
        return allCategoriesIn;
    }

    public HashMap<String, ArrayList<String>> getSpecialFields() {
        return specialFields;
    }

    //Setters:
    public void setName(String name) throws ExceptionalMassage {
        if (getCategoryByName(name) != null) {
            throw new ExceptionalMassage("Category with this name has already initialized. <Category.setName>");
        }
        this.name = name;
        CategoryDataBase.update(this);
    }

    //Modeling methods:
    public boolean isCategoryClassifier() {
        return this.allProductsIn == null;
    }

    public boolean isProductIn(Product product) {
        return this.allProductsIn.contains(product);
    }

    public HashMap<String, String> implementedSpecification(Product product) {
        HashMap<String, String> specifications = product.getSpecification();
        return specifications;
    }

    public void addProduct(Product product) throws ExceptionalMassage {
        if (this.isCategoryClassifier())
            throw new ExceptionalMassage("Cannot Add Product to this category. <Category.addProduct>");
        if (this.isProductIn(product))
            throw new ExceptionalMassage("This product has already added to this category. <Category.addProduct>");
        if (Category.getProductCategory(product) != null)
            throw new ExceptionalMassage("This product has already added to another category. <Category.addProduct>");

        this.allProductsIn.add(product);
        CategoryDataBase.update(this);
        //modify product specifications
    }

    public void removeProduct(Product product) throws ExceptionalMassage {
        if (this.isCategoryClassifier())
            throw new ExceptionalMassage("This is not a product category. <Category.removeProduct>");
        if (!this.isProductIn(product))
            throw new ExceptionalMassage("This product is not in this category. <Category.removeProduct>");
        this.allProductsIn.remove(product);
        CategoryDataBase.update(this);
    }

    public void addToSubCategoryList(Category addingCategory) throws ExceptionalMassage {
        if (!this.isCategoryClassifier())
            throw new ExceptionalMassage("Cannot add a subcategory to this category. <Category.addSubCategory>");
        this.allCategoriesInName.add(addingCategory.getName());
    }

    public void addSubCategory(String addingCategoryName, boolean isParentCategory) throws ExceptionalMassage {
        if (!this.isCategoryClassifier())
            throw new ExceptionalMassage("Cannot add a subcategory to this category. <Category.addSubCategory>");
        Category.getInstance(addingCategoryName, isParentCategory, this.getName());
    }

    public void removeSubCategory(String removingCategoryName) throws ExceptionalMassage {
        if (!this.isCategoryClassifier())
            throw new ExceptionalMassage("This category is a product classifier. <Category.removeSubCategory>");
        if (!this.allCategoriesInName.contains(removingCategoryName))
            throw new ExceptionalMassage("This subcategory is not in this category. <Category.removeSubCategory>");
        //check if category is empty if required
        Category.allCategories.remove(getCategoryByName(removingCategoryName));
        this.allCategoriesInName.remove(removingCategoryName);
        CategoryDataBase.update(this);
    }

    public static void removeCategory(String removingCategoryName) throws ExceptionalMassage {
        Category removingCategory = Category.getCategoryByName(removingCategoryName);
        if (removingCategory == null)
            throw new ExceptionalMassage("Category not found. <Category.removingCategory>");
        (removingCategory.getParentCategory()).removeSubCategory(removingCategoryName);
    }

    public ArrayList<Category> getReference() {
        ArrayList<Category> previousReference;
        if (this == superCategory) {
            previousReference = new ArrayList<>();
            previousReference.add(superCategory);
            return previousReference;
        }
        previousReference = this.getParentCategory().getReference();
        previousReference.add(this);
        return previousReference;
    }

    public ArrayList<Product> getAllProductInAllSubCategories() {
        ArrayList<Product> allProductsInAll = new ArrayList<>();
        if (!this.isCategoryClassifier()) {
            allProductsInAll.addAll(this.allProductsIn);
        } else {
            for (Category category : this.getAllCategoriesIn()) {
                allProductsInAll.addAll(category.getAllProductInAllSubCategories());
            }
        }
        return allProductsInAll;
    }

    public boolean isProductInSubCategories(Product product) {
        return getAllProductInAllSubCategories().contains(product);
    }

    public static Category getCategoryByName(String searchingName) {
        if (allCategories.size() != 0) {
            for (Category category : allCategories) {
                if (category.getName().equals(searchingName)) {
                    return category;
                }
            }
        }
        return null;
    }

    public static Category getProductCategory(Product product) {
        if (allCategories.size() != 0) {
            for (Category category : allCategories) {
                if (category.isProductIn(product)) {
                    return category;
                }
            }
        }
        return null;
    }

    public void addField(String filterKey, String filterValue) throws ExceptionalMassage {
        if (this.isCategoryClassifier())
            throw new ExceptionalMassage("A category classifier category doesn't support filters. <Category.addFilter>");
        if (this.specialFields.containsKey(filterKey)) {
            ArrayList<String> filterKeyValues = this.specialFields.get(filterKey);
            if (filterKeyValues.contains(filterValue))
                throw new ExceptionalMassage("This filter has already added. <Category.addFilter>");
            filterKeyValues.add(filterValue);
        } else {
            ArrayList<String> filterKeyValue = new ArrayList<>();
            filterKeyValue.add(filterValue);
            specialFields.put(filterKey, filterKeyValue);
        }
        CategoryDataBase.update(this);
        //modify product specifications
    }

    public void removeField(String filterKey, String filterValue) throws ExceptionalMassage {
        if (this.isCategoryClassifier())
            throw new ExceptionalMassage("A category classifier category doesn't support filters. <Category.removeFilter>");
        if (!this.specialFields.containsKey(filterKey))
            throw new ExceptionalMassage("Filter with key " + filterKey + " not found. <Category.removeFilter>");
        if (!this.specialFields.get(filterKey).contains(filterValue))
            throw new ExceptionalMassage("\"" + filterValue + "\" " + "not found in " + "\"" + filterKey + "\". " + "<Category.removeFilter>");
        specialFields.get(filterKey).remove(filterValue);
        CategoryDataBase.update(this);
        //modify product specifications
    }

    public HashMap<String, ArrayList<String>> getAvailableSpecialFilters() {
        if (!isCategoryClassifier()) {
            return specialFields;
        } else {
            Set<String> keys = new HashSet<>();
            for (String categoryName : allCategoriesInName) {
                Category category = getCategoryByName(categoryName);
                Set<String> categoryFilterKeys = category.getAvailableSpecialFilters().keySet();
                keys.retainAll(categoryFilterKeys);
            }
            HashMap<String, ArrayList<String>> availableFilters = new HashMap<>();
            for (String key : keys) {
                Set<String> valuesSet = new HashSet<>();
                for (String categoryName : allCategoriesInName) {
                    Category category = getCategoryByName(categoryName);
                    ArrayList<String> categoryValues = category.getAvailableSpecialFilters().get(key);
                    valuesSet.addAll(categoryValues);
                }
                ArrayList<String> valuesArrayList = new ArrayList<>(valuesSet);
                availableFilters.put(key, valuesArrayList);
            }
            return availableFilters;
        }
        //check
    }
}
