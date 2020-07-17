package controller;

import account.Account;
import account.Customer;
import account.Supervisor;
import account.Supplier;
import cart.Cart;
import cart.ProductInCart;
import cart.ShippingInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import communications.*;
import discount.CodedDiscount;
import exceptionalMassage.ExceptionalMassage;
import log.CustomerLog;
import log.LogStatus;
import log.SupplierLog;
import product.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AccountController {
    private static final long WEEK = 7*24*60*60000;

    private static final int BOUND = 100;

    private final Controller mainController;

    public AccountController(Controller mainController) {
        this.mainController = mainController;
    }

    public JsonElement communication(String function, JsonArray inputs) throws ExceptionalMassage {
        return new JsonParser().parse(mainController.
                communication(function, inputs, ControllerSource.ACCOUNT_CONTROLLER).getContent());
    }

    public boolean hasSomeOneLoggedIn() throws ExceptionalMassage{
        JsonElement response = communication("hasSomeOneLoggedIn",new JsonArray());
        return response.getAsBoolean();

    }

    public String loggedInAccountType() throws ExceptionalMassage{
        JsonElement jsonElement = communication("loggedInAccountType",new JsonArray());

        return jsonElement.getAsString();
    }

   /* public Account getAccount() {
        JsonElement account = communication("getAccount",new JsonArray());
        return Utils.
    } */

    public void controlCreateAccount(String username, String type, String name, String familyName, String email,
                                     String phoneNumber, String password, int credit, String nameOfCompany)
            throws ExceptionalMassage {

        JsonArray input = new JsonArray();
        input.add(username);
        input.add(type);
        input.add(name);
        input.add(familyName);
        input.add(email);
        input.add(phoneNumber);
        input.add(password);
        input.add(String.valueOf(credit));
        input.add(nameOfCompany);
        communication("controlCreateAccount",input);
    }

    /*private void controlCreateCustomer(String username, String name, String familyName, String email, String phoneNumber,
                                       String password, int credit) {
        new Customer(username, name, familyName, email, phoneNumber, password, credit);
    }*/

    /*private void controlCreateSupplier(String username, String name, String familyName, String email, String phoneNumber,
                                       String password, int credit, String nameOfCompany) throws ExceptionalMassage {
        if (Supplier.getSupplierByCompanyName(nameOfCompany) != null) {
            throw new ExceptionalMassage("Duplicate company name.");
        }
        new Supplier(username, name, familyName, email, phoneNumber, password, credit, nameOfCompany);
    }*/

    /*private void controlCreateSupervisor(String username, String name, String familyName, String email,
                                         String phoneNumber, String password, int credit) throws ExceptionalMassage {
        mainController.setIsFirstSupervisorCreated(Account.isSupervisorCreated());
        if (mainController.getIsFirstSupervisorCreated()) {
            if (mainController.getAccount() == null)
                throw new ExceptionalMassage("You must login as supervisor before create a supervisor account.");
            if (!(mainController.getAccount() instanceof Supervisor))
                throw new ExceptionalMassage("You must be a supervisor to create supervisor account.");
        }
        new Supervisor(username, name, familyName, email, phoneNumber, password, credit);
        mainController.setIsFirstSupervisorCreated(true);
    }*/

    public void controlLogin(String username, String password) throws ExceptionalMassage {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(username);
        jsonArray.add(password);
        communication("controlLogin",jsonArray);
    }

    public void controlLogout() throws ExceptionalMassage{
        communication("controlLogout",new JsonArray());
    }

    public String controlViewPersonalInfo() throws ExceptionalMassage{
        JsonElement response = communication("controlViewPersonalInfo",new JsonArray());
        return response.getAsString();
    }

    public int controlViewBalance() throws ExceptionalMassage{
        JsonElement response = communication("controlViewBalance",new JsonArray());
        return response.getAsInt();
    }

    public void controlEditField(String field, String newValue) throws ExceptionalMassage {
        JsonArray input = new JsonArray();
        input.add(field);
        input.add(newValue);
        communication("controlEditField",input);
    }

    public void editAllFields(String name, String familyName, String email, String phoneNumber, String password,
                              int credit, String nameOfCompany) throws ExceptionalMassage{
        JsonArray input = new JsonArray();
        input.add(name);
        input.add(familyName);
        input.add(email);
        input.add(phoneNumber);
        input.add(password);
        input.add(String.valueOf(credit));
        input.add(nameOfCompany);
        communication("editAllFields",input);
    }

    public String controlGetListOfAccounts() throws ExceptionalMassage{
        JsonElement accounts = communication("controlGetListOfAccounts",new JsonArray());
        return accounts.getAsString();
    }

    public ArrayList<String> controlGetListOfAccountUserNames() throws ExceptionalMassage{
        JsonElement response = communication("controlGetListOfAccountUserNames",new JsonArray());
        ArrayList<String> allUsername = Utils.convertJasonElementToStringArrayList(response);
        return allUsername;
    }

    public String controlViewUserInfo(String username) throws ExceptionalMassage {
        JsonArray input = new JsonArray();
        input.add(username);
        JsonElement account = communication("controlViewUserInfo",input);
        return account.getAsString();
    }

    public void controlDeleteUser(String username) throws ExceptionalMassage {
        JsonArray input = new JsonArray();
        input.add(username);
        communication("controlDeleteUser", input);
    }

    public String controlViewCompanyInfo() throws ExceptionalMassage{
        JsonElement companyInfo = communication("controlViewCompanyInfo", new JsonArray());
        return companyInfo.getAsString();
    }

    /*public String controlGetListOfProductsForThisSupplier() throws  ExceptionalMassage{
        Account account = mainController.getAccount();
        if (!(account instanceof Supplier)) {
            throw new ExceptionalMassage("Sign in as a supplier");
        }
        else {
            ArrayList<Product> products = Product.getProductForSupplier((Supplier)account);
            String.valueOf(products);
            StringBuilder result = new StringBuilder("");
            for (Product product : products) {
                result.append(product.getName()).append("   ").append(product.getProductId()).append("\n");
            }
            return String.valueOf(result);
        }
    }*/

    public void controlAddToCart(String productId, String supplierNameOfCompany) throws ExceptionalMassage {
        JsonArray input = new JsonArray();
        input.add(productId);
        input.add(supplierNameOfCompany);
        communication("controlAddToCart",input);
    }

    public void increaseProductQuantity(String productId, String supplierNameOfCompany) throws ExceptionalMassage {
        JsonArray input = new JsonArray();
        input.add(productId);
        input.add(supplierNameOfCompany);
        communication("increaseProductQuantity", input);
    }

    public void decreaseProductQuantity(String productId, String supplierNameOfCompany) throws ExceptionalMassage {
        JsonArray input = new JsonArray();
        input.add(productId);
        input.add(supplierNameOfCompany);
        communication("decreaseProductQuantity", input);
    }

    public Cart controlViewCart() throws ExceptionalMassage{
        JsonElement cart = communication("controlViewCart",new JsonArray());
        return Cart.convertJsonStringToCart(cart.getAsString());
    }

    public void controlSubmitShippingInfo(String firstName, String lastName, String city, String address,
                                          String postalCode, String phoneNumber) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(firstName);
        inputs.add(lastName);
        inputs.add(city);
        inputs.add(address);
        inputs.add(postalCode);
        inputs.add(phoneNumber);
        communication("controlSubmitShippingInfo",inputs);
    }

    public void controlRemoveShippingInfo() throws ExceptionalMassage {
        communication("controlRemoveShippingInfo",new JsonArray());
    }
    public void controlSubmitDiscountCode(String discountCode) throws ExceptionalMassage {
        JsonArray input = new JsonArray();
        input.add(discountCode);
        communication("controlSubmitDiscountCode", input);
    }

    public void controlRemoveDiscountCode() throws ExceptionalMassage {
        communication("controlRemoveDiscountCode",new JsonArray());
    }

    public boolean finalizeOrder() throws ExceptionalMassage {
        Account account = mainController.getAccount();
        if (account == null)
            throw new ExceptionalMassage("Login First.");
        if (!(account instanceof Customer))
            throw new ExceptionalMassage("Login as a customer.");
        Cart cart = mainController.getCart();
        Customer customer = (Customer) mainController.getAccount();
        if (cart.isCartClear())
            throw new ExceptionalMassage("Your cart is clear.");
        if (account.getCredit() < cart.getBill())
            throw new ExceptionalMassage("You don't have enough credit.");
        HashMap<ProductInCart, Integer> productInCount = cart.getProductInCount();
        for (ProductInCart productInCart : cart.getProductsIn()) {
            if (productInCart.getProduct().getRemainedNumber(productInCart.getSupplier()) < productInCount.get(productInCart))
                throw new ExceptionalMassage("Product " + productInCart.getProduct().getName() +
                        " is not available in this amount, please reduce.");
        }
        if(cart.getCodedDiscount() != null){
            cart.getCodedDiscount().addUsedCountForCustomer((Customer)mainController.getAccount());
        }
        customer.setCart(new Cart(customer));
        mainController.setCart(customer.getCart());
        CustomerLog customerLog = new CustomerLog(cart);
        if (customerLog.getPaidAmount() >= BOUND) {
            controlCreateCodedDiscountForLoggedInCustomer();
        }
        return customerLog.getPaidAmount() >= BOUND;
    }

    public String getAccountUsername() {
        return getAccount().getUserName();
    }

    public String getAccountName() {
        return getAccount().getName();
    }

    public String getAccountFamilyName() {
        return getAccount().getFamilyName();
    }

    public String getAccountEmail() {
        return getAccount().getEmail();
    }

    public String getAccountPhoneNumber() {
        return getAccount().getPhoneNumber();
    }

    public int getAccountCredit() {
        return getAccount().getCredit();
    }

    public String getAccountNameOfCompany() {
        return ((Supplier) getAccount()).getNameOfCompany();
    }

    public ArrayList<String> getCustomerLogs() {
        ArrayList<CustomerLog> customerLogs = CustomerLog.getCustomerCustomerLogs((Customer) getAccount());
        ArrayList<String> toStringLogs = new ArrayList<>();
        for (CustomerLog customerLog : customerLogs) {
            toStringLogs.add(customerLog.toString());
        }
        return toStringLogs;
    }

    public ArrayList<String> getSupplierLogs() {
        ArrayList<SupplierLog> supplierLogs = SupplierLog.getSupplierSupplierLog((Supplier) getAccount());
        ArrayList<String> toStringLogs = new ArrayList<>();
        for (SupplierLog supplierLog : supplierLogs) {
            toStringLogs.add(supplierLog.toString());
        }
        return toStringLogs;
    }

    public ArrayList<String> getSupervisorLogs() {
        ArrayList<CustomerLog> supervisorLogs = CustomerLog.getAllCustomerLogs();
        ArrayList<String> toStringLogs = new ArrayList<>();
        for (CustomerLog customerLog : supervisorLogs) {
            toStringLogs.add(customerLog.toString());
        }
        return toStringLogs;
    }

    public String getCustomerLogById(String id) {
        return CustomerLog.getCustomerLogById(id).toString();
    }

    public String getSupplierLogById(String id) {
        return SupplierLog.getSupplierLogById(id).toString();
    }

    public boolean proceedCustomerLog(String id) {
        try {
            CustomerLog.getCustomerLogById(id).proceedToNextStep();
        } catch (ExceptionalMassage exceptionalMassage) {
            return false;
        }
        return true;
    }

    public boolean isLogProcessable(String id) {
        return CustomerLog.getCustomerLogById(id).getDeliveryStatus() != LogStatus.DELIVERED;
    }

    public int numberOfProductInCart(ProductInCart productInCart){
        try {
            return mainController.getCart().getCountOfProductInCart(productInCart);
        } catch (ExceptionalMassage exceptionalMassage) {
            return 0;
        }
    }

    public void controlClearCart(){
        mainController.getCart().clear();
    }

    public ArrayList<Product> controlGetRequestForLoggedInSupplier(){
        return Product.getRequestsForThisSupplier((Supplier) mainController.getAccount());
    }

    public void controlCreateRandomCodesForCustomers(ArrayList<Customer> luckyCustomers,int percent, int maxAmount){
        HashMap<Customer, Integer> maxUsagePerCustomer = new HashMap<>();
        String randomCode = CodedDiscount.codeGenerator();
        for (Customer luckyCustomer : luckyCustomers) {
            maxUsagePerCustomer.put(luckyCustomer, 1);
        }
        Date start = new Date(System.currentTimeMillis());
        Date end = new Date(System.currentTimeMillis() + WEEK);
        new CodedDiscount(randomCode,start, end,percent,maxAmount,maxUsagePerCustomer);
    }

    public void controlCreateCodedDiscountForLoggedInCustomer(){
        Date start = new Date(System.currentTimeMillis());
        Date end = new Date(System.currentTimeMillis() + WEEK);
        String randomCode = CodedDiscount.codeGenerator();
        HashMap<Customer, Integer> maxUsagePerCustomer = new HashMap<>();
        maxUsagePerCustomer.put((Customer)mainController.getAccount(),1);
        new CodedDiscount(randomCode,start,end, 15, 100,maxUsagePerCustomer);
    }
}
