package discount;

import product.Product;
import state.State;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author soheil
 * @since 0.00
 * This class represents the HARAJ for the Online Market!
 */

public class Sale extends Discount{
    private String offId;
    private ArrayList<Product> products;
    private static ArrayList<Sale> sales;
    private State state;

    public Sale(Date start, Date end, int percent, String offId) {
        super(start, end, percent);
        this.offId = offId;
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
