package account;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class Customer extends Account {
    private String cartIdentifier;

    public String getCartIdentifier() {
        return cartIdentifier;
    }

    public void setCartIdentifier(String cartIdentifier) {
        this.cartIdentifier = cartIdentifier;
    }
}
