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

    public Score(float score, Customer customer) {
        this.score = score;
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public float getScore() {
        return score;
    }

    public boolean canCustomerRateThisProduct(Customer customer){
        return false;
    }

    public static float getAverageScoreForProduct(Product product){
        float total = 0;
        int counter = 0;
        for (Score score : scores) {
            if(score.getProduct() == product){
                total += score.getScore();
                counter++;
            }
        }
        return total/counter;
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
