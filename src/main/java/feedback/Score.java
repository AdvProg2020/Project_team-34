package feedback;

import account.Customer;
import product.Product;

import java.util.ArrayList;

/**
 * @author soheil
 * @since 0.01
 */


public class Score {
    private static ArrayList<Score> scores = new ArrayList<>();
    private static int allCreatedScoreNum = 0 ;
    private String identifier ;
    private Customer customer;
    private Product product;
    private float score;


    public Score(float score, Customer customer, Product product) {
        this.score = score;
        this.customer = customer;
        this.product = product;
        this.identifier = generateIdentifier();
        allCreatedScoreNum ++ ;
        scores.add(this);
    }

    public Score(String identifier, Customer customer, Product product, float score) {
        this.identifier = identifier;
        this.customer = customer;
        this.product = product;
        this.score = score;
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

    public String getIdentifier() {
        return identifier;
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

    private String generateIdentifier(){
        return "T34P" + String.format("%015d", allCreatedScoreNum + 1);
    }

    //Added by rpirayadi
    public static Score getScoreByIdentifier(String scoreId){
        for (Score eachScore : scores) {
            if(eachScore.getIdentifier().equals(scoreId))
                return eachScore;
        }
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
        return "Score{" +
                "customer=" + customer +
                ", product=" + product +
                ", score=" + score +
                '}';
    }
}
