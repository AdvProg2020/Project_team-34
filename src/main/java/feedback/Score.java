package feedback;

import account.Customer;
import database.DataBase;
import database.ScoreDataBase;
import discount.CodedDiscount;
import product.Product;
import server.communications.Response;
import server.communications.Utils;

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
        ScoreDataBase.add(this);
        scores.add(this);
    }

    public Score(String identifier, Customer customer, Product product, float score) {
        this.identifier = identifier;
        this.customer = customer;
        this.product = product;
        this.score = score;
        scores.add(this);
        allCreatedScoreNum++;

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

    public static float getAverageScoreForProduct(Product product){
        float total = 0;
        int counter = 0;
        for (Score score : scores) {
            if(score.getProduct() == product){
                total += score.getScore();
                counter++;
            }
        }
        if(total == 0 && counter == 0){
            return 0;
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

    public static boolean hasCustomerRateThisProduct(Product product,Customer customer){
        for (Score score : scores) {
            if (score.getCustomer() == customer && score.getProduct() == product){
                return true;
            }
        }
        return false;
    }


    public static Score convertJsonStringToScore(String jsonString){
        return (Score) Utils.convertStringToObject(jsonString, "score.Score");
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
