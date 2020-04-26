package controller;

import account.Account;
import account.Customer;
import account.Supervisor;
import account.Supplier;
import cart.Cart;
import log.ShippingInfo;
import menu.menuAbstract.Menu;
import discount.CodedDiscount;
import discount.Sale;
import feedback.Comment;
import product.Category;
import product.Product;
import request.Request;

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

    public void controlAddToCart(String productId, String supplierUsername) throws Exception {
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new Exception("Product not found.");
        }
        Supplier supplier = (Supplier) Supplier.getAccountByUsername(supplierUsername);
        if (supplier == null) {
            throw new Exception("Supplier not Found");
        }
        if (!product.doesSupplierSellThisProduct(supplier)) {
            throw new Exception("This supplier doesn't provide this product.");
        }
        if (product.getRemainedNumber(supplier) == 0) {
            throw new Exception("This supplier currently can't provide this product.");
        }
        cart.addProductToCart(product, supplier);
    }

    public Cart controlViewCart() {
        return cart;
    }

    public void controlSubmitShippingInfo(String firstName, String lastName, String city, String address, long postalCode, long phoneNumber) throws Exception {
        ShippingInfo shippingInfo = new ShippingInfo(firstName, lastName, city, address, postalCode, phoneNumber);
        cart.submitShippingInfo(shippingInfo);
    }

    public void controlRemoveShippingInfo() throws Exception {
        cart.removeShippingInfo();
    }

    public void controlApplyDiscountCode(String discountCode) throws Exception {
        cart.applyCodedDiscount(discountCode);
    }

    public void controlRemoveDiscountCode() throws Exception {
        cart.removeCodedDiscount();
    }

    public void decreaseProductCount(String productId) throws Exception {
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new Exception("Product not found");
        }
        cart.decreaseProductCount(product);
    }

    public void increaseProductCount(String productId) throws Exception {
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new Exception("Product not found");
        }
        cart.increaseProductCount(product);
    }

    public boolean controlFinalizeOrder() {
        return false;
    }

    public void controlAddCategory(String name, String parentCategoryName, boolean isParent) throws Exception{
        if (Category.getCategoryByName(name) != null) {
            throw new Exception("duplicate name");
        }
        Category parent = Category.getCategoryByName(parentCategoryName);
        if (parent == null) {
            throw new Exception("parent not found");
        }
        new Category(name, isParent, parent);
    }

    public void controlRemoveCategory(String name) throws Exception {
        Category deletingCategory = Category.getCategoryByName(name);
        if (deletingCategory == null) {
            throw new Exception("category not found");
        }
        (deletingCategory.getParentCategory()).removeSubCategory(deletingCategory);
    }

    public void controlAddProductToCategory(String categoryName, String productIdentifier) throws Exception {
        Category category = Category.getCategoryByName(categoryName);
        Product product = Product.getProductById(productIdentifier);
        if (category == null) {
            throw new Exception("category not found");
        }
        if (product == null) {
            throw new Exception("product not found");
        }
        category.addProduct(product);
    }

    public void controlRemoveProductFromCategory(String categoryName, String productIdentifier) throws Exception {
        Category category = Category.getCategoryByName(categoryName);
        Product product = Product.getProductById(productIdentifier);
        if (category == null) {
            throw new Exception("category not found");
        }
        if (product == null) {
            throw new Exception("product not found");
        }
        category.removeProduct(product);
    }

    public void controlEditCategoryName(String oldName, String newName) throws Exception {
        Category category = Category.getCategoryByName(oldName);
        if (category == null) {
            throw new Exception("Category <" + oldName + "> not found.");
        }
        if (Category.getCategoryByName(newName) != null) {
            throw new Exception(("There is already a category with name " + newName + "."));
        }
        category.setName(newName);
    }

    public void controlAddFilterToCategory() throws Exception {

    }

    public void controlDeleteFilterFromCategory() throws Exception {

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
                                  Category category, String description) {
        Product newProduct, product;
        product = Product.getProductByName(name);
        if (product == null)
            newProduct = new Product(name, nameOfCompany, price, remainedNumbers, category, description);
        else {
            ArrayList<Supplier> newSuppliers = new ArrayList<>(product.getListOfSuppliers());
            newSuppliers.add((Supplier) account);
            newProduct = new Product(name, nameOfCompany, price, newSuppliers, remainedNumbers, category, description, product.getComments(),product.getNumberOfViews());
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
        return null;
    }

    public CodedDiscount controlGetDiscountByCode(String code){
        return null;
    }

    public void controlEditDiscountByCode(String code, Date newStartDate, Date newEndDate, int newPercent, int newMaxDiscount){

    }

    public void controlCreateCodedDiscount(Date startDate, Date endDate, int percent, int maxDiscountAmount){

    }

    public void controlRemoveDiscountCode(String code){

    }

    public void controlCreateSale(Date startDate, Date endDate, int percent, ArrayList<Product> products){

    }

    public ArrayList<Sale> controlGetAllSales(){
        return null;
    }

    public Sale controlGetSaleById(String id){
        return null;
    }

    public void controlEditSaleById(String id, Date newEndDate, Date newStartDate, int newPercent, ArrayList<Product> addingProduct, ArrayList<Product> removingProduct){

    }

    public void controlRemoveSaleById(String id){

    }

    public ArrayList<CodedDiscount> controlGetCodedDiscountByCustomer(Customer customer){
        return null;
    }

    public void controlAddCommentToProduct(String title,String content,Product product){

    }

    public ArrayList<Comment> controlGetCommentsOfAProduct(Product product){
        return null;
    }

    public void controlRateProductById(String id, float score){

    }

    public float controlGetAverageScoreByProduct(Product product){
        return 0;
    }

}
