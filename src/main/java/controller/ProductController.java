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
        if (mainController.getAccount() == null)
            throw new ExceptionalMassage("Sing in first.");
        if (!(mainController.getAccount() instanceof Supplier))
            throw new ExceptionalMassage("Sign in as a Supplier");
        if(!specifications.keySet().containsAll(Category.getCategoryByName(category).getSpecialFields().keySet())){
            String error = "You have to enter a value for the category's special fields :\n";
            for (String s : Category.getCategoryByName(category).getSpecialFields().keySet()) {
                error += s + "\n";
            }
            throw new ExceptionalMassage(error);
        }
        /*if(!Category.getCategoryByName(category).getSpecialFields().values().contains(specifications.values())){
            String error = "You have to enter valid values for each field : \n";
            HashMap<String, ArrayList<String>> map = Category.getCategoryByName(category).getSpecialFields();
            for (String s : map.keySet()) {
                error += s + " : ";
                for (String s1 : map.get(s)) {
                    error += s1 + " ";
                }
                error += "\n";
            }
            throw new ExceptionalMassage(error);
        }*/
        Supplier supplier = (Supplier) mainController.getAccount();
        Product product;
        product = Product.getProductByName(name);
        if (Category.getCategoryByName(category) == null) {
            throw new ExceptionalMassage("Category not found.");
        }
//        if (product == null)
//            new Product(supplier, name, nameOfCompany, price, remainedNumbers, description, null, category, specifications, imageURL);
//        else {
//            product.addNewSupplierForProduct(supplier, price, remainedNumbers);
//        }
        Product product1 = new Product(supplier, name, nameOfCompany, price, remainedNumbers, description, null, category, specifications, imageURL);
        System.out.println(product1);
    }

    public void controlRemoveProductById(String productId) throws ExceptionalMassage {
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new ExceptionalMassage("Invalid Id");
        } else {
            Product newProduct = new Product(product);
            newProduct.setProductState(State.PREPARING_TO_BE_DELETED);
            ArrayList<Supplier> newListOfSuppliers = new ArrayList<>();
            newListOfSuppliers.add((Supplier)mainController.getAccount());
            newProduct.setListOfSuppliers(newListOfSuppliers);
        }
    }

    public String controlGetDigestInfosOfProduct(Product product) throws ExceptionalMassage {
        Product productGotById = Product.getProductById(product.getProductId());
        if (productGotById == null) {
            throw new ExceptionalMassage("Invalid Id");
        } else {
            // !!
            //needs sale for all
            String result = "description= " + product.getDescription() + "\n" +
                    "listOfSuppliers= " + product.getListOfSuppliers() + "\n" +
                    "priceForEachSupplier= " + product.getPriceForEachSupplier() + "\n" +
                    "category= " + Category.getProductCategory(product) + "\n" +
                    "Score= " + Score.getAverageScoreForProduct(product) + "\n";
            return String.valueOf(result);
        }
    }

    public String controlGetAttributesOfProduct(String productId) throws ExceptionalMassage {
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new ExceptionalMassage("Invalid Id");
        } else {
            StringBuilder result = new StringBuilder();
            result.append("name= ").append(product.getName()).append("\n");
            result.append("numberOfViews= ").append(product.getNumberOfViews()).append("\n");
            result.append("nameOfCompany= ").append(product.getNameOfCompany()).append("\n");
            result.append("description= ").append(product.getDescription()).append("\n");
            result.append("listOfSuppliers= ").append(product.getListOfSuppliers()).append("\n");
            result.append("priceForEachSupplier= ").append(product.getPriceForEachSupplier()).append("\n");
            result.append("category= ").append(Category.getProductCategory(product)).append("\n");
            HashMap<String, String> specification = product.getSpecification();
            for (String specialField : specification.keySet()) {
                result.append(specialField).append("= ").append(specification.get(specialField)).append("\n");
            }
            return String.valueOf(result);
        }
    }

    public String controlCompare(String firstProductId, String secondProductId) throws ExceptionalMassage {
        Product firstProduct = Product.getProductById(firstProductId);
        Product secondProduct = Product.getProductById(secondProductId);
        if (firstProduct == null || secondProduct == null) {
            throw new ExceptionalMassage("Invalid Id");
        } else if (!(Category.getProductCategory(firstProduct).equals(Category.getProductCategory(secondProduct))))
            throw new ExceptionalMassage("You can not compare products in different categories");
        else {
            StringBuilder result = new StringBuilder();
            result.append("name= ").append(firstProduct.getName()).append(" , ").append(secondProduct.getName()).append("\n");
            result.append("numberOfViews= ").append(firstProduct.getNumberOfViews()).append(" , ").append(secondProduct.getNumberOfViews()).append("\n");
            result.append("nameOfCompany= ").append(firstProduct.getNameOfCompany()).append(" , ").append(secondProduct.getNameOfCompany()).append("\n");
            result.append("description= ").append(firstProduct.getDescription()).append(" , ").append(secondProduct.getDescription()).append("\n");
            HashMap<String, String> firstSpecification = firstProduct.getSpecification();
            HashMap<String, String> secondSpecification = firstProduct.getSpecification();
            for (String specialField : firstSpecification.keySet()) {
                result.append(specialField).append("= ").append(firstSpecification.get(specialField)).append(" , ").append(secondSpecification.get(specialField)).append("\n");
            }
            return String.valueOf(result);
        }
    }

    public void controlEditProductById(String productId, HashMap<String, String> fieldsToChange) throws ExceptionalMassage {
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new ExceptionalMassage("Invalid Id");
        } else {
            Product newProduct = new Product(product);
            String value;
            for (String field : fieldsToChange.keySet()) {
                value = fieldsToChange.get(field);
                switch (field) {
                    case "name":
                        newProduct.setName(value);
                        break;
                    case "nameOfCompany":
                        newProduct.setNameOfCompany(value);
                        break;
                    case "description":
                        newProduct.setDescription(value);
                        break;
                    case "imageUrl":
                        newProduct.setImageUrl(value);
                    default:
                        newProduct.editSpecialField(field, value);
                }
            }
            newProduct.getListOfSuppliers().clear();
            newProduct.getListOfSuppliers().add((Supplier) mainController.getAccount());
        }
    }

    public ArrayList<Supplier> controlGetAllSuppliersForAProduct(Product product) {
        return product.getListOfSuppliers();
    }

    public ArrayList<Customer> controlViewBuyersOfProduct(String productId) {
        return CustomerLog.getAllCustomersBoughtProduct(Product.getProductById(productId));
    }

    public boolean doesThisSupplierSellThisProduct(Supplier seller, Product product) throws ExceptionalMassage {
        if (mainController.getAccount() == null)
            throw new ExceptionalMassage("Sing in first.");
        return product.doesSupplierSellThisProduct(seller);
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
        return Utils.convertJasonObjectToStringArrayList(communication("controlGetAllCategoriesName", new JsonArray()));
    }

    public ArrayList<String> controlGetAllProductCategoriesName() throws ExceptionalMassage {
        return Utils.convertJasonObjectToStringArrayList(communication("controlGetAllProductCategoriesName", new JsonArray()));
    }

    public ArrayList<String> controlGetAllCategoryCategoriesName() throws ExceptionalMassage {
        return Utils.convertJasonObjectToStringArrayList(communication("controlGetAllCategoryCategoriesName", new JsonArray()));
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
        return Utils.convertJasonObjectToStringArrayList(communication("controlFilterGetNameFilter",
                new JsonArray()));
    }

    public ArrayList<String> controlFilterGetBrandFilter() throws ExceptionalMassage {
        return Utils.convertJasonObjectToStringArrayList(communication("controlFilterGetBrandFilter",
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
            specialFilters.put(key, Utils.convertJasonObjectToStringArrayList(jsonObject.get(key)));
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
            availableFilters.put(key, Utils.convertJasonObjectToStringArrayList(jsonObject.get(key)));
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
