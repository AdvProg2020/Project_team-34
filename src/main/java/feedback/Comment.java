package feedback;

import account.Customer;
import product.Product;

import java.util.ArrayList;

public class Comment {
    private Customer customer;
    private Product product;
    private String title;
    private String content;
    //enum
    private boolean customerBoughtThisProduct;
    private static ArrayList<Comment> comments;

    public Comment(Customer customer, Product product, String title, String content, boolean customerBoughtThisProduct) {
        this.title = title;
        this.customer = customer;
        this.product = product;
        this.content = content;
        this.customerBoughtThisProduct = customerBoughtThisProduct;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean hasCustomerBoughtThisProduct(Customer customer){
        return true;
    }

    public void setCustomerBoughtThisProduct(boolean customerBoughtThisProduct) {
        this.customerBoughtThisProduct = customerBoughtThisProduct;
    }
}
