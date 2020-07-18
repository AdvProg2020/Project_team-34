package controller;

import account.Account;
import account.Customer;
import cart.Cart;
import server.ClientThread;

public class Controller {
    private final ClientThread clientThread;

    private String token;
    private Account account;
    private Cart cart;

    private boolean isFirstSupervisorCreated;

    private final AccountController accountController;
    private final ProductController productController;
    private final OffController offController;

    public Controller(ClientThread clientThread, String firstToken) {
        this.clientThread = clientThread;
        this.token = firstToken;
        this.account = null;
        this.cart = new Cart((Customer) null);
        this.isFirstSupervisorCreated = Account.isFirstSupervisorCreated();
        this.accountController = new AccountController(this);
        this.productController = new ProductController(this);
        this.offController = new OffController(this);
    }

    public ClientThread getClientThread() {
        return clientThread;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public boolean getIsFirstSupervisorCreated() {
        return isFirstSupervisorCreated;
    }

    public void setIsFirstSupervisorCreated(boolean firstSupervisorCreated) {
        isFirstSupervisorCreated = firstSupervisorCreated;
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
}
