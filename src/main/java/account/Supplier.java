package account;

import exceptionalMassage.ExceptionalMassage;
import log.SupplierLog;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class Supplier extends Account{

    private String nameOfCompany;
    private SupplierLog supplierLog ;


    public Supplier(String userName, String name, String familyName, String email, String phoneNumber, String password, int credit , String nameOfCompany) {
        super(userName, name, familyName, email, phoneNumber, password, credit);
        this.nameOfCompany = nameOfCompany;
    }

    public SupplierLog getSupplierLog() {
        return supplierLog;
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

    public static Supplier getSupplierByCompanyName(String nameOfCompany) {
        return null;
    }

    @Override
    public String getType() {
        return "Supplier";
    }
}
