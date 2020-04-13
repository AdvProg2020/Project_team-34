package discount;

import product.Product;
import state.State;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author soheil
 * @since 0.00
 */

public class Sale extends Discount{
    private String offId;
    private ArrayList<Product> products;
    private static ArrayList<Sale> sales;
    private State state;

    public Sale(Date start, Date end, int percent) {
        super(start, end, percent);
        this.offId = generateOffId();
    }

    private String generateOffId(){
        return null;
    }

    public void addSale(Sale sale){
        sales.add(sale);
    }

    public void addProductToSale(Product product){

    }

    public void removeProductFromSale(Product product){

    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Sale> getSales(){
        return null;
    }

    public Sale getSaleById(String id){
        return null;
    }

    public static void importAllData(){

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
