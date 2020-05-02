package account;

import cart.Cart;
import exceptionalMassage.ExceptionalMassage;
import log.CustomerLog;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class Customer extends Account {
    private String cartIdentifier;

    public Customer(String userName, String name, String familyName, String email, String phoneNumber, String password, int credit) {
        super(userName, name, familyName, email, phoneNumber, password, credit);
        this.cartIdentifier = new Cart(this).getIdentifier();
    }

    public Customer(String userName, String name, String familyName, String email, String phoneNumber, String password, int credit,  String cartIdentidier) {
        super(userName, name, familyName, email, phoneNumber, password, credit);
        this.cartIdentifier = cartIdentidier;
    }

    public Cart getCart() {
        return Cart.getCartById(cartIdentifier);
    }

    public void setCart(Cart cart) {
        this.cartIdentifier = cart.getIdentifier();
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
