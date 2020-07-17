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

    public void controlCreateAccount(String username, String type, String name, String familyName, String email,
                                     String phoneNumber, String password, int credit, String nameOfCompany)
            throws ExceptionalMassage {
        if (username.trim().length() == 0) throw new ExceptionalMassage("username can't be empty");
        if (name.trim().length() == 0) throw new ExceptionalMassage("name can't be empty");
        if (familyName.trim().length() == 0) throw new ExceptionalMassage("family name can't be empty");
        if (email.trim().length() == 0) throw new ExceptionalMassage("email can't be empty");
        if (phoneNumber.trim().length() == 0) throw new ExceptionalMassage("phone number can't be empty");
        if (password.trim().length() == 0) throw new ExceptionalMassage("password can't be empty");

        if (!Account.isUsernameAvailable(username))
            throw new ExceptionalMassage("Duplicate username");
        if (type.equals("customer")) {
            if (credit == 0) throw new ExceptionalMassage("credit cannot be 0");
            controlCreateCustomer(username, name, familyName, email, phoneNumber, password, credit);
            controlLogin(username, password);
        }
        if (type.equals("supplier")) {
            if (nameOfCompany.trim().length() == 0) throw new ExceptionalMassage("credit cannot be 0");
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
        mainController.setIsFirstSupervisorCreated(Account.isSupervisorCreated());
        if (mainController.getIsFirstSupervisorCreated()) {
            if (mainController.getAccount() == null)
                throw new ExceptionalMassage("You must login as supervisor before create a supervisor account.");
            if (!(mainController.getAccount() instanceof Supervisor))
                throw new ExceptionalMassage("You must be a supervisor to create supervisor account.");
        }
        new Supervisor(username, name, familyName, email, phoneNumber, password, credit);
        mainController.setIsFirstSupervisorCreated(true);
    }

    public void controlLogin(String username, String password) throws ExceptionalMassage {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(username);
        jsonArray.add(password);
        Request request = new Request(mainController.getToken(), "controlLogin", jsonArray.toString(),
                ControllerSource.ACCOUNT_CONTROLLER);
        try {
            mainController.getObjectOutputStream().writeUTF(Utils.convertObjectToJsonString(request));
            try {
                String responseString = mainController.getObjectInputStream().readUTF();
                Response response = Response.convertJsonStringToResponse(responseString);
                if (response.getStatus() == RequestStatus.EXCEPTIONAL_MASSAGE) {
                    throw new ExceptionalMassage(response.getContent());
                }
            } catch (IOException e) {
                throw new ExceptionalMassage("Response not received");
            }
        } catch (IOException e) {
            throw new ExceptionalMassage("Request failed");
        }
    }

    public void controlLogout() {
        mainController.setAccount(null);
        mainController.setCart(new Cart(null));
        mainController.getProductController().getFilterAndSort().clear();
    }

    public String controlViewPersonalInfo() {
        return String.valueOf(mainController.getAccount());
    }

    public int controlViewBalance() {
        return (mainController.getAccount()).getCredit();
    }

    public void controlEditField(String field, String newValue) throws ExceptionalMassage {
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
                    throw new ExceptionalMassage("You must enter a number for credit");
                }
                account.setCredit(value);
                break;
            case "nameOfCompany":
                if (account instanceof Supplier)
                    ((Supplier) account).setNameOfCompany(newValue);
                else
                    throw new ExceptionalMassage("You must login as a Supplier to edit company name");
                break;
            case  "username":
                throw new ExceptionalMassage("You can not edit username");
            case "password":
                account.setPassword(newValue);
                break;
            default:
                throw new ExceptionalMassage("No such field " + field);
        }

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

    public void increaseProductQuantity(String productId, String supplierNameOfCompany) throws ExceptionalMassage {
        Product product = Product.getProductById(productId);
        if (product == null)
            throw new ExceptionalMassage("Product not found.");
        Supplier supplier = Supplier.getSupplierByCompanyName(supplierNameOfCompany);
        if (supplier == null)
            throw new ExceptionalMassage("Supplier not found.");
        mainController.getCart().increaseProductCount(product, supplier);
    }

    public void decreaseProductQuantity(String productId, String supplierNameOfCompany) throws ExceptionalMassage {
        Product product = Product.getProductById(productId);
        if (product == null)
            throw new ExceptionalMassage("Product not found.");
        Supplier supplier = Supplier.getSupplierByCompanyName(supplierNameOfCompany);
        if (supplier == null)
            throw new ExceptionalMassage("Supplier not found.");
        mainController.getCart().decreaseProductCount(product, supplier);
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

    public void controlRemoveShippingInfo() throws ExceptionalMassage {
        Account account = mainController.getAccount();
        if (account == null)
            throw new ExceptionalMassage("Login First.");
        if (!(account instanceof Customer))
            throw new ExceptionalMassage("Login as a customer.");
        mainController.getCart().removeShippingInfo();
    }
    public void controlSubmitDiscountCode(String discountCode) throws ExceptionalMassage {
        Account account = mainController.getAccount();
        if (account == null)
            throw new ExceptionalMassage("Login First.");
        if (!(account instanceof Customer))
            throw new ExceptionalMassage("Login as a customer.");
        mainController.getCart().applyCodedDiscount(discountCode);
    }

    public void controlRemoveDiscountCode() throws ExceptionalMassage {
        Account account = mainController.getAccount();
        if (account == null)
            throw new ExceptionalMassage("Login First.");
        if (!(account instanceof Customer))
            throw new ExceptionalMassage("Login as a customer.");
        mainController.getCart().removeCodedDiscount();
    }

    public boolean finalizeOrder() throws ExceptionalMassage {
        return Boolean.parseBoolean(communication("finalizeOrder", new JsonArray()).getAsString());
    }

    public String getAccountUsername() throws ExceptionalMassage {
        return communication("getAccountUsername", new JsonArray()).getAsString();
    }

    public String getAccountName() throws ExceptionalMassage {
        return communication("getAccountName", new JsonArray()).getAsString();
    }

    public String getAccountFamilyName() throws ExceptionalMassage {
        return communication("getAccountFamilyName", new JsonArray()).getAsString();
    }

    public String getAccountEmail() throws ExceptionalMassage {
        return communication("getAccountEmail", new JsonArray()).getAsString();
    }

    public String getAccountPhoneNumber() throws ExceptionalMassage {
        return communication("getAccountPhoneNumber", new JsonArray()).getAsString();
    }

    public int getAccountCredit() throws ExceptionalMassage {
        return Integer.parseInt(communication("getAccountCredit", new JsonArray()).getAsString());
    }

    public String getAccountNameOfCompany() throws ExceptionalMassage {
        return communication("", new JsonArray()).getAsString();
    }

    public ArrayList<String> getCustomerLogs() throws ExceptionalMassage {
        return Utils.convertJasonElementToStringArrayList(communication("getCustomerLogs", new JsonArray()));
    }

    public ArrayList<String> getSupplierLogs() throws ExceptionalMassage {
        return Utils.convertJasonElementToStringArrayList(communication("getSupplierLogs", new JsonArray()));
    }

    public ArrayList<String> getSupervisorLogs() throws ExceptionalMassage {
        return Utils.convertJasonElementToStringArrayList(communication("getSupervisorLogs", new JsonArray()));
    }

    public String getCustomerLogById(String id) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(id);
        return communication("getCustomerLogById", inputs).getAsString();
    }

    public String getSupplierLogById(String id) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(id);
        return communication("getSupplierLogById", inputs).getAsString();
    }

    public boolean proceedCustomerLog(String id) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(id);
        return Boolean.parseBoolean(communication("proceedCustomerLog", inputs).getAsString());
    }

    public boolean isLogProcessable(String id) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(id);
        return Boolean.parseBoolean(communication("isLogProcessable", inputs).getAsString());
    }

    public int numberOfProductInCart(ProductInCart productInCart) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(Utils.convertObjectToJsonString(productInCart));
        return Integer.parseInt(communication("numberOfProductInCart", inputs).getAsString());
    }

    public void controlClearCart() throws ExceptionalMassage {
        communication("controlClearCart", new JsonArray());
    }

    public ArrayList<Product> controlGetRequestForLoggedInSupplier() throws ExceptionalMassage {
        return Utils.convertJasonElementToProductArrayList(communication("controlGetRequestForLoggedInSupplier",
                new JsonArray()));
    }

    public void controlCreateCodedDiscountForLoggedInCustomer() throws ExceptionalMassage {
        communication("controlCreateCodedDiscountForLoggedInCustomer", new JsonArray());
    }
}
