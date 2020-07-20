package account;

import cart.Cart;
import database.AccountDataBase;
import server.communications.Response;
import server.communications.Utils;

import java.util.Objects;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class Customer extends Account {
    private String cartIdentifier;

    public Customer(String userName, String name, String familyName, String email, String phoneNumber, String password,
                    int credit, int bankAccountNumber) {
        super(userName, name, familyName, email, phoneNumber, password, credit,true, bankAccountNumber);
        this.cartIdentifier = new Cart(this).getIdentifier();
        AccountDataBase.add(this);
    }

    public Customer(String userName, String name, String familyName, String email, String phoneNumber, String password,
                    int credit,  String cartIdentifier, boolean isAvailable, int bankAccountNumber) {
        super(userName, name, familyName, email, phoneNumber, password, credit,isAvailable, bankAccountNumber);
        this.cartIdentifier = cartIdentifier;
    }

    public Cart getCart() {
        return Cart.getCartById(cartIdentifier);
    }

    public void setCart(Cart cart) {
        this.cartIdentifier = cart.getIdentifier();
        AccountDataBase.update(this);
    }

    public static Customer convertJsonStringToCustomer(String jsonString){
        return (Customer) Utils.convertStringToObject(jsonString, "account.Customer");
    }

    @Override
    public String toString() {
        return "Customer: \n" +
                "userName=\'" + userName + "\'" + "\n" +
                "name=\'" + name + "\'" + "\n" +
                "familyName=\'" + familyName + "\'" + "\n" +
                "email=\'" + email + "\'" + "\n" +
                "phoneNumber=\'" + phoneNumber + "\'" + "\n" +
                "password=\'" + password + "\'" + "\n" +
                "credit=\'" + credit + "\'" + "\n";
    }
}
