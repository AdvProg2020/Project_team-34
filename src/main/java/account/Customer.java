package account;

import communications.Utils;

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

    public static Customer convertJsonStringToCustomer(String jsonString){
        return (Customer) Utils.convertStringToObject(jsonString, "account.Customer");
    }
}
