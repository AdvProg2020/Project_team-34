package controller;

import account.Account;
import account.Customer;
import account.Supervisor;
import account.Supplier;
import cart.Cart;
import exceptionalMassage.ExceptionalMassage;
import feedback.CommentState;
import feedback.Score;
import cart.ShippingInfo;
import discount.CodedDiscount;
import discount.Sale;
import feedback.Comment;
import product.Category;
import product.Product;
import request.Request;
import request.SaleRequest;

import java.util.*;

public class Controller {
    private Account account;
    private Cart cart;
    private boolean isFirstSupervisorCreated;
    private final FilterAndSort filterAndSort;

    public Controller() {
        account = null;
        cart = new Cart(null);
        isFirstSupervisorCreated = false;
        filterAndSort = new FilterAndSort();
    }

    public boolean hasSomeOneLoggedIn(){
        return account != null;
    }

    public String loggedInAccountType() {
        return account.getType();
    }

    public Account getAccount() {
        return account;
    }

    public void controlAddToCart(String productId, String supplierNameOfCompany) throws ExceptionalMassage {
        Product product = Product.getProductById(productId);
        if (product == null)
            throw new ExceptionalMassage("Product not found.");
        Supplier supplier = Supplier.getSupplierByCompanyName(supplierNameOfCompany);
        if (supplier == null)
            throw new ExceptionalMassage("Supplier not found.");
        cart.addProductToCart(product, supplier);
    }

    public void increaseProductQuantity(String productId, String supplierNameOfCompany) throws ExceptionalMassage {
        Product product = Product.getProductById(productId);
        if (product == null)
            throw new ExceptionalMassage("Product not found.");
        Supplier supplier = Supplier.getSupplierByCompanyName(supplierNameOfCompany);
        if (supplier == null)
            throw new ExceptionalMassage("Supplier not found.");
        cart.increaseProductCount(product, supplier);
    }

    public void decreaseProductQuantity(String productId, String supplierNameOfCompany) throws ExceptionalMassage {
        Product product = Product.getProductById(productId);
        if (product == null)
            throw new ExceptionalMassage("Product not found.");
        Supplier supplier = Supplier.getSupplierByCompanyName(supplierNameOfCompany);
        if (supplier == null)
            throw new ExceptionalMassage("Supplier not found.");
        cart.decreaseProductCount(product, supplier);
    }

    public Cart controlViewCart() {
        return cart;
    }

    public void controlSubmitShippingInfo(String firstName, String lastName, String city, String address,
                                          String postalCode, String phoneNumber) throws ExceptionalMassage {
        if (account == null)
            throw new ExceptionalMassage("Login First.");
        if (!(account instanceof Customer))
            throw new ExceptionalMassage("Login as a customer.");
        cart.submitShippingInfo(new ShippingInfo(firstName, lastName, city, address, postalCode, phoneNumber));
        //Modification required
    }

    public void controlRemoveShippingInfo() throws ExceptionalMassage {
        if (account == null)
            throw new ExceptionalMassage("Login First.");
        if (!(account instanceof Customer))
            throw new ExceptionalMassage("Login as a customer.");
        cart.removeShippingInfo();
    }

    public void controlSubmitDiscountCode(String discountCode) throws ExceptionalMassage {
        if (account == null)
            throw new ExceptionalMassage("Login First.");
        if (!(account instanceof Customer))
            throw new ExceptionalMassage("Login as a customer.");
        cart.applyCodedDiscount(discountCode);
    }

    public void controlRemoveDiscountCode() throws ExceptionalMassage {
        if (account == null)
            throw new ExceptionalMassage("Login First.");
        if (!(account instanceof Customer))
            throw new ExceptionalMassage("Login as a customer.");
        cart.removeCodedDiscount();
    }

    public boolean controlFinalizeOrder() {
        return false;
    }

    public void controlAddCategory(String name, boolean isParentCategory, String parentCategoryName)
            throws ExceptionalMassage {
        if (account == null)
            throw new ExceptionalMassage("Login First. <Controller.controlAddCategory>");
        if (!(account instanceof Supervisor))
            throw new ExceptionalMassage("Login as a supervisor. <Controller.controlAddCategory>");
        Category.getInstance(name, isParentCategory, parentCategoryName);
    }

    public void controlRemoveCategory(String name) throws ExceptionalMassage {
        if (account == null)
            throw new ExceptionalMassage("Login First. <Controller.controlRemoveCategory>");
        if (!(account instanceof Supervisor))
            throw new ExceptionalMassage("Login as a supervisor. <Controller.controlRemoveCategory>");
        Category.removeCategory(name);
    }

    public void controlAddProductToCategory(String categoryName, String productIdentifier) throws ExceptionalMassage {
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

    }

    public void controlRemoveSpecialFieldFromCategory(String categoryName, String filterKey)
            throws ExceptionalMassage {

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

    public void controlFilterSetSortType(SortType sortType) {
        filterAndSort.setSortType(sortType);
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
    //Filter and sort End

    private boolean doesAccountExist(String username) {
        return Account.getAccountByUsername(username) != null;
    }

    public void controlCreateAccount(String username, String type, String name, String familyName, String email,
                                     String phoneNumber, String password, int credit, String nameOfCompany) throws ExceptionalMassage {
        if (this.doesAccountExist(username))
            throw new ExceptionalMassage("Duplicate username");
        if (type.equals("customer")) {
            controlCreateCustomer(username, name, familyName, email, phoneNumber, password, credit);
            controlLogin(username, password);
        }
        if (type.equals("supplier")) {
            controlCreateSupplier(username, name, familyName, email, phoneNumber, password, credit, nameOfCompany);
            controlLogin(username, password);
        }
        if (type.equals("supervisor"))
            controlCreateSupervisor(username, name, familyName, email, phoneNumber, password, credit);
    }

    private void controlCreateCustomer(String username, String name, String familyName, String email, String phoneNumber,
                                       String password, int credit) {
        new Customer(username, name, familyName, email, phoneNumber, password, credit);
    }

    private void controlCreateSupplier(String username, String name, String familyName, String email, String phoneNumber,
                                       String password, int credit, String nameOfCompany) throws ExceptionalMassage {
        if (Supplier.getSupplierByCompanyName(nameOfCompany) != null) {
            throw new ExceptionalMassage("Duplicate company name.");
        }
        new Supplier(username, name, familyName, email, phoneNumber, password, credit, nameOfCompany);
    }

    private void controlCreateSupervisor(String username, String name, String familyName, String email,
                                         String phoneNumber, String password, int credit) throws ExceptionalMassage {
        if (isFirstSupervisorCreated) {
            if (account == null)
                throw new ExceptionalMassage("You must login as supervisor before create a supervisor account.");
            if (!(account instanceof Supervisor))
                throw new ExceptionalMassage("You must be a supervisor to create supervisor account.");
        }
        new Supervisor(username, name, familyName, email, phoneNumber, password, credit);
        isFirstSupervisorCreated = true;
    }

    public void controlLogin(String username, String password) throws ExceptionalMassage {
        if (hasSomeOneLoggedIn())
            throw new ExceptionalMassage("Logout first.");
        Account account = Account.getAccountByUsername(username);
        if (account == null)
            throw new ExceptionalMassage("Username doesn't exist.");
        if (!account.getPassword().equals(password))
            throw new ExceptionalMassage("Invalid password.");
        this.account = account;
        if (account instanceof Customer) {
            if (cart.isCartClear()) {
                cart = ((Customer) account).getCart();
            } else {
                if (((Customer) account).getCart().isCartClear()) {
                    ((Customer) account).setCart(cart);
                    cart.setOwner((Customer) account);
                } else {
                    cart = ((Customer) account).getCart();
                }
            }
        }
    }

    public void controlLogout() {
        account = null;
        cart = new Cart(null);
        filterAndSort.clear();
    }

    public String controlViewPersonalInfo() {
        return String.valueOf(account);
    }

    public Response controlEditField(String field, String newValue) {
        return null;
    }

    public String controlGetListOfAccounts() {
        ArrayList<String> allUsername = Account.getAllUsername();
        StringBuilder result = new StringBuilder();
        for (String username : allUsername) {
            result.append(username).append('\n');
        }
        return result.toString();
    }

    public String controlViewUserInfo(String username) throws ExceptionalMassage {
        Account account = Account.getAccountByUsername(username);
        if (account == null)
            throw new ExceptionalMassage("Account not found.");
        return account.toString();
    }

    public void controlDeleteUser(String username) throws ExceptionalMassage {
        Account accountGotByUsername = Account.getAccountByUsername(username);
        if (accountGotByUsername == null)
            throw new ExceptionalMassage("Account not found.");
        accountGotByUsername.removeAccount();
        //data base
    }

    public Response controlDeleteProduct(String productId) {
        Product product = Product.getProductById(productId);
        if (product == null)
            return Response.INVALID_PRODUCT_ID;
        product.removeProduct();
        return Response.OK;
    }

    public String controlGetListOfRequestId() {
        ArrayList<String> allId = Request.getAllRequestId();
        StringBuilder result = new StringBuilder();
        for (String requestId : allId) {
            result.append(requestId).append('\n');
        }

        return result.toString();
    }

    public String controlShowDetailForRequest(String requestId) {
        return String.valueOf(Request.getRequestById(requestId));
    }

    public Response controlAcceptOrDeclineRequest(String requestId, boolean isAccepted) {
        Request request = Request.getRequestById(requestId);
        if(request != null) {
            request.doRequest(isAccepted);
            return Response.OK;
        }
        return Response.INVALID_REQUEST_ID;
    }

    public String controlViewCompanyInfo() {
        if (account instanceof Supplier) {
            return ((Supplier) account).getNameOfCompany();
        }
        return null;
    }

    public String controlGetListOfProductsForThisSupplier() {
        if (account instanceof Supplier) {
            //Product.getProductIdForSupplier((Supplier)loggedInAccount);
        }
        return null;
    }

    public String controlShowProductDetail(String productId) {
        return String.valueOf(Product.getProductById(productId));
    }

    public void controlEditProductById(String productId, HashMap<String, String> fieldsToChange) {

    }

    public void controlAddProduct(String name, String nameOfCompany, int price, int remainedNumbers,
                                  Category category, String description) {
        Product newProduct, product;
        product = Product.getProductByName(name);
        if (product == null)
            newProduct = new Product((Supplier)account, name, nameOfCompany, price, remainedNumbers, category, description);
        else {
            /*
            ArrayList<Supplier > newSuppliers = new ArrayList<>(product.getListOfSuppliers());
            newSuppliers.add((Supplier)account);
            HashMap<Supplier,Integer> newPrice = new HashMap<>(product.getPriceForEachSupplier());
            newPrice.put((Supplier)account,price);
            HashMap<Supplier,Integer> newPrice = new HashMap<>(product.getPriceForEachSupplier());
            newPrice.put((Supplier)account,price);
            newProduct = new Product(name, nameOfCompany, newPrice, newSuppliers,newRemainedNumbers, category, description,
                    product.getComments(),product.getNumberOfViews(),product.getProductId());*/
        }
    }

    public void  controlRemoveProductById(String productId) throws ExceptionalMassage{
        Product product = Product.getProductById(productId);
        if (product != null) {
            product.removeProduct();

        }
    }

    public int controlViewBalance() {
        return (account).getCredit();
    }

    public String controlDigest() {
        return null;
    }

    public String ControlShowAttribute() {
        return null;
    }

    public String controlCompare(String firstProductId , String secondProductId) throws ExceptionalMassage{
        return null;
    }

    public String controlShowAvailableFilters(){
        return null;
    }

    public void controlFilter(String filter){

    }

    public String controlCurrentFilters(){
        return null;
    }

    public Response controlDisableFilter(String filter){
        return Response.OK;
    }

    public void controlSort(String typeOfSort) throws ExceptionalMassage{

    }

    public String controlShowCurrentSort(){
        return null;
    }

    public void controlDisableSort(){

    }

    //soheil :

    public ArrayList<CodedDiscount> controlGetAllCodedDiscounts(){
        return CodedDiscount.getCodedDiscounts();
    }

    public CodedDiscount controlGetDiscountByCode(String code) {
        for (CodedDiscount codedDiscount : CodedDiscount.getCodedDiscounts()) {
            if(codedDiscount.getDiscountCode().equals(code)){
                return codedDiscount;
            }
        }
        return null;
    }

    public void controlEditDiscountByCode (String code, Date newStartDate, Date newEndDate, int newPercent, int newMaxDiscount) throws ExceptionalMassage{
        if(controlGetDiscountByCode(code) == null){
            throw new ExceptionalMassage("No CodedDiscount with this Code");
        }
        controlGetDiscountByCode(code).setStart(newStartDate);
        controlGetDiscountByCode(code).setEnd(newEndDate);
        controlGetDiscountByCode(code).setPercent(newPercent);
        controlGetDiscountByCode(code).setMaxDiscountAmount(newMaxDiscount);
    }

    public void controlCreateCodedDiscount(String code,Date startDate, Date endDate, int percent, int maxDiscountAmount) throws ExceptionalMassage {
        for (CodedDiscount codedDiscount : controlGetAllCodedDiscounts()) {
            if(codedDiscount.getDiscountCode().equals(code)){
                throw new ExceptionalMassage("Code already exists!");
            }
        }
        new CodedDiscount(code,startDate, endDate, percent, maxDiscountAmount);
    }

    public void controlRemoveDiscountCode(String code) throws ExceptionalMassage{
        if(controlGetDiscountByCode(code) == null){
            throw new ExceptionalMassage("No such code!");
        }
        CodedDiscount.removeCodeFromList(controlGetDiscountByCode(code));
    }

    public void controlCreateSale(Date startDate, Date endDate, int percent, ArrayList<Product> products){
        Sale newSale = new Sale(startDate,endDate,percent);
        for (Product product : products) {
            newSale.addProductToSale(product);
        }
        new SaleRequest(null, newSale);
    }

    public ArrayList<Sale> controlGetAllSales(){
        return Sale.getSales();
    }

    public Sale controlGetSaleById(String id){
        for (Sale sale : Sale.getSales()) {
            if(sale.getOffId().equals(id)){
                return sale;
            }
        }
        return null;
    }

    public void controlEditSaleById(String id, Date newEndDate, Date newStartDate, int newPercent, ArrayList<Product> addingProduct, ArrayList<Product> removingProduct) throws ExceptionalMassage{
        if(controlGetSaleById(id) == null){
            throw new ExceptionalMassage("No such sale with this code!");
        }
        Sale newSale = new Sale(newStartDate,newEndDate,newPercent,id);
        for (Product product : controlGetSaleById(id).getProducts()) {
            newSale.addProductToSale(product);
        }
        for (Product product : addingProduct) {
            newSale.addProductToSale(product);
        }
        for (Product product : removingProduct) {
            newSale.removeProductFromSale(product);
        }
        new SaleRequest(controlGetSaleById(id),newSale);
    }

    public void controlRemoveSaleById(String id) throws ExceptionalMassage{
        if(controlGetSaleById(id) == null){
            throw new ExceptionalMassage("No such sale with id!");
        }
        Sale.getSales().remove(controlGetSaleById(id));
    }

    public ArrayList<CodedDiscount> controlGetCodedDiscountByCustomer(){
        ArrayList<CodedDiscount> codedDiscounts = new ArrayList<>();
        for (CodedDiscount codedDiscount : CodedDiscount.getCodedDiscounts()) {
            if(codedDiscount.getCustomers().contains((Customer)account)){
                codedDiscounts.add(codedDiscount);
            }
        }
        return codedDiscounts;
    }

    public void controlAddCommentToProduct(String title,String content,Product product){
        //Need modification!
        new Comment((Customer) account, product,title, content, false);
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

    public void controlRateProductById(String id, float score, Customer customer, Product product) throws ExceptionalMassage{
        if(Product.getProductById(id) == null){
            throw new ExceptionalMassage("No such product with id!");
        }
        new Score(score, customer, product);
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

    //Added methods Needed in menus!
    public String controlGetAllCategories(){
        //I need a printable string of all categories!
        return null;
    }

    public ArrayList<String> controlGetAllProducts(){
        //apply filters and sorts!
        return null;
    }

    public ArrayList<Supplier> controlGetAllSuppliersForAProduct(Product product){
        // method is clear from its name !
        return null;
    }

    public boolean doesThisSupplierSellsThisProduct(Product product){
        //method is clear from its name!
        return false;
    }

    public String controlGetDigestInfosOfProduct(Product product) throws ExceptionalMassage{
        // I need a Digest info of a product ! refer to Doc!
        return null;
    }

    public ArrayList<String> controlGetAllAvailableFilters(){
        // method is clear from its name!
        return null;
    }

    public ArrayList<String> controlGetAllAvailableSorts(){
        // method is clear from its name !
        return null;
    }

    public boolean isThisFilterAvailable(String filter){
        // method is clear from its name !
        return false;
    }

    public boolean isThisSortAvailable(String sort){
        // method is clear from its name !
        return false;
    }

    public String controlGetAttributesOfProduct(Product product){
        //return the attributes of a product in a string form!! refer to doc!
        return null;
    }

    public ArrayList<String> controlViewBuyersOfProduct(String productId) throws ExceptionalMassage{
        return null;
    }
}
