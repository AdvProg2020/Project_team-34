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
    private String parentCategoryName;
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

    public Category(String name, String parentCategoryName, ArrayList<String> allCategoriesInName,
                    ArrayList<Product> allProductsIn, HashMap<String, ArrayList<String>> filters) {
        this.name = name;
        this.parentCategoryName = parentCategoryName;
        if (parentCategoryName.equals("All Products")) {
            try {
                superCategory.addToSubCategoryList(this);
            } catch (ExceptionalMassage exceptionalMassage) {
                exceptionalMassage.printStackTrace();
            }
        }
        this.allCategoriesInName = allCategoriesInName;
        this.allProductsIn = allProductsIn;
        this.specialFields = filters;
        allCategories.add(this);
    }

    public static Category getInstance(String name, boolean isParentCategory, String parentCategoryName) throws ExceptionalMassage {
        if (getCategoryByName(name) != null)
            throw new ExceptionalMassage("Category with name " + name + " has already created.");
        Category parentCategory = getCategoryByName(parentCategoryName);
        if (parentCategory == null)
            throw new ExceptionalMassage("Parent category not found.");
        if (!parentCategory.isCategoryClassifier())
            throw new ExceptionalMassage("Category " + parentCategoryName + " is a product classifier.");
        if (name.trim().equals(""))
            throw new ExceptionalMassage("Invalid name for a category");
        Category addingCategory = new Category(name, isParentCategory, parentCategoryName);
        parentCategory.addToSubCategoryList(addingCategory);
        if (parentCategory != superCategory)
            CategoryDataBase.update(parentCategory);
        return addingCategory;
    }

    //Getters:
    public String getName() {
        return name;
    }

    public Category getParentCategory() {
        return getCategoryByName(parentCategoryName);
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public ArrayList<Product> getAllProductsIn() {
        return allProductsIn;
    }

    public ArrayList<Category> getAllCategoriesIn() {
        if (allCategoriesInName != null) {
            ArrayList<Category> allCategoriesIn = new ArrayList<>();
            for (String name : allCategoriesInName) {
                allCategoriesIn.add(getCategoryByName(name));
            }
            return allCategoriesIn;
        }
        return null;
    }

    public ArrayList<String> getAllCategoriesInName() {
        return allCategoriesInName;
    }

    public HashMap<String, ArrayList<String>> getSpecialFields() {
        return specialFields;
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    //Setters:
    private void setParentCategoryName(String name) {
        this.parentCategoryName = name;
        CategoryDataBase.update(this);
    }

    public void setName(String name) throws ExceptionalMassage {
        if (name.equals("All Products"))
            throw new ExceptionalMassage("You cannot modify this category.");
        if (getCategoryByName(name) != null) {
            throw new ExceptionalMassage("Category with this name has already initialized. <Category.setName>");
        }
        CategoryDataBase.delete(this.name);
        getParentCategory().allCategoriesInName.remove(this.name);
        getParentCategory().allCategoriesInName.add(name);
        this.name = name;
        if (getAllCategoriesIn() != null)
            for (Category category : getAllCategoriesIn()) {
            category.setParentCategoryName(name);
        }
        if (getParentCategory() != superCategory)
            CategoryDataBase.update(getParentCategory());
        CategoryDataBase.add(this);
    }

    //Modeling methods:
    public boolean isCategoryClassifier() {
        return this.allProductsIn == null;
    }

    public boolean isProductIn(Product product) {
        if (!isCategoryClassifier())
            return this.allProductsIn.contains(product);
        return false;
    }

    public HashMap<String, String> implementedSpecification(Product product) {
        HashMap<String, String> implementedSpecification = new HashMap<>();
        HashMap<String, String> specification = product.getSpecification();
        if (specification.size() != 0) {
            for (String key : specification.keySet()) {
                if (specialFields.containsKey(key)) {
                    implementedSpecification.put(key, specification.get(key));
                }
            }
            for (String key : specialFields.keySet()) {
                if (!implementedSpecification.containsKey(key)) {
                    implementedSpecification.put(key, "Not Assigned");
                }
            }
        }
        return implementedSpecification;
    }

    public void addProduct(Product product) throws ExceptionalMassage {
        if (this.isCategoryClassifier())
            throw new ExceptionalMassage("Cannot Add Product to this category. <Category.addProduct>");
        if (this.isProductIn(product))
            throw new ExceptionalMassage("This product has already added to this category. <Category.addProduct>");
        if (Category.getProductCategory(product) != null)
            throw new ExceptionalMassage("This product has already added to another category. <Category.addProduct>");
        product.setSpecification(implementedSpecification(product));
        this.allProductsIn.add(product);
        CategoryDataBase.update(this);
    }

    public void removeProduct(Product product) throws ExceptionalMassage {
        if (this.isCategoryClassifier())
            throw new ExceptionalMassage("This is not a product category. <Category.removeProduct>");
        if (!this.isProductIn(product))
            throw new ExceptionalMassage("This product is not in this category. <Category.removeProduct>");
        this.allProductsIn.remove(product);
        CategoryDataBase.update(this);
    }

    private void addToSubCategoryList(Category addingCategory) throws ExceptionalMassage {
        if (!this.isCategoryClassifier())
            throw new ExceptionalMassage("Cannot add a subcategory to this category. <Category.addSubCategory>");
        this.allCategoriesInName.add(addingCategory.getName());
    }

    public void addSubCategory(String addingCategoryName, boolean isParentCategory) throws ExceptionalMassage {
        if (!this.isCategoryClassifier())
            throw new ExceptionalMassage("Cannot add a subcategory to this category. <Category.addSubCategory>");
        Category.getInstance(addingCategoryName, isParentCategory, this.getName());
    }

    private void removeSubCategory(String removingCategoryName) throws ExceptionalMassage {
        if (!this.isCategoryClassifier())
            throw new ExceptionalMassage("This category is a product classifier. <Category.removeSubCategory>");
        if (!this.allCategoriesInName.contains(removingCategoryName))
            throw new ExceptionalMassage("This subcategory is not in this category. <Category.removeSubCategory>");
        Category.allCategories.remove(getCategoryByName(removingCategoryName));
        this.allCategoriesInName.remove(removingCategoryName);
        if (!name.equals("All Products")) {
            CategoryDataBase.update(this);
        }
    }

    public static void removeCategory(String removingCategoryName) throws ExceptionalMassage {
        Category removingCategory = Category.getCategoryByName(removingCategoryName);
        if (removingCategory == null)
            throw new ExceptionalMassage("Category not found. <Category.removingCategory>");
        if (removingCategory.allCategoriesInName != null)
            if (removingCategory.allCategoriesInName.size() != 0)
                throw new ExceptionalMassage("Category is not empty. <Category.removingCategory>");
        if (removingCategory.allProductsIn != null)
            if (removingCategory.allProductsIn.size() != 0)
                throw new ExceptionalMassage("Category is not empty. <Category.removingCategory>");
        (removingCategory.getParentCategory()).removeSubCategory(removingCategoryName);
        CategoryDataBase.delete(removingCategory.name);
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
            for (Product product : allProductsIn) {
                product.setSpecification(implementedSpecification(product));
            }
        }
        CategoryDataBase.update(this);
    }

    public void removeField(String filterKey, String filterValue) throws ExceptionalMassage {
        if (this.isCategoryClassifier())
            throw new ExceptionalMassage("A category classifier category doesn't support filters. <Category.removeFilter>");
        if (!this.specialFields.containsKey(filterKey))
            throw new ExceptionalMassage("Filter with key " + filterKey + " not found. <Category.removeFilter>");
        if (!this.specialFields.get(filterKey).contains(filterValue))
            throw new ExceptionalMassage("\"" + filterValue + "\" " + "not found in " + "\"" + filterKey + "\". " + "<Category.removeFilter>");
        specialFields.get(filterKey).remove(filterValue);
        if (specialFields.get(filterKey).size() == 0)
            specialFields.remove(filterKey);
        for (Product product : allProductsIn) {
            product.setSpecification(implementedSpecification(product));
        }
        CategoryDataBase.update(this);
    }

    public HashMap<String, ArrayList<String>> getAvailableSpecialFilters() {
        if (!isCategoryClassifier()) {
            return specialFields;
        } else {
            Set<String> keys = new HashSet<>();
            boolean firstRetain = true;
            for (String categoryName : allCategoriesInName) {
                Category category = getCategoryByName(categoryName);
                Set<String> categoryFilterKeys = category.getAvailableSpecialFilters().keySet();
                if (firstRetain) {
                    keys.addAll(categoryFilterKeys);
                    firstRetain = false;
                } else {
                    keys.retainAll(categoryFilterKeys);
                }
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

    public static ArrayList<String> getAllCategoriesName() {
        ArrayList<String> allCategoriesToString = new ArrayList<>();
        for (Category category : allCategories) {
            allCategoriesToString.add(category.getName());
        }
        return allCategoriesToString;
    }
}
