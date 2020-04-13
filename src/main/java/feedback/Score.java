package feedback;

import account.Customer;
import product.Product;

import java.util.ArrayList;

/**
 * @author soheil
 * @since 0.01
 */


public class Score {
    private static ArrayList<Score> scores;
    private Customer customer;
    private Product product;
    private float score;


    public Score(float score, Customer customer, Product product) {
        this.score = score;
        this.customer = customer;
        this.product = product;
    }

    public boolean canCustomerRateThisProduct(Customer customer){
        return false;
    }

    public static float getAverageScoreForProduct(Product product){
        return 1;
    }

    public static void importAllData(){

    }

    /**
     *
     * @return returns the String form of a JSON object for storing in the database.
     */
    @Override
    public String toString() {
        return "Score{" +
                "customer=" + customer +
                ", product=" + product +
                ", score=" + score +
                '}';
    }
}
