package log;

import account.Supplier;

import java.util.ArrayList;

public class SupplierLog {
    private static ArrayList<SupplierLog> allSupplierLogs = new ArrayList<>();

    private String identifier;
    private int earnedMoney;
    private int discountAmount;
    private CustomerLog customerLog;
    private Supplier supplier;

    //Constructor:
    public SupplierLog(CustomerLog customerLog, Supplier supplier) {
        this.customerLog = customerLog;
        this.supplier = supplier;
    }

    //Getters:
    public String getIdentifier() {
        return identifier;
    }

    public int getEarnedMoney() {
        return earnedMoney;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public CustomerLog getCustomerLog() {
        return customerLog;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    //Setters:
    private void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    private void setEarnedMoney(int earnedMoney) {
        this.earnedMoney = earnedMoney;
    }

    private void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    private void setCustomerLog(CustomerLog customerLog) {
        this.customerLog = customerLog;
    }

    private void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    //Modeling methods:
    private String generateIdentifier() {
        return null;
    }

    public static ArrayList<Supplier> getAllSupplierLogs(Supplier supplier) {
        return null;
    }

    public static void importAll() {

    }

    @Override
    public String toString() {
        return null;
    }
}
