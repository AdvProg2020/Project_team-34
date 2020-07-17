package controller;

import account.Customer;
import account.Supplier;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import communications.ControllerSource;
import communications.Utils;
import exceptionalMassage.ExceptionalMassage;
import feedback.Comment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import product.Category;
import product.Product;
import state.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ProductController {
    private final Controller mainController;

    public ProductController(Controller mainController) {
        this.mainController = mainController;
    }

    private JsonElement communication(String function, JsonArray inputs) throws ExceptionalMassage {
        return new JsonParser().parse(mainController.
                communication(function, inputs, ControllerSource.PRODUCT_CONTROLLER).getContent());
    }

    public void controlAddProduct(String name, String nameOfCompany, int price, int remainedNumbers, String category,
                                  String description, HashMap<String, String> specifications, String imageURL) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(name);
        inputs.add(nameOfCompany);
        inputs.add(String.valueOf(price));
        inputs.add(String.valueOf(remainedNumbers));
        inputs.add(category);
        inputs.add(description);
        inputs.add(Utils.convertStringToStringHashMapToJsonElement(specifications));
        inputs.add(imageURL);
        communication("controlAddProduct",inputs);
    }

    public void controlRemoveProductById(String productId) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(productId);
        communication("controlRemoveProductById",inputs);
    }

    public String controlGetDigestInfosOfProduct(Product product) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(Utils.convertObjectToJsonString(product));
        return communication("controlGetDigestInfosOfProduct",inputs).getAsString();
    }

    public String controlGetAttributesOfProduct(String productId) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(productId);
        return communication("controlGetAttributesOfProduct",inputs).getAsString();
    }


    public void controlEditProductById(String productId, HashMap<String, String> fieldsToChange) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(productId);
        inputs.add(Utils.convertStringToStringHashMapToJsonElement(fieldsToChange));
        communication("controlEditProductById",inputs);
    }

    public ArrayList<Supplier> controlGetAllSuppliersForAProduct(Product product) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(Utils.convertObjectToJsonString(product));
        return Utils.convertJsonElementToSupplierArrayList(communication("controlGetAllSuppliersForAProduct",inputs));
    }

    public ArrayList<Customer> controlViewBuyersOfProduct(String productId) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(productId);
        return Utils.convertJsonElementToCustomerArrayList(communication("controlViewBuyersOfProduct",inputs));
    }

    public boolean doesThisSupplierSellThisProduct(Supplier seller, Product product) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(Utils.convertObjectToJsonString(seller));
        inputs.add(Utils.convertObjectToJsonString(product));
        return communication("doesThisSupplierSellThisProduct",inputs).getAsBoolean();
    }

    //added by rpirayadi
    public ArrayList<Category> controlGetAllCategoriesInACategory(Category rootCategory) throws ExceptionalMassage{
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(Utils.convertObjectToJsonString(rootCategory));
        JsonElement categories = communication("controlGetAllCategoriesInACategory", jsonArray);
        ArrayList<Category> categoryArrayList = Utils.convertJsonElementToCategoryArrayList(categories);
        return categoryArrayList;
    }
    //added by rpirayadi
    public Category controlGetAllProductCategory () throws ExceptionalMassage{
        JsonElement category = communication("controlGetAllProductCategory",new JsonArray());
        return Category.convertJsonStringToCategory(category.getAsString());
    }

    //related to feedback:
    public void controlAddCommentToProduct(String title, String content, Product product) throws ExceptionalMassage{
        JsonArray inputs = new JsonArray();
        inputs.add(title);
        inputs.add(content);
        inputs.add(Utils.convertObjectToJsonString(product));
        communication("controlAddCommentToProduct", inputs);
    }

    public ArrayList<Comment> controlGetCommentsOfAProduct(Product product) throws ExceptionalMassage{
        JsonArray inputs = new JsonArray();
        inputs.add(Utils.convertObjectToJsonString(product));
        JsonElement comments = communication("controlGetCommentsOfAProduct",inputs);
        ArrayList<Comment> commentArrayList = Utils.convertJsonElementToCommentArrayList(comments);
        return commentArrayList;
    }

    public void controlRateProductById(String id, float score) throws ExceptionalMassage {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(id);
        jsonArray.add(String.valueOf(score));
        communication("controlRateProductById", jsonArray);
    }

    public float controlGetAverageScoreByProduct(Product product) throws ExceptionalMassage{
        JsonArray inputs = new JsonArray();
        inputs.add(Utils.convertObjectToJsonString(product));
        JsonElement score = communication("controlGetAverageScoreByProduct", inputs);
        return score.getAsFloat();
    }

    public ArrayList<Comment> controlGetConfirmedComments() throws ExceptionalMassage{
        JsonElement comments = communication("controlGetConfirmedComments", new JsonArray());
        ArrayList<Comment> commentArrayList = Utils.convertJsonElementToCommentArrayList(comments);
        return commentArrayList;
    }

    //related to request:
    public String controlGetListOfRequestId() throws  ExceptionalMassage{
        return communication("controlGetListOfRequestId", new JsonArray()).getAsString();
    }

    public ArrayList<String> controlGetArrayOfRequestId() throws ExceptionalMassage{
        JsonElement arrayOfString = communication("controlGetArrayOfRequestId", new JsonArray());
        ArrayList<String> arrayList = Utils.convertJsonElementToStringArrayList(arrayOfString);
        return arrayList;
    }

    public String controlShowDetailForRequest(String requestId) throws ExceptionalMassage {
        JsonArray input = new JsonArray();
        input.add(requestId);
        return communication("controlShowDetailForRequest",input).getAsString();
    }

    public State controlGetEnumForRequest(String requestId) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(requestId);
        return State.convertJsonStringToState(communication("controlGetEnumForRequest",inputs).getAsString());

    }

    public void controlAcceptOrDeclineRequest(String requestId, boolean isAccepted) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(requestId);
        inputs.add(String.valueOf(isAccepted));
        communication("controlAcceptOrDeclineRequest", inputs);
    }

    public boolean controlHasCustomerBoughtThisProduct(Customer customer, Product product ) throws ExceptionalMassage{
        JsonArray inputs = new JsonArray();
        inputs.add(Utils.convertObjectToJsonString(customer));
        inputs.add(Utils.convertObjectToJsonString(product));
        return communication("controlHasCustomerBoughtThisProduct", inputs).getAsBoolean();
    }

    public void controlViewThisProduct(Product product) throws ExceptionalMassage{
        JsonArray input = new JsonArray();
        input.add(Utils.convertObjectToJsonString(product));
        communication("controlViewThisProduct", input);
    }

    public ObservableList<Customer> getCustomersBoughtProductObservable(Product product, Supplier supplier) throws ExceptionalMassage{
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        JsonArray inputs = new JsonArray();
        inputs.add(Utils.convertObjectToJsonString(product));
        inputs.add(Utils.convertObjectToJsonString(supplier));
        JsonElement customersJson = communication("getCustomersBoughtProductObservable", inputs);
        customers.addAll(Utils.convertJsonElementToCustomerArrayList(customersJson));
        return customers;
    }

    //related to Category:
    public void controlAddCategory(String name, boolean isParentCategory, String parentCategoryName) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(name);
        inputs.add(String.valueOf(isParentCategory));
        inputs.add(parentCategoryName);
        communication("controlAddCategory", inputs);
    }

    public void controlRemoveCategory(String name) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(name);
        communication("controlRemoveCategory", inputs);
    }

    public void controlAddProductToCategory(String categoryName, String productIdentifier) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(categoryName);
        inputs.add(productIdentifier);
        communication("controlAddProductToCategory", inputs);
    }

    public void controlRemoveProductFromCategory(String categoryName, String productIdentifier) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(categoryName);
        inputs.add(productIdentifier);
        communication("controlRemoveProductFromCategory", inputs);
    }

    public void controlChangeCategoryName(String oldName, String newName) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(oldName);
        inputs.add(newName);
        communication("controlChangeCategoryName", inputs);
    }

    public void controlAddSpecialFieldToCategory(String categoryName, String filterKey, String filterValues)
            throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(categoryName);
        inputs.add(filterKey);
        inputs.add(filterValues);
        communication("controlAddSpecialFieldToCategory", inputs);
    }

    public void controlRemoveSpecialFieldFromCategory(String categoryName, String filterKey, String filterValue)
            throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(categoryName);
        inputs.add(filterKey);
        inputs.add(filterValue);
        communication("controlRemoveSpecialFieldFromCategory", inputs);
    }

    public ArrayList<String> controlGetAllCategoriesName() throws ExceptionalMassage {
        return Utils.convertJsonElementToStringArrayList(communication("controlGetAllCategoriesName", new JsonArray()));
    }

    public ArrayList<String> controlGetAllProductCategoriesName() throws ExceptionalMassage {
        return Utils.convertJsonElementToStringArrayList(communication("controlGetAllProductCategoriesName", new JsonArray()));
    }

    public ArrayList<String> controlGetAllCategoryCategoriesName() throws ExceptionalMassage {
        return Utils.convertJsonElementToStringArrayList(communication("controlGetAllCategoryCategoriesName", new JsonArray()));
    }

    public String controlGetCategoryParentName(String name) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(name);
        return communication("controlGetCategoryParentName", inputs).getAsString();
    }

    public boolean isThisCategoryClassifier(String name) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(name);
        return Boolean.parseBoolean(communication("isThisCategoryClassifier", inputs).getAsString());
    }

    public HashMap<String, ArrayList<String>> controlGetCategorySpecialFields(String name) throws ExceptionalMassage{
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(name);
        JsonElement jsonElement = communication("controlGetCategorySpecialFields",jsonArray);
        return Utils.convertJasonElementToStringToStringArrayListHashMap(jsonElement);
    }

    //related to FilterAndSort
    public boolean controlFilterGetAvailabilityFilter() throws ExceptionalMassage {
        return communication("controlFilterGetAvailabilityFilter", new JsonArray()).getAsBoolean();
    }

    public SortType controlFilterGetSortType() throws ExceptionalMassage {
        return SortType.valueOf(communication("controlFilterGetSortType", new JsonArray()).getAsString());
    }

    public ArrayList<String> controlFilterGetNameFilter() throws ExceptionalMassage {
        return Utils.convertJsonElementToStringArrayList(communication("controlFilterGetNameFilter",
                new JsonArray()));
    }

    public ArrayList<String> controlFilterGetBrandFilter() throws ExceptionalMassage {
        return Utils.convertJsonElementToStringArrayList(communication("controlFilterGetBrandFilter",
                new JsonArray()));
    }

    public Integer controlFilterGetPriceLowerBound() throws ExceptionalMassage {
        int bound = communication("controlFilterGetPriceLowerBound", new JsonArray()).getAsInt();
        if (bound == -1)
            return null;
        return bound;
    }

    public Integer controlFilterGetPriceUpperBound() throws ExceptionalMassage {
        int bound = communication("controlFilterGetPriceUpperBound", new JsonArray()).getAsInt();
        if (bound == -1)
            return null;
        return bound;
    }

    public Category controlFilterGetCategory() throws ExceptionalMassage {
        return Category.convertJsonStringToCategory(communication("controlFilterGetCategory", new JsonArray()).getAsString());
    }

    public HashMap<String, ArrayList<String>> controlFilterGetSpecialFilter() throws ExceptionalMassage {
        JsonObject jsonObject = communication("controlFilterGetSpecialFilter", new JsonArray()).getAsJsonObject();
        HashMap<String, ArrayList<String>> specialFilters = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            specialFilters.put(key, Utils.convertJsonElementToStringArrayList(jsonObject.get(key)));
        }
        return specialFilters;
    }

    public void controlFilterSetAvailabilityFilter(boolean availabilityFilter) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(String.valueOf(availabilityFilter));
        communication("controlFilterSetAvailabilityFilter", inputs);
    }

    public void controlFilterSetPriceLowerBound(int priceLowerBound) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(String.valueOf(priceLowerBound));
        communication("controlFilterSetPriceLowerBound", inputs);
        //check
    }

    public void controlFilterSetPriceUpperBound(int priceUpperBound) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(String.valueOf(priceUpperBound));
        communication("controlFilterSetPriceUpperBound", inputs);
        //check
    }

    public void controlFilterSetInSaleOnly(boolean inSaleOnly) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(String.valueOf(inSaleOnly));
        communication("controlFilterSetInSaleOnly", inputs);
    }


    public void controlFilterSetCategoryFilter(String categoryName) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(Objects.requireNonNullElse(categoryName, "All Products"));
        communication("controlFilterSetCategoryFilter", inputs);
    }

    public void controlFilterSetSortType(String sortType) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(Objects.requireNonNullElse(sortType, "by number of views"));
        communication("controlFilterSetSortType", inputs);
    }

    public void controlFilterAddSpecialFilter(String key, String value) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(key);
        inputs.add(value);
        communication("controlFilterAddSpecialFilter", inputs);
    }

    public void controlFilterRemoveSpecialFilter(String key, String value) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(key);
        inputs.add(value);
        communication("controlFilterRemoveSpecialFilter", inputs);
    }

    public void controlFilterRemoveAllSpecialFilter() throws ExceptionalMassage {
        communication("controlFilterRemoveAllSpecialFilter", new JsonArray());
    }

    public void controlFilterAddNameFilter(String name) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(name);
        communication("controlFilterAddNameFilter", inputs);
    }

    public void controlFilterRemoveNameFilter(String name) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(name);
        communication("controlFilterRemoveNameFilter", inputs);
    }

    public void controlFilterAddSupplierFilter(String supplierName) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(supplierName);
        communication("controlFilterAddSupplierFilter", inputs);
    }

    public void controlFilterRemoveSupplierFilter(String supplierName) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(supplierName);
        communication("controlFilterRemoveSupplierFilter", inputs);
    }


    public void controlFilterAddBrandFilter(String brand) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(brand);
        communication("controlFilterAddBrandFilter", inputs);
    }

    public void controlFilterRemoveBrandFilter(String brand) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(brand);
        communication("controlFilterRemoveBrandFilter", inputs);
    }

    public ArrayList<Product> controlFilterGetFilteredAndSortedProducts() throws ExceptionalMassage {
        return Utils.convertJsonElementToProductArrayList(communication(
                "controlFilterGetFilteredAndSortedProducts", new JsonArray()));
    }

    public HashMap<String, ArrayList<String>> controlGetAllAvailableFilters() throws ExceptionalMassage {
        JsonObject jsonObject = communication("controlGetAllAvailableFilters", new JsonArray()).getAsJsonObject();
        HashMap<String, ArrayList<String>> availableFilters = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            availableFilters.put(key, Utils.convertJsonElementToStringArrayList(jsonObject.get(key)));
        }
        return availableFilters;
    }

    public String controlCurrentFilters() throws ExceptionalMassage {
        return communication("controlCurrentFilters", new JsonArray()).getAsString();
    }

    public void clearFilterAndSort() throws ExceptionalMassage {
        communication("clearFilterAndSort", new JsonArray());
    }
}
