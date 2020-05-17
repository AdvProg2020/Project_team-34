package log;

import account.Supplier;
import discount.Sale;
import product.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

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
    private final ArrayList<Product> products;
    private final HashMap<Product, Integer> productsCount;
    private final HashMap<Product, Sale> productsSale;

    //Constructor:
    public SupplierLog(CustomerLog customerLog, Supplier supplier) {
        this.customerLog = customerLog;
        this.supplier = supplier;
        this.earnedMoney = customerLog.getSupplierEarnedMoney(supplier);
        this.discountAmount = customerLog.getSupplierSaleAmount(supplier);
        this.totalPurchase = customerLog.getTotalPurchaseFromSupplier(supplier);
        this.identifier = generateIdentifier();
        this.products = customerLog.getProductsBoughtFromSupplier(supplier);
        this.productsCount = customerLog.getProductsBoughtFromSupplierCount(supplier);
        this.productsSale = customerLog.getProductsBoughtFromSupplierSale(supplier);
        this.supplier.setCredit(this.supplier.getCredit() + earnedMoney);
        allSupplierLogs.add(this);
        allSupplierLogCreatedCount++;
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

    public ArrayList<Product> getProducts() {
        return products;
    }

    public HashMap<Product, Integer> getProductsCount() {
        return productsCount;
    }

    public HashMap<Product, Sale> getProductsSale() {
        return productsSale;
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
    }

    public static ArrayList<SupplierLog> getSupplierSupplierLog(Supplier supplier) {
        ArrayList<SupplierLog> supplierLogs = new ArrayList<>();
        if (allSupplierLogs.size() != 0) {
            for (SupplierLog supplierLog : allSupplierLogs) {
                if (supplierLog.getSupplier() == supplier) {
                    supplierLogs.add(supplierLog);
                }
            }
        }
        return supplierLogs;
    }

    public static SupplierLog getSupplierLogById(String identifier){
        for (SupplierLog supplierLog : allSupplierLogs) {
            if (supplierLog.getIdentifier().equals(identifier))
                return supplierLog;
        }
        return null;
    }

    public String productsBoughtString() {
        StringBuilder string = new StringBuilder();
        int i = 1;
        for (Product product : products) {
            string.append("Product").append(i).append(". ").append(product.getProductId()).append(" X ").append(productsCount.get(product));
            if (productsSale.get(product) != null) {
                string.append(" in sale: ").append(productsSale.get(product).getOffId());
            }
            string.append("\n");
        }
        return string.toString();
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
        return "SupplierLog on " + formatter.format(customerLog.getDate()) + ", Log identifier: " + identifier + "\n" +
                "earnedMoney: " + earnedMoney + ", discountAmount: " + discountAmount + ", totalPurchase: " + totalPurchase + "\n" +
                productsBoughtString();
    }
}
