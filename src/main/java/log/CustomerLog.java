package log;

import account.Customer;
import account.Supplier;
import cart.Cart;
import cart.ProductInCart;
import database.CustomerLogDataBase;
import discount.Sale;
import exceptionalMassage.ExceptionalMassage;
import product.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
    private final Cart cart;
    private LogStatus deliveryStatus;
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
        CustomerLogDataBase.add(this);
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
        CustomerLogDataBase.update(this);
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
        for (CustomerLog customerLog: allCustomerLogs) {
            if (customerLog.getCustomer() == customer) {
                customerLogs.add(customerLog);
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

    public static ArrayList<Customer> getAllCustomersBoughtProduct(Product product) {
        ArrayList<Customer> customerBoughtProduct = new ArrayList<>();
        for (CustomerLog customerLog : allCustomerLogs) {
            if ((customerLog.cart).isProductInCart(product)) {
                if (!customerBoughtProduct.contains(customerLog.getCustomer()))
                    customerBoughtProduct.add(customerLog.getCustomer());
            }
        }
        return customerBoughtProduct;
    }

    public static ArrayList<Customer> getAllCustomersBoughtProductFromSupplier(Product product, Supplier supplier) {
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

    public ArrayList<Product> getProductsBoughtFromSupplier(Supplier supplier) {
        ArrayList<Product> productsBoughtFromSupplier = new ArrayList<>();
        for (ProductInCart productInCart : cart.getProductsIn()) {
            if (productInCart.getSupplier() == supplier) {
                productsBoughtFromSupplier.add(productInCart.getProduct());
            }
        }
        return productsBoughtFromSupplier;
    }

    public HashMap<Product, Integer> getProductsBoughtFromSupplierCount(Supplier supplier) {
        HashMap<Product, Integer> productsBoughtFromSupplierCount = new HashMap<>();
        HashMap<ProductInCart, Integer> cartCountHashMap = cart.getProductInCount();
        for (ProductInCart productInCart : cart.getProductsIn()) {
            if (productInCart.getSupplier() == supplier) {
                productsBoughtFromSupplierCount.put(productInCart.getProduct(), cartCountHashMap.get(productInCart));
            }
        }
        return productsBoughtFromSupplierCount;
    }

    public HashMap<Product, Sale> getProductsBoughtFromSupplierSale(Supplier supplier) {
        HashMap<Product, Sale> productsBoughtFromSupplierSale = new HashMap<>();
        HashMap<ProductInCart, Sale> cartSaleHashMap = cart.getProductInSale();
        for (ProductInCart productInCart : cart.getProductsIn()) {
            if (productInCart.getSupplier() == supplier) {
                productsBoughtFromSupplierSale.put(productInCart.getProduct(), cartSaleHashMap.get(productInCart));
            }
        }
        return productsBoughtFromSupplierSale;
    }


    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
        return
                "Order on " + formatter.format(date) + ", Order Identifier: " + identifier + "\n" +
                "delivery status: " + deliveryStatus.toString() + "\n" +
                "paidAmount: " + paidAmount + "\n" +
                "codedDiscountAmount: " + codedDiscountAmount + "\n" +
                cart.toString();
    }
}
