package feedback;

import account.Customer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import communications.Utils;
import product.Product;

import java.util.Objects;

/**
 * @author soheil
 * @since 0.01
 */

public class Score {
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

    public static Score convertJsonStringToScore(String jsonString) {
        return new Score(jsonString);
//        return (Score) Utils.convertStringToObject(jsonString, "score.Score");
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
