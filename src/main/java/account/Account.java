package account;


import database.AccountDataBase;

import java.util.ArrayList;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public abstract class Account {
    protected String userName , name , familyName , email , phoneNumber , password;
    protected int credit;
    private static ArrayList<Account> allAccounts = new ArrayList<>();

    public Account(String userName, String name, String familyName, String email, String phoneNumber, String password, int credit) {
        this.userName = userName;
        this.name = name;
        this.familyName = familyName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.credit = credit;
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


    public static ArrayList<String> getAllUsername(){
        ArrayList <String> allUsername= new ArrayList<>();
        for (Account account : allAccounts) {
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

    public static Account getAccountByUsername(String userName) {
        if (allAccounts.size() != 0) {
            for (Account account : allAccounts) {
                if (account.getUserName().equals(userName)) {
                    return account;
                }
            }
        }
        return null;
    }

    public void removeAccount (){
        allAccounts.remove(this);
    }

    public abstract String getType();

}
