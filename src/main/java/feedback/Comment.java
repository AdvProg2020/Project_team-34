package feedback;

import account.Customer;
import communications.Utils;
import product.Product;

/**
 * @author soheil
 * @since 0.01
 */

public class Comment {
    private String commentId;
    private Customer customer;
    private Product product;
    private String title;
    private String content;
    private CommentState state;
    private boolean customerBoughtThisProduct;

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
        return (Comment) Utils.convertStringToObject(jsonString, "feedback.Comment");
    }
}
