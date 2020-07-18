package account;

import communications.Utils;

public class Supporter extends Account {
    public static Supporter convertJsonStringToSupporter(String jsonString){
        return (Supporter) Utils.convertStringToObject(jsonString, "account.Supporter");
    }
}
