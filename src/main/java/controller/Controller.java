package controller;

import account.Account;
import cart.Cart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Controller {
    private Account account;
    private Cart cart;
    private boolean isFirstSupervisorCreated;
    private final AccountController accountController;
    private final ProductController productController;
    private final OffController offController;

    public Controller() {
        account = null;
        cart = new Cart(null);
        isFirstSupervisorCreated = Account.isFirstSupervisorCreated();
        accountController = new AccountController(this);
        productController = new ProductController(this);
        offController = new OffController(this);
        periodicCodedDiscount();
    }

    public void periodicCodedDiscount() {
        try {
            Scanner reader = new Scanner(new File("src/main/java/PeriodicCodedDiscount.txt"));
            reader.nextLine();
            int percent = Integer.parseInt(reader.nextLine());
            int maxAmount = Integer.parseInt(reader.nextLine());
            long period = Long.parseLong(reader.nextLine());
            long lastTime = Long.parseLong(reader.nextLine());
            long timeNow = System.currentTimeMillis();
            reader.close();
            if (timeNow - lastTime >= period) {
                FileWriter writer = new FileWriter(new File("src/main/java/PeriodicCodedDiscount.txt"));
                writer.write("Data\n");
                writer.write(percent + "\n");
                writer.write(maxAmount + "\n");
                writer.write(period + "\n");
                writer.write(timeNow + "\n");
                writer.write("percent, max amount, period, lastTime");
                writer.close();
                accountController.controlCreateRandomCodesForCustomers(Account.getRandomCustomers(), percent, maxAmount);
            }
        } catch (FileNotFoundException e) {
            System.err.println("PeriodicCodedDiscount Err");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
