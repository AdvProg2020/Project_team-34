package controller;

import account.Account;
import account.Customer;
import account.Supervisor;
import account.Supplier;
import discount.Sale;
import exceptionalMassage.ExceptionalMassage;
import feedback.Comment;
import feedback.CommentState;
import feedback.Score;
import product.Category;
import product.Product;

import java.util.ArrayList;
import java.util.HashMap;

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


    public void controlAddProduct(String name, String nameOfCompany, int price, int remainedNumbers,
                                  String category, String description,HashMap<String, String> specifications) throws ExceptionalMassage {
        if (mainController.getAccount() == null)
            throw new ExceptionalMassage("Sing in first.");
        if(!(mainController.getAccount() instanceof Supplier))
            throw new ExceptionalMassage("Sign in as a Supplier");
        Supplier supplier = (Supplier) mainController.getAccount();
        Product  product;
        product = Product.getProductByName(name);
        if (Category.getCategoryByName(category) == null) {
            throw new ExceptionalMassage("Category not found.");
        }
        if (product == null)
            new Product(supplier, name, nameOfCompany, price, remainedNumbers, description, null, category, specifications);
        else {
            product.addNewSupplierForProduct(supplier,price,remainedNumbers);
        }
    }

    public void  controlRemoveProductById(String productId) throws ExceptionalMassage {
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new ExceptionalMassage("Invalid Id");
        }
        else {
            product.removeProduct();
        }
    }

    public String controlGetDigestInfosOfProduct(Product product) throws ExceptionalMassage{
        Product productGotById = Product.getProductById(product.getProductId());
        if (productGotById == null) {
            throw new ExceptionalMassage("Invalid Id");
        }
        else {
            StringBuilder result = new StringBuilder();
            result.append("description= ").append(product.getDescription()).append("\n");
            result.append("listOfSuppliers= " ).append(product.getListOfSuppliers()).append("\n");
            result.append("priceForEachSupplier= ").append(product.getPriceForEachSupplier()).append("\n");
            result.append("category= ").append(Category.getProductCategory(product)).append("\n");
            result.append("Score= ").append(Score.getAverageScoreForProduct(product)).append("\n");
            // !!
            //needs sale for all
            return String.valueOf(result);
        }
    }

    public String controlGetAttributesOfProduct(String productId) throws ExceptionalMassage{
        Product product = Product.getProductById(productId);
        if (product== null) {
            throw new ExceptionalMassage("Invalid Id");
        }
        else {
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

    public String controlCompare(String firstProductId , String secondProductId) throws ExceptionalMassage{
        Product firstProduct = Product.getProductById(firstProductId);
        Product secondProduct = Product.getProductById(secondProductId);
        if (firstProduct== null || secondProduct== null) {
            throw new ExceptionalMassage("Invalid Id");
        }
        else if(! (Category.getProductCategory(firstProduct).equals(Category.getProductCategory(secondProduct))))
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
        if (product== null) {
            throw new ExceptionalMassage("Invalid Id");
        }
        else {
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
                    case  "description":
                        newProduct.setDescription(value);
                        break;
                    default:
                        newProduct.editSpecialField(field,value);
                }
            }
        }
    }


    public ArrayList<Supplier> controlGetAllSuppliersForAProduct(Product product){
        return product.getListOfSuppliers();
    }

    public ArrayList<String> controlViewBuyersOfProduct(String productId) throws ExceptionalMassage{
        return null;
    }

    public boolean doesThisSupplierSellThisProduct(Supplier seller,Product product) throws ExceptionalMassage {
        if (mainController.getAccount() == null)
            throw new ExceptionalMassage("Sing in first.");
        return product.doesSupplierSellThisProduct(seller);
    }

    //related to Category:
    public void controlAddCategory(String name, boolean isParentCategory, String parentCategoryName)
            throws ExceptionalMassage {
        Account account = mainController.getAccount();
        if (account == null)
            throw new ExceptionalMassage("Login First. <Controller.controlAddCategory>");
        if (!(account instanceof Supervisor))
            throw new ExceptionalMassage("Login as a supervisor. <Controller.controlAddCategory>");
        Category.getInstance(name, isParentCategory, parentCategoryName);
    }

    public void controlRemoveCategory(String name) throws ExceptionalMassage {
        Account account = mainController.getAccount();
        if (account == null)
            throw new ExceptionalMassage("Login First. <Controller.controlRemoveCategory>");
        if (!(account instanceof Supervisor))
            throw new ExceptionalMassage("Login as a supervisor. <Controller.controlRemoveCategory>");
        Category.removeCategory(name);
    }

    public void controlAddProductToCategory(String categoryName, String productIdentifier) throws ExceptionalMassage {
        Account account = mainController.getAccount();
        if (account == null)
            throw new ExceptionalMassage("Login First. <Controller.controlAddProductToCategory>");
        if (!(account instanceof Supervisor))
            throw new ExceptionalMassage("Login as a supervisor. <Controller.controlAddProductToCategory>");
        Category category = Category.getCategoryByName(categoryName);
        if (category == null)
            throw new ExceptionalMassage("Category not found.");
        Product product = Product.getProductById(productIdentifier);
        if (product == null)
            throw new ExceptionalMassage("Product not found.");
        category.addProduct(product);
        //check
    }

    public void controlRemoveProductFromCategory(String categoryName, String productIdentifier)
            throws ExceptionalMassage {
        Account account = mainController.getAccount();
        //can modify
        if (account == null)
            throw new ExceptionalMassage("Login First. <Controller.controlRemoveProductFromCategory>");
        if (!(account instanceof Supervisor))
            throw new ExceptionalMassage("Login as a supervisor. <Controller.controlRemoveProductFromCategory>");
        Category category = Category.getCategoryByName(categoryName);
        if (category == null)
            throw new ExceptionalMassage("Category not found.");
        Product product = Product.getProductById(productIdentifier);
        if (product == null)
            throw new ExceptionalMassage("Product not found.");
        category.removeProduct(product);
        //check
    }

    public void controlChangeCategoryName(String oldName, String newName) throws ExceptionalMassage {
        Account account = mainController.getAccount();
        if (account == null)
            throw new ExceptionalMassage("Login First. <Controller.controlChangeCategoryName>");
        if (!(account instanceof Supervisor))
            throw new ExceptionalMassage("Login as a supervisor. <Controller.controlChangeCategoryName>");
        Category category = Category.getCategoryByName(oldName);
        if (category == null)
            throw new ExceptionalMassage("Category not found.");
        category.setName(newName);
    }

    public void controlAddSpecialFieldToCategory(String categoryName, String filterKey, String filterValues)
            throws ExceptionalMassage {
        Account account = mainController.getAccount();
        if (account == null)
            throw new ExceptionalMassage("Login First.");
        if (!(account instanceof Supervisor))
            throw new ExceptionalMassage("Login as a supervisor.");
        Category category = Category.getCategoryByName(categoryName);
        if (category == null)
            throw new ExceptionalMassage("Category not found.");
        category.addField(filterKey, filterValues);
    }

    public void controlRemoveSpecialFieldFromCategory(String categoryName, String filterKey, String filterValue)
            throws ExceptionalMassage {
        Account account = mainController.getAccount();
        if (account == null)
            throw new ExceptionalMassage("Login First.");
        if (!(account instanceof Supervisor))
            throw new ExceptionalMassage("Login as a supervisor.");
        Category category = Category.getCategoryByName(categoryName);
        if (category == null)
            throw new ExceptionalMassage("Category not found.");
        category.removeField(filterKey, filterValue);
    }

    public String controlGetAllCategories(){
        String allCategories = "";
        for (Category category : Category.allCategories) {
            String categoryDescription = "";
            categoryDescription += "Name: " + category.getName() + ", ";
            if (category.getParentCategory() == null) {
                categoryDescription += "Parent: " + null + ", ";
            } else {
                categoryDescription += "Parent: " + category.getParentCategory().getName() + ", ";
            }
            categoryDescription += "Is Category Classifier: " + category.isCategoryClassifier() + "\n";
            categoryDescription += "\t" + "Fields: " + category.getSpecialFields() + "\n";
            allCategories += categoryDescription;
        }
        return allCategories;
    }



    //related to feedback:

    public void controlAddCommentToProduct(String title,String content,Product product){
        //Need modification!
        new Comment((Customer) mainController.getAccount(), product,title, content, false);
    }

    public ArrayList<Comment> controlGetCommentsOfAProduct(Product product){
        ArrayList<Comment> productComment = new ArrayList<>();
        for (Comment comment : Comment.getComments()) {
            if(comment.getProduct() == product){
                productComment.add(comment);
            }
        }
        return productComment;
    }

    public void controlRateProductById(String id, float score) throws ExceptionalMassage{
        if(Product.getProductById(id) == null){
            throw new ExceptionalMassage("No such product with id!");
        }
        new Score(score, (Customer)(mainController.getAccount()), Product.getProductById(id));
    }

    public float controlGetAverageScoreByProduct(Product product){
        return  Score.getAverageScoreForProduct(product);
    }

    public ArrayList<Comment> controlGetConfirmedComments(){
        ArrayList<Comment> confirmedComments = new ArrayList<>();
        for (Comment comment : Comment.getComments()) {
            if(comment.getState() == CommentState.CONFIRMED){
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

    public ArrayList<String> controlGetArrayOfRequestId(){
        ArrayList<String> allRequests = new ArrayList<>();
        allRequests.addAll(Product.getAllProductRequestId());
        allRequests.addAll(Sale.getAllSaleRequestId());
        return allRequests;
    }

    public String controlShowDetailForRequest(String requestId) throws ExceptionalMassage{
        if(requestId.charAt(3) == 'P'){
            return Product.getDetailsForProductRequest(requestId);
        }
        else {
            return Sale.getDetailsForSaleRequest(requestId);
        }
    }

    public void controlAcceptOrDeclineRequest(String requestId, boolean isAccepted) throws ExceptionalMassage {
        if(requestId.charAt(3) == 'P'){
            Product.acceptOrDeclineRequest(requestId, isAccepted);
        }
        else {
            Sale.acceptOrDeclineRequest(requestId, isAccepted);
        }
    }




    //Filter and sort:
    public boolean controlFilterGetAvailabilityFilter() {
        return filterAndSort.getAvailabilityFilter();
    }

    public SortType controlFilterGetSortType() {
        return filterAndSort.getSortType();
    }

    public ArrayList<String> controlFilterGetNameFilter() {
        return filterAndSort.getNameFilter();
    }

    public ArrayList<String> controlFilterGetBrandFilter() {
        return filterAndSort.getBrandFilter();
    }

    public Integer controlFilterGetPriceLowerBound() {
        return filterAndSort.getPriceLowerBound();
    }

    public Integer controlFilterGetPriceUpperBound() {
        return filterAndSort.getPriceUpperBound();
    }

    public Category controlFilterGetCategory() {
        return filterAndSort.getCategory();
    }

    public HashMap<String, ArrayList<String>> controlFilterGetSpecialFilter() {
        return filterAndSort.getSpecialFilter();
    }

    public void controlFilterSetAvailabilityFilter(boolean availabilityFilter) {
        filterAndSort.setAvailabilityFilter(availabilityFilter);
    }

    public void controlFilterSetPriceLowerBound(int priceLowerBound) throws ExceptionalMassage {
        filterAndSort.setPriceLowerBound(priceLowerBound);
    }

    public void controlFilterSetPriceUpperBound(int priceUpperBound) throws ExceptionalMassage {
        filterAndSort.setPriceUpperBound(priceUpperBound);
    }

    public void controlFilterSetCategoryFilter(String categoryName) throws ExceptionalMassage {
        if (categoryName == null) {
            filterAndSort.setCategory(null);
        } else {
            Category category = Category.getCategoryByName(categoryName);
            if (category == null)
                throw new ExceptionalMassage("Category not found");
            filterAndSort.setCategory(category);
        }
        //explain
    }

    public void controlFilterSetSortType(String sortType) {
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
        }
    }

    public void controlFilterAddSpecialFilter(String key, String value) throws ExceptionalMassage {
        filterAndSort.addSpecialFilter(key, value);
    }

    public void controlFilterRemoveSpecialFilter(String key, String value) throws ExceptionalMassage {
        filterAndSort.removeSpecialFilter(key, value);
    }

    public void controlFilterAddNameFilter(String name) throws ExceptionalMassage {
        filterAndSort.addNameFilter(name);
    }

    public void controlFilterRemoveNameFilter(String name) throws ExceptionalMassage {
        filterAndSort.removeNameFilter(name);
    }

    public void controlFilterAddBrandFilter(String brand) throws ExceptionalMassage {
        filterAndSort.addBrandFilter(brand);
    }

    public void controlFilterRemoveBrandFilter(String brand) throws ExceptionalMassage {
        filterAndSort.removeBrandFilter(brand);
    }

    public ArrayList<Product> controlFilterGetFilteredAndSortedProducts() {
        return filterAndSort.getProducts();
    }

    public HashMap<String, ArrayList<String>> controlGetAllAvailableFilters(){
        return filterAndSort.getCategory().getAvailableSpecialFilters();
    }

    public String controlCurrentFilters(){
        return filterAndSort.toString();
    }

    public ArrayList<String> controlGetAllCategoriesName() {
        return Category.getAllCategoriesName();
    }

    public String controlGetCategoryParentName(String name) {
        return Category.getCategoryByName(name).getParentCategoryName();
    }

    public boolean isThisCategoryClassifier(String name) {
        return Category.getCategoryByName(name).isCategoryClassifier();
    }

    public HashMap<String, ArrayList<String>> controlGetCategorySpecialFields(String name) {
        return Category.getCategoryByName(name).getSpecialFields();
    }
}
