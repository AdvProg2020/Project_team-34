package discount;

import account.Supplier;
import communications.Utils;
import product.Product;
import state.State;

import java.util.ArrayList;

/**
 * @author soheil
 * @since 0.01
 */

public class Sale extends Discount {
    private String offId;
    private String rootSaleId;
    private ArrayList<Product> products;
    private State state;
    private Supplier supplier;

    public String getOffId() {
        return offId;
    }

    public void setOffId(String offId) {
        this.offId = offId;
    }

    public String getRootSaleId() {
        return rootSaleId;
    }

    public void setRootSaleId(String rootSaleId) {
        this.rootSaleId = rootSaleId;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public static Sale convertJsonStringToSale(String jsonString){
        return (Sale) Utils.convertStringToObject(jsonString, "discount.Sale");
    }
}
