package account;

import database.AccountDataBase;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
//
public class Supervisor extends Account {

    public Supervisor(String userName, String name, String familyName, String email, String phoneNumber, String password, int credit) {
        super(userName, name, familyName, email, phoneNumber, password, credit);
        AccountDataBase.add(this);
    }

    @Override
    public String getType() {
        return "Supervisor";
    }

    public String toString() {
        return "Supervisor{" +
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
