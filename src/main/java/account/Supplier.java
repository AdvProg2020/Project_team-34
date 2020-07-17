package account;

import communications.Utils;

/**
 * @author rpirayadi
 * @since 0.0.1
 */

public class Supplier extends Account{
    private String nameOfCompany;

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

    public static Supplier convertJsonStringToSupplier(String jsonString){
        return (Supplier) Utils.convertStringToObject(jsonString, "account.Supplier");
    }
}
