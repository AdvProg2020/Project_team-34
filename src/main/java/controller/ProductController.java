package controller;

import account.Account;
import account.Customer;
import account.Supervisor;
import account.Supplier;
import auction.Auction;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import discount.Sale;
import exceptionalMassage.ExceptionalMassage;
import feedback.Comment;
import feedback.CommentState;
import feedback.Score;
import javafx.scene.chart.ValueAxis;
import log.CustomerLog;
import product.Category;
import product.Product;
import server.communications.RequestStatus;
import server.communications.Response;
import server.communications.Utils;
import server.security.Validation;
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
                                      String description, String specification, String imageInStringForm,String filePath, String portStr) {
        try {
            Validation.normalStringValidation(name);
            Validation.normalStringValidation(nameOfCompany);
            Validation.normalIntValidation(price);
            Validation.normalIntValidation(remainedNumbers);
            Validation.categoryValidation(category);
            Validation.descriptionValidation(description);
            Validation.specificationValidation(specification);
            Validation.imageInStringFormValidation(imageInStringForm);
            Validation.filePathValidation(filePath);
            Validation.normalIntValidation(portStr);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        JsonParser parser = new JsonParser();
        int port = Integer.parseInt(portStr);
        HashMap<String, String> specifications = Utils.convertJsonElementStringToStringToHashMap(parser.parse(specification));
        if (mainController.getAccount() == null)
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Sing in first."), mainController);
        if (!(mainController.getAccount() instanceof Supplier))
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Sign in as a Supplier"), mainController);
        if(!specifications.keySet().containsAll(Category.getCategoryByName(category).getSpecialFields().keySet())){
            String error = "You have to enter a value for the category's special fields :\n";
            for (String s : Category.getCategoryByName(category).getSpecialFields().keySet()) {
                error += s + "\n";
            }
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage(error), mainController);
        }

        Supplier supplier = (Supplier) mainController.getAccount();
        Product product;
        product = Product.getProductByName(name);
        if (Category.getCategoryByName(category) == null) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Category not found.", mainController);
        }
//        if (product == null)
//            new Product(supplier, name, nameOfCompany, price, remainedNumbers, description, null, category, specifications, imageURL);
//        else {
//            product.addNewSupplierForProduct(supplier, price, remainedNumbers);
//        }
        Product product1 = new Product(supplier, name, nameOfCompany, Integer.parseInt(price), Integer.parseInt(remainedNumbers),
                description, null, category, specifications, imageInStringForm, filePath, port);
        System.out.println(product1);
        return Response.createSuccessResponse(mainController);
    }

    public Response controlRemoveProductById(String productId) {
        try {
            Validation.identifierValidation(productId);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Product product = Product.getProductById(productId);
        if (product == null) {
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Invalid Id"), mainController);
        } else {
            Product newProduct = new Product(product);
            newProduct.setProductState(State.PREPARING_TO_BE_DELETED);
            ArrayList<Supplier> newListOfSuppliers = new ArrayList<>();
            newListOfSuppliers.add((Supplier)mainController.getAccount());
            newProduct.setListOfSuppliers(newListOfSuppliers);
        }
        return Response.createSuccessResponse(mainController);
    }

    public Response controlEditProductById(String productId, String fieldToChange) {
        try {
            Validation.identifierValidation(productId);
            Validation.normalStringValidation(fieldToChange);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        JsonParser parser = new JsonParser();
        HashMap<String, String> fieldsToChange = Utils.convertJsonElementStringToStringToHashMap(parser.parse(fieldToChange));
        Product product = Product.getProductById(productId);
        if (product == null) {
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Invalid Id"), mainController);
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
                        newProduct.setImageInStringForm(value);
                    default:
                        try {
                            newProduct.editSpecialField(field, value);
                        } catch (ExceptionalMassage exceptionalMassage) {
                            return Response.createResponseFromExceptionalMassage(exceptionalMassage, mainController);
                        }
                }
            }
            newProduct.getListOfSuppliers().clear();
            newProduct.getListOfSuppliers().add((Supplier) mainController.getAccount());
            return Response.createSuccessResponse(mainController);
        }
    }

    public Response controlGetAllSuppliersForAProduct(String productId) {
        try {
            Validation.identifierValidation(productId);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Product product = Product.getProductById(productId);
        JsonElement suppliers = Utils.convertSupplierArrayListToJsonElement(product.getListOfSuppliers());
        return new Response(RequestStatus.SUCCESSFUL, suppliers.toString(), mainController);
    }

    public Response controlViewBuyersOfProduct(String productId) {
        try {
            Validation.identifierValidation(productId);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        JsonElement customers = Utils.convertCustomerArrayListToJsonElement(CustomerLog.getAllCustomersBoughtProduct(Product.getProductById(productId)));
        return new Response(RequestStatus.SUCCESSFUL, customers.toString(), mainController);
    }

    public Response doesThisSupplierSellThisProduct(String sellerUsername, String productId) {
        try {
            Validation.identifierValidation(productId);
            Validation.normalStringValidation(sellerUsername);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Supplier seller = (Supplier) Account.getAccountByUsernameWithinAvailable(sellerUsername);
        Product product = Product.getProductById(productId);
        if (mainController.getAccount() == null)
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Sing in first."), mainController);
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(String.valueOf(product.
                doesSupplierSellThisProduct(seller))), mainController);
    }

    //added by rpirayadi
    public Response controlGetAllCategoriesInACategory(String rootCategoryName){
        try {
            Validation.categoryValidation(rootCategoryName);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Category rootCategory = Category.getCategoryByName(rootCategoryName);
        JsonElement categories = Utils.convertCategoryArrayListToJsonElement(rootCategory.getAllCategoriesIn());
        return new Response(RequestStatus.SUCCESSFUL,categories.toString(), mainController);
    }
    //added by rpirayadi
    public Response controlGetAllProductCategory (){
        String category = Utils.convertObjectToJsonString(Category.getSuperCategory());
        return new Response(RequestStatus.SUCCESSFUL,category, mainController);
    }

    //related to feedback:
    public Response controlAddCommentToProduct(String title, String content, String productId){
        try {
            Validation.normalStringValidation(title);
            Validation.identifierValidation(productId);
            Validation.messageValidation(content);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Product product = Product.getProductById(productId);
        if (!(mainController.getAccount() instanceof Customer)){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Sign in as a customer first!"), mainController);
        }
        new Comment((Customer) mainController.getAccount(), product, title, content, controlHasCustomerBoughtThisProductInternal((Customer)mainController.getAccount(),product));
        return Response.createSuccessResponse(mainController);
    }

    public Response controlGetCommentsOfAProduct(String productId) {
        try {
            Validation.identifierValidation(productId);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Product product = Product.getProductById(productId);
        ArrayList<Comment> productComment = new ArrayList<>();
        for (Comment comment : Comment.getComments()) {
            if (comment.getProduct() == product) {
                productComment.add(comment);
            }
        }
        JsonElement response = Utils.convertCommentArrayListToJsonElement(productComment);
        return new Response(RequestStatus.SUCCESSFUL, response.toString(), mainController);
    }

    public Response controlRateProductById(String id, String score)  {
        try {
            Validation.identifierValidation(id);
            Validation.floatValidation(score);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        if (Product.getProductById(id) == null) {
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("No such product with id!"), mainController);
        } else if (!(mainController.getAccount() instanceof Customer)){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Sign in as customer first!"), mainController);
        } else if (!controlHasCustomerBoughtThisProductInternal((Customer)mainController.getAccount(),Product.getProductById(id))) {
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("You haven't bought this product yet!"), mainController);
        } else if (Score.hasCustomerRateThisProduct(Product.getProductById(id),(Customer)mainController.getAccount())){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("You can't rate again!"), mainController);
        }
        new Score(Float.valueOf(score), (Customer) (mainController.getAccount()), Product.getProductById(id));
        return Response.createSuccessResponse(mainController);
    }

    public Response controlGetAverageScoreByProduct(String productId) {
        try {
            Validation.identifierValidation(productId);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Product product = Product.getProductById(productId);
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(String.valueOf(Score.
                getAverageScoreForProduct(product))), mainController);
    }

    public Response controlGetConfirmedComments() {
        ArrayList<Comment> confirmedComments = new ArrayList<>();
        for (Comment comment : Comment.getComments()) {
            if (comment.getState() == CommentState.CONFIRMED) {
                confirmedComments.add(comment);
            }
        }
        JsonElement jsonComments = Utils.convertCommentArrayListToJsonElement(confirmedComments);
        return new Response(RequestStatus.SUCCESSFUL, jsonComments.toString(), mainController);
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
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(result.toString()), mainController);
    }

    public Response controlGetArrayOfRequestId() {
        ArrayList<String> allRequests = new ArrayList<>();
        allRequests.addAll(Product.getAllProductRequestId());
        allRequests.addAll(Sale.getAllSaleRequestId());
        JsonElement requestsJson = Utils.convertStringArrayListToJsonElement(allRequests);
        return new Response(RequestStatus.SUCCESSFUL,requestsJson.toString(), mainController);
    }

    public Response controlShowDetailForRequest(String requestId) {
        try {
            Validation.identifierValidation(requestId);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        if (requestId.charAt(3) == 'P') {
            return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(Product.getDetailsForProductRequest(requestId)), mainController);
        } else {
            try {
                return new Response(RequestStatus.SUCCESSFUL,Utils.convertObjectToJsonString(Sale.getDetailsForSaleRequest(requestId)), mainController);
            } catch (ExceptionalMassage exceptionalMassage) {
                return Response.createResponseFromExceptionalMassage(exceptionalMassage, mainController);
            }
        }
    }

    public Response controlGetEnumForRequest(String requestId)  {
        try {
            Validation.identifierValidation(requestId);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        if(requestId.charAt(3) == 'P'){
            return new Response(RequestStatus.SUCCESSFUL,Utils.convertObjectToJsonString(Product.getProductById(Product.convertRequestIdToProductId(requestId)).getProductState()), mainController);
        } else {
            return new Response(RequestStatus.SUCCESSFUL,Utils.convertObjectToJsonString(Sale.getSaleById(Sale.convertRequestIdToSaleId(requestId)).getState()), mainController);
        }
    }

    public Response controlAcceptOrDeclineRequest(String requestId, String isAccepted) {
        try {
            Validation.identifierValidation(requestId);
            Validation.booleanValidation(isAccepted);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        if (requestId.charAt(3) == 'P') {
            try {
                Product.acceptOrDeclineRequest(requestId, Boolean.parseBoolean(isAccepted));
            } catch (ExceptionalMassage exceptionalMassage) {
                return Response.createResponseFromExceptionalMassage(exceptionalMassage, mainController);
            }
        } else {
            try {
                Sale.acceptOrDeclineRequest(requestId, Boolean.parseBoolean(isAccepted));
            } catch (ExceptionalMassage exceptionalMassage) {
                return Response.createResponseFromExceptionalMassage(exceptionalMassage, mainController);
            }
        }
        return Response.createSuccessResponse(mainController);
    }

    public Response controlGetCategorySpecialFields(String name) {
        try {
            Validation.normalStringValidation(name);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        HashMap<String, ArrayList<String>> specialFields = Category.getCategoryByName(name).getSpecialFields();
        if(specialFields == null){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("This is a parent category!"), mainController);
        }
        JsonElement jsonElement = Utils.convertStringToStringArrayListHashMapToJsonElement(specialFields);
        return new Response(RequestStatus.SUCCESSFUL, jsonElement.toString(), mainController);
    }

    public Response controlHasCustomerBoughtThisProduct(String customerUsername, String productId){
        try {
            Validation.normalStringValidation(customerUsername);
            Validation.identifierValidation(productId);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Customer customer = (Customer) Account.getAccountByUsernameWithinAvailable(customerUsername);
        Product product = Product.getProductById(productId);
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(String.valueOf(CustomerLog.
                getAllCustomersBoughtProduct(product).contains(customer))), mainController);
    }

    public boolean controlHasCustomerBoughtThisProductInternal(Customer customer, Product product){
        return CustomerLog.getAllCustomersBoughtProduct(product).contains(customer);
    }

    public Response controlViewThisProduct(String productId){
        try {
            Validation.identifierValidation(productId);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Product product = Product.getProductById(productId);
        product.setNumberOfViews(product.getNumberOfViews()+ 1);
        return Response.createSuccessResponse(mainController);
    }

    public Response getCustomersBoughtProductObservable(String productId ,String supplierUsername) {
        try {
            Validation.identifierValidation(productId);
            Validation.userPassStringValidation(supplierUsername);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Product product = Product.getProductById(productId);
        Supplier supplier = (Supplier) Account.getAccountByUsernameWithinAvailable(supplierUsername);
        JsonElement returning = Utils.convertCustomerArrayListToJsonElement(CustomerLog.getAllCustomersBoughtProductFromSupplier(product, supplier));
        return new Response(RequestStatus.SUCCESSFUL,returning.toString(), mainController);
    }

    //related to Category:
    public Response controlAddCategory(String name, String isParentCategoryStr, String parentCategoryName) {
        try {
            Validation.normalStringValidation(name);
            Validation.booleanValidation(isParentCategoryStr);
            Validation.categoryValidation(parentCategoryName);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        boolean isParentCategory = Boolean.parseBoolean(isParentCategoryStr);
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First. <Controller.controlAddCategory>", mainController);
        if (!(account instanceof Supervisor))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a supervisor. <Controller.controlAddCategory>", mainController);
        try {
            Category.getInstance(name, isParentCategory, parentCategoryName);
            return new Response(RequestStatus.SUCCESSFUL, "", mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage(), mainController);
        }
    }

    public Response controlRemoveCategory(String name) {
        try {
            Validation.normalStringValidation(name);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First. <Controller.controlRemoveCategory>", mainController);
        if (!(account instanceof Supervisor))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a supervisor. <Controller.controlRemoveCategory>", mainController);
        try {
            Category.removeCategory(name);
            return new Response(RequestStatus.SUCCESSFUL, "", mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage(), mainController);
        }
    }

    public Response controlAddProductToCategory(String categoryName, String productIdentifier) {
        try {
            Validation.categoryValidation(categoryName);
            Validation.identifierValidation(productIdentifier);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First. <Controller.controlAddProductToCategory>", mainController);
        if (!(account instanceof Supervisor))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a supervisor. <Controller.controlAddProductToCategory>", mainController);
        Category category = Category.getCategoryByName(categoryName);
        if (category == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Category not found.", mainController);
        Product product = Product.getProductById(productIdentifier);
        if (product == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Product not found.", mainController);
        try {
            category.addProduct(product);
            return new Response(RequestStatus.SUCCESSFUL, "", mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage(), mainController);
        }
    }

    public Response controlRemoveProductFromCategory(String categoryName, String productIdentifier) {
        try {
            Validation.categoryValidation(categoryName);
            Validation.identifierValidation(productIdentifier);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Account account = mainController.getAccount();
        //can modify
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First. <Controller.controlRemoveProductFromCategory>", mainController);
        if (!(account instanceof Supervisor))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a supervisor. <Controller.controlRemoveProductFromCategory>", mainController);
        Category category = Category.getCategoryByName(categoryName);
        if (category == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Category not found.", mainController);
        Product product = Product.getProductById(productIdentifier);
        if (product == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Product not found.", mainController);
        try {
            category.removeProduct(product);
            return new Response(RequestStatus.SUCCESSFUL, "", mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage(), mainController);
        }
        //check
    }

    public Response controlChangeCategoryName(String oldName, String newName) {
        try {
            Validation.categoryValidation(oldName);
            Validation.normalStringValidation(newName);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First. <Controller.controlChangeCategoryName>", mainController);
        if (!(account instanceof Supervisor))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a supervisor. <Controller.controlChangeCategoryName>", mainController);
        Category category = Category.getCategoryByName(oldName);
        if (category == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Category not found.", mainController);
        try {
            category.setName(newName);
            return new Response(RequestStatus.SUCCESSFUL, "", mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage(), mainController);
        }
    }

    public Response controlAddSpecialFieldToCategory(String categoryName, String filterKey, String filterValues) {
        try {
            Validation.categoryValidation(categoryName);
            Validation.normalStringValidation(filterKey);
            //TODO
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First.", mainController);
        if (!(account instanceof Supervisor))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a supervisor.", mainController);
        Category category = Category.getCategoryByName(categoryName);
        if (category == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Category not found.", mainController);
        try {
            category.addField(filterKey, filterValues);
            return new Response(RequestStatus.SUCCESSFUL, "", mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage(), mainController);
        }
    }

    public Response controlRemoveSpecialFieldFromCategory(String categoryName, String filterKey, String filterValue) {
        try {
            Validation.categoryValidation(categoryName);
            Validation.normalStringValidation(filterKey);
            //TODO
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First.", mainController);
        if (!(account instanceof Supervisor))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a supervisor.", mainController);
        Category category = Category.getCategoryByName(categoryName);
        if (category == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Category not found.", mainController);
        try {
            category.removeField(filterKey, filterValue);
            return new Response(RequestStatus.SUCCESSFUL, "", mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE,exceptionalMassage.getMessage(), mainController);
        }
    }

    public Response controlGetAllCategoriesName() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(
                Category.getAllCategoriesName()).toString(), mainController);
    }

    public Response controlGetAllProductCategoriesName() {
        ArrayList<String> allProductCategoriesName = new ArrayList<>();
        for (String categoryName : Category.getAllCategoriesName()) {
            if (!Category.getCategoryByName(categoryName).isCategoryClassifier()) {
                allProductCategoriesName.add(categoryName);
            }
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(
                allProductCategoriesName).toString(), mainController);
    }

    public Response controlGetAllCategoryCategoriesName() {
        ArrayList<String> allCategoryCategoriesName = new ArrayList<>();
        for (String categoryName : Category.getAllCategoriesName()) {
            if (Category.getCategoryByName(categoryName).isCategoryClassifier()) {
                allCategoryCategoriesName.add(categoryName);
            }
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(
                allCategoryCategoriesName).toString(), mainController);

    }

    public Response controlGetCategoryParentName(String name) {
        try {
            Validation.categoryValidation(name);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
//        String parentCategoryName = Category.getCategoryByName(name).getParentCategoryName();
//        Response response ;
//        if(parentCategoryName == null)
//            response = new Response(RequestStatus.SUCCESSFUL,"ap" );
//        else
//            response = new Response(RequestStatus.SUCCESSFUL, parentCategoryName);
//        return response;
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(Category.getCategoryByName(name).getParentCategoryName()), mainController);
    }

    public Response isThisCategoryClassifier(String name) {
        try {
            Validation.categoryValidation(name);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(String.valueOf(Category.
                getCategoryByName(name).isCategoryClassifier())), mainController);
    }

    //related to FilterAndSort
    public Response controlFilterGetAvailabilityFilter() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(filterAndSort.
                getAvailabilityFilter()), mainController);
    }

    public Response controlFilterGetSortType() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(String.valueOf(filterAndSort.
                getSortType())), mainController);
    }

    public Response controlFilterGetNameFilter() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(filterAndSort.
                getNameFilter()).toString(), mainController);
    }

    public Response controlFilterGetBrandFilter() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(filterAndSort.
                getBrandFilter()).toString(), mainController);
    }

    public Response controlFilterGetPriceLowerBound() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(Objects.requireNonNullElse(
                filterAndSort.getPriceLowerBound(), -1)), mainController);
    }

    public Response controlFilterGetPriceUpperBound() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(Objects.requireNonNullElse(
                filterAndSort.getPriceUpperBound(), -1)), mainController);
    }

    public Response controlFilterGetCategory() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(filterAndSort.getCategory()), mainController);
    }

    public Response controlFilterGetSpecialFilter() {
        JsonObject jsonHashMap = new JsonObject();
        HashMap<String, ArrayList<String>> hashMap = filterAndSort.getSpecialFilter();
        for (String key : hashMap.keySet()) {
            jsonHashMap.add(key, Utils.convertStringArrayListToJsonElement(hashMap.get(key)));
        }
        return new Response(RequestStatus.SUCCESSFUL, jsonHashMap.toString(), mainController);
    }

    public Response controlFilterSetAvailabilityFilter(String availabilityFilterStr) {
        try {
            Validation.booleanValidation(availabilityFilterStr);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        boolean availabilityFilter = new JsonParser().parse(availabilityFilterStr).getAsBoolean();
        filterAndSort.setAvailabilityFilter(availabilityFilter);
        return new Response(RequestStatus.SUCCESSFUL, "", mainController);
    }

    public Response controlFilterSetOnlyInAuctionFilter(String onlyInAuctionString){
        try {
            Validation.booleanValidation(onlyInAuctionString);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        boolean onlyInAuction = new JsonParser().parse(onlyInAuctionString).getAsBoolean();
        filterAndSort.setInAuctionOnly(onlyInAuction);
        return new Response(RequestStatus.SUCCESSFUL, "", mainController);
    }


    public Response controlFilterSetPriceLowerBound(String priceLowerBoundStr) {
        try {
            Validation.normalIntValidation(priceLowerBoundStr);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        int priceLowerBound = new JsonParser().parse(priceLowerBoundStr).getAsInt();
        try {
            filterAndSort.setPriceLowerBound(priceLowerBound);
            return new Response(RequestStatus.SUCCESSFUL, "", mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage(), mainController);
        }
    }

    public Response controlFilterSetPriceUpperBound(String priceUpperBoundStr) {
        try {
            Validation.normalIntValidation(priceUpperBoundStr);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        int priceUpperBound = new JsonParser().parse(priceUpperBoundStr).getAsInt();
        try {
            filterAndSort.setPriceUpperBound(priceUpperBound);
            return new Response(RequestStatus.SUCCESSFUL, "", mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage(), mainController);
        }
    }

    public Response controlFilterSetInSaleOnly(String inSaleOnlyString){
        try {
            Validation.booleanValidation(inSaleOnlyString);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        boolean inSaleOnly = Boolean.parseBoolean(inSaleOnlyString);
        filterAndSort.setInSaleOnly(inSaleOnly);
        return Response.createSuccessResponse(mainController);
    }

    public Response controlFilterSetCategoryFilter(String categoryName) {
        if (categoryName == null) {
            filterAndSort.setCategory(null);
        } else {
            Category category = Category.getCategoryByName(categoryName);
            if (category == null)
                return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Category not found", mainController);
            filterAndSort.setCategory(category);
        }
        return new Response(RequestStatus.SUCCESSFUL, "", mainController);
    }

    public Response controlFilterSetSortType(String sortType) {
        try {
            Validation.normalStringValidation(sortType);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
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
                return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Invalid Argument", mainController);
        }
        return new Response(RequestStatus.SUCCESSFUL, "", mainController);
    }

    public Response controlFilterAddSpecialFilter(String key, String value) {
        try {
            Validation.normalStringValidation(key);
            Validation.normalStringValidation(value);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        try {
            filterAndSort.addSpecialFilter(key, value);
            return new Response(RequestStatus.SUCCESSFUL, "", mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage(), mainController);
        }
    }

    public Response controlFilterRemoveSpecialFilter(String key, String value) {
        try {
            Validation.normalStringValidation(key);
            Validation.normalStringValidation(value);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        try {
            filterAndSort.removeSpecialFilter(key, value);
            return new Response(RequestStatus.SUCCESSFUL, "", mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage(), mainController);
        }
    }

    public Response controlFilterRemoveAllSpecialFilter() {
        filterAndSort.removeAllSpecialFilter();
        return new Response(RequestStatus.SUCCESSFUL, "", mainController);
    }

    public Response controlFilterAddNameFilter(String name) {
        try {
            Validation.normalStringValidation(name);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        try {
            filterAndSort.addNameFilter(name);
            return new Response(RequestStatus.SUCCESSFUL, "", mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage(), mainController);
        }
    }

    public Response controlFilterRemoveNameFilter(String name) {
        try {
            Validation.normalStringValidation(name);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        try {
            filterAndSort.removeNameFilter(name);
            return new Response(RequestStatus.SUCCESSFUL, "", mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage(), mainController);
        }
    }

    public Response controlFilterAddSupplierFilter(String supplierName){
        try {
            Validation.normalStringValidation(supplierName);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        try {
            filterAndSort.addSupplierFilter(supplierName);
            return Response.createSuccessResponse(mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return Response.createResponseFromExceptionalMassage(exceptionalMassage, mainController);
        }
    }

    public Response controlFilterRemoveSupplierFilter(String supplierName){
        try {
            Validation.normalStringValidation(supplierName);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        try {
            filterAndSort.removeSupplierFilter(supplierName);
            return Response.createSuccessResponse(mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return Response.createResponseFromExceptionalMassage(exceptionalMassage, mainController);
        }
    }

    public Response controlFilterAddBrandFilter(String brand) {
        try {
            Validation.normalStringValidation(brand);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        try {
            filterAndSort.addBrandFilter(brand);
            return new Response(RequestStatus.SUCCESSFUL, "", mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage(), mainController);
        }
    }

    public Response controlFilterRemoveBrandFilter(String brand) {
        try {
            Validation.normalStringValidation(brand);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        try {
            filterAndSort.removeBrandFilter(brand);
            return new Response(RequestStatus.SUCCESSFUL, "", mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage(), mainController);
        }
    }

    public Response controlFilterGetFilteredAndSortedProducts() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertProductArrayListToJsonElement(filterAndSort.
                getProducts()).toString(), mainController);
    }

    public Response controlGetAllAvailableFilters() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringToStringArrayListHashMapToJsonElement(filterAndSort.getCategory().getAvailableSpecialFilters()).toString(), mainController);
    }

    public Response controlCurrentFilters() {
        return new Response(RequestStatus.SUCCESSFUL,Utils.convertObjectToJsonString(filterAndSort.toString()), mainController);
    }

    public Response clearFilterAndSort() {
        filterAndSort.clear();
        return new Response(RequestStatus.SUCCESSFUL, "", mainController);
    }

    public Response getAllSuppliersThatHaveAvailableProduct(String productId){
        try {
            Validation.identifierValidation(productId);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Product product = Product.getProductById(productId);
        ArrayList<Supplier> suppliers = product.getAllSuppliersThatHaveAvailableProduct();
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertSupplierArrayListToJsonElement(suppliers).toString(), mainController);
    }

    public Response getProductByName(String name){
        try {
            Validation.normalStringValidation(name);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Product product = Product.getProductByName(name);
        return new Response(RequestStatus.SUCCESSFUL,Utils.convertObjectToJsonString(product), mainController);
    }

    public Response getProductForSupplier(String supplierUsername){
        try {
            Validation.userPassStringValidation(supplierUsername);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Supplier supplier = (Supplier) Account.getAccountByUsernameWithinAvailable(supplierUsername);
        ArrayList<Product> products = Product.getProductForSupplier(supplier);
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertProductArrayListToJsonElement(products).toString(), mainController);
    }

    public Response getProductById(String id){
        try {
            Validation.identifierValidation(id);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(Product.getProductById(id)), mainController);
    }

    public Response promoteAuctionPrice(String newPrice,String minimum,String auctionId){
        try {
            Validation.normalIntValidation(newPrice);
            Validation.normalIntValidation(minimum);
            Validation.identifierValidation(auctionId);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        if(!(mainController.getAccount() instanceof Customer)){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Login as Customer!"), mainController);
        }
        Customer customer = (Customer) mainController.getAccount();
        int minimumAmountOfCredit = Integer.parseInt(minimum);
        int price = Integer.parseInt(newPrice);
        Auction auction = Auction.getAuctionByIdentifier(auctionId);
        try {
            auction.promote(customer, price, minimumAmountOfCredit);
            return Response.createSuccessResponse(mainController);
        } catch (ExceptionalMassage ex){
            return Response.createResponseFromExceptionalMassage(ex, mainController);
        }
    }

    public Response controlGetAuctionsForASupplier(){
        if(!(mainController.getAccount() instanceof Supplier)){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Login as Supplier!"), mainController);
        }
        Supplier supplier =(Supplier) mainController.getAccount();
        ArrayList<Auction> allAuctions = Auction.getAllAuctions();
        ArrayList<String> auctionsIds = new ArrayList<>();
        for (Auction allAuction : allAuctions) {
            if(allAuction.getSupplier().equals(supplier) && allAuction.isActive()){
                auctionsIds.add(allAuction.getIdentifier());
            }
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(auctionsIds).toString(), mainController);
    }

    public Response controlGetNotAuctionedProductsOfSupplier(){
        if(!(mainController.getAccount() instanceof Supplier)){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Login as Supplier!"), mainController);
        }
        Supplier supplier = (Supplier) mainController.getAccount();
        ArrayList<Product> products = Product.getProductForSupplier(supplier);
        ArrayList<Product> notAuctionedProducts = new ArrayList<>();
        for (Product product : products) {
            if(!Auction.isThisProductInAuction(product,supplier)){
                notAuctionedProducts.add(product);
            }
        }
        return new Response(RequestStatus.SUCCESSFUL,Utils.convertProductArrayListToJsonElement(notAuctionedProducts).toString(), mainController);
    }

    public Response controlAddAuction(String productId,String dateString){
        try {
            Validation.identifierValidation(productId);
            Validation.dateValidation(dateString);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        if(!(mainController.getAccount() instanceof Supplier)){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Login as Supplier!"), mainController);
        }
        Supplier supplier = (Supplier) mainController.getAccount();
        Product product = Product.getProductById(productId);
        if(product.getRemainedNumberForEachSupplier().get(supplier) == 0) {
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Product sold out!"), mainController);
        }
        Long date = Long.parseLong(dateString);
        int wage = mainController.getAccountController().controlGetWageInternal();
        new Auction(product,supplier,date,wage);
        return Response.createSuccessResponse(mainController);
    }

    public Response controlGetAuctionById(String id){
        try {
            Validation.identifierValidation(id);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Auction auction = Auction.getAuctionByIdentifier(id);
        if(auction == null){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Auction not found!"), mainController);
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(auction), mainController);
    }

    public Response controlGetAuctionForProduct(String productId){
        try {
            Validation.identifierValidation(productId);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Product product = Product.getProductById(productId);
        Auction auction = Auction.getAuctionForProduct(product, product.getListOfSuppliers().get(0));
        String result;
        if(auction == null){
            result = "";
        }else {
            result = auction.toJson();
        }
        return new Response(RequestStatus.SUCCESSFUL,result, mainController);
    }

    public Response controlRemoveProductWithThisPort(String portString){
        try {
            Validation.normalIntValidation(portString);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        try {
            Product.removeProductsWithThisSupplierPort(Integer.parseInt(portString));
            return Response.createSuccessResponse(mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return Response.createResponseFromExceptionalMassage(exceptionalMassage, mainController);
        }
    }

    public Response controlRemoveProductForSupervisor(String productId){
        try {
            Validation.identifierValidation(productId);
        }catch (ExceptionalMassage e){
            return Response.createResponseFromExceptionalMassage(e, mainController);
        }
        Product product = Product.getProductById(productId);
        if(product == null){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Product not found!"), mainController);
        }
        product.setProductState(State.DELETED);
        try {
            Category.getProductCategory(product).removeProduct(product);
        } catch (ExceptionalMassage ex){
            return Response.createResponseFromExceptionalMassage(ex, mainController);
        }
        return Response.createSuccessResponse(mainController);
    }
}
