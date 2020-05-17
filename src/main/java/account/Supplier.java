package account;

import database.AccountDataBase;

/**
 * @author rpirayadi
 * @since 0.0.1
 */

public class Supplier extends Account{

    private String nameOfCompany;

    public Supplier(String userName, String name, String familyName, String email, String phoneNumber, String password,
                    int credit , String nameOfCompany) {
        super(userName, name, familyName, email, phoneNumber, password, credit);
        this.nameOfCompany = nameOfCompany;
        AccountDataBase.add(this);
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
    public String toString() {
        return "Supplier{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", familyName='" + familyName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", credit=" + credit +
                ", nameOfCompany='" + nameOfCompany + '\'' +
                '}';
    }
}
