package product;

import account.Supplier;
import feedback.Comment;
import state.State;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class Product {
    private static ArrayList<Product> allProduct = new ArrayList<>();
    private static int allCreatedProductNum = 0 ;

    private String productId;
    private State productState;
    private String name , nameOfCompany;
    private int price;
    private ArrayList<Supplier> listOfSuppliers;
    private boolean isAvailable ;
    private Category category;
    private String description;
    private ArrayList<Comment> comments;
    private HashMap<String, String> specification; //method check


    public Product(String name, String nameOfCompany, int price, boolean isAvailable,
                   Category category, String description) {
        this.productId = generateIdentifier();
        this.name = name;
        this.nameOfCompany = nameOfCompany;
        this.price = price;
        listOfSuppliers = new ArrayList<>();
        this.isAvailable = isAvailable;
        this.category = category;
        this.description = description;
        comments = new ArrayList<>();
        this.productState = State.PREPARING_TO_BUILD;
    }

    public Product(String name, String nameOfCompany, int price, ArrayList<Supplier> listOfSuppliers,
                   boolean isAvailable, Category category, String description, ArrayList<Comment> comments) {
        this.productState = State.PREPARING_TO_BUILD;
        this.name = name;
        this.nameOfCompany = nameOfCompany;
        this.price = price;
        this.listOfSuppliers = listOfSuppliers;
        this.isAvailable = isAvailable;
        this.category = category;
        this.description = description;
        this.comments = comments;
    }

    private String generateIdentifier(){
        return "T34P" + String.format("%015d", allCreatedProductNum + 1);
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<Supplier> getListOfSuppliers() {
        return listOfSuppliers;
    }

    public static Product getProductById(String productId){return null;}

    public void removeProduct(){
        allProduct.remove(this);
    }

    public void addProduct(){
        productState = State.CONFIRMED;
        allProduct.add(this);
    }

    public boolean doesSupplierSellThisProduct (Supplier supplier){
        return listOfSuppliers.contains(supplier);
    }

    public static ArrayList<String> getProductIdForSupplier(Supplier supplier){
        ArrayList<String> result = new ArrayList<>();
        for (Product product : allProduct) {
            if(product.doesSupplierSellThisProduct(supplier))
                result.add(product.getProductId());
        }
        return result;
    }

    public static Product getProductByName (String name){
        for (Product product : allProduct) {
            if(product.getName().equals(name))
                return product;
        }
        return null;
    }

}
