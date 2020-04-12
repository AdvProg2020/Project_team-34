package log;

import account.Supplier;

import java.util.ArrayList;

public class SupplierLog {
    private static ArrayList<SupplierLog> allSupplierLogs = new ArrayList<>();
    private static int allSupplierLogCreatedCount = 0;

    private String identifier;
    private int earnedMoney;
    private int discountAmount;
    private CustomerLog customerLog;
    private Supplier supplier;

    //Constructor:
    public SupplierLog(CustomerLog customerLog, Supplier supplier) {
        this.customerLog = customerLog;
        this.supplier = supplier;
        this.earnedMoney = customerLog.getSupplierEarnedMoney(supplier);
        this.discountAmount = customerLog.getSupplierSaleAmount(supplier);
        this.identifier = generateIdentifier();
        allSupplierLogs.add(this);
        allSupplierLogCreatedCount++;
        //file modification required
        //completed
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

    public static ArrayList<SupplierLog> getAllSupplierLogs() {
        return allSupplierLogs;
    }

    public static int getAllSupplierLogCreatedCount() {
        return allSupplierLogCreatedCount;
    }

    //Setters:
    private void setIdentifier(String identifier) {
        this.identifier = identifier;
        //file modification required
    }

    private void setEarnedMoney(int earnedMoney) {
        this.earnedMoney = earnedMoney;
        //file modification required
    }

    private void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
        //file modification required
    }

    private void setCustomerLog(CustomerLog customerLog) {
        this.customerLog = customerLog;
        //file modification required
    }

    private void setSupplier(Supplier supplier) {
        this.supplier = supplier;
        //file modification required
    }

    //Modeling methods:
    private String generateIdentifier() {
        return "T34SL" + String.format("%015d", allSupplierLogCreatedCount + 1);
        //completed
    }

    public boolean proceedToNextStep() {
        return customerLog.proceedToNextStep();
        //completed
    }

    public ArrayList<SupplierLog> getSupplierSupplierLog(Supplier supplier) {
        ArrayList<SupplierLog> supplierLogs = new ArrayList<>();
        if (allSupplierLogs.size() != 0) {
            for (SupplierLog supplierLog : allSupplierLogs) {
                if (supplierLog.getSupplier() == supplier) {
                    supplierLogs.add(supplierLog);
                }
            }
        }
        return supplierLogs;
        //completed
    }

    @Override
    public String toString() {
        return null;
    }
}
