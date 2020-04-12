package feedback;

import account.Customer;
import product.Product;

import java.util.ArrayList;

/**
 * @author soheil
 * @since 0.00
 * This class represents the NOMRE for Online Market!
 */


public class Score {
    private Customer customer;
    private Product product;
    private float score;
    private static ArrayList<Score> scores;

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
