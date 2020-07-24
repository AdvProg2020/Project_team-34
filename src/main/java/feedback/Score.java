package feedback;

import account.Customer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import database.ScoreDataBase;
import product.Product;
import server.communications.Utils;

import java.util.ArrayList;
import java.util.Objects;

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

    public Score(String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        this.identifier = jsonObject.get("identifier").getAsString();
        this.customer = Customer.convertJsonStringToCustomer(jsonObject.get("customer").toString());
        this.product = Product.convertJsonStringToProduct(jsonObject.get("product").toString());
        this.score = Float.parseFloat(jsonObject.get("score").getAsString());
    }

    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        jsonObject.add("identifier", jsonParser.parse(Utils.convertObjectToJsonString(identifier)));
        jsonObject.add("customer", jsonParser.parse(Utils.convertObjectToJsonString(customer)));
        jsonObject.add("product", jsonParser.parse(Utils.convertObjectToJsonString(product)));
        jsonObject.add("score", jsonParser.parse(Utils.convertObjectToJsonString(String.valueOf(score))));
        return jsonObject.toString();
    }

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


    public static Score convertJsonStringToScore(String jsonString) {
        return new Score(jsonString);
//        return (Score) Utils.convertStringToObject(jsonString, "score.Score");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return Objects.equals(identifier, score.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
