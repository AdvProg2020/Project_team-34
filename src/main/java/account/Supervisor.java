package account;

import database.AccountDataBase;
import server.communications.Response;
import server.communications.Utils;

/**
 * @author rpirayadi
 * @since 0.0.1
 */

public class Supervisor extends Account {

    public Supervisor(String userName, String name, String familyName, String email, String phoneNumber, int credit,
                      int bankAccountNumber) {
        super(userName, name, familyName, email, phoneNumber, credit,true, bankAccountNumber);
        AccountDataBase.add(this);
    }

    public Supervisor(String userName, String name, String familyName, String email, String phoneNumber, int credit,
                      boolean isAvailable, int bankAccountNumber) {
        super(userName, name, familyName, email, phoneNumber, credit, isAvailable, bankAccountNumber);
    }

    public static Supervisor convertJsonStringToSupervisor(String jsonString){
        return (Supervisor) Utils.convertStringToObject(jsonString, "account.Supervisor");
    }

    public String toString() {
        return "Supervisor: " +
                "userName=\'" + userName + '\'' + "\n"+
                "name=\'" + name + '\'' + "\n"+
                "familyName=\'" + familyName + '\'' + "\n"+
                "email=\'" + email + '\'' + "\n"+
                "phoneNumber=\'" + phoneNumber + '\'' + "\n"+
                "credit=\'" + credit + '\'' + "\n";
    }
}
