package controller;

import account.Account;
import account.Customer;
import account.Supervisor;
import account.Supplier;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import discount.Sale;
import exceptionalMassage.ExceptionalMassage;
import feedback.Comment;
import feedback.CommentState;
import feedback.Score;
import log.CustomerLog;
import product.Category;
import product.Product;
import server.communications.RequestStatus;
import server.communications.Response;
import server.communications.Utils;
import state.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ProductController {

    private Controller mainController;
    private final FilterAndSort filterAndSort;

    public ProductController(Controller mainController) {
        this.mainController = mainController;
        filterAndSort = new FilterAndSort();
    }

    public FilterAndSort getFilterAndSort() {
        return filterAndSort;
    }

    public Response controlAddProduct(String name, String nameOfCompany, String price, String remainedNumbers, String category,
                                  String description, String specification, String imageURL) throws ExceptionalMassage {
        JsonParser parser = new JsonParser();
        HashMap<String, String> specifications = Utils.convertJsonElementStringToStringToHashMap(parser.parse(specification));
        if (mainController.getAccount() == null)
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Sing in first."));
        if (!(mainController.getAccount() instanceof Supplier))
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Sign in as a Supplier"));
        if(!specifications.keySet().containsAll(Category.getCategoryByName(category).getSpecialFields().keySet())){
            String error = "You have to enter a value for the category's special fields :\n";
            for (String s : Category.getCategoryByName(category).getSpecialFields().keySet()) {
                error += s + "\n";
            }
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage(error));
        }

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
        Product product1 = new Product(supplier, name, nameOfCompany, Integer.parseInt(price), Integer.parseInt(remainedNumbers), description, null, category, specifications, imageURL);
        System.out.println(product1);
        return Response.createSuccessResponse();
    }

    public Response controlRemoveProductById(String productId) {
        Product product = Product.getProductById(productId);
        if (product == null) {
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Invalid Id"));
        } else {
            Product newProduct = new Product(product);
            newProduct.setProductState(State.PREPARING_TO_BE_DELETED);
            ArrayList<Supplier> newListOfSuppliers = new ArrayList<>();
            newListOfSuppliers.add((Supplier)mainController.getAccount());
            newProduct.setListOfSuppliers(newListOfSuppliers);
        }
        return Response.createSuccessResponse();
    }

    /*public String controlGetDigestInfosOfProduct(Product product) throws ExceptionalMassage {
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
    }*/

    /*public String controlGetAttributesOfProduct(String productId) throws ExceptionalMassage {
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
    }*/

    /*public String controlCompare(String firstProductId, String secondProductId) throws ExceptionalMassage {
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
    }*/

    public Response controlEditProductById(String productId, String fieldToChange) throws ExceptionalMassage {
        JsonParser parser = new JsonParser();
        HashMap<String, String> fieldsToChange = Utils.convertJsonElementStringToStringToHashMap(parser.parse(fieldToChange));
        Product product = Product.getProductById(productId);
        if (product == null) {
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Invalid Id"));
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
            return Response.createSuccessResponse();
        }
    }

    public Response controlGetAllSuppliersForAProduct(String productString) {
        Product product = Product.convertJsonStringToProduct(productString);
        JsonElement suppliers = Utils.convertSupplierArrayListToJsonElement(product.getListOfSuppliers());
        return new Response(RequestStatus.SUCCESSFUL, suppliers.getAsString());
    }

    public Response controlViewBuyersOfProduct(String productId) {
        JsonElement customers = Utils.convertCustomerArrayListToJsonElement(CustomerLog.getAllCustomersBoughtProduct(Product.getProductById(productId)));
        return new Response(RequestStatus.SUCCESSFUL, customers.getAsString());
    }

    public Response doesThisSupplierSellThisProduct(String sellerString, String productString) {
        Supplier seller = Supplier.convertJsonStringToSupplier(sellerString);
        Product product = Product.convertJsonStringToProduct(productString);
        if (mainController.getAccount() == null)
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Sing in first."));
        JsonParser parser = new JsonParser();
        return new Response(RequestStatus.SUCCESSFUL,parser.parse(String.valueOf(product.doesSupplierSellThisProduct(seller))).getAsString());
    }

    //added by rpirayadi
    public Response controlGetAllCategoriesInACategory(String rootCategoryString){
        Category rootCategory = Category.convertJsonStringToCategory(rootCategoryString);
        JsonElement categories = Utils.convertCategoryArrayListToJsonElement(rootCategory.getAllCategoriesIn());
        return new Response(RequestStatus.SUCCESSFUL,categories.getAsString());
    }
    //added by rpirayadi
    public Response controlGetAllProductCategory (){
        String category = Utils.convertObjectToJsonString(Category.getSuperCategory());
        return new Response(RequestStatus.SUCCESSFUL,category);
    }

    //related to feedback:
    public Response controlAddCommentToProduct(String title, String content, String productString){
        Product product = Product.convertJsonStringToProduct(productString);
        if (!(mainController.getAccount() instanceof Customer)){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Sign in as a customer first!"));
        }
        new Comment((Customer) mainController.getAccount(), product, title, content, controlHasCustomerBoughtThisProduct((Customer)mainController.getAccount(),product));
        return Response.createSuccessResponse();
    }

    public Response controlGetCommentsOfAProduct(String productString) {
        Product product = Product.convertJsonStringToProduct(productString);
        ArrayList<Comment> productComment = new ArrayList<>();
        for (Comment comment : Comment.getComments()) {
            if (comment.getProduct() == product) {
                productComment.add(comment);
            }
        }
        JsonElement response = Utils.convertCommentArrayListToJsonElement(productComment);
        return new Response(RequestStatus.SUCCESSFUL, response.getAsString());
    }

    public Response controlRateProductById(String id, String score)  {
        if (Product.getProductById(id) == null) {
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("No such product with id!"));
        } else if (!(mainController.getAccount() instanceof Customer)){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Sign in as customer first!"));
        } else if (!controlHasCustomerBoughtThisProduct((Customer)mainController.getAccount(),Product.getProductById(id))) {
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("You haven't bought this product yet!"));
        } else if (Score.hasCustomerRateThisProduct(Product.getProductById(id),(Customer)mainController.getAccount())){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("You can't rate again!"));
        }
        new Score(Float.valueOf(score), (Customer) (mainController.getAccount()), Product.getProductById(id));
        return Response.createSuccessResponse();
    }

    public Response controlGetAverageScoreByProduct(String productString) {
        Product product = Product.convertJsonStringToProduct(productString);
        return new Response(RequestStatus.SUCCESSFUL, String.valueOf(Score.getAverageScoreForProduct(product)));
    }

    public Response controlGetConfirmedComments() {
        ArrayList<Comment> confirmedComments = new ArrayList<>();
        for (Comment comment : Comment.getComments()) {
            if (comment.getState() == CommentState.CONFIRMED) {
                confirmedComments.add(comment);
            }
        }
        JsonElement jsonComments = Utils.convertCommentArrayListToJsonElement(confirmedComments);
        return new Response(RequestStatus.SUCCESSFUL, jsonComments.getAsString());
    }

    //related to request:
    public Response controlGetListOfRequestId() {
        ArrayList<String> allProductRequestId = Product.getAllProductRequestId();
        StringBuilder result = new StringBuilder();
        for (String requestId : allProductRequestId) {
            result.append(requestId).append('\n');
        }
        for (String requestId : Sale.getAllSaleRequestId()) {
            result.append(requestId).append('\n');
        }
        return new Response(RequestStatus.SUCCESSFUL, result.toString());
    }

    public Response controlGetArrayOfRequestId() {
        ArrayList<String> allRequests = new ArrayList<>();
        allRequests.addAll(Product.getAllProductRequestId());
        allRequests.addAll(Sale.getAllSaleRequestId());
        JsonElement requestsJson = Utils.convertStringArrayListToJsonElement(allRequests);
        return new Response(RequestStatus.SUCCESSFUL,requestsJson.getAsString());
    }

    public Response controlShowDetailForRequest(String requestId) {
        if (requestId.charAt(3) == 'P') {
            return new Response(RequestStatus.SUCCESSFUL, Product.getDetailsForProductRequest(requestId));
        } else {
            try {
                return new Response(RequestStatus.SUCCESSFUL,Sale.getDetailsForSaleRequest(requestId));
            } catch (ExceptionalMassage exceptionalMassage) {
                return Response.createResponseFromExceptionalMassage(exceptionalMassage);
            }
        }
    }

    public Response controlGetEnumForRequest(String requestId)  {
        if(requestId.charAt(3) == 'P'){
            return new Response(RequestStatus.SUCCESSFUL,Utils.convertObjectToJsonString(Product.getProductById(Product.convertRequestIdToProductId(requestId)).getProductState()));
        } else {
            return new Response(RequestStatus.SUCCESSFUL,Utils.convertObjectToJsonString(Sale.getSaleById(Sale.convertRequestIdToSaleId(requestId)).getState()));
        }
    }

    public Response controlAcceptOrDeclineRequest(String requestId, String isAccepted) {
        if (requestId.charAt(3) == 'P') {
            try {
                Product.acceptOrDeclineRequest(requestId, Boolean.parseBoolean(isAccepted));
            } catch (ExceptionalMassage exceptionalMassage) {
                return Response.createResponseFromExceptionalMassage(exceptionalMassage);
            }
        } else {
            try {
                Sale.acceptOrDeclineRequest(requestId, Boolean.parseBoolean(isAccepted));
            } catch (ExceptionalMassage exceptionalMassage) {
                return Response.createResponseFromExceptionalMassage(exceptionalMassage);
            }
        }
        return Response.createSuccessResponse();
    }

    public Response controlGetCategorySpecialFields(String name) {
        JsonElement jsonElement = Utils.convertStringToStringArrayListHashMapToJsonElement(Category.getCategoryByName(name).getSpecialFields());
        return new Response(RequestStatus.SUCCESSFUL, jsonElement.getAsString());
    }

    public Response controlHasCustomerBoughtThisProduct(String customerString, String productString){
        Customer customer = Customer.convertJsonStringToCustomer(customerString);
        Product product = Product.convertJsonStringToProduct(productString);
        return new Response(RequestStatus.SUCCESSFUL,String.valueOf(CustomerLog.getAllCustomersBoughtProduct(product).contains(customer)));
    }

    public Response controlViewThisProduct(String productString){
        Product product = Product.convertJsonStringToProduct(productString);
        product.setNumberOfViews(product.getNumberOfViews()+ 1);
        return Response.createSuccessResponse();
    }

    public Response getCustomersBoughtProductObservable(String productString ,String supplierString) {
        Product product = Product.convertJsonStringToProduct(productString);
        Supplier supplier = Supplier.convertJsonStringToSupplier(supplierString);
        JsonElement returning = Utils.convertCustomerArrayListToJsonElement(CustomerLog.getAllCustomersBoughtProductFromSupplier(product, supplier));
        return new Response(RequestStatus.SUCCESSFUL,returning.getAsString());
    }

    //related to Category:
    public Response controlAddCategory(String name, String isParentCategoryStr, String parentCategoryName) {
        boolean isParentCategory = Boolean.parseBoolean(isParentCategoryStr);
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First. <Controller.controlAddCategory>");
        if (!(account instanceof Supervisor))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a supervisor. <Controller.controlAddCategory>");
        try {
            Category.getInstance(name, isParentCategory, parentCategoryName);
            return new Response(RequestStatus.SUCCESSFUL, "");
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage());
        }
    }

    public Response controlRemoveCategory(String name) {
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First. <Controller.controlRemoveCategory>");
        if (!(account instanceof Supervisor))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a supervisor. <Controller.controlRemoveCategory>");
        try {
            Category.removeCategory(name);
            return new Response(RequestStatus.SUCCESSFUL, "");
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage());
        }
    }

    public Response controlAddProductToCategory(String categoryName, String productIdentifier) {
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First. <Controller.controlAddProductToCategory>");
        if (!(account instanceof Supervisor))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a supervisor. <Controller.controlAddProductToCategory>");
        Category category = Category.getCategoryByName(categoryName);
        if (category == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Category not found.");
        Product product = Product.getProductById(productIdentifier);
        if (product == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Product not found.");
        try {
            category.addProduct(product);
            return new Response(RequestStatus.SUCCESSFUL, "");
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage());
        }
    }

    public Response controlRemoveProductFromCategory(String categoryName, String productIdentifier) {
        Account account = mainController.getAccount();
        //can modify
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First. <Controller.controlRemoveProductFromCategory>");
        if (!(account instanceof Supervisor))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a supervisor. <Controller.controlRemoveProductFromCategory>");
        Category category = Category.getCategoryByName(categoryName);
        if (category == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Category not found.");
        Product product = Product.getProductById(productIdentifier);
        if (product == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Product not found.");
        try {
            category.removeProduct(product);
            return new Response(RequestStatus.SUCCESSFUL, "");
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage());
        }
        //check
    }

    public Response controlChangeCategoryName(String oldName, String newName) {
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First. <Controller.controlChangeCategoryName>");
        if (!(account instanceof Supervisor))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a supervisor. <Controller.controlChangeCategoryName>");
        Category category = Category.getCategoryByName(oldName);
        if (category == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Category not found.");
        try {
            category.setName(newName);
            return new Response(RequestStatus.SUCCESSFUL, "");
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage());
        }
    }

    public Response controlAddSpecialFieldToCategory(String categoryName, String filterKey, String filterValues) {
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First.");
        if (!(account instanceof Supervisor))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a supervisor.");
        Category category = Category.getCategoryByName(categoryName);
        if (category == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Category not found.");
        try {
            category.addField(filterKey, filterValues);
            return new Response(RequestStatus.SUCCESSFUL, "");
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage());
        }
    }

    public Response controlRemoveSpecialFieldFromCategory(String categoryName, String filterKey, String filterValue) {
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First.");
        if (!(account instanceof Supervisor))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a supervisor.");
        Category category = Category.getCategoryByName(categoryName);
        if (category == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Category not found.");
        try {
            category.removeField(filterKey, filterValue);
            return new Response(RequestStatus.SUCCESSFUL, "");
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE,exceptionalMassage.getMessage());
        }
    }

    public Response controlGetAllCategoriesName() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(
                Category.getAllCategoriesName()).getAsString());
    }

    public Response controlGetAllProductCategoriesName() {
        ArrayList<String> allProductCategoriesName = new ArrayList<>();
        for (String categoryName : Category.getAllCategoriesName()) {
            if (!Category.getCategoryByName(categoryName).isCategoryClassifier()) {
                allProductCategoriesName.add(categoryName);
            }
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(
                allProductCategoriesName).getAsString());
    }

    public Response controlGetAllCategoryCategoriesName() {
        ArrayList<String> allCategoryCategoriesName = new ArrayList<>();
        for (String categoryName : Category.getAllCategoriesName()) {
            if (Category.getCategoryByName(categoryName).isCategoryClassifier()) {
                allCategoryCategoriesName.add(categoryName);
            }
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(
                allCategoryCategoriesName).getAsString());

    }

    public Response controlGetCategoryParentName(String name) {
        return new Response(RequestStatus.SUCCESSFUL, Category.getCategoryByName(name).getParentCategoryName());
    }

    public Response isThisCategoryClassifier(String name) {
        return new Response(RequestStatus.SUCCESSFUL, String.valueOf(Category.getCategoryByName(name).isCategoryClassifier()));
    }

    //related to FilterAndSort
    public Response controlFilterGetAvailabilityFilter() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(filterAndSort.
                getAvailabilityFilter()));
    }

    public Response controlFilterGetSortType() {
        return new Response(RequestStatus.SUCCESSFUL, String.valueOf(filterAndSort.getSortType()));
    }

    public Response controlFilterGetNameFilter() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(filterAndSort.
                getNameFilter()).getAsString());
    }

    public Response controlFilterGetBrandFilter() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(filterAndSort.
                getBrandFilter()).getAsString());
    }

    public Response controlFilterGetPriceLowerBound() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(Objects.requireNonNullElse(
                filterAndSort.getPriceLowerBound(), -1)));
    }

    public Response controlFilterGetPriceUpperBound() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(Objects.requireNonNullElse(
                filterAndSort.getPriceUpperBound(), -1)));
    }

    public Response controlFilterGetCategory() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(filterAndSort.getCategory()));
    }

    public Response controlFilterGetSpecialFilter() {
        JsonObject jsonHashMap = new JsonObject();
        HashMap<String, ArrayList<String>> hashMap = filterAndSort.getSpecialFilter();
        for (String key : hashMap.keySet()) {
            jsonHashMap.add(key, Utils.convertStringArrayListToJsonElement(hashMap.get(key)));
        }
        return new Response(RequestStatus.SUCCESSFUL, jsonHashMap.getAsString());
    }

    public Response controlFilterSetAvailabilityFilter(String availabilityFilterStr) {
        boolean availabilityFilter = new JsonParser().parse(availabilityFilterStr).getAsBoolean();
        filterAndSort.setAvailabilityFilter(availabilityFilter);
        return new Response(RequestStatus.SUCCESSFUL, "");
    }

    public Response controlFilterSetPriceLowerBound(String priceLowerBoundStr) {
        int priceLowerBound = new JsonParser().parse(priceLowerBoundStr).getAsInt();
        try {
            filterAndSort.setPriceLowerBound(priceLowerBound);
            return new Response(RequestStatus.SUCCESSFUL, "");
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage());
        }
    }

    public Response controlFilterSetPriceUpperBound(String priceUpperBoundStr) {
        int priceUpperBound = new JsonParser().parse(priceUpperBoundStr).getAsInt();
        try {
            filterAndSort.setPriceUpperBound(priceUpperBound);
            return new Response(RequestStatus.SUCCESSFUL, "");
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage());
        }
    }

    public Response controlFilterSetCategoryFilter(String categoryName) {
        if (categoryName == null) {
            filterAndSort.setCategory(null);
        } else {
            Category category = Category.getCategoryByName(categoryName);
            if (category == null)
                return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Category not found");
            filterAndSort.setCategory(category);
        }
        return new Response(RequestStatus.SUCCESSFUL, "");
    }

    public Response controlFilterSetSortType(String sortType) {
        switch (sortType) {
            case "by number of views":
                filterAndSort.setSortType(SortType.BY_NUMBER_OF_VIEWS);
                break;
            case "by time added":
                filterAndSort.setSortType(SortType.BY_TIME);
                break;
            case "by score":
                filterAndSort.setSortType(SortType.BY_AVERAGE_SCORE);
                break;
            default:
                return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Invalid Argument");
        }
        return new Response(RequestStatus.SUCCESSFUL, "");
    }

    public Response controlFilterAddSpecialFilter(String key, String value) {
        try {
            filterAndSort.addSpecialFilter(key, value);
            return new Response(RequestStatus.SUCCESSFUL, "");
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage());
        }
    }

    public Response controlFilterRemoveSpecialFilter(String key, String value) {
        try {
            filterAndSort.removeSpecialFilter(key, value);
            return new Response(RequestStatus.SUCCESSFUL, "");
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage());
        }
    }

    public Response controlFilterRemoveAllSpecialFilter() {
        filterAndSort.removeAllSpecialFilter();
        return new Response(RequestStatus.SUCCESSFUL, "");
    }

    public Response controlFilterAddNameFilter(String name) {
        try {
            filterAndSort.addNameFilter(name);
            return new Response(RequestStatus.SUCCESSFUL, "");
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage());
        }
    }

    public Response controlFilterRemoveNameFilter(String name) {
        try {
            filterAndSort.removeNameFilter(name);
            return new Response(RequestStatus.SUCCESSFUL, "");
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage());
        }
    }

    public Response controlFilterAddBrandFilter(String brand) {
        try {
            filterAndSort.addBrandFilter(brand);
            return new Response(RequestStatus.SUCCESSFUL, "");
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage());
        }
    }

    public Response controlFilterRemoveBrandFilter(String brand) {
        try {
            filterAndSort.removeBrandFilter(brand);
            return new Response(RequestStatus.SUCCESSFUL, "");
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage());
        }
    }

    public Response controlFilterGetFilteredAndSortedProducts() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertProductArrayListToJsonElement(filterAndSort.
                getProducts()).getAsString());
    }

    public HashMap<String, ArrayList<String>> controlGetAllAvailableFilters() {
        return filterAndSort.getCategory().getAvailableSpecialFilters();
    }

    public Response controlCurrentFilters() {
        return new Response(RequestStatus.SUCCESSFUL, filterAndSort.toString());
    }

    public Response clearFilterAndSort() {
        filterAndSort.clear();
        return new Response(RequestStatus.SUCCESSFUL, "");
    }
}
