package cart;

import account.Supplier;
import discount.CodedDiscount;
import discount.Sale;
import log.ShippingInfo;
import product.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
    private ArrayList<Product> productsIn;
    private HashMap<Product, Integer> productsQuantity;
    private HashMap<Product, Supplier> productsSupplier;
    private HashMap<Product, Sale> productsSales;
    private CodedDiscount codedDiscount;
    private ShippingInfo shippingInfo;

    //Constructor
    public Cart() {
        productsIn = new ArrayList<>();
        productsQuantity = new HashMap<>();
        productsSupplier = new HashMap<>();
        productsSales = new HashMap<>();
        codedDiscount = null;
        shippingInfo = null;
    }

    //Getters:
    public ArrayList<Product> getProductsIn() {
        return productsIn;
    }

    public HashMap<Product, Integer> getProductsQuantity() {
        return productsQuantity;
    }

    public HashMap<Product, Supplier> getProductsSupplier() {
        return productsSupplier;
    }

    public HashMap<Product, Sale> getProductsSales() {
        return productsSales;
    }

    public CodedDiscount getCodedDiscount() {
        return codedDiscount;
    }

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    //Setters:
    private void setProductsIn(ArrayList<Product> productsIn) {
        this.productsIn = productsIn;
    }

    private void setProductsQuantity(HashMap<Product, Integer> productsQuantity) {
        this.productsQuantity = productsQuantity;
    }

    private void setProductsSupplier(HashMap<Product, Supplier> productsSupplier) {
        this.productsSupplier = productsSupplier;
    }

    private void setProductsSales(HashMap<Product, Sale> productsSales) {
        this.productsSales = productsSales;
    }

    private void setCodedDiscount(CodedDiscount codedDiscount) {
        this.codedDiscount = codedDiscount;
    }

    private void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    //Modeling methods:
    public void clearCart() {
        productsIn = new ArrayList<>();
        productsQuantity = new HashMap<>();
        productsSupplier = new HashMap<>();
        productsSales = new HashMap<>();
        codedDiscount = null;
        shippingInfo = null;
    }

    public void addProductToCart(Product product, Supplier supplier) {
        productsIn.add(product);
        productsQuantity.put(product, 1);
        productsSupplier.put(product, supplier);
        //check
        productsSales.put(product, null);
    }

    public void removeProductFromCart(Product product) {
        productsIn.remove(product);
        productsQuantity.remove(product);
        productsSupplier.remove(product);
        productsSales.remove(product);
    }

    public void changeQuantityOfProductInCart(Product product, int newQuantity) {
        if (newQuantity == 0) {
            removeProductFromCart(product);
        } else {
            //check
            if (true) {
                productsQuantity.replace(product, newQuantity);
            }
        }
    }

    public void updateSales() {

    }

    public boolean applyCodedDiscount(String discountCode) {
        return false;
    }

    public boolean removeCodedDiscount() {
        return false;
    }

    public boolean submitShippingInfo(ShippingInfo shippingInfo) {
        if (this.shippingInfo == null) {
            this.shippingInfo = shippingInfo;
            return true;
        }
        return false;
    }

    public boolean removeShippingInfo() {
        if (this.shippingInfo != null) {
            this.shippingInfo = null;
            return true;
        }
        return false;
    }

    public boolean isShippingInfoSubmitted() {
        return shippingInfo != null;
    }

    public int getValueOfCartWithoutDiscounts() {
        return 0;
    }

    public int getAmountOfSale() {
        return 0;
    }

    public int getAmountOfCodedDiscount() {
        return 0;
    }

    public int getBill() {
        return getValueOfCartWithoutDiscounts() - (getAmountOfSale() + getAmountOfCodedDiscount());
    }

    public ArrayList<Supplier> getAllSupplier() {
        ArrayList<Supplier> allSuppliers = new ArrayList<>();
        for (Product product : productsIn) {
            if (!allSuppliers.contains(productsSupplier.get(product))) {
                allSuppliers.add(productsSupplier.get(product));
            }
        }
        return allSuppliers;
    }

    public boolean isProductInCart(Product product) {
        return productsIn.contains(product);
        //completed
    }
}
