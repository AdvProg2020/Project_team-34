package feedback;

import account.Customer;
import communications.Utils;
import product.Product;

/**
 * @author soheil
 * @since 0.01
 */

public class Score {
    private String identifier ;
    private Customer customer;
    private Product product;
    private float score;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public static Score convertJsonStringToScore(String jsonString){
        return (Score) Utils.convertStringToObject(jsonString, "score.Score");
    }
}
