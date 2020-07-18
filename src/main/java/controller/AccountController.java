package controller;

import account.*;
import cart.Cart;
import cart.ProductInCart;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import communications.ControllerSource;
import communications.Utils;
import exceptionalMassage.ExceptionalMassage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import product.Product;

import java.util.ArrayList;

public class AccountController {
    private static final long WEEK = 7 * 24 * 60 * 60000;

    private static final int BOUND = 100;

    private final Controller mainController;

    public AccountController(Controller mainController) {
        this.mainController = mainController;
    }

    public JsonElement communication(String function, JsonArray inputs) throws ExceptionalMassage {
        return new JsonParser().parse(mainController.
                communication(function, inputs, ControllerSource.ACCOUNT_CONTROLLER).getContent());
    }

    public Account getAccount() throws ExceptionalMassage {
        JsonElement jsonElement = communication("getAccount", new JsonArray());
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        if (jsonArray.get(0).getAsString().equals("Customer"))
            return Customer.convertJsonStringToCustomer(jsonArray.get(1).getAsString());
        if (jsonArray.get(0).getAsString().equals("Supervisor"))
            return Supervisor.convertJsonStringToSupervisor(jsonArray.get(1).getAsString());
        if (jsonArray.get(0).getAsString().equals("Supplier"))
            return Supplier.convertJsonStringToSupplier(jsonArray.get(1).getAsString());
        return null;
    }

    public boolean hasSomeOneLoggedIn() throws ExceptionalMassage {
        JsonElement response = communication("hasSomeOneLoggedIn", new JsonArray());
        return response.getAsBoolean();

    }

    public String loggedInAccountType() throws ExceptionalMassage {
        JsonElement jsonElement = communication("loggedInAccountType", new JsonArray());

        return jsonElement.getAsString();
    }

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
        JsonElement response = communication("controlCreateAccount", input);
        if (type.equals("customer") || type.equals("supplier")) {
            mainController.setToken(response.getAsString());
        }
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
        mainController.setToken(communication("controlLogin", jsonArray).getAsString());
    }

    public void controlLogout() throws ExceptionalMassage {
        mainController.setToken(communication("controlLogout", new JsonArray()).getAsString());
    }

    public String controlViewPersonalInfo() throws ExceptionalMassage {
        JsonElement response = communication("controlViewPersonalInfo", new JsonArray());
        return response.getAsString();
    }

    public int controlViewBalance() throws ExceptionalMassage {
        JsonElement response = communication("controlViewBalance", new JsonArray());
        return response.getAsInt();
    }

    public void controlEditField(String field, String newValue) throws ExceptionalMassage {
        JsonArray input = new JsonArray();
        input.add(field);
        input.add(newValue);
        communication("controlEditField", input);
    }

    public void editAllFields(String name, String familyName, String email, String phoneNumber, String password,
                              int credit, String nameOfCompany) throws ExceptionalMassage {
        JsonArray input = new JsonArray();
        input.add(name);
        input.add(familyName);
        input.add(email);
        input.add(phoneNumber);
        input.add(password);
        input.add(String.valueOf(credit));
        input.add(nameOfCompany);
        communication("editAllFields", input);
    }

    public String controlGetListOfAccounts() throws ExceptionalMassage {
        JsonElement accounts = communication("controlGetListOfAccounts", new JsonArray());
        return accounts.getAsString();
    }

    public ArrayList<String> controlGetListOfAccountUserNames() throws ExceptionalMassage {
        JsonElement response = communication("controlGetListOfAccountUserNames", new JsonArray());
        ArrayList<String> allUsername = Utils.convertJsonElementToStringArrayList(response);
        return allUsername;
    }

    public String controlViewUserInfo(String username) throws ExceptionalMassage {
        JsonArray input = new JsonArray();
        input.add(username);
        JsonElement account = communication("controlViewUserInfo", input);
        return account.getAsString();
    }

    public void controlDeleteUser(String username) throws ExceptionalMassage {
        JsonArray input = new JsonArray();
        input.add(username);
        communication("controlDeleteUser", input);
    }

    public String controlViewCompanyInfo() throws ExceptionalMassage {
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
        communication("controlAddToCart", input);
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

    public Cart controlViewCart() throws ExceptionalMassage {
        JsonElement cart = communication("controlViewCart", new JsonArray());
        return Cart.convertJsonStringToCart(cart.toString());
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
        communication("controlSubmitShippingInfo", inputs);
    }

    public void controlRemoveShippingInfo() throws ExceptionalMassage {
        communication("controlRemoveShippingInfo", new JsonArray());
    }

    public void controlSubmitDiscountCode(String discountCode) throws ExceptionalMassage {
        JsonArray input = new JsonArray();
        input.add(discountCode);
        communication("controlSubmitDiscountCode", input);
    }

    public void controlRemoveDiscountCode() throws ExceptionalMassage {
        communication("controlRemoveDiscountCode", new JsonArray());
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
        return Utils.convertJsonElementToStringArrayList(communication("getCustomerLogs", new JsonArray()));
    }

    public ArrayList<String> getSupplierLogs() throws ExceptionalMassage {
        return Utils.convertJsonElementToStringArrayList(communication("getSupplierLogs", new JsonArray()));
    }

    public ArrayList<String> getSupervisorLogs() throws ExceptionalMassage {
        return Utils.convertJsonElementToStringArrayList(communication("getSupervisorLogs", new JsonArray()));
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
        return Utils.convertJsonElementToProductArrayList(communication("controlGetRequestForLoggedInSupplier",
                new JsonArray()));
    }

    public void controlCreateCodedDiscountForLoggedInCustomer() throws ExceptionalMassage {
        communication("controlCreateCodedDiscountForLoggedInCustomer", new JsonArray());
    }

    public ObservableList<Customer> getCustomerObservableList() throws ExceptionalMassage {
        ArrayList<Customer> arrayList = Utils.convertJsonElementToCustomerArrayList(communication(
                "getCustomerObservableList", new JsonArray()));
        ObservableList<Customer> observableList = FXCollections.observableArrayList();
        observableList.addAll(arrayList);
        return observableList;
    }

    public ObservableList<Supplier> getSupplierObservableList() throws ExceptionalMassage {
        ArrayList<Supplier> arrayList = Utils.convertJsonElementToSupplierArrayList(communication(
                "getSupplierObservableList", new JsonArray()));
        ObservableList<Supplier> observableList = FXCollections.observableArrayList();
        observableList.addAll(arrayList);
        return observableList;
    }

    public ObservableList<Supervisor> getSupervisorObservableList() throws ExceptionalMassage {
        ArrayList<Supervisor> arrayList = Utils.convertJsonElementToSupervisorArrayList(communication(
                "getSupervisorObservableList", new JsonArray()));
        ObservableList<Supervisor> observableList = FXCollections.observableArrayList();
        observableList.addAll(arrayList);
        return observableList;
    }

    public Supplier getSupplierByCompanyName(String companyName) throws ExceptionalMassage {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(companyName);
        return Supplier.convertJsonStringToSupplier(communication("getSupplierByCompanyName",jsonArray).toString());
    }

    public Account getAccountByUsernameWithinAvailable(String username) throws ExceptionalMassage{
        JsonArray inputs = new JsonArray();
        inputs.add(username);
        JsonElement jsonElement = communication("getAccountByUsernameWithinAvailable", inputs);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        if (jsonArray.get(0).getAsString().equals("Customer"))
            return Customer.convertJsonStringToCustomer(jsonArray.get(1).getAsString());
        if (jsonArray.get(0).getAsString().equals("Supervisor"))
            return Supervisor.convertJsonStringToSupervisor(jsonArray.get(1).getAsString());
        if (jsonArray.get(0).getAsString().equals("Supplier"))
            return Supplier.convertJsonStringToSupplier(jsonArray.get(1).getAsString());
        return null;
    }

    public void controlUpdateCart() throws ExceptionalMassage {
        communication("controlUpdateCart", new JsonArray());
    }

    public String createChatRoomBetweenSupporterAndCustomer(Supporter supporter,Customer customer) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(Utils.convertObjectToJsonString(supporter));
        inputs.add(Utils.convertObjectToJsonString(customer));
        return communication("createChatRoomBetweenSupporterAndCustomer", inputs).toString();

    }

    public void addMessageToChatRoom(String senderUsername,String content,String chatRoomId) throws ExceptionalMassage{
        JsonArray inputs = new JsonArray();
        inputs.add(senderUsername);
        inputs.add(content);
        inputs.add(chatRoomId);
        communication("addMessageToChatRoom",inputs);
    }

    public void controlSetWageAndMinimum(int wage , int minimum ) throws ExceptionalMassage {
        JsonArray inputs = new JsonArray();
        inputs.add(wage);
        inputs.add(minimum);
        communication("controlSetWageAndMinimum",inputs);
    }

    public int controlGetWage() throws ExceptionalMassage {
        return Integer.parseInt(communication("controlGetWage", new JsonArray()).getAsString());
    }

    public int controlGetMinimum() throws ExceptionalMassage {
        return Integer.parseInt(communication("controlGetMinimum", new JsonArray()).getAsString());
    }
}
