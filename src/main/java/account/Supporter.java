package account;

import database.AccountDataBase;
import server.communications.Utils;

public class Supporter extends Account {
    public Supporter(String userName, String name, String familyName, String email, String phoneNumber, int credit) {
        super(userName, name, familyName, email, phoneNumber, credit, true,0);
        AccountDataBase.add(this);
    }

    public Supporter(String userName, String name, String familyName, String email, String phoneNumber, int credit,
                     boolean isAvailable) {
        super(userName, name, familyName, email, phoneNumber, credit, isAvailable,0);
    }

    public static Supporter convertJsonStringToSupporter(String jsonString){
        return (Supporter) Utils.convertStringToObject(jsonString, "account.Supporter");
    }
}
