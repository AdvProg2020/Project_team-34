package feedback;

import account.Customer;
import database.CommentDataBase;
import discount.CodedDiscount;
import product.Product;
import server.communications.Response;
import server.communications.Utils;

import java.util.ArrayList;

/**
 * @author soheil
 * @since 0.01
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

    public static Comment convertJsonStringToComment(String jsonString){
        return (Comment) Utils.convertStringToObject(jsonString, "feedback.Comment");
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

}
