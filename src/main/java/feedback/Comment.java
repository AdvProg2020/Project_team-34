package feedback;

import account.Customer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import database.CommentDataBase;
import discount.CodedDiscount;
import product.Product;
import server.communications.Response;
import server.communications.Utils;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author soheil
 * @since 0.0.1
 */

public class Comment {
    private static final ArrayList<Comment> comments = new ArrayList<>();
    private static int allCommentsNum = 0;
    private final String commentId;
    private final Customer customer;
    private final Product product;
    private final String title;
    private final String content;
    private final CommentState state;
    private final boolean customerBoughtThisProduct;

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

    public Comment(Customer customer, Product product, String title, String content, CommentState state,
                   boolean customerBoughtThisProduct, String commentId) {
        this.title = title;
        this.customer = customer;
        this.product = product;
        this.content = content;
        this.state = state;
        this.customerBoughtThisProduct = customerBoughtThisProduct;
        this.commentId = commentId;
        allCommentsNum++;
        comments.add(this);
    }

    public Comment(Customer customer, Product product, String title, String content, boolean customerBoughtThisProduct) {
        this.title = title;
        this.customer = customer;
        this.product = product;
        this.content = content;
        this.commentId = generateIdentifier();
        this.customerBoughtThisProduct = customerBoughtThisProduct;
        this.state = CommentState.CONFIRMED;
        comments.add(this);
        allCommentsNum++;
        CommentDataBase.add(this);
    }

    public String getCommentId() {
        return commentId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CommentState getState() {
        return state;
    }

    public boolean didCustomerBoughtThisProduct() {
        return customerBoughtThisProduct;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCustomerBoughtThisProduct() {
        return customerBoughtThisProduct;
    }

    public static ArrayList<Comment> getComments() {
        return comments;
    }

    public Product getProduct() {
        return product;
    }

    private String generateIdentifier() {
        return "T34C" + String.format("%015d", allCommentsNum + 1);
    }

    public static ArrayList<Comment> getCommentsForProduct(Product product) {
        ArrayList<Comment> returningComments = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getProduct() == product) {
                returningComments.add(comment);
            }
        }
        return returningComments;
    }

    //Added by rpirayadi
    public static Comment getCommentByIdentifier(String commentId) {
        for (Comment eachComment : comments) {
            if (eachComment.getCommentId().equals(commentId))
                return eachComment;
        }
        return null;
    }

    public static Comment convertJsonStringToComment(String jsonString) {
        return new Comment(jsonString);
        //return (Comment) Utils.convertStringToObject(jsonString, "feedback.Comment");
    }

    @Override
    public String toString() {
        return "Comment{" +
                "customer=" + customer +
                ", product=" + product +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", customerBoughtThisProduct=" + customerBoughtThisProduct +
                '}';
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
