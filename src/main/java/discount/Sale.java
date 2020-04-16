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

    public Sale(Date start, Date end) {
        super(start, end, 0);
        this.offId = generateOffId();
    }

    private String generateOffId(){
        return null;
    }

    public void addSale(Sale sale){
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
