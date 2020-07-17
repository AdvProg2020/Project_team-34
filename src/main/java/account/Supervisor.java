package account;

import communications.Utils;

/**
 * @author rpirayadi
 * @since 0.0.1
 */

public class Supervisor extends Account {
    public static Supervisor convertJsonStringToSupervisor(String jsonString) {
        return (Supervisor) Utils.convertStringToObject(jsonString, "account.Supervisor");
    }
}

