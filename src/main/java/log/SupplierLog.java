package log;

import account.Supplier;
import discount.Sale;
import product.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class SupplierLog {
    private String identifier;
    private Date date;
    private int earnedMoney;
    private int discountAmount;
    private int totalPurchase;
    private CustomerLog customerLog;
    private Supplier supplier;
    private ArrayList<Product> products;
    private HashMap<Product, Integer> productsCount;
    private HashMap<Product, Sale> productsSale;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getEarnedMoney() {
        return earnedMoney;
    }

    public void setEarnedMoney(int earnedMoney) {
        this.earnedMoney = earnedMoney;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(int totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

    public CustomerLog getCustomerLog() {
        return customerLog;
    }

    public void setCustomerLog(CustomerLog customerLog) {
        this.customerLog = customerLog;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public HashMap<Product, Integer> getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(HashMap<Product, Integer> productsCount) {
        this.productsCount = productsCount;
    }

    public HashMap<Product, Sale> getProductsSale() {
        return productsSale;
    }

    public void setProductsSale(HashMap<Product, Sale> productsSale) {
        this.productsSale = productsSale;
    }
}
