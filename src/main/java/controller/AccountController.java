package controller;

import account.Account;
import account.Customer;
import account.Supervisor;
import account.Supplier;
import cart.Cart;
import cart.ProductInCart;
import cart.ShippingInfo;
import discount.CodedDiscount;
import exceptionalMassage.ExceptionalMassage;
import log.CustomerLog;
import log.LogStatus;
import log.SupplierLog;
import product.Product;
import server.communications.RequestStatus;
import server.communications.Response;
import server.communications.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AccountController {
    private static final long WEEK = 7*24*60*60000;

    private static final int BOUND = 100;

    private Controller mainController;

    public AccountController(Controller mainController) {
        this.mainController = mainController;
    }

    public boolean hasSomeOneLoggedIn(){
        return mainController.getAccount()!= null;
    }

    public String loggedInAccountType() {
        Account account = mainController.getAccount();
        if (account == null)
            return null;
        if (account instanceof Customer)
            return "Customer";
        if (account instanceof Supervisor)
            return "Supervisor";
        return "Supplier";
    }

    public Account getAccount() {
        return mainController.getAccount();
    }

    public Response controlCreateAccount(String username, String type, String name, String familyName, String email,
                                     String phoneNumber, String password, int credit, String nameOfCompany) {
        if (username.trim().length() == 0) return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("username can't be empty"));
        if (name.trim().length() == 0) return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("name can't be empty"));
        if (familyName.trim().length() == 0) return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("family name can't be empty"));
        if (email.trim().length() == 0) return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("email can't be empty"));
        if (phoneNumber.trim().length() == 0) return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("phone number can't be empty"));
        if (password.trim().length() == 0) return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("password can't be empty"));

        if (!Account.isUsernameAvailable(username))
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Duplicate username"));
        if (type.equals("customer")) {
            if (credit == 0) return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("credit cannot be 0"));
            Response response = controlCreateCustomer(username, name, familyName, email, phoneNumber, password, credit);
            controlLogin(username, password);
            return response;
        }
        if (type.equals("supplier")) {
            if (nameOfCompany.trim().length() == 0) return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("credit cannot be 0"));
            Response response = controlCreateSupplier(username, name, familyName, email, phoneNumber, password, credit, nameOfCompany);
            if(response.getStatus() == RequestStatus.EXCEPTIONAL_MASSAGE)
                return response;
            controlLogin(username, password);
            return response;
        }
        if (type.equals("supervisor"))
            return controlCreateSupervisor(username, name, familyName, email, phoneNumber, password, credit);
        return null;
    }

    private Response controlCreateCustomer(String username, String name, String familyName, String email, String phoneNumber,
                                       String password, int credit) {
        new Customer(username, name, familyName, email, phoneNumber, password, credit);
        return Response.createSuccessResponse();
    }

    private Response controlCreateSupplier(String username, String name, String familyName, String email, String phoneNumber,
                                       String password, int credit, String nameOfCompany){
        if (Supplier.getSupplierByCompanyName(nameOfCompany) != null) {
            return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Duplicate company name."));
        }
        new Supplier(username, name, familyName, email, phoneNumber, password, credit, nameOfCompany);
        return Response.createSuccessResponse();
    }

    private Response controlCreateSupervisor(String username, String name, String familyName, String email,
                                         String phoneNumber, String password, int credit){
        mainController.setIsFirstSupervisorCreated(Account.isSupervisorCreated());
        if (mainController.getIsFirstSupervisorCreated()) {
            if (mainController.getAccount() == null)
                return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("You must login as supervisor before create a supervisor account."));
            if (!(mainController.getAccount() instanceof Supervisor))
                return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("You must be a supervisor to create supervisor account."));
        }
        new Supervisor(username, name, familyName, email, phoneNumber, password, credit);
        mainController.setIsFirstSupervisorCreated(true);
        return Response.createSuccessResponse();
    }

    public Response controlLogin(String username, String password){
        if (hasSomeOneLoggedIn())
            return  Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Logout first."));
        Account account = Account.getAccountByUsernameWithinAvailable(username);
        if (account == null)
            return  Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Username doesn't exist."));
        if (!account.getPassword().equals(password))
            return  Response.createResponseFromExceptionalMassage(new ExceptionalMassage("Invalid password."));
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
        return new Response(RequestStatus.SUCCESSFUL,"");
    }

    public Response controlLogout() {
        mainController.setAccount(null);
        mainController.setCart(new Cart(null));
        mainController.getProductController().getFilterAndSort().clear();
        return Response.createSuccessResponse();
    }

    public Response controlViewPersonalInfo() {
        String personalInfo = Utils.convertObjectToJsonString(mainController.getAccount());
        return new Response(RequestStatus.SUCCESSFUL, personalInfo);
    }

    public Response controlViewBalance() {
        String credit = String.valueOf((mainController.getAccount()).getCredit());
        return new Response(RequestStatus.SUCCESSFUL, credit);
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
                   return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("You must enter a number for credit"));
                }
                account.setCredit(value);
                break;
            case "nameOfCompany":
                if (account instanceof Supplier)
                    ((Supplier) account).setNameOfCompany(newValue);
                else
                    return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("You must login as a Supplier to edit company name"));
                break;
            case  "username":
                return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("You can not edit username"));
            case "password":
                account.setPassword(newValue);
                break;
            default:
                return Response.createResponseFromExceptionalMassage(new ExceptionalMassage("No such field " + field));
        }
        return Response.createSuccessResponse();
    }

    public void editAllFields(String name, String familyName, String email, String phoneNumber, String password,
                              int credit, String nameOfCompany) {
        if (password == null || password.trim().length() == 0) {
            password = getAccount().getPassword();
        }
        if (getAccount() instanceof Customer) {
            ((Customer) getAccount()).editAllFields(name, familyName, email, phoneNumber, password, credit);
        } else if (getAccount() instanceof Supplier) {
            ((Supplier) getAccount()).editAllFields(name, familyName, email, phoneNumber, password, nameOfCompany);
        } else if (getAccount() instanceof Supervisor) {
            ((Supervisor) getAccount()).editAllFields(name, familyName, email, phoneNumber, password, credit);
        }
    }

    public String controlGetListOfAccounts() {
        ArrayList<String> allUsername = Account.getAllUsername();
        StringBuilder result = new StringBuilder();
        for (String username : allUsername) {
            result.append(username).append('\n');
        }
        return result.toString();
    }

    public ArrayList<String> controlGetListOfAccountUserNames(){
        ArrayList<String> allUsername = Account.getAllUsername();
        return allUsername;
    }

    public String controlViewUserInfo(String username) throws ExceptionalMassage {
        Account account = Account.getAccountByUsernameWithinAvailable(username);
        if (account == null)
            throw new ExceptionalMassage("Account not found.");
        return account.toString();
    }

    public void controlDeleteUser(String username) throws ExceptionalMassage {
        Account accountGotByUsername = Account.getAccountByUsernameWithinAvailable(username);
        if (accountGotByUsername == null)
            throw new ExceptionalMassage("Account not found.");
        if(username.equals(getAccount().getUserName()))
            throw new ExceptionalMassage("You cannot delete Your self");
        accountGotByUsername.removeAccount();
    }

    public String controlViewCompanyInfo() {
        Account account = mainController.getAccount();
        if (account instanceof Supplier) {
            return ((Supplier) account).getNameOfCompany();
        }
        return null;
    }

    public String controlGetListOfProductsForThisSupplier() throws  ExceptionalMassage{
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
    }

    public void controlAddToCart(String productId, String supplierNameOfCompany) throws ExceptionalMassage {
        if (getAccount() instanceof Supplier || getAccount() instanceof Supervisor) {
            throw new ExceptionalMassage("logout, Supervisor and Supplier are denied");
        }
        Product product = Product.getProductById(productId);
        if (product == null)
            throw new ExceptionalMassage("Product not found.");
        Supplier supplier = Supplier.getSupplierByCompanyName(supplierNameOfCompany);
        if (supplier == null)
            throw new ExceptionalMassage("Supplier not found.");
        mainController.getCart().addProductToCart(product, supplier);
    }

    public Response increaseProductQuantity(String productId, String supplierNameOfCompany) {
        Product product = Product.getProductById(productId);
        if (product == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Product not found.");
        Supplier supplier = Supplier.getSupplierByCompanyName(supplierNameOfCompany);
        if (supplier == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Supplier not found.");
        try {
            mainController.getCart().increaseProductCount(product, supplier);
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.SUCCESSFUL, exceptionalMassage.getMessage());
        }
        return new Response(RequestStatus.SUCCESSFUL, "");
    }

    public Response decreaseProductQuantity(String productId, String supplierNameOfCompany) throws ExceptionalMassage {
        Product product = Product.getProductById(productId);
        if (product == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Product not found.");
        Supplier supplier = Supplier.getSupplierByCompanyName(supplierNameOfCompany);
        if (supplier == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Supplier not found.");
        mainController.getCart().decreaseProductCount(product, supplier);
        return new Response(RequestStatus.SUCCESSFUL, "");
    }

    public Cart controlViewCart() {
        return mainController.getCart();
    }

    public void controlSubmitShippingInfo(String firstName, String lastName, String city, String address,
                                          String postalCode, String phoneNumber) throws ExceptionalMassage {
        Account account = mainController.getAccount();
        if (account == null)
            throw new ExceptionalMassage("Login First.");
        if (!(account instanceof Customer))
            throw new ExceptionalMassage("Login as a customer.");
        mainController.getCart().submitShippingInfo(new ShippingInfo(firstName, lastName, city, address, postalCode, phoneNumber));
        //Modification required
    }

    public Response controlRemoveShippingInfo() throws ExceptionalMassage {
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First.");
        if (!(account instanceof Customer))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a customer.");
        mainController.getCart().removeShippingInfo();
        return new Response(RequestStatus.SUCCESSFUL, "");
    }

    public Response controlSubmitDiscountCode(String discountCode) throws ExceptionalMassage {
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First.");
        if (!(account instanceof Customer))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a customer.");
        mainController.getCart().applyCodedDiscount(discountCode);
        return new Response(RequestStatus.SUCCESSFUL, "");
    }

    public Response controlRemoveDiscountCode() throws ExceptionalMassage {
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First.");
        if (!(account instanceof Customer))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a customer.");
        mainController.getCart().removeCodedDiscount();
        return new Response(RequestStatus.SUCCESSFUL, "");
    }

    public Response finalizeOrder() {
        Account account = mainController.getAccount();
        if (account == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login First.");
        if (!(account instanceof Customer))
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Login as a customer.");
        Cart cart = mainController.getCart();
        Customer customer = (Customer) mainController.getAccount();
        if (cart.isCartClear())
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Your cart is clear.");
        if (account.getCredit() < cart.getBill())
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "You don't have enough credit.");
        HashMap<ProductInCart, Integer> productInCount = cart.getProductInCount();
        for (ProductInCart productInCart : cart.getProductsIn()) {
            if (productInCart.getProduct().getRemainedNumber(productInCart.getSupplier()) < productInCount.get(productInCart))
                return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Product " +
                        productInCart.getProduct().getName() + " is not available in this amount, please reduce.");
        }
        if(cart.getCodedDiscount() != null){
            cart.getCodedDiscount().addUsedCountForCustomer((Customer)mainController.getAccount());
        }
        customer.setCart(new Cart(customer));
        mainController.setCart(customer.getCart());
        CustomerLog customerLog = new CustomerLog(cart);
        if (customerLog.getPaidAmount() >= BOUND) {
            controlInternalCreateCodedDiscountForLoggedInCustomer();
        }
        return new Response(RequestStatus.SUCCESSFUL, String.valueOf(customerLog.getPaidAmount() >= BOUND));
    }

    public Response getAccountUsername() {
        return new Response(RequestStatus.SUCCESSFUL, getAccount().getUserName());
    }

    public Response getAccountName() {
        return new Response(RequestStatus.SUCCESSFUL, getAccount().getName());
    }

    public Response getAccountFamilyName() {
        return new Response(RequestStatus.SUCCESSFUL,  getAccount().getFamilyName());
    }

    public Response getAccountEmail() {
        return new Response(RequestStatus.SUCCESSFUL, getAccount().getEmail());
    }

    public Response getAccountPhoneNumber() {
        return new Response(RequestStatus.SUCCESSFUL, getAccount().getPhoneNumber());
    }

    public Response getAccountCredit() {
        return new Response(RequestStatus.SUCCESSFUL, String.valueOf(getAccount().getCredit()));
    }

    public Response getAccountNameOfCompany() {
        return new Response(RequestStatus.SUCCESSFUL, ((Supplier) getAccount()).getNameOfCompany());
    }

    public Response getCustomerLogs() {
        ArrayList<CustomerLog> customerLogs = CustomerLog.getCustomerCustomerLogs((Customer) getAccount());
        ArrayList<String> toStringLogs = new ArrayList<>();
        for (CustomerLog customerLog : customerLogs) {
            toStringLogs.add(customerLog.toString());
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(toStringLogs).getAsString());
    }

    public Response getSupplierLogs() {
        ArrayList<SupplierLog> supplierLogs = SupplierLog.getSupplierSupplierLog((Supplier) getAccount());
        ArrayList<String> toStringLogs = new ArrayList<>();
        for (SupplierLog supplierLog : supplierLogs) {
            toStringLogs.add(supplierLog.toString());
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(toStringLogs).getAsString());
    }

    public Response getSupervisorLogs() {
        ArrayList<CustomerLog> supervisorLogs = CustomerLog.getAllCustomerLogs();
        ArrayList<String> toStringLogs = new ArrayList<>();
        for (CustomerLog customerLog : supervisorLogs) {
            toStringLogs.add(customerLog.toString());
        }
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertStringArrayListToJsonElement(toStringLogs).getAsString());
    }

    public Response getCustomerLogById(String id) {
        CustomerLog customerLog = CustomerLog.getCustomerLogById(id);
        if (customerLog == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Log not found");
        return new Response(RequestStatus.SUCCESSFUL, customerLog.toString());
    }

    public Response getSupplierLogById(String id) {
        SupplierLog supplierLog = SupplierLog.getSupplierLogById(id);
        if (supplierLog == null)
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Log not found");
        return new Response(RequestStatus.SUCCESSFUL, supplierLog.toString());
    }

    public Response proceedCustomerLog(String id) {
        try {
            CustomerLog customerLog = CustomerLog.getCustomerLogById(id);
            if (customerLog == null)
                return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Log not found");
            customerLog.proceedToNextStep();
            return new Response(RequestStatus.SUCCESSFUL, String.valueOf(true));
        } catch (ExceptionalMassage exceptionalMassage) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "");
        }
    }

    public Response isLogProcessable(String id) {
        CustomerLog customerLog = CustomerLog.getCustomerLogById(id);
        if (customerLog == null) {
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, "Log not found");
        }
        return new Response(RequestStatus.SUCCESSFUL, String.valueOf(customerLog.
                getDeliveryStatus() != LogStatus.DELIVERED));
    }

    public Response numberOfProductInCart(ProductInCart productInCart) {
        int c;
        try {
            c = mainController.getCart().getCountOfProductInCart(productInCart);
        } catch (ExceptionalMassage exceptionalMassage) {
            c = 0;
        }
        return new Response(RequestStatus.SUCCESSFUL, String.valueOf(c));
    }

    public Response controlClearCart() {
        mainController.getCart().clear();
        return new Response(RequestStatus.SUCCESSFUL, "");
    }

    public Response controlGetRequestForLoggedInSupplier() {
        return new Response(RequestStatus.SUCCESSFUL, Utils.convertProductArrayListToJsonElement(Product.
                getRequestsForThisSupplier((Supplier) mainController.getAccount())).getAsString());
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
        return new Response(RequestStatus.SUCCESSFUL, "");
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
}
