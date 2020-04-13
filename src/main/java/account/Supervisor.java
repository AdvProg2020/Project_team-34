package account;

/**
 * @author rpirayadi
 * @since 0.0.1
 * This class represents a supplier which is also an account
 */
public class Supervisor extends Account {

    public Supervisor(String userName, String name, String familyName, String email, String phoneNumber, String password, int credit) {
        super(userName, name, familyName, email, phoneNumber, password, credit);
    }

    @Override
    public String getType() {
        return "Supervisor";
    }
}
