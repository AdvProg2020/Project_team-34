package product;

import account.Supplier;
import feedback.Comment;
import state.State;

import java.util.ArrayList;

public class Product {
    private String productId;
    private State productState;
    private String name , nameOfCompany;
    private int price;
    private ArrayList<Supplier> listOfSuppliers;
    private boolean isAvailable ;
    private Category category;
    private String description;
    //private int averageScore ;
    private ArrayList<Comment> comments;
    private static ArrayList<Product> allProduct = new ArrayList<>();


    public Product(String name, String nameOfCompany, int price, ArrayList<Supplier> listOfSuppliers, boolean isAvailable,
                   Category category, String description, ArrayList<Comment> comments) {
        this.name = name;
        this.nameOfCompany = nameOfCompany;
        this.price = price;
        this.listOfSuppliers = listOfSuppliers;
        this.isAvailable = isAvailable;
        this.category = category;
        this.description = description;
        this.comments = comments;
        this.productState = State.PREPARING_TO_BUILD;
    }

    public static Product getProductById(String productId){return null;}

    public void removeProduct(){
        allProduct.remove(this);
    }

    public void addProduct(){
        productState = State.CONFIRMED;
        allProduct.add(this);

    }
}
