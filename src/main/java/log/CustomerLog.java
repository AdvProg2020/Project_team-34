package log;

import account.Customer;
import account.Supplier;
import cart.Cart;

import java.util.ArrayList;
import java.util.Date;

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
    }

    private void setDate(Date date) {
        this.date = date;
    }

    private void setPaidAmount(int paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setCodedDiscountAmount(int codedDiscountAmount) {
        this.codedDiscountAmount = codedDiscountAmount;
    }

    private void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDeliveryStatus(LogStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    //Modeling Methods:
    private String generateIdentifier() {
        return "T34CL" + String.format("%015d", allCustomerLogCreatedCount);
    }

    public void proceedToNextStep() {

    }

    public static ArrayList<CustomerLog> getCustomerCustomerLogs(Customer customer) {
        return null;
    }

    public ArrayList<SupplierLog> getSubLogForSuppliers() {
        return null;
    }

    public SupplierLog getSubLogForSupplier(Supplier supplier) {
        return null;
    }

    public ArrayList<Supplier> getAllSuppliers() {
        return null;
    }

    public static ArrayList<SupplierLog> getAllSubLogsForSupplier(Supplier supplier) {
        return null;
    }

    public static ArrayList<SupplierLog> getAllLogsForAllSuppliers() {
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
