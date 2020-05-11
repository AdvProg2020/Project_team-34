package log;

import account.Customer;
import account.Supplier;
import cart.Cart;
import exceptionalMassage.ExceptionalMassage;
import product.Product;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class CustomerLog {
    private static final ArrayList<CustomerLog> allCustomerLogs = new ArrayList<>();
    private static int allCustomerLogCreatedCount = 0;

    private final String identifier;
    private final Date date;
    private final int paidAmount;
    private final int codedDiscountAmount;
    private final Customer customer;
    private LogStatus deliveryStatus;
    private final Cart cart;
    //Suppliers Name Saved In a variable: <cart: cart.Cart>

    //Constructors:
    public CustomerLog(Cart cart) {
        this.customer = cart.getOwner();
        this.cart = cart;
        this.identifier = generateIdentifier();
        this.date = new Date(System.currentTimeMillis());
        this.paidAmount = cart.getBill();
        this.codedDiscountAmount = cart.getAmountOfCodedDiscount();
        this.deliveryStatus = LogStatus.PENDING;
        allCustomerLogs.add(this);
        allCustomerLogCreatedCount++;
        addSubLogForSuppliers();
        //file modification required
    }

    public CustomerLog(String identifier, Date date, LogStatus deliveryStatus, Cart cart) {
        this.identifier = identifier;
        this.date = date;
        this.paidAmount = cart.getBill();
        this.codedDiscountAmount = cart.getAmountOfCodedDiscount();
        this.customer = cart.getOwner();
        this.deliveryStatus = deliveryStatus;
        this.cart = cart;
        allCustomerLogs.add(this);
        allCustomerLogCreatedCount++;
        addSubLogForSuppliers();
    }

    //Getters:
    public String getIdentifier() {
        return identifier;
    }

    public Date getDate() {
        return date;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public int getCodedDiscountAmount() {
        return codedDiscountAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LogStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public Cart getCart() {
        return cart;
    }

    public static int getAllCustomerLogCreatedCount() {
        return allCustomerLogCreatedCount;
    }

    //Setters:
    public void setDeliveryStatus(LogStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
        //file modification required
    }

    //Modeling Methods:
    private static String generateIdentifier() {
        return "T34CL" + String.format("%015d", allCustomerLogCreatedCount + 1);
    }

    public void proceedToNextStep() throws ExceptionalMassage {
        if (deliveryStatus == LogStatus.DELIVERED)
            throw new ExceptionalMassage("This order has already delivered.");
        setDeliveryStatus(deliveryStatus.nextStep());
    }

    public static ArrayList<CustomerLog> getCustomerCustomerLogs(Customer customer) {
        ArrayList<CustomerLog> customerLogs = new ArrayList<>();
        if (allCustomerLogs.size() != 0) {
            for (CustomerLog customerLog: allCustomerLogs) {
                if (customerLog.getCustomer() == customer) {
                    customerLogs.add(customerLog);
                }
            }
        }
        return customerLogs;
    }

    public void addSubLogForSuppliers() {
        ArrayList<Supplier> allSupplierInThisLog = cart.getAllSupplier();
        for (Supplier supplier : allSupplierInThisLog) {
            addSubLogForSupplier(supplier);
        }
    }

    public void addSubLogForSupplier(Supplier supplier) {
        new SupplierLog(this, supplier);
    }

    public int getTotalPurchaseFromSupplier(Supplier supplier) {
        return cart.getSupplierPurchase(supplier);
    }

    public int getSupplierSaleAmount(Supplier supplier) {
        return cart.getSupplierSaleAmount(supplier);
    }

    public int getSupplierEarnedMoney(Supplier supplier) {
        return cart.getSupplierEarnedMoney(supplier);
    }

    public static ArrayList<Customer> getCustomerBoughtProduct(Product product) {
        ArrayList<Customer> customerBoughtProduct = new ArrayList<>();
        for (CustomerLog customerLog : allCustomerLogs) {
            if ((customerLog.cart).isProductInCart(product)) {
                if (!customerBoughtProduct.contains(customerLog.getCustomer()))
                    customerBoughtProduct.add(customerLog.getCustomer());
            }
        }
        return customerBoughtProduct;
    }

    public static ArrayList<Customer> getCustomerBoughtProductFromSupplier(Product product, Supplier supplier) {
        ArrayList<Customer> customerBoughtProduct = new ArrayList<>();
        for (CustomerLog customerLog : allCustomerLogs) {
            if ((customerLog.cart).isProductInCart(product, supplier)) {
                if (!customerBoughtProduct.contains(customerLog.getCustomer()))
                    customerBoughtProduct.add(customerLog.getCustomer());
            }
        }
        return customerBoughtProduct;
    }

    public static CustomerLog getCustomerLogById(String identifier){
        for (CustomerLog customerLog : allCustomerLogs) {
            if (customerLog.getIdentifier().equals(identifier))
                return customerLog;
        }
        return null;
    }

    public static void importAll() {
    }
}
