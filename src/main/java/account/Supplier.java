package account;

import log.SupplierLog;

/**
 * @author rpirayadi
 * @since 0.0.1
 * This class represents a supplier which is also an account
 */
public class Supplier extends Account{

    private String nameOfCompany;
    private SupplierLog supplierLog ;


    public Supplier(String userName, String name, String familyName, String email, String phoneNumber, String password, int credit , String nameOfCompany) {
        super(userName, name, familyName, email, phoneNumber, password, credit);
        this.nameOfCompany = nameOfCompany;
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

    @Override
    public String getType() {
        return "Supplier";
    }
}
