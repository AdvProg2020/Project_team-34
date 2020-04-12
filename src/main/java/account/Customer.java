package account;

import log.CustomerLog;

public class Customer extends Account {
    private CustomerLog customerLog;

    public Customer(String userName, String name, String familyName, String email, String phoneNumber, String password, int credit) {
        super(userName, name, familyName, email, phoneNumber, password, credit);
    }

    @Override
    public String getType() {
        return "Customer";
    }
}
