package controller;

import account.Account;
import account.Customer;
import account.Supervisor;
import account.Supplier;
import cart.Cart;
import log.ShippingInfo;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class Controller {
    private Account account;
    private Cart cart;
    private Menu menu;
    private boolean isFirstSupervisorCreated;

    public boolean hasSomeOneLoggedIn(){
        return false;
    }

    public boolean controlAddToCart() {
        return false;
    }

    public Cart controlViewCart() {
        return null;
    }

    public boolean controlSubmitShippingInfo(String firstName, String lastName, String city, String address, long postalCode, long phoneNumber) {
        return false;
    }

    public boolean controlRemoveShippingInfo() {
        return false;
    }

    public boolean controlSubmitDiscountCode() {
        return false;
    }

    public boolean controlRemoveDiscountCode() {
        return false;
    }

    public boolean controlFinalizeOrder() {
        return false;
    }

    public boolean controlAddCategory( String name, String parentCategoryName) {
        return false;
    }

    public boolean controlAddSubcategoryToCategory( String name, String subcategoryName) {
        return false;
    }

    public boolean controlRemoveSubcategoryFromCategory( String name, String subcategoryName) {
        return false;
    }

    public boolean controlAddProductToCategory(String categoryName, String productIdentifier) {
        return false;
    }

    public boolean controlRemoveProductFromCategory( String categoryName, String productIdentifier) {
        return false;
    }


    private boolean doesAccountExist(String username) {
        if (Account.getAccountByUsername(username) == null)
            return false;
        return true;
    }

    public Response controlCreateAccount(String username, String type, String name, String familyName, String email, String phoneNumber
            , String password, int credit, String nameOfCompany) {
        if (doesAccountExist(username))
            return Response.INVALID_USERNAME;
        if (type.equals("Customer"))
            controlCreateCustomer(username, name, familyName, email, phoneNumber, password, credit);
        if (type.equals("Supplier"))
            controlCreateSupplier(username, name, familyName, email, phoneNumber, password, credit, nameOfCompany);
        if (type.equals("Supervisor") && !isFirstSupervisorCreated)
            controlCreateSupervisor(username, name, familyName, email, phoneNumber, password, credit);
        return Response.OK;
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

    public Account controlLogin(String username, String password) {
        return null;
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
                                  String categoryId, String description) {
        Product newProduct, product;
        product = Product.getProductByName(name);
        if (product == null)
            newProduct = new Product(name, nameOfCompany, price, remainedNumbers, categoryId, description);
        else {
            ArrayList<String > newSuppliers = new ArrayList<>(product.getListOfSuppliersUsername());
            newSuppliers.add(account.getUserName());
            newProduct = new Product(name, nameOfCompany, price, newSuppliers, remainedNumbers, categoryId, description,
                    product.getComments(),product.getNumberOfViews(),product.getProductId());
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

    public String controlCompare(String firstProductId , String secondProductId){
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

}
