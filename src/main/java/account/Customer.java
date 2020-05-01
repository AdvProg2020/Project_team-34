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
        this.cart = new Cart(this);
    }

    public Customer(String userName, String name, String familyName, String email, String phoneNumber, String password, int credit,  Cart cart) {
        super(userName, name, familyName, email, phoneNumber, password, credit);
        this.cart = cart;
    }

    public CustomerLog getCustomerLog() {
        return customerLog;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        //file modification required
    }

    @Override
    public String getType() {
        return "Customer";
    }

    @Override
    public String toString() {
        return "Customer{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", familyName='" + familyName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", credit=" + credit +
                '}';
    }
}
