package discount;

import product.Product;
import state.State;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author soheil
 * @since 0.01
 */

public class Sale extends Discount{
    private static ArrayList<Sale> sales;
    private String offId;
    private ArrayList<Product> products;

    private State state;

    public Sale(Date start, Date end, int percent) {
        super(start, end, percent);
        this.offId = generateOffId();
    }

    public Sale(Date start, Date end,int percent, String offId) {
        super(start, end, 0);
        this.offId = offId;
    }

    private String generateOffId(){
        return null;
    }

    public static void addSale(Sale sale){
        sales.add(sale);
    }

    public void addProductToSale(Product product){
        products.add(product);
    }

    public String getOffId() {
        return offId;
    }

    public State getState() {
        return state;
    }

    public void setOffId(String offId) {
        this.offId = offId;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void removeProductFromSale(Product product){
        products.remove(product);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public static ArrayList<Sale> getSales(){
        return sales;
    }

    public Sale getSaleById(String id){
        for (Sale sale : sales) {
            if(sale.getOffId().equals(id)){
                return sale;
            }
        }
        return null;
    }

    public static void removeSale(Sale sale){
        sales.remove(sale);
    }

    public static void importAllData(){

    }

    public boolean isProductInSale(Product product){
        for (Product product1 : products) {
            if(product1 == product){
                return true;
            }
        }
        return false;
    }

    public static Sale getProductSale(Product product) {
        //HAZARD!!!
        //this method has written by Aryan Ahadinia for use in Cart.java
        for (Sale sale : sales) {
            if (sale.isProductInSale(product)) {
                return sale;
            }
        }
        return null;
    }

    /**
     *
     * @return returns the String form of a JSON object for storing in the database.
     */
    @Override
    public String toString() {
        return "Sale{" +
                "offId='" + offId + '\'' +
                ", products=" + products +
                ", state=" + state +
                ", percent=" + percent +
                '}';
    }
}
