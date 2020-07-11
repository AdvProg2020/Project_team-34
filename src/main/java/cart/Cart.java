package cart;

import account.Customer;
import discount.CodedDiscount;
import discount.Sale;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class Cart {
    private String identifier;
    private Customer owner;
    private ArrayList<ProductInCart> productsIn;
    private HashMap<ProductInCart, Integer> productInCount;
    private HashMap<ProductInCart, Sale> productInSale;
    private CodedDiscount codedDiscount;
    private ShippingInfo shippingInfo;

    public String getIdentifier() {
        return identifier;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public ArrayList<ProductInCart> getProductsIn() {
        return productsIn;
    }

    public HashMap<ProductInCart, Integer> getProductInCount() {
        return productInCount;
    }

    public HashMap<ProductInCart, Sale> getProductInSale() {
        return productInSale;
    }

    public CodedDiscount getCodedDiscount() {
        return codedDiscount;
    }

    public void setCodedDiscount(CodedDiscount codedDiscount) {
        this.codedDiscount = codedDiscount;
    }

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }
}
