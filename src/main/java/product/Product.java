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
    private int  numberOfViews ;
    private String productId;
    private State productState;
    private String name , nameOfCompany;
    private int price;
    private ArrayList<String> listOfSuppliersUsername;
    private int remainedNumber;
    private String categoryId;
    private String description;
    private ArrayList<String> commentsId;
    private HashMap<String, String> specification; //method check


    public Product(String name, String nameOfCompany, int price, int remainedNumber,
                   String categoryId, String description) {
        this.productId = generateIdentifier();
        this.name = name;
        this.nameOfCompany = nameOfCompany;
        this.price = price;
        listOfSuppliersUsername = new ArrayList<>();
        this.remainedNumber = remainedNumber;
        this.categoryId = categoryId;
        this.description = description;
        commentsId = new ArrayList<>();
        this.productState = State.PREPARING_TO_BUILD;
        numberOfViews = 0;
        allCreatedProductNum ++;
    }

    public Product(String name, String nameOfCompany, int price, ArrayList<String> listOfSuppliersUsername,
                   int remainedNumber, String categoryId, String description, ArrayList<String> commentsId, int numberOfViews) {
        this.productState = State.PREPARING_TO_BUILD;
        this.name = name;
        this.nameOfCompany = nameOfCompany;
        this.price = price;
        this.listOfSuppliersUsername = listOfSuppliersUsername;
        this.remainedNumber = remainedNumber;
        this.categoryId = categoryId;
        this.description = description;
        this.commentsId = commentsId;
        this.numberOfViews= numberOfViews;
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

    public ArrayList<String> getComments() {
        return commentsId;
    }

    public ArrayList<String> getListOfSuppliersUsername() {
        return listOfSuppliersUsername;
    }

    public static Product getProductById(String productId){return null;}

    public int getNumberOfViews() {
        return numberOfViews;
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public int getPrice() {
        return price;
    }

    public int getRemainedNumber() {
        return remainedNumber;
    }

    public String getDescription() {
        return description;
    }

    public State getProductState() {
        return productState;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public HashMap<String, String> getSpecification() {
        return specification;
    }

    public void removeProduct(){
        allProduct.remove(this);
    }

    public void addProduct(){
        productState = State.CONFIRMED;
        allProduct.add(this);
    }

    public boolean doesSupplierSellThisProduct (Supplier supplier){
        return listOfSuppliersUsername.contains(supplier.getUserName());
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

    public void reduceRemainedNumber (int amount){
        remainedNumber -= amount;
    }

    public static void importAll(){
        
    }

}
