package cart;

import account.Customer;
import account.Supplier;
import discount.CodedDiscount;
import discount.Sale;
import log.ShippingInfo;
import product.Product;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class Cart {
    private Customer owner;
    private ArrayList<Product> productsIn;
    private HashMap<Product, Integer> productsQuantity;
    private HashMap<Product, Supplier> productsSupplier;
    private HashMap<Product, Sale> productsSales;
    private CodedDiscount codedDiscount;
    private ShippingInfo shippingInfo;

    //Constructor
    public Cart(Customer owner) {
        this.owner = owner;
        productsIn = new ArrayList<>();
        productsQuantity = new HashMap<>();
        productsSupplier = new HashMap<>();
        productsSales = new HashMap<>();
        codedDiscount = null;
        shippingInfo = null;
        //file modification required if <owner != null>
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
    private void setCodedDiscount(CodedDiscount codedDiscount) {
        this.codedDiscount = codedDiscount;
        //file modification required
    }

    private void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
        //file modification required
    }

    //Modeling methods:
    public void clearCart() {
        productsIn = new ArrayList<>();
        productsQuantity = new HashMap<>();
        productsSupplier = new HashMap<>();
        productsSales = new HashMap<>();
        codedDiscount = null;
        shippingInfo = null;
        //file modification required if <owner != null>
    }

    public void addProductToCart(Product product, Supplier supplier) throws Exception {
        if (productsIn.contains(product)) {
            changeQuantityOfProductInCart(product, productsQuantity.get(product) + 1);
        } else {
            if (product.getRemainedNumber(supplier) == 0) {
                throw new Exception("This product with this supplier is currently out of stock.");
            }
            productsIn.add(product);
            productsQuantity.put(product, 1);
            productsSupplier.put(product, supplier);
            productsSales.put(product, Sale.getProductSale(product));
            //file modification required if <owner != null>
        }
    }

    public void removeProductFromCart(Product product) throws Exception {
        if (!productsIn.contains(product)) {
            throw new Exception("Product is not in cart yet.");
        }
        productsIn.remove(product);
        productsQuantity.remove(product);
        productsSupplier.remove(product);
        productsSales.remove(product);
        //file modification required if <owner != null>
    }

    public void changeQuantityOfProductInCart(Product product, int newQuantity) throws Exception {
        if (newQuantity == 0) {
            removeProductFromCart(product);
        } else {
            if (product.getRemainedNumber(productsSupplier.get(product)) < newQuantity) {
                throw new Exception("This product is currently out of stock.");
            }
            productsQuantity.replace(product, newQuantity);
        }
        //file modification required if <owner != null>
    }

    public void increaseProductCount(Product product) throws Exception {
        changeQuantityOfProductInCart(product, productsQuantity.get(product) + 1);
    }

    public void decreaseProductCount(Product product) throws Exception {
        changeQuantityOfProductInCart(product, productsQuantity.get(product) + 1);
    }

    public void update() {

    }

    public void applyCodedDiscount(String discountCode) throws Exception {
        if (owner == null) {
            throw new Exception("You should login before apply discount code.");
        }
        CodedDiscount discount = CodedDiscount.getCodedDiscountByCode(discountCode);
        if (discount == null) {
            throw new Exception("This Code is invalid.");
        }
        if (!discount.canCustomerUseCode(owner)) {
            throw new Exception("You can't use this code.");
        }
        setCodedDiscount(discount);
    }

    public void removeCodedDiscount() throws Exception {
        if (codedDiscount == null) {
            throw new Exception("Code hasn't applied yet.");
        }
        setCodedDiscount(null);
    }

    public void submitShippingInfo(ShippingInfo shippingInfo) {
        setShippingInfo(shippingInfo);
    }

    public void removeShippingInfo() throws Exception {
        if (shippingInfo == null) {
            throw new Exception("ShippingInfo hasn't submitted yet.");
        }
        setShippingInfo(null);
    }

    public boolean isShippingInfoSubmitted() {
        return shippingInfo != null;
    }

    public int getValueOfCartWithoutDiscounts() {
        int totalAmount = 0;
        for (Product product : productsIn) {
            totalAmount += product.getPrice(productsSupplier.get(product));
        }
        return totalAmount;
    }

    public int getAmountOfSale() {
        int saleAmount = 0;
        for (Product product : productsIn) {
            Sale productSale = productsSales.get(product);
            if (productSale != null) {
                saleAmount += productSale.discountAmountFor(product.getPrice(productsSupplier.get(product)));
            }
        }
        return saleAmount;
    }

    public int getAmountOfCodedDiscount() {
        return codedDiscount.discountAmountFor(getValueOfCartWithoutDiscounts() - getAmountOfSale());
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

    public String getIdentifier(){
        return null;
    }
}
