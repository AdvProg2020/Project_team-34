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
    private HashMap<Supplier, Integer> priceForEachSupplier;
    private ArrayList<Supplier> listOfSuppliers;
    private HashMap<Supplier,Integer> remainedNumberForEachSupplier;
    private Category category;
    private String description;
    private ArrayList<Comment> comments;
    private HashMap<String, String> specification; //method check


    public Product(Supplier supplier, String name, String nameOfCompany, int price, int remainedNumber,
                   Category category, String description) {
        numberOfViews = 0;
        this.productState = State.PREPARING_TO_BUILD;
        this.productId = generateIdentifier();
        this.name = name;
        this.nameOfCompany = nameOfCompany;
        this.priceForEachSupplier = new HashMap<>();
        this.priceForEachSupplier.put(supplier, price);
        this.listOfSuppliers = new ArrayList<>();
        listOfSuppliers.add(supplier);
        this.remainedNumberForEachSupplier = new HashMap<>();
        this.remainedNumberForEachSupplier.put(supplier,remainedNumber);
        this.category = category;
        this.description = description;
        comments = new ArrayList<>();



        allCreatedProductNum ++;
    }

    public Product(String name, String nameOfCompany, HashMap<Supplier,Integer> priceForEachSupplier, ArrayList<Supplier> listOfSuppliers,
                   HashMap<Supplier,Integer> remainedNumberForEachSupplier, Category category, String description, ArrayList<Comment> comments,
                   int numberOfViews , String productId,String state) {
        this.productState = convertStringToState(state);
        this.productId = productId;
        this.name = name;
        this.nameOfCompany = nameOfCompany;
        this.priceForEachSupplier = priceForEachSupplier;
        this.listOfSuppliers = listOfSuppliers;
        this.remainedNumberForEachSupplier = remainedNumberForEachSupplier;
        this.category = category;
        this.description = description;
        this.comments = comments;
        this.numberOfViews= numberOfViews;

    }

    private State convertStringToState(String state){
        if(state.equals("PREPARING_TO_BUILD"))
            return State.PREPARING_TO_BUILD;
        if(state.equals("PREPARING_TO_EDIT"))
            return State.PREPARING_TO_EDIT;
        return State.CONFIRMED;
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

    public int getNumberOfViews() {
        return numberOfViews;
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public HashMap<Supplier, Integer> getPriceForEachSupplier() {
        return priceForEachSupplier;
    }

    public HashMap<Supplier, Integer> getRemainedNumberForEachSupplier() {
        return remainedNumberForEachSupplier;
    }

    public String getDescription() {
        return description;
    }

    public State getProductState() {
        return productState;
    }

    public Category getCategory() {
        return category;
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

    public void reduceRemainedNumber (Supplier supplier, int amount){
        int remainedNumber = remainedNumberForEachSupplier.get(supplier);
        remainedNumberForEachSupplier.put(supplier, remainedNumber - amount);
    }

    public int getPrice(Supplier supplier) {
        return 0;
    }

    public int getRemainedNumber(Supplier supplier) {
        return 0;
    }

    public static void importAll(){

    }

}
