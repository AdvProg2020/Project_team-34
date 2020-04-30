package account;

import cart.Cart;
import log.CustomerLog;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class Customer extends Account {
    private Cart cart;

    public Customer(String userName, String name, String familyName, String email, String phoneNumber, String password, int credit) {
        super(userName, name, familyName, email, phoneNumber, password, credit);
        this.cart = new Cart(this);
    }

    public Customer(String userName, String name, String familyName, String email, String phoneNumber, String password, int credit,  Cart cart) {
        super(userName, name, familyName, email, phoneNumber, password, credit);
        this.cart = cart;
    }


    public Cart getCart() {
        return cart;
    }

    @Override
    public String getType() {
        return "Customer";
    }
}
