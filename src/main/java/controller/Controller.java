package controller;

import account.Account;
import account.Customer;
import account.Supervisor;
import account.Supplier;
import cart.Cart;
import exceptionalMassage.ExceptionalMassage;
import feedback.Score;
import cart.ShippingInfo;
import menu.menuAbstract.Menu;
import discount.CodedDiscount;
import discount.Sale;
import feedback.Comment;
import product.Category;
import product.Product;
import request.Request;
import request.SaleRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class Controller {
    private Account account;
    private Cart cart;
    private Menu menu;
    private boolean isFirstSupervisorCreated;

    public Controller() {
        account = null;
        cart = new Cart(null);
        isFirstSupervisorCreated = false;
    }

    public boolean hasSomeOneLoggedIn(){
        return account != null;
    }

    public String loggedInAccountType() {
        return account.getType();
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

    public void controlSubmitShippingInfo(String firstName, String lastName, String city, String address, long postalCode, long phoneNumber) throws ExceptionalMassage {
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

    public void controlAddCategory(String name, boolean isParentCategory, String parentCategoryName) throws ExceptionalMassage {
        if (account == null)
            throw new ExceptionalMassage("Login First.");
        if (!(account instanceof Supervisor))
            throw new ExceptionalMassage("Login as a supervisor.");
        Category parentCategory = Category.getCategoryByName(parentCategoryName);
        if (parentCategory == null) {
            throw new ExceptionalMassage("Parent category not found.");
        }
        if (Category.getCategoryByName(name) != null) {
            throw new ExceptionalMassage("Category with this name has already initialized.");
        }
        Category newCategory = new Category(name, isParentCategory, parentCategory);
    }

    public void controlRemoveCategory(String name) throws ExceptionalMassage {
        if (account == null)
            throw new ExceptionalMassage("Login First.");
        if (!(account instanceof Supervisor))
            throw new ExceptionalMassage("Login as a supervisor.");
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

    public void controlChangeCategoryName(String oldName, String newName) throws ExceptionalMassage {
        Category category = Category.getCategoryByName(oldName);
        if (category == null)
            throw new ExceptionalMassage("Category not found.");
        category.setName(newName);
    }

    public void controlAddSpecialFilterToCategory(String categoryName, String filterKey, String filterValues) throws ExceptionalMassage {
        Category category = Category.getCategoryByName(categoryName);
        if (category == null)
            throw new ExceptionalMassage("Category not found.");
        String[] valuesSplit = filterValues.split(", ");
        ArrayList<String> values = new ArrayList<>();
        Collections.addAll(values, valuesSplit);
        category.addSpecialFilter(filterKey, values);
    }

    public void controlRemoveSpecialFilterFromCategory(String categoryName, String filterKey) throws ExceptionalMassage {
        Category category = Category.getCategoryByName(categoryName);
        if (category == null)
            throw new ExceptionalMassage("Category not found.");
        category.removeSpecialFilter(filterKey);
    }

    private boolean doesAccountExist(String username) {
        return Account.getAccountByUsername(username) != null;
    }

    public void controlCreateAccount(String username, String type, String name, String familyName, String email, String phoneNumber, String password, int credit, String nameOfCompany) throws ExceptionalMassage {
        //modified by aryan
        if (doesAccountExist(username))
            throw new ExceptionalMassage("Duplicate username");
        if (type.equals("customer"))
            controlCreateCustomer(username, name, familyName, email, phoneNumber, password, credit);
        if (type.equals("supplier"))
            controlCreateSupplier(username, name, familyName, email, phoneNumber, password, credit, nameOfCompany);
        if (type.equals("supervisor"))
            controlCreateSupervisor(username, name, familyName, email, phoneNumber, password, credit);
        controlLogin(username, password);
        //edited by aryan
    }

    private void controlCreateCustomer(String username, String name, String familyName, String email, String phoneNumber, String password, int credit) {
        new Customer(username, name, familyName, email, phoneNumber, password, credit);
    }

    private void controlCreateSupplier(String username, String name, String familyName, String email, String phoneNumber, String password, int credit, String nameOfCompany) throws ExceptionalMassage {
        if (Supplier.getSupplierByCompanyName(nameOfCompany) != null) {
            throw new ExceptionalMassage("Duplicate company name.");
        }
        new Supplier(username, name, familyName, email, phoneNumber, password, credit, nameOfCompany);
    }

    private void controlCreateSupervisor(String username, String name, String familyName, String email, String phoneNumber, String password, int credit) throws ExceptionalMassage {
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
