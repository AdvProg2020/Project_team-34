package account;

import database.AccountDataBase;

/**
 * @author rpirayadi
 * @since 0.0.1
 */

public class Supervisor extends Account {

    public Supervisor(String userName, String name, String familyName, String email, String phoneNumber, String password, int credit) {
        super(userName, name, familyName, email, phoneNumber, password, credit,true);
        AccountDataBase.add(this);
    }

    public Supervisor(String userName, String name, String familyName, String email, String phoneNumber, String password, int credit, boolean isAvailable) {
        super(userName, name, familyName, email, phoneNumber, password, credit, isAvailable);
    }

    public String toString() {
        return "Supervisor: " +
                "userName=\'" + userName + '\'' + "\n"+
                "name=\'" + name + '\'' + "\n"+
                "familyName=\'" + familyName + '\'' + "\n"+
                "email=\'" + email + '\'' + "\n"+
                "phoneNumber=\'" + phoneNumber + '\'' + "\n"+
                "password=\'" + password + '\'' + "\n"+
                "credit=\'" + credit + '\'' + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Supervisor)) {
            return false;
        }
        return this.getUserName().equals(((Supervisor) o).getUserName());
    }
}
