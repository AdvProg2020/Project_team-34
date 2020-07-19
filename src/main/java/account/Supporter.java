package account;

import cart.Cart;
import database.AccountDataBase;
import server.communications.Utils;

public class Supporter extends Account {
    public Supporter(String userName, String name, String familyName, String email, String phoneNumber, String password, int credit) {
        super(userName, name, familyName, email, phoneNumber, password, credit, true);
        AccountDataBase.add(this);
    }

    public Supporter(String userName, String name, String familyName, String email, String phoneNumber, String password, int credit, boolean isAvailable) {
        super(userName, name, familyName, email, phoneNumber, password, credit, isAvailable);
    }

    public static Supporter convertJsonStringToSupporter(String jsonString){
        return (Supporter) Utils.convertStringToObject(jsonString, "account.Supporter");
    }
}
