package feedback;

import account.Customer;
import product.Product;

import java.util.ArrayList;

/**
 * @author soheil
 * @since 0.01
 */



public class Comment {
    private static ArrayList<Comment> comments;
    private Customer customer;
    private Product product;
    private String title;
    private String content;
    private CommentState state;
    private boolean customerBoughtThisProduct;


    public Comment(Customer customer, Product product, String title, String content, boolean customerBoughtThisProduct) {
        this.title = title;
        this.customer = customer;
        this.product = product;
        this.content = content;
        this.customerBoughtThisProduct = customerBoughtThisProduct;
    }

    public Comment(Customer customer, Product product, String title, String content) {
        this.title = title;
        this.customer = customer;
        this.product = product;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public CommentState getState(){
        return state;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean hasCustomerBoughtThisProduct(Customer customer){
        return customerBoughtThisProduct;
    }

    public void setCustomerBoughtThisProduct(boolean customerBoughtThisProduct) {
        this.customerBoughtThisProduct = customerBoughtThisProduct;
    }

    public static ArrayList<Comment> getComments(){
        return comments;
    }

    public Product getProduct() {
        return product;
    }

    public static void importAllData(){

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
