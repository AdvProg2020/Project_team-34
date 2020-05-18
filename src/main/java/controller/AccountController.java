package controller;

import account.Account;
import account.Customer;
import account.Supervisor;
import account.Supplier;
import cart.Cart;
import cart.ShippingInfo;
import exceptionalMassage.ExceptionalMassage;
import log.CustomerLog;
import product.Product;

import java.util.ArrayList;

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

    private boolean doesAccountExist(String username) {
        return Account.getAccountByUsername(username) != null;
    }

    public void controlCreateAccount(String username, String type, String name, String familyName, String email,
                                     String phoneNumber, String password, int credit, String nameOfCompany) throws ExceptionalMassage {
        if (this.doesAccountExist(username))
            throw new ExceptionalMassage("Duplicate username");
        if (type.equals("customer")) {
            controlCreateCustomer(username, name, familyName, email, phoneNumber, password, credit);
            controlLogin(username, password);
        }
        if (type.equals("supplier")) {
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
                    account.setFamilyName(newValue);
                else
                    throw new ExceptionalMassage("You must login as a Supplier to edit company name");
                break;
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
        customer.setCart(new Cart(customer));
        mainController.setCart(customer.getCart());
        CustomerLog customerLog = new CustomerLog(cart);
        //customer credit decrease
    }
}
