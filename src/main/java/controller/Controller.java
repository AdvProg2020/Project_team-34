package controller;

import account.Account;
import cart.Cart;

public class Controller {
    private Account account;
    private Cart cart;
    private boolean isFirstSupervisorCreated;
    private AccountController accountController;
    private ProductController productController;
    private OffController offController;

    public Controller() {
        account = null;
        cart = new Cart(null);
        isFirstSupervisorCreated = false;
        accountController = new AccountController(this);
        productController = new ProductController(this);
        offController = new OffController(this);
    }

    public AccountController getAccountController() {
        return accountController;
    }

    public ProductController getProductController() {
        return productController;
    }

    public OffController getOffController() {
        return offController;
    }

    public Account getAccount() {
        return account;
    }

    public Cart getCart() {
        return cart;
    }

    public boolean getIsFirstSupervisorCreated() {
        return isFirstSupervisorCreated;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setIsFirstSupervisorCreated(boolean firstSupervisorCreated) {
        isFirstSupervisorCreated = firstSupervisorCreated;
    }
}















