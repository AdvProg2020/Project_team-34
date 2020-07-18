package account;

import communications.Utils;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(nameOfCompany, supplier.nameOfCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfCompany);
    }
}
