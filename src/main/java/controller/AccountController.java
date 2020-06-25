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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AccountController {

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
        if (hasSomeOneLoggedIn())
            throw new ExceptionalMassage("Logout first.");
        Account account = Account.getAccountByUsername(username);
        if (account == null)
            throw new ExceptionalMassage("Username doesn't exist.");
        if (!account.getPassword().equals(password))
            throw new ExceptionalMassage("Invalid password.");
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
        Account account = Account.getAccountByUsername(username);
        if (account == null)
            throw new ExceptionalMassage("Account not found.");
        return account.toString();
    }

    public void controlDeleteUser(String username) throws ExceptionalMassage {
        Account accountGotByUsername = Account.getAccountByUsername(username);
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

    public void finalizeOrder() throws ExceptionalMassage {
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
        //customer credit decrease
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
        Date end = new Date(System.currentTimeMillis() + 7*24*60*60000);
        new CodedDiscount(randomCode,start, end,percent,maxAmount,maxUsagePerCustomer);
    }
}
