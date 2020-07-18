package feedback;

import account.Customer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import communications.Utils;
import product.Product;

import java.util.Objects;

/**
 * @author soheil
 * @since 0.0.1
 */

public class Comment {
    private String commentId;
    private Customer customer;
    private Product product;
    private String title;
    private String content;
    private CommentState state;
    private boolean customerBoughtThisProduct;

    public Comment(String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        this.commentId = jsonObject.get("commentId").getAsString();
        this.customer = Customer.convertJsonStringToCustomer(jsonObject.get("customer").toString());
        this.product = Product.convertJsonStringToProduct(jsonObject.get("product").toString());
        this.title = jsonObject.get("title").getAsString();
        this.content = jsonObject.get("content").getAsString();
        this.state = CommentState.valueOf(jsonObject.get("state").getAsString());
        this.customerBoughtThisProduct = Boolean.parseBoolean(jsonObject.get("customerBoughtThisProduct").getAsString());
    }

    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        jsonObject.add("commentId", jsonParser.parse(Utils.convertObjectToJsonString(commentId)));
        jsonObject.add("customer", jsonParser.parse(Utils.convertObjectToJsonString(customer)));
        jsonObject.add("product", jsonParser.parse(Utils.convertObjectToJsonString(product)));
        jsonObject.add("title", jsonParser.parse(Utils.convertObjectToJsonString(title)));
        jsonObject.add("content", jsonParser.parse(Utils.convertObjectToJsonString(content)));
        jsonObject.add("state", jsonParser.parse(Utils.convertObjectToJsonString(String.valueOf(state))));
        jsonObject.add("customerBoughtThisProduct", jsonParser.parse(Utils.convertObjectToJsonString(
                String.valueOf(customerBoughtThisProduct))));
        return jsonObject.toString();
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommentState getState() {
        return state;
    }

    public void setState(CommentState state) {
        this.state = state;
    }

    public boolean isCustomerBoughtThisProduct() {
        return customerBoughtThisProduct;
    }

    public void setCustomerBoughtThisProduct(boolean customerBoughtThisProduct) {
        this.customerBoughtThisProduct = customerBoughtThisProduct;
    }

    public static Comment convertJsonStringToComment(String jsonString){
        return new Comment(jsonString);
//        return (Comment) Utils.convertStringToObject(jsonString, "feedback.Comment");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(commentId, comment.commentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId);
    }
}
