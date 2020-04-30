package controller;

import account.Account;
import account.Customer;
import account.Supervisor;
import account.Supplier;
import cart.Cart;
import exceptionalMassage.ExceptionalMassage;
import feedback.CommentState;
import feedback.Score;
import log.ShippingInfo;
import menu.menuAbstract.Menu;
import discount.CodedDiscount;
import discount.Sale;
import feedback.Comment;
import product.Category;
import product.Product;
import request.Request;
import request.SaleRequest;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Controller {
    private Account account;
    private Cart cart;
    private Menu menu;
    private boolean isFirstSupervisorCreated;

    public boolean hasSomeOneLoggedIn(){
        return false;
    }

    public Account getAccount() {
        return account;
    }

    public boolean controlAddToCart() {
        return false;
    }

    public Cart controlViewCart() throws ExceptionalMassage {
        if (!account.getType().equals("Customer")) {
            throw new ExceptionalMassage("Cart is only for customer account.");
        }
        return cart;
    }

    public void controlSubmitShippingInfo(String firstName, String lastName, String city, String address, long postalCode, long phoneNumber) throws Exception {
        cart.submitShippingInfo(new ShippingInfo(firstName, lastName, city, address, postalCode, phoneNumber));
    }

    public void controlRemoveShippingInfo() throws ExceptionalMassage {
        cart.removeShippingInfo();
    }

    public void controlSubmitDiscountCode(String discountCode) throws Exception {
        cart.applyCodedDiscount(discountCode);
    }

    public void controlRemoveDiscountCode() throws Exception {
        cart.removeCodedDiscount();
    }

    public boolean controlFinalizeOrder() {
        return false;
    }

    public void controlAddCategory(String name, boolean isParentCategory, String parentCategoryName) throws ExceptionalMassage {
        Category parentCategory = Category.getCategoryByName(parentCategoryName);
        if (parentCategory == null) {
            throw new ExceptionalMassage("Parent category not found.");
        }
        Category newCategory = new Category(name, isParentCategory, parentCategory);
    }

    public void controlRemoveCategory(String name) throws ExceptionalMassage {
        Category category = Category.getCategoryByName(name);
        if (category == null) {
            throw new ExceptionalMassage("Category not found.");
        }
        (category.getParentCategory()).removeSubCategory(category);
    }

    public void controlAddProductToCategory(String categoryName, String productIdentifier) throws ExceptionalMassage {
        Category category = Category.getCategoryByName(categoryName);
        Product product = Product.getProductById(productIdentifier);
        if (category == null) {
            throw new ExceptionalMassage("Category not found.");
        }
        if (product == null) {
            throw new ExceptionalMassage("Product not found.");
        }
        category.addProduct(product);
    }

    public void controlRemoveProductFromCategory(String categoryName, String productIdentifier) throws ExceptionalMassage {
        Category category = Category.getCategoryByName(categoryName);
        Product product = Product.getProductById(productIdentifier);
        if (category == null) {
            throw new ExceptionalMassage("Category not found.");
        }
        if (product == null) {
            throw new ExceptionalMassage("Product not found.");
        }
        category.removeProduct(product);
    }

    private boolean doesAccountExist(String username) {
        if (Account.getAccountByUsername(username) == null)
            return false;
        return true;
    }

    public void controlCreateAccount(String username, String type, String name, String familyName, String email, String phoneNumber, String password, int credit, String nameOfCompany) throws ExceptionalMassage {
        if (doesAccountExist(username))
            throw new ExceptionalMassage("Duplicate username");
        if (type.equals("customer"))
            controlCreateCustomer(username, name, familyName, email, phoneNumber, password, credit);
        if (type.equals("supplier"))
            controlCreateSupplier(username, name, familyName, email, phoneNumber, password, credit, nameOfCompany);
        //check for name of company is not duplicate
        if (type.equals("supervisor") && !isFirstSupervisorCreated)
            controlCreateSupervisor(username, name, familyName, email, phoneNumber, password, credit);
        //edited by aryan
    }

    private void controlCreateCustomer(String username, String name, String familyName, String email, String phoneNumber
            , String password, int credit) {
        new Customer(username, name, familyName, email, phoneNumber, password, credit);
    }

    private void controlCreateSupplier(String username, String name, String familyName, String email, String phoneNumber
            , String password, int credit, String nameOfCompany) {
        new Supplier(username, name, familyName, email, phoneNumber, password, credit, nameOfCompany);
    }

    private void controlCreateSupervisor(String username, String name, String familyName, String email, String phoneNumber
            , String password, int credit) {
        new Supervisor(username, name, familyName, email, phoneNumber, password, credit);
    }


    public Response controlCanLogin(String username, String password) {
        if (!doesAccountExist(username))
            return Response.INVALID_USERNAME;
        Account account = Account.getAccountByUsername(username);
        if (!account.getPassword().equals(password))
            return Response.INVALID_PASSWORD;
        return Response.OK;
    }

    public void controlLogin(String username, String password) throws ExceptionalMassage {
        //variable cart must modified
    }

    public String controlViewPersonalInfo() {
        return String.valueOf(account);
    }

    public Response controlEditField( String field, String newValue) {
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

    public String controlViewUserInfo(String username) {
        return String.valueOf(Account.getAccountByUsername(username));
    }

    public Response controlDeleteUser(String username) {
        Account accountGotByUsername = Account.getAccountByUsername(username);
        if (accountGotByUsername != null)
            accountGotByUsername.removeAccount();
        //data base
        return null;
    }

    public Response controlCreateSupervisorBySupervisor( String username, String name, String familyName, String email, String phoneNumber
            , String password, int credit) {
        if (doesAccountExist(username))
            return Response.INVALID_USERNAME;
        if (!(account instanceof Supervisor))
            return Response.ACCESS_DENIED;
        controlCreateSupervisor(username, name, familyName, email, phoneNumber, password, credit);
        return Response.OK;
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

    public Response controlRemoveProductById(String productId) {
        Product product = Product.getProductById(productId);
        if (product != null) {
            product.removeProduct();
            return Response.OK;
        }
        return Response.INVALID_PRODUCT_ID;
    }

    public int controlViewBalance(Account account) {
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
    public void controlSort(String typeOfSort){

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

    public CodedDiscount controlGetDiscountByCode(String code){
        for (CodedDiscount codedDiscount : CodedDiscount.getCodedDiscounts()) {
            if(codedDiscount.getDiscountCode().equals(code)){
                return codedDiscount;
            }
        }
        return null;
    }

    public void controlEditDiscountByCode (String code, Date newStartDate, Date newEndDate, int newPercent, int newMaxDiscount) throws Exception{
        if(controlGetDiscountByCode(code) == null){
            throw new Exception("No CodedDiscount with this Code");
        }
        controlGetDiscountByCode(code).setStart(newStartDate);
        controlGetDiscountByCode(code).setEnd(newEndDate);
        controlGetDiscountByCode(code).setPercent(newPercent);
        controlGetDiscountByCode(code).setMaxDiscountPercent(newMaxDiscount);
    }

    public void controlCreateCodedDiscount(Date startDate, Date endDate, int percent, int maxDiscountAmount) {
        new CodedDiscount(startDate, endDate, percent, maxDiscountAmount);
    }

    public void controlRemoveDiscountCode(String code) throws Exception{
        if(controlGetDiscountByCode(code) == null){
            throw new Exception("No such code!");
        }
        CodedDiscount.removeCodeFromList(controlGetDiscountByCode(code));
    }

    public void controlCreateSale(Date startDate, Date endDate, int percent, ArrayList<Product> products){
        new SaleRequest(null, new Sale(startDate,endDate,percent),products,null);
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

    public void controlEditSaleById(String id, Date newEndDate, Date newStartDate, int newPercent, ArrayList<Product> addingProduct, ArrayList<Product> removingProduct) throws Exception{
        if(controlGetSaleById(id) == null){
            throw new Exception("No such sale with this code!");
        }
        new SaleRequest(controlGetSaleById(id),new Sale(newStartDate,newEndDate,newPercent,id),addingProduct,removingProduct);
    }

    public void controlRemoveSaleById(String id) throws Exception{
        if(controlGetSaleById(id) == null){
            throw new Exception("No such sale with id!");
        }
        Sale.getSales().remove(controlGetSaleById(id));
    }

    public ArrayList<CodedDiscount> controlGetCodedDiscountByCustomer(Customer customer){
        ArrayList<CodedDiscount> codedDiscounts = new ArrayList<>();
        for (CodedDiscount codedDiscount : CodedDiscount.getCodedDiscounts()) {
            if(codedDiscount.getCustomers().contains(customer)){
                codedDiscounts.add(codedDiscount);
            }
        }
        return codedDiscounts;
    }

    public void controlAddCommentToProduct(Customer customer,String title,String content,Product product){
        //Need modification!
        new Comment(customer, product,title, content, false);
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

    public void controlRateProductById(String id, float score, Customer customer, Product product) throws Exception{
        if(Product.getProductById(id) == null){
            throw new Exception("No such product with id!");
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

    public ArrayList<String> controlGetAllProducts(ArrayList<String> sorts,ArrayList<String> filters){
        //I need a Sorted and filtered list of products! and toString form of product need to have a quick introduction!
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

    public String controlGetDigestInfosOfProduct(Product product){
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




}
