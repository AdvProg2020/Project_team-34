package controller;

import account.*;
import cart.Cart;
import cart.ProductInCart;
import cart.ShippingInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import database.WageDataBase;
import discount.CodedDiscount;
import exceptionalMassage.ExceptionalMassage;
import log.CustomerLog;
import log.LogStatus;
import log.SupplierLog;
import product.Product;
import server.communications.RequestStatus;
import server.communications.Response;
import server.communications.Utils;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
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

    public Account getInternalAccount() {
        return mainController.getAccount();
    }

    public Response getAccount() {
        Account account = getInternalAccount();
        JsonArray jsonArray = new JsonArray();
        if (account == null) {
            jsonArray.add("null");
            jsonArray.add("null");
            return new Response(RequestStatus.SUCCESSFUL, jsonArray.toString(), mainController);
        }
        if (account instanceof Customer) {
            jsonArray.add("Customer");
        }
        if (account instanceof Supplier) {
            jsonArray.add("Supplier");
        }
        if (account instanceof Supervisor) {
            jsonArray.add("Supervisor");
        }
        if (account instanceof Supporter) {
            jsonArray.add("Supporter");
        }
        jsonArray.add(Utils.convertObjectToJsonString(account));
        return new Response(RequestStatus.SUCCESSFUL, jsonArray.toString(), mainController);
    }

    private boolean hasSomeOneLoggedInInternal(){
        return mainController.getAccount()!= null;
    }

    public Response hasSomeOneLoggedIn(){
        JsonParser jsonParser = new JsonParser();
        JsonElement json = jsonParser.parse(String.valueOf(hasSomeOneLoggedInInternal()));
        return new Response(RequestStatus.SUCCESSFUL, json.getAsString(), mainController);
    }

    public Response loggedInAccountType() {
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(""), mainController);
        if (account instanceof Customer)
            return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString("Customer"), mainController);
        if (account instanceof Supervisor)
            return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString("Supervisor"), mainController);
        if (account instanceof Supporter)
            return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString("Supporter"), mainController);
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString("Supplier"), mainController);
    }

    public Response controlCreateAccount(String type, String username, String password, String firstName, String lastName,
                                         String email, String phoneNumber, String nameOfCompany, String bankUsername,
                                         String bankPassword, String alsoBankStr) {
        boolean alsoBank = Boolean.parseBoolean(alsoBankStr);
        if (!Account.isUsernameAvailable(username))
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Duplicate username"), mainController);
        if (type.equals("supporter"))
            return controlCreateSupporter(username, firstName, lastName, email, phoneNumber, password);
        else if (type.equals("supervisor"))
            return controlCreateSupervisor(username, firstName, lastName, email, phoneNumber, password);
        else {
            int bankAccountNumber = -1;
            if (alsoBank) {
                try {
                    bankAccountNumber = controlInternalCreateBankAccount(firstName, lastName, bankUsername, bankPassword);
                } catch (ExceptionalMassage exceptionalMassage) {
                    return Response.createResponseFromExceptionalMassage(exceptionalMassage,mainController);
                }
            }
            Response response;
            if (type.equals("customer")) {
                response = controlCreateCustomer(username, firstName, lastName, email, phoneNumber, password, bankAccountNumber);
            }
            else {
                response = controlCreateSupplier(username, firstName, lastName, email, phoneNumber, password, nameOfCompany, bankAccountNumber);
            }
            if (response.getStatus() == RequestStatus.EXCEPTIONAL_MASSAGE)
                return response;
            return controlLogin(username, password);
        }
    }

    private Response controlCreateSupporter(String username, String name, String familyName, String email,
                                            String phoneNumber, String password) {
        if (!(mainController.getAccount() instanceof Supervisor))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "You must be a supervisor to create supporter account.", mainController);
        new Supporter(username, name, familyName, email, phoneNumber, password, 0);
        return Response.createSuccessResponse(mainController);
    }

    private Response controlCreateCustomer(String username, String name, String familyName, String email,
                                           String phoneNumber, String password, int bankAccountNumber) {
        new Customer(username, name, familyName, email, phoneNumber, password, 0, bankAccountNumber);
        return Response.createSuccessResponse(mainController);
    }

    private Response controlCreateSupplier(String username, String name, String familyName, String email, String phoneNumber,
                                           String password, String nameOfCompany, int bankAccountNumber) {
        if (Supplier.getSupplierByCompanyName(nameOfCompany) != null) {
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Duplicate company name."),mainController);
        }
        new Supplier(username, name, familyName, email, phoneNumber, password, 0, nameOfCompany, bankAccountNumber);
        return Response.createSuccessResponse(mainController);
    }

    private Response controlCreateSupervisor(String username, String name, String familyName, String email,
                                             String phoneNumber, String password) {
        mainController.setIsFirstSupervisorCreated(Account.isSupervisorCreated());
        int bankAccountNumber;
        if (mainController.getIsFirstSupervisorCreated()) {
            if (mainController.getAccount() == null)
                return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("You must login as supervisor before create a supervisor account."), mainController);
            if (!(mainController.getAccount() instanceof Supervisor))
                return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("You must be a supervisor to create supervisor account."), mainController);
            bankAccountNumber = Account.getStoreBankAccount();
        } else {
            try {
                bankAccountNumber = controlInternalCreateBankAccount(name, familyName, "Team34", "343434");
            } catch (ExceptionalMassage exceptionalMassage) {
                return Response.createResponseFromExceptionalMassage(exceptionalMassage, mainController);
            }
        }
        new Supervisor(username, name, familyName, email, phoneNumber, password, 0, bankAccountNumber);
        mainController.setIsFirstSupervisorCreated(true);
        return Response.createSuccessResponse(mainController);
    }

    public Response controlLogin(String username, String password){
        if (hasSomeOneLoggedInInternal())
            return  Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Logout first."), mainController);
        Account account = Account.getAccountByUsernameWithinAvailable(username);
        if (account == null)
            return  Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Username doesn't exist."), mainController);
        if (!account.getPassword().equals(password))
            return  Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Invalid password."), mainController);
        Cart cart = mainController.getCart();
        mainController.setAccount(account);
        if (account instanceof Customer) {
            if (cart.isCartClear()) {
                mainController.setCart(((Customer) mainController.getAccount()).getCart());
            } else {
                if (((Customer) account).getCart().isCartClear()) {
                    ((Customer) account).setCart(cart);
                    cart.setOwner((Customer) account);
                } else {
                    mainController.setCart(((Customer) account).getCart());
                }
            }
        }
        mainController.renewToken();
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(mainController.getToken()), mainController);
    }

    public Response controlLogout() {
        mainController.setAccount(null);
        mainController.setCart(new Cart((Customer) null));
        mainController.getProductController().getFilterAndSort().clear();
        mainController.renewToken();
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(mainController.getToken()), mainController);
    }

    public Response controlViewPersonalInfo() {
        String personalInfo = Utils.convertObjectToJsonString(mainController.getAccount());
        return new Response(RequestStatus.SUCCESSFUL, personalInfo, mainController);
    }

    public Response controlViewBalance() {
        String credit = String.valueOf((mainController.getAccount()).getCredit());
        return new Response(RequestStatus.SUCCESSFUL, credit, mainController);
    }

    public Response controlEditField(String field, String newValue) {
        Account account = mainController.getAccount();
        switch (field) {
            case "name":
                account.setName(newValue);
                break;
            case "familyName":
                account.setFamilyName(newValue);
                break;
            case "email":
                account.setEmail(newValue);
                break;
            case "phoneNumber":
                account.setPhoneNumber(newValue);
                break;
            case "credit":
                int value;
                try {
                    value = Integer.parseInt(newValue);
                } catch (Exception exception) {
                   return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("You must enter a number for credit"), mainController);
                }
                account.setCredit(value);
                break;
            case "nameOfCompany":
                if (account instanceof Supplier)
                    ((Supplier) account).setNameOfCompany(newValue);
                else
                    return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("You must login as a Supplier to edit company name"), mainController);
                break;
            case  "username":
                return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("You can not edit username"), mainController);
            case "password":
                account.setPassword(newValue);
                break;
            default:
                return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("No such field " + field), mainController);
        }
        return Response.createSuccessResponse(mainController);
    }

    public Response editAllFields(String name, String familyName, String email, String phoneNumber, String password,
                                  String nameOfCompany) {
        if (password == null || password.trim().length() == 0) {
            password = getInternalAccount().getPassword();
        }
        if (getInternalAccount() instanceof Supplier) {
            ((Supplier) getInternalAccount()).editAllFields(name, familyName, email, phoneNumber, password, nameOfCompany);
        } else {
            (getInternalAccount()).editAllFields(name, familyName, email, phoneNumber, password);
        }
        return Response.createSuccessResponse( mainController);
    }

    /*public String controlGetListOfAccounts() {
        ArrayList<String> allUsername = Account.getAllUsername();
        StringBuilder result = new StringBuilder();
        for (String username : allUsername) {
            result.append(username).append('\n');
        }
        return result.toString();
    }*/

    public Response controlGetListOfAccountUserNames(){
        ArrayList<String> allUsername = Account.getAllUsername();
        JsonElement jsonElement = Utils.convertStringArrayListToJsonElement(allUsername);
        return new Response(RequestStatus.SUCCESSFUL,jsonElement.toString(), mainController);
    }

    public Response controlViewUserInfo(String username) {
        Account account = Account.getAccountByUsernameWithinAvailable(username);
        if (account == null)
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Account not found."), mainController);
        return new Response(RequestStatus.SUCCESSFUL,account.toString(), mainController);
    }

    public Response controlDeleteUser(String username) {
        Account accountGotByUsername = Account.getAccountByUsernameWithinAvailable(username);
        if (accountGotByUsername == null)
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Account not found."), mainController);
        if(username.equals(getInternalAccount().getUserName()))
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("You cannot delete Your self"), mainController);
        accountGotByUsername.removeAccount();
        return Response.createSuccessResponse(mainController);
    }

    public Response controlViewCompanyInfo() {
        Account account = mainController.getAccount();
        if (account instanceof Supplier) {
            return new Response(RequestStatus.SUCCESSFUL,((Supplier) account).getNameOfCompany(), mainController);
        }
        return new Response(RequestStatus.EXCEPTIONAL_MASSAGE,"Sign in as a Supplier!", mainController);
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

    public Response controlAddToCart(String productId, String supplierNameOfCompany)  {
        if (getInternalAccount() instanceof Supplier || getInternalAccount() instanceof Supervisor) {
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("logout, Supervisor and Supplier are denied"), mainController);
        }
        Product product = Product.getProductById(productId);
        if (product == null)
            return Response.createResponseFromExceptionalMassage( new ExceptionalMassage("Product not found."), mainController);
        Supplier supplier = Supplier.getSupplierByCompanyName(supplierNameOfCompany);
        if (supplier == null)
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Supplier not found."), mainController);
        try {
            mainController.getCart().addProductToCart(product, supplier);
        } catch (ExceptionalMassage ex){
            return Response.createResponseFromExceptionalMassage(ex, mainController);
        }
        return Response.createSuccessResponse(mainController);
    }

    public Response increaseProductQuantity(String productId, String supplierNameOfCompany) {
        Product product = Product.getProductById(productId);
        if (product == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Product not found.", mainController);
        Supplier supplier = Supplier.getSupplierByCompanyName(supplierNameOfCompany);
        if (supplier == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Supplier not found.", mainController);
        try {
            mainController.getCart().increaseProductCount(product, supplier);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.SUCCESSFUL, exceptionalMassage.getMessage(), mainController);
        }
        return new Response(RequestStatus.SUCCESSFUL, "", mainController);
    }

    public Response decreaseProductQuantity(String productId, String supplierNameOfCompany) throws ExceptionalMassage {
        Product product = Product.getProductById(productId);
        if (product == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Product not found.", mainController);
        Supplier supplier = Supplier.getSupplierByCompanyName(supplierNameOfCompany);
        if (supplier == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Supplier not found.", mainController);
        mainController.getCart().decreaseProductCount(product, supplier);
        return new Response(RequestStatus.SUCCESSFUL, "", mainController);
    }

    public Response controlViewCart() {
        String cartJson = Utils.convertObjectToJsonString(mainController.getCart());
        return new Response(RequestStatus.SUCCESSFUL,cartJson, mainController);
    }

    public Response controlSubmitShippingInfo(String firstName, String lastName, String city, String address,
                                          String postalCode, String phoneNumber)  {
        Account account = mainController.getAccount();
        if (account == null)
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Login First."), mainController);
        if (!(account instanceof Customer))
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Login as a customer."), mainController);
        mainController.getCart().submitShippingInfo(new ShippingInfo(firstName, lastName, city, address, postalCode, phoneNumber));
        return Response.createSuccessResponse(mainController);
    }

    public Response controlRemoveShippingInfo() throws ExceptionalMassage {
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First.", mainController);
        if (!(account instanceof Customer))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a customer.", mainController);
        mainController.getCart().removeShippingInfo();
        return new Response(RequestStatus.SUCCESSFUL, "", mainController);
    }

    public Response controlViewCartInfo() {
        String cartStringForm = mainController.getCart().toString();
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(cartStringForm), mainController);
    }

    public Response controlSubmitDiscountCode(String discountCode) {
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First.", mainController);
        if (!(account instanceof Customer))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a customer.", mainController);
        try {
            mainController.getCart().applyCodedDiscount(discountCode);
        } catch (ExceptionalMassage exceptionalMassage) {
            return Response.createResponseFromExceptionalMassage(exceptionalMassage, mainController);
        }
        return new Response(RequestStatus.SUCCESSFUL, "", mainController);
    }

    public Response controlRemoveDiscountCode(){
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First.", mainController);
        if (!(account instanceof Customer))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a customer.", mainController);
        mainController.getCart().removeCodedDiscount();
        return new Response(RequestStatus.SUCCESSFUL, "", mainController);
    }

    public Response checkFinalizeOrder(){
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First.", mainController);
        if (!(account instanceof Customer))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a customer.", mainController);
        Cart cart = mainController.getCart();
        if (cart.isCartClear())
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Your cart is clear.", mainController);
        if (account.getCredit() < cart.getBill())
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "You don't have enough credit.", mainController);
        HashMap<ProductInCart, Integer> productInCount = cart.getProductInCount();
        for (ProductInCart productInCart : cart.getProductsIn()) {
            if (productInCart.getProduct().getRemainedNumber(productInCart.getSupplier()) < productInCount.get(productInCart))
                return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Product " +
                        productInCart.getProduct().getName() + " is not available in this amount, please reduce.", mainController);
        }
        return Response.createSuccessResponse(mainController);
    }

    public Response finalizeOrder() {
        Cart cart = mainController.getCart();
        Customer customer = (Customer) mainController.getAccount();
        if(cart.getCodedDiscount() != null){
            cart.getCodedDiscount().addUsedCountForCustomer((Customer)mainController.getAccount());
        }
        customer.setCart(new Cart(customer));
        mainController.setCart(customer.getCart());
        CustomerLog customerLog = new CustomerLog(cart,WageDataBase.getWage());
        if (customerLog.getPaidAmount() >= BOUND) {
            controlInternalCreateCodedDiscountForLoggedInCustomer();
        }
        return new Response(RequestStatus.SUCCESSFUL, String.valueOf(customerLog.getPaidAmount() >= BOUND), mainController);
    }

    public Response controlGetLinksToDownloadFileProducts(){
        Cart cart = mainController.getCart();
        ArrayList<Product> products = cart.getFileProductsInCart();
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject;
        for (Product product : products) {
            jsonObject = new JsonObject();
            jsonObject.addProperty("filePath", product.getFilePath());
            jsonObject.addProperty("port", product.getSupplierPort());
            jsonArray.add(jsonObject);
        }
        return new Response(RequestStatus.SUCCESSFUL, jsonArray.toString(), mainController);
    }

    public Response getAccountUsername() {
        return new Response(RequestStatus.SUCCESSFUL, getInternalAccount().getUserName(), mainController);
    }

    public Response getAccountName() {
        return new Response(RequestStatus.SUCCESSFUL, getInternalAccount().getName(), mainController);
    }

    public Response getAccountFamilyName() {
        return new Response(RequestStatus.SUCCESSFUL,  getInternalAccount().getFamilyName(), mainController);
    }

    public Response getAccountEmail() {
        return new Response(RequestStatus.SUCCESSFUL, getInternalAccount().getEmail(), mainController);
    }

    public Response getAccountPhoneNumber() {
        return new Response(RequestStatus.SUCCESSFUL, getInternalAccount().getPhoneNumber(), mainController);
    }

    public Response getAccountCredit() {
        return new Response(RequestStatus.SUCCESSFUL, String.valueOf(getInternalAccount().getCredit()), mainController);
    }

    public Response getAccountNameOfCompany() {
        return new Response(RequestStatus.SUCCESSFUL, ((Supplier) getInternalAccount()).getNameOfCompany(), mainController);
    }

    public Response getCustomerLogs() {
        ArrayList<CustomerLog> customerLogs = CustomerLog.getCustomerCustomerLogs((Customer) getInternalAccount());
        ArrayList<String> toStringLogs = new ArrayList<>();
        for (CustomerLog customerLog : customerLogs) {
            toStringLogs.add(customerLog.toString());
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(toStringLogs).toString(), mainController);
    }

    public Response getSupplierLogs() {
        ArrayList<SupplierLog> supplierLogs = SupplierLog.getSupplierSupplierLog((Supplier) getInternalAccount());
        ArrayList<String> toStringLogs = new ArrayList<>();
        for (SupplierLog supplierLog : supplierLogs) {
            toStringLogs.add(supplierLog.toString());
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(toStringLogs).toString(), mainController);
    }

    public Response getSupervisorLogs() {
        ArrayList<CustomerLog> supervisorLogs = CustomerLog.getAllCustomerLogs();
        ArrayList<String> toStringLogs = new ArrayList<>();
        for (CustomerLog customerLog : supervisorLogs) {
            toStringLogs.add(customerLog.toString());
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(toStringLogs).toString(), mainController);
    }

    public Response getCustomerLogById(String id) {
        CustomerLog customerLog = CustomerLog.getCustomerLogById(id);
        if (customerLog == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Log not found", mainController);
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(customerLog.toString()), mainController);
    }

    public Response getSupplierLogById(String id) {
        SupplierLog supplierLog = SupplierLog.getSupplierLogById(id);
        if (supplierLog == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Log not found", mainController);
        return new Response(RequestStatus.SUCCESSFUL, supplierLog.toString(), mainController);
    }

    public Response proceedCustomerLog(String id) {
        try {
            CustomerLog customerLog = CustomerLog.getCustomerLogById(id);
            if (customerLog == null)
                return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Log not found", mainController);
            customerLog.proceedToNextStep();
            return new Response(RequestStatus.SUCCESSFUL, String.valueOf(true), mainController);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "", mainController);
        }
    }

    public Response isLogProcessable(String id) {
        CustomerLog customerLog = CustomerLog.getCustomerLogById(id);
        if (customerLog == null) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Log not found", mainController);
        }
        return new Response(RequestStatus.SUCCESSFUL, String.valueOf(customerLog.
                getDeliveryStatus() != LogStatus.DELIVERED), mainController);
    }

    public Response numberOfProductInCart(String productInCartId) {
        ProductInCart productInCart = ProductInCart.getProductInCartByIdentifier(productInCartId);
        int c;
        try {
            c = mainController.getCart().getCountOfProductInCart(productInCart);
        } catch (ExceptionalMassage exceptionalMassage) {
            c = 0;
        }
        return new Response(RequestStatus.SUCCESSFUL, String.valueOf(c), mainController);
    }

    public Response controlClearCart() {
        mainController.getCart().clear();
        return new Response(RequestStatus.SUCCESSFUL, "", mainController);
    }

    public Response controlGetRequestForLoggedInSupplier() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertProductArrayListToJsonElement(Product.
                getRequestsForThisSupplier((Supplier) mainController.getAccount())).toString(), mainController);
    }

    private void controlInternalCreateCodedDiscountForLoggedInCustomer() {
        Date start = new Date(System.currentTimeMillis());
        Date end = new Date(System.currentTimeMillis() + WEEK);
        String randomCode = CodedDiscount.codeGenerator();
        HashMap<Customer, Integer> maxUsagePerCustomer = new HashMap<>();
        maxUsagePerCustomer.put((Customer)mainController.getAccount(),1);
        new CodedDiscount(randomCode,start,end, 15, 100, maxUsagePerCustomer);
    }

    public Response controlCreateCodedDiscountForLoggedInCustomer() {
        controlInternalCreateCodedDiscountForLoggedInCustomer();
        return new Response(RequestStatus.SUCCESSFUL, "", mainController);
    }

    //Aryan:
    public static synchronized void controlInternalCreateRandomCodesForCustomers(ArrayList<Customer> luckyCustomers,
                                                                                 int percent, int maxAmount) {
        HashMap<Customer, Integer> maxUsagePerCustomer = new HashMap<>();
        String randomCode = CodedDiscount.codeGenerator();
        for (Customer luckyCustomer : luckyCustomers) {
            maxUsagePerCustomer.put(luckyCustomer, 1);
        }
        Date start = new Date(System.currentTimeMillis());
        Date end = new Date(System.currentTimeMillis() + WEEK);
        new CodedDiscount(randomCode, start, end, percent, maxAmount, maxUsagePerCustomer);
    }

    public Response getCustomerObservableList() {
        return new Response(RequestStatus.SUCCESSFUL,
                Utils.convertCustomerArrayListToJsonElement(Account.getCustomersList()).toString(), mainController);
    }

    public Response getSupplierObservableList() throws ExceptionalMassage {
        return new Response(RequestStatus.SUCCESSFUL,
                Utils.convertSupplierArrayListToJsonElement(Account.getSuppliersList()).toString(), mainController);
    }

    public Response getSupervisorObservableList() throws ExceptionalMassage {
        return new Response(RequestStatus.SUCCESSFUL,
                Utils.convertSupervisorArrayListToJsonElement(Account.getSupervisorsList()).toString(), mainController);
    }

    public Response getSupporterObservableList() throws ExceptionalMassage {
        return new Response(RequestStatus.SUCCESSFUL,
                Utils.convertSupporterArrayListToJsonElement(Account.getSupportersList()).toString(), mainController);
    }

    public Response getSupplierByCompanyName(String name){
        return new Response(RequestStatus.SUCCESSFUL,Utils.convertObjectToJsonString(Supplier.getSupplierByCompanyName(name)), mainController);
    }

    public Response getAccountByUsernameWithinAvailable(String username){
        Account account = Account.getAccountByUsernameWithinAvailable(username);
        JsonArray jsonArray = new JsonArray();
        if (account == null) {
            jsonArray.add("null");
            jsonArray.add("null");
            return new Response(RequestStatus.SUCCESSFUL, jsonArray.toString(), mainController);
        }
        if (account instanceof Customer) {
            jsonArray.add("Customer");
        }
        if (account instanceof Supplier) {
            jsonArray.add("Supplier");
        }
        if (account instanceof Supervisor) {
            jsonArray.add("Supervisor");
        }
        if (account instanceof Supporter) {
            jsonArray.add("Supporter");
        }
        jsonArray.add(Utils.convertObjectToJsonString(account));
        return new Response(RequestStatus.SUCCESSFUL, jsonArray.toString(), mainController);
    }

    public Response controlUpdateCart(){
        mainController.getCart().update();
        return Response.createSuccessResponse(mainController);
    }

    //Supporter methods!
    public Response createChatRoomBetweenSupporterAndCustomer(String supporterUsername){
        Supporter supporter = (Supporter) Account.getAccountByUsernameWithinAvailable(supporterUsername);
        if(!(mainController.getAccount() instanceof Customer)){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Login as customer!"), mainController);
        }
        Customer customer = (Customer) mainController.getAccount();
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.joinChatRoom(supporter);
        chatRoom.joinChatRoom(customer);
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(chatRoom.getChatRoomId()), mainController);
    }

    public Response addMessageToChatRoom(String senderUsername,String content,String chatRoomId){
        try {
            Message.getInstance(senderUsername, content, chatRoomId);
            return Response.createSuccessResponse( mainController);
        } catch (ExceptionalMassage ex){
            return Response.createResponseFromExceptionalMassage(ex, mainController);
        }
    }

    public Response getAllMessagesOfChatRoomById(String chatRoomId){
        ChatRoom chatRoom = ChatRoom.getChatRoomById(chatRoomId);
        if(chatRoom == null){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Chat Room is closed!"), mainController);
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertMessageToJsonElement(chatRoom.getMessages()).toString(), mainController);
    }

    public Response controlSetWageAndMinimum(String wageString , String minimumString ){
        int wage = Integer.parseInt(wageString);
        int minimum = Integer.parseInt(minimumString);
        WageDataBase.update(wage, minimum);
        return Response.createSuccessResponse(mainController);
    }

    public Response controlGetWage(){
        String wageString = String.valueOf(WageDataBase.getWage());
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(wageString), mainController);
    }

    public int controlGetWageInternal(){
        return WageDataBase.getWage();
    }

    public Response controlGetMinimum() {
        String minimumString = String.valueOf(WageDataBase.getMinimum());
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertObjectToJsonString(minimumString), mainController);
    }

    public Response getOnlineCustomers() {
       return new Response(RequestStatus.SUCCESSFUL, Utils.convertCustomerArrayListToJsonElement(mainController.
               getClientThread().getServer().getOnlineCustomers()).toString(), mainController);
    }

    public Response getOnlineSuppliers() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertSupplierArrayListToJsonElement(mainController.
                getClientThread().getServer().getOnlineSuppliers()).toString(), mainController);
    }

    public Response getOnlineSupervisors() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertSupervisorArrayListToJsonElement(mainController.
                getClientThread().getServer().getOnlineSupervisors()).toString(), mainController);
    }

    public Response getOnlineSupporters() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertSupporterArrayListToJsonElement(mainController.
                getClientThread().getServer().getOnlineSupporters()).toString(), mainController);
    }

    public Response getRequestingCustomersBySupporter(){
        if(!(mainController.getAccount() instanceof Supporter)){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Login as Supporter!"), mainController);
        }
        Supporter supporter = (Supporter)mainController.getAccount();
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(ChatRoom.getSupportiveChatRoomIdsForSupporter(supporter)).toString(), mainController);
    }

    public Response getCustomerOfAChatRoom(String chatRoomId){
        ChatRoom chatRoom = ChatRoom.getChatRoomById(chatRoomId);
        if(chatRoom == null){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Chat room doesn't exist!"), mainController);
        }
        Customer customer = chatRoom.getCustomerOfChatRoom();
        if(customer == null) {
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("No customer in this Chat room!"), mainController);
        }
        return new Response(RequestStatus.SUCCESSFUL,Utils.convertObjectToJsonString(customer), mainController);

    }

    public Response controlCloseChatRoomById(String id){
        ChatRoom chatRoom = ChatRoom.getChatRoomById(id);
        if(chatRoom == null){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Chat room doesn't exist!"), mainController);
        }
        ChatRoom.removeChatRoom(chatRoom);
        return Response.createSuccessResponse( mainController);
    }

    public int controlInternalCreateBankAccount(String firstName, String lastName, String username, String password) throws ExceptionalMassage {
        try {
            Socket socket = new Socket(Controller.BANK_IP, Controller.BANK_SOCKET);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("create_account " + firstName + " " + lastName + " " + username + " " + password + " " + password);
            dataOutputStream.flush();
            String response = dataInputStream.readUTF();
            if (!response.matches("\\d{6}")) {
                throw new ExceptionalMassage(response);
            }
            disconnectFromBank(socket, dataOutputStream, dataInputStream);
            return Integer.parseInt(response);
        } catch (IOException e) {
            throw new ExceptionalMassage("Error communicating with bank");
        }
    }

    public void disconnectFromBank(Socket socket, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
        try {
            dataOutputStream.writeUTF("exit");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response controlPayBack(String accountNumberStr, String amountStr) {
        int amount = Integer.parseInt(amountStr);
        if(amount > (mainController.getAccount().getCredit() - WageDataBase.getMinimum())) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Not enough credit in your wallet! MAX: " + (mainController.getAccount().getCredit() - WageDataBase.getMinimum()), mainController);
        }
        if (!(getInternalAccount() instanceof Supplier))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Only suppliers can do this action", mainController);
        try {
            Socket socket = new Socket(Controller.BANK_IP, Controller.BANK_SOCKET);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("get_token Team34 343434");
            dataOutputStream.flush();
            String response1 = dataInputStream.readUTF();
            if (!response1.matches("TOKEN_\\w{10}")) {
                return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, response1, mainController);
            }
            dataOutputStream.writeUTF("create_receipt " + response1 + " move " + amountStr + " " + Account.getStoreBankAccount() + " " + accountNumberStr +
                    " TEAM34_ONLINE_STORE");
            dataOutputStream.flush();
            String response2 = dataInputStream.readUTF();
            if (!response2.matches("TRMOV\\d{15}")) {
                return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, response2, mainController);
            }
            dataOutputStream.writeUTF("pay " + response2);
            dataOutputStream.flush();
            String response3 = dataInputStream.readUTF();
            disconnectFromBank(socket, dataOutputStream, dataInputStream);
            if (!response3.equals("done successfully")) {
                return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, response3, mainController);
            }
            getInternalAccount().setCredit(getInternalAccount().getCredit() - Integer.parseInt(amountStr));
            return new Response(RequestStatus.SUCCESSFUL, "", mainController);
        } catch (IOException e) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "cannot connect to bank server", mainController);
        }
    }

    public Response controlPay(String username, String password, String accountNumber, String amountStr) {
        if (!(getInternalAccount() instanceof Supplier) && !(getInternalAccount() instanceof  Customer))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Only suppliers and customers can do this action", mainController);
        try {
            Socket socket = new Socket(Controller.BANK_IP, Controller.BANK_SOCKET);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("get_token " + username + " " + password);
            dataOutputStream.flush();
            String response1 = dataInputStream.readUTF();
            if (!response1.matches("TOKEN_\\w{10}")) {
                return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, response1, mainController);
            }
            dataOutputStream.writeUTF("create_receipt " + response1 + " move " + amountStr + " " + accountNumber + " " + Account.getStoreBankAccount() +
                    " TEAM34_ONLINE_STORE");
            dataOutputStream.flush();
            String response2 = dataInputStream.readUTF();
            if (!response2.matches("TRMOV\\d{15}")) {
                return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, response2, mainController);
            }
            dataOutputStream.writeUTF("pay " + response2);
            dataOutputStream.flush();
            String response3 = dataInputStream.readUTF();
            disconnectFromBank(socket, dataOutputStream, dataInputStream);
            if (!response3.equals("done successfully")) {
                return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, response3, mainController);
            }
            getInternalAccount().setCredit(getInternalAccount().getCredit() + Integer.parseInt(amountStr));
            return new Response(RequestStatus.SUCCESSFUL, "", mainController);
        } catch (IOException e) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "cannot connect to bank server", mainController);
        }
    }

    public Response controlPay(String amount) {
        return controlPay(getInternalAccount().getUserName(), getInternalAccount().getPassword(), String.valueOf(
                getInternalAccount().getBankAccountNumber()), amount);
    }

    public Response controlGetMembersOfChatRoom(String chatRoomId){
        ArrayList<String> userNames = new ArrayList<>();
        ChatRoom chatRoom = ChatRoom.getChatRoomById(chatRoomId);
        if(mainController.getAccount() == null){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Login First!"), mainController);
        }
        if(chatRoom == null){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Chat room is closed!"), mainController);
        }
        for (Account joinedAccount : chatRoom.getJoinedAccounts()) {
            userNames.add(joinedAccount.getUserName());
        }
        return new Response(RequestStatus.SUCCESSFUL,Utils.convertStringArrayListToJsonElement(userNames).toString(), mainController);
    }

    public Response controlJoinChatRoom(String chatRoomId){
        ChatRoom chatRoom = ChatRoom.getChatRoomById(chatRoomId);
        if(mainController.getAccount() == null){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Login first!"), mainController);
        }
        if(chatRoom == null){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Chat room is closed!"), mainController);
        }
        if(chatRoom.getJoinedAccounts().contains(mainController.getAccount())){
            return Response.createSuccessResponse(mainController);
        }
        chatRoom.joinChatRoom(mainController.getAccount());
        try {
            Message.getInstance("Server",mainController.getAccount().getUserName() + " joined chat room.", chatRoomId);
        } catch (ExceptionalMassage exceptionalMassage) {
            return Response.createResponseFromExceptionalMassage(exceptionalMassage, mainController);
        }
        return Response.createSuccessResponse(mainController);
    }

    public Response controlLeaveChatRoom(String chatRoomId){
        ChatRoom chatRoom = ChatRoom.getChatRoomById(chatRoomId);
        if(chatRoom == null){
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Chat room is closed!"), mainController);
        }
        chatRoom.leaveChatRoom(mainController.getAccount());
        try {
            Message.getInstance("Server",mainController.getAccount().getUserName() + " left chat room.", chatRoomId);
        } catch (ExceptionalMassage exceptionalMassage) {
            return Response.createResponseFromExceptionalMassage(exceptionalMassage, mainController);
        }
        return Response.createSuccessResponse(mainController);
    }
}


