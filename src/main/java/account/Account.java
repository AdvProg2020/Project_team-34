package account;


import java.util.ArrayList;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public abstract class Account {
    private String userName , name , familyName , email , phoneNumber , password;
    private int credit;
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
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public static Account getAccountByUsername(String userName){
        return null;
    }

    public void removeAccount (){
        allAccounts.remove(this);
    }

    public abstract String getType();

    public static void importAll(){

    }

    @Override
    public String toString() {
        return "Account{" +
                "userName:'" + userName + '\'' +
                ", name:'" + name + '\'' +
                ", familyName:'" + familyName + '\'' +
                ", email:'" + email + '\'' +
                ", phoneNumber:'" + phoneNumber + '\'' +
                ", password:'" + password + '\'' +
                ", credit:" + credit +
                ", Type:" + getType()+ '\'' +
                '}';
    }

}
