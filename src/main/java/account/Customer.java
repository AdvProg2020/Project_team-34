package account;

import cart.Cart;
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
    }

    @Override
    public String getType() {
        return "Customer";
    }
}
