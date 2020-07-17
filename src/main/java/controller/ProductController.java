package controller;

import account.Customer;
import account.Supplier;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import communications.ControllerSource;
import communications.Utils;
import discount.Sale;
import exceptionalMassage.ExceptionalMassage;
import feedback.Comment;
import feedback.CommentState;
import feedback.Score;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import log.CustomerLog;
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
    public ArrayList<Category> controlGetAllCategoriesInACategory(Category rootCategory){
        return rootCategory.getAllCategoriesIn();
    }
    //added by rpirayadi
    public Category controlGetAllProductCategory (){
        return Category.getSuperCategory();
    }

    //related to feedback:
    public void controlAddCommentToProduct(String title, String content, Product product) throws ExceptionalMassage{
        if (!(mainController.getAccount() instanceof Customer)){
            throw new ExceptionalMassage("Sign in as a customer first!");
        }
        new Comment((Customer) mainController.getAccount(), product, title, content, controlHasCustomerBoughtThisProduct((Customer)mainController.getAccount(),product));
    }

    public ArrayList<Comment> controlGetCommentsOfAProduct(Product product) {
        ArrayList<Comment> productComment = new ArrayList<>();
        for (Comment comment : Comment.getComments()) {
            if (comment.getProduct() == product) {
                productComment.add(comment);
            }
        }
        return productComment;
    }

    public void controlRateProductById(String id, float score) throws ExceptionalMassage {
        if (Product.getProductById(id) == null) {
            throw new ExceptionalMassage("No such product with id!");
        } else if (!(mainController.getAccount() instanceof Customer)){
            throw new ExceptionalMassage("Sign in as customer first!");
        } else if (!controlHasCustomerBoughtThisProduct((Customer)mainController.getAccount(),Product.getProductById(id))) {
            throw new ExceptionalMassage("You haven't bought this product yet!");
        } else if (Score.hasCustomerRateThisProduct(Product.getProductById(id),(Customer)mainController.getAccount())){
            throw new ExceptionalMassage("You can't rate again!");
        }
        new Score(score, (Customer) (mainController.getAccount()), Product.getProductById(id));
    }

    public float controlGetAverageScoreByProduct(Product product) {
        return Score.getAverageScoreForProduct(product);
    }

    public ArrayList<Comment> controlGetConfirmedComments() {
        ArrayList<Comment> confirmedComments = new ArrayList<>();
        for (Comment comment : Comment.getComments()) {
            if (comment.getState() == CommentState.CONFIRMED) {
                confirmedComments.add(comment);
            }
        }
        return confirmedComments;
    }

    //related to request:
    public String controlGetListOfRequestId() {
        ArrayList<String> allProductRequestId = Product.getAllProductRequestId();
        StringBuilder result = new StringBuilder();
        for (String requestId : allProductRequestId) {
            result.append(requestId).append('\n');
        }
        for (String requestId : Sale.getAllSaleRequestId()) {
            result.append(requestId).append('\n');
        }
        return result.toString();
    }

    public ArrayList<String> controlGetArrayOfRequestId() {
        ArrayList<String> allRequests = new ArrayList<>();
        allRequests.addAll(Product.getAllProductRequestId());
        allRequests.addAll(Sale.getAllSaleRequestId());
        return allRequests;
    }

    public String controlShowDetailForRequest(String requestId) throws ExceptionalMassage {
        if (requestId.charAt(3) == 'P') {
            return Product.getDetailsForProductRequest(requestId);
        } else {
            return Sale.getDetailsForSaleRequest(requestId);
        }
    }

    public State controlGetEnumForRequest(String requestId) throws ExceptionalMassage {
        if(requestId.charAt(3) == 'P'){
            return Product.getProductById(Product.convertRequestIdToProductId(requestId)).getProductState();
        } else {
            return Sale.getSaleById(Sale.convertRequestIdToSaleId(requestId)).getState();
        }
    }

    public void controlAcceptOrDeclineRequest(String requestId, boolean isAccepted) throws ExceptionalMassage {
        if (requestId.charAt(3) == 'P') {
            Product.acceptOrDeclineRequest(requestId, isAccepted);
        } else {
            Sale.acceptOrDeclineRequest(requestId, isAccepted);
        }
    }

    public boolean controlHasCustomerBoughtThisProduct(Customer customer, Product product ){
        return CustomerLog.getAllCustomersBoughtProduct(product).contains(customer);
    }

    public void controlViewThisProduct(Product product){
        product.setNumberOfViews(product.getNumberOfViews()+ 1);
    }

    public ObservableList<Customer> getCustomersBoughtProductObservable(Product product, Supplier supplier) {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        customers.addAll(CustomerLog.getAllCustomersBoughtProductFromSupplier(product, supplier));
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
        return Utils.convertJasonElementToStringArrayList(communication("controlGetAllCategoriesName", new JsonArray()));
    }

    public ArrayList<String> controlGetAllProductCategoriesName() throws ExceptionalMassage {
        return Utils.convertJasonElementToStringArrayList(communication("controlGetAllProductCategoriesName", new JsonArray()));
    }

    public ArrayList<String> controlGetAllCategoryCategoriesName() throws ExceptionalMassage {
        return Utils.convertJasonElementToStringArrayList(communication("controlGetAllCategoryCategoriesName", new JsonArray()));
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

    public HashMap<String, ArrayList<String>> controlGetCategorySpecialFields(String name) {
        return Category.getCategoryByName(name).getSpecialFields();
    }

    //related to FilterAndSort
    public boolean controlFilterGetAvailabilityFilter() throws ExceptionalMassage {
        return communication("controlFilterGetAvailabilityFilter", new JsonArray()).getAsBoolean();
    }

    public SortType controlFilterGetSortType() throws ExceptionalMassage {
        return SortType.valueOf(communication("controlFilterGetSortType", new JsonArray()).getAsString());
    }

    public ArrayList<String> controlFilterGetNameFilter() throws ExceptionalMassage {
        return Utils.convertJasonElementToStringArrayList(communication("controlFilterGetNameFilter",
                new JsonArray()));
    }

    public ArrayList<String> controlFilterGetBrandFilter() throws ExceptionalMassage {
        return Utils.convertJasonElementToStringArrayList(communication("controlFilterGetBrandFilter",
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
            specialFilters.put(key, Utils.convertJasonElementToStringArrayList(jsonObject.get(key)));
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
        return Utils.convertJasonElementToProductArrayList(communication(
                "controlFilterGetFilteredAndSortedProducts", new JsonArray()));
    }

    public HashMap<String, ArrayList<String>> controlGetAllAvailableFilters() throws ExceptionalMassage {
        JsonObject jsonObject = communication("controlGetAllAvailableFilters", new JsonArray()).getAsJsonObject();
        HashMap<String, ArrayList<String>> availableFilters = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            availableFilters.put(key, Utils.convertJasonElementToStringArrayList(jsonObject.get(key)));
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
