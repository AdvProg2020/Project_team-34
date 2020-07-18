package product;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import communications.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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

//    public static Category convertJsonStringToCategory(String jsonString) {
//        return (Category) Utils.convertStringToObject(jsonString, "product.Category");
//    }

    public boolean isCategoryClassifier() {
        return this.allProductsIn == null;
    }

    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        jsonObject.add("name", jsonParser.parse(Utils.convertObjectToJsonString(name)));
        jsonObject.add("parentCategoryName", jsonParser.parse(Utils.convertObjectToJsonString(parentCategoryName)));
        jsonObject.add("allCategoriesInName", jsonParser.parse(Utils.convertStringArrayListToJsonElement(allCategoriesInName).toString()));
        jsonObject.add("allProductsIn", jsonParser.parse(Utils.convertProductArrayListToJsonElement(allProductsIn).toString()));
        jsonObject.add("specialFields", jsonParser.parse(Utils.convertStringToStringArrayListHashMapToJsonElement(specialFields).toString()));
        return jsonObject.toString();
    }

    public Category(String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        this.name = jsonObject.get("name").getAsString();
        this.parentCategoryName = jsonObject.get("parentCategoryName").getAsString();
        this.allCategoriesInName = Utils.convertJsonElementToStringArrayList(jsonObject.get("allCategoriesInName"));
        this.allProductsIn = Utils.convertJsonElementToProductArrayList(jsonObject.get("allProductsIn"));
        this.specialFields = Utils.convertJsonElementToStringToStringArrayListHashMap(jsonObject.get("specialFields"));
    }

    public static Category convertJsonStringToCategory(String jsonString) {
        return new Category(jsonString);
        //return (Category) Utils.convertStringToObject(jsonString, "product.Category");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parentCategoryName);
        //check
    }
}
