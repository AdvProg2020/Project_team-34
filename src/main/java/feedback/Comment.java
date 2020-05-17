package feedback;

import account.Customer;
import database.CommentDataBase;
import product.Product;

import java.util.ArrayList;

/**
 * @author soheil
 * @since 0.01
 */

public class Comment {
    private static ArrayList<Comment> comments = new ArrayList<>();
    private static int allCommentsNum = 0 ;
    private String commentId ;
    private Customer customer;
    private Product product;
    private String title;
    private String content;
    private CommentState state;
    private boolean customerBoughtThisProduct;


    public Comment(Customer customer, Product product, String title, String content, CommentState state , boolean customerBoughtThisProduct, String commentId) {
        this.title = title;
        this.customer = customer;
        this.product = product;
        this.content = content;
        this.state = state;
        this.customerBoughtThisProduct = customerBoughtThisProduct;
        this.commentId = commentId;
        comments.add(this);
    }

    public Comment(Customer customer, Product product, String title, String content,boolean customerBoughtThisProduct) {
        this.title = title;
        this.customer = customer;
        this.product = product;
        this.content = content;
        this.commentId = generateIdentifier();
        this.customerBoughtThisProduct = customerBoughtThisProduct;
        this.state = CommentState.CONFIRMED;
        comments.add(this);
        allCommentsNum ++;
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

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        CommentDataBase.update(this);
    }

    public boolean hasCustomerBoughtThisProduct(Customer customer){
        return customerBoughtThisProduct;
    }

    public void setCustomerBoughtThisProduct(boolean customerBoughtThisProduct) {
        this.customerBoughtThisProduct = customerBoughtThisProduct;
        CommentDataBase.update(this);
    }

    public static ArrayList<Comment> getComments(){
        return comments;
    }

    public Product getProduct() {
        return product;
    }

    private String generateIdentifier(){
        return "T34C" + String.format("%015d", allCommentsNum + 1);
    }

    public static ArrayList<Comment> getCommentsForProduct(Product product) {
        ArrayList<Comment> returningComments = new ArrayList<>();
        for (Comment comment : comments) {
            if(comment.getProduct() == product){
                returningComments.add(comment);
            }
        }
        return returningComments;
    }

    //Added by rpirayadi
    public static Comment getCommentByIdentifier(String commentId){
        for (Comment eachComment : comments) {
            if(eachComment.getCommentId().equals(commentId))
                return eachComment;
        }
        return null;
    }


    /**
     *
     * @return returns the String form of a JSON object for storing in the database.
     */
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
