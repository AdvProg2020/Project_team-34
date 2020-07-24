package account;

import database.AccountDataBase;
import server.communications.Utils;

import java.util.Objects;

/**
 * @author rpirayadi
 * @since 0.0.1
 */

public class Supplier extends Account{

    private String nameOfCompany;

    public Supplier(String userName, String name, String familyName, String email, String phoneNumber,
                    int credit , String nameOfCompany,int bankAccountNumber) {
        super(userName, name, familyName, email, phoneNumber, credit,true, bankAccountNumber);
        this.nameOfCompany = nameOfCompany;
        AccountDataBase.add(this);
    }

    public Supplier(String userName, String name, String familyName, String email, String phoneNumber,
                    int credit, boolean isAvailable, String nameOfCompany, int bankAccountNumber) {
        super(userName, name, familyName, email, phoneNumber, credit, isAvailable, bankAccountNumber);
        this.nameOfCompany = nameOfCompany;
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

    public static Supplier getSupplierByCompanyName(String nameOfCompany) {
        return Account.getSupplierByCompanyName(nameOfCompany);
    }

    @Override
    public String toString() {
        return "Supplier :" +
                "userName=\'" + userName + '\'' + "\n" +
                "name=\'" + name +'\''+ "\n" +
                "familyName=\'" + familyName +'\''+ "\n" +
                "email=\'" + email + '\'' + "\n" +
                "phoneNumber=\'" + '\'' + phoneNumber + "\n" +
                "credit=\'" + credit + '\''+ "\n" +
                "nameOfCompany=\'" + nameOfCompany + '\'' + "\n";
    }


    public void editAllFields(String name, String familyName, String email, String phoneNumber, String nameOfCompany) {
        this.name = name;
        this.familyName = familyName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.nameOfCompany = nameOfCompany;
        AccountDataBase.update(this);
    }

    public static Supplier convertJsonStringToSupplier(String jsonString){
        return (Supplier) Utils.convertStringToObject(jsonString, "account.Supplier");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(nameOfCompany, supplier.nameOfCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nameOfCompany);
    }
}
