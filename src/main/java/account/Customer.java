package account;

import cart.Cart;
import exceptionalMassage.ExceptionalMassage;
import log.CustomerLog;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class Customer extends Account {
    private CustomerLog customerLog;
    private Cart cart;

    public Customer(String userName, String name, String familyName, String email, String phoneNumber, String password, int credit) {
        super(userName, name, familyName, email, phoneNumber, password, credit);
        cart = new Cart(this);
    }

    public CustomerLog getCustomerLog() {
        return customerLog;
    }

    public Cart getCart() {
        return cart;
    }

    @Override
    public String getType() {
        return "Customer";
    }
}
