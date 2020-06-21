package account;

import database.AccountDataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author rpirayadi
 * @since 0.0.1
 */

public abstract class Account {
    protected String userName , name , familyName , email , phoneNumber , password;
    protected int credit;
    protected boolean isAvailable ;
    private static final ArrayList<Account> allAccounts = new ArrayList<>();

    public Account(String userName, String name, String familyName, String email, String phoneNumber, String password,
                   int credit, boolean isAvailable) {
        this.userName = userName;
        this.name = name;
        this.familyName = familyName;
        this.email = email;
       this.phoneNumber = phoneNumber;
        this.password = password;
        this.credit = credit;
        this.isAvailable = isAvailable;
        allAccounts.add(this);
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public int getCredit() {
        return credit;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public static ArrayList<String> getAllUsername(){
        ArrayList<Account> availableAccounts = getAllAvailableAccounts();
        ArrayList <String> allUsername= new ArrayList<>();
        for (Account account :availableAccounts) {
            allUsername.add(account.getUserName());
        }
        return allUsername;
    }

    public void setName(String name) {
        this.name = name;
        AccountDataBase.update(this);
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
        AccountDataBase.update(this);
    }

    public void setEmail(String email) {
        this.email = email;
        AccountDataBase.update(this);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        AccountDataBase.update(this);
    }

    public void setPassword(String password) {
        this.password = password;
        AccountDataBase.update(this);
    }

    public void setCredit(int credit) {
        this.credit = credit;
        AccountDataBase.update(this);
    }

    public void setIsAvailable(boolean IsAvailable) {
        isAvailable = IsAvailable;
        AccountDataBase.update(this);
    }

    public static boolean isUsernameAvailable(String userName) {
        for (Account account : allAccounts) {
            if (account.getUserName().equals(userName)) {
                return false;
            }
        }
        return true;
    }

    public static Account getAccountByUsername(String userName) {
        ArrayList<Account> availableAccounts = getAllAvailableAccounts();
        if( availableAccounts.size() != 0) {
            for (Account account : availableAccounts) {
                if (account.getUserName().equals(userName)) {
                    return account;
                }
            }
        }
        return null;
    }

    public void removeAccount (){
       setIsAvailable(false);
    }

    public static Supplier getSupplierByCompanyName(String companyName){
        ArrayList<Account> availableAccounts = getAllAvailableAccounts();
        for (Account account : availableAccounts) {
            if(account instanceof  Supplier && ((Supplier) account).getNameOfCompany().equals(companyName))
                return (Supplier)account;
        }
        return null;
    }

    private static ArrayList<Account> getAllAvailableAccounts (){
        ArrayList<Account> availableAccounts = new ArrayList<>();
        for (Account account : allAccounts) {
            if(account.isAvailable)
                availableAccounts.add(account);
        }
        return availableAccounts;
    }

    public static boolean isSupervisorCreated() {
        if (allAccounts.size() != 0) {
            for (Account account : allAccounts) {
                if (account instanceof Supervisor) {
                    return true;
                }
            }
        }
        return false;
    }

    public void editAllFields(String name, String familyName, String email, String phoneNumber, String password,
                              int credit) {
        this.userName = userName;
        this.name = name;
        this.familyName = familyName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.credit = credit;
        AccountDataBase.update(this);
    }

    public String getAccountType(){
        if(this instanceof Customer)
            return "Customer";
        if(this instanceof Supervisor)
            return "Supervisor";
        return "Supplier";
    }

    public static ObservableList<Supervisor> getSupervisorsObservableList() {
        ObservableList<Supervisor> allSupervisors = FXCollections.observableArrayList();
        for (Account account: allAccounts) {
            if (account instanceof Supervisor && account.isAvailable) {
                allSupervisors.add((Supervisor) account);
            }
        }
        return allSupervisors;
    }

    public static ObservableList<Supplier> getSuppliersObservableList() {
        ObservableList<Supplier> allSuppliers = FXCollections.observableArrayList();
        for (Account account: allAccounts) {
            if (account instanceof Supplier && account.isAvailable) {
                allSuppliers.add((Supplier) account);
            }
        }
        return allSuppliers;
    }

    public static ObservableList<Customer> getCustomersObservableList() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        for (Account account: allAccounts) {
            if (account instanceof Customer && account.isAvailable) {
                allCustomers.add((Customer) account);
            }
        }
        return allCustomers;
    }

    public static boolean isFirstSupervisorCreated() {
        for (Account account : allAccounts) {
            if (account.isAvailable && account instanceof Supervisor) {
                return true;
            }
        }
        return false;
    }
}
