package log;

import account.Supplier;
import exceptionalMassage.ExceptionalMassage;

import java.util.ArrayList;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class SupplierLog {
    private static final ArrayList<SupplierLog> allSupplierLogs = new ArrayList<>();
    private static int allSupplierLogCreatedCount = 0;

    private final String identifier;
    private final int earnedMoney;
    private final int discountAmount;
    private final int totalPurchase;
    private final CustomerLog customerLog;
    private final Supplier supplier;

    //Constructor:
    public SupplierLog(CustomerLog customerLog, Supplier supplier) {
        this.customerLog = customerLog;
        this.supplier = supplier;
        this.earnedMoney = customerLog.getSupplierEarnedMoney(supplier);
        this.discountAmount = customerLog.getSupplierSaleAmount(supplier);
        this.totalPurchase = customerLog.getTotalPurchaseFromSupplier(supplier);
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

    public int getTotalPurchase() {
        return totalPurchase;
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

    //Modeling methods:
    private static String generateIdentifier() {
        return "T34SL" + String.format("%015d", allSupplierLogCreatedCount + 1);
        //completed
    }

    public void proceedToNextStep() throws ExceptionalMassage {
        customerLog.proceedToNextStep();
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
