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
    private static ArrayList<CustomerLog> allCustomerLogs = new ArrayList<>();
    private static int allCustomerLogCreatedCount = 0;

    private String identifier;
    private Date date;
    private int paidAmount;
    private int codedDiscountAmount;
    private Customer customer;
    private LogStatus deliveryStatus;
    private Cart cart;
    //Suppliers Name Saved In a variable: <cart: cart.Cart>

    //Constructors:
    public CustomerLog(Customer customer, Cart cart) {
        this.customer = customer;
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
        //completed
    }

    public CustomerLog(String identifier, Date date, int paidAmount, int codedDiscountAmount, Customer customer, LogStatus deliveryStatus, Cart cart) {
        this.identifier = identifier;
        this.date = date;
        this.paidAmount = paidAmount;
        this.codedDiscountAmount = codedDiscountAmount;
        this.customer = customer;
        this.deliveryStatus = deliveryStatus;
        this.cart = cart;
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

    public ArrayList<CustomerLog> getAllLCustomerLogs() {
        return allCustomerLogs;
    }

    //Setters:
    private void setIdentifier(String identifier) {
        this.identifier = identifier;
        //file modification required
    }

    private void setDate(Date date) {
        this.date = date;
        //file modification required
    }

    private void setPaidAmount(int paidAmount) {
        this.paidAmount = paidAmount;
        //file modification required
    }

    public void setCodedDiscountAmount(int codedDiscountAmount) {
        this.codedDiscountAmount = codedDiscountAmount;
        //file modification required
    }

    private void setCustomer(Customer customer) {
        this.customer = customer;
        //file modification required
    }

    public void setDeliveryStatus(LogStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
        //file modification required
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        //file modification required
    }

    //Modeling Methods:
    private static String generateIdentifier() {
        return "T34CL" + String.format("%015d", allCustomerLogCreatedCount + 1);
        //completed
    }

    public void proceedToNextStep() throws ExceptionalMassage {
        if (deliveryStatus == LogStatus.DELIVERED)
            throw new ExceptionalMassage("This order has already delivered.");
        setDeliveryStatus(deliveryStatus.nextStep());
        //completed
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
        //completed
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
        if (allCustomerLogs.size() != 0) {
            for (CustomerLog customerLog : allCustomerLogs) {
                if ((customerLog.cart).isProductInCart(product)) {
                    customerBoughtProduct.add(customerLog.getCustomer());
                }
            }
        }
        return customerBoughtProduct;
    }

    public static ArrayList<Customer> getCustomerBoughtProductFromSupplier(Product product, Supplier supplier) {
        ArrayList<Customer> customerBoughtProduct = new ArrayList<>();
        if (allCustomerLogs.size() != 0) {
            for (CustomerLog customerLog : allCustomerLogs) {
                if ((customerLog.cart).isProductInCart(product, supplier)) {
                    customerBoughtProduct.add(customerLog.getCustomer());
                }
            }
        }
        return customerBoughtProduct;
    }

    public static CustomerLog getCustomerLogById(String identifier){
        return null;
    }
    public static void exportAll() {

    }

    public static void importAll() {

    }


    @Override
    public String toString() {
        return null;
    }
}
