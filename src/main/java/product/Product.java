package product;

import account.Supplier;
import communications.Utils;
import state.State;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class Product {
    private int numberOfViews;
    private String productId;
    private State productState;
    private String name, nameOfCompany;
    private HashMap<Supplier, Integer> priceForEachSupplier;
    private ArrayList<Supplier> listOfSuppliers;
    private HashMap<Supplier, Integer> remainedNumberForEachSupplier;
    private String description;
    private HashMap<String, String> specification; //method check
    private String rootProductId;
    private String futureCategoryName;
    private String imageUrl;

    public int getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public State getProductState() {
        return productState;
    }

    public void setProductState(State productState) {
        this.productState = productState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

    public HashMap<Supplier, Integer> getPriceForEachSupplier() {
        return priceForEachSupplier;
    }

    public void setPriceForEachSupplier(HashMap<Supplier, Integer> priceForEachSupplier) {
        this.priceForEachSupplier = priceForEachSupplier;
    }

    public ArrayList<Supplier> getListOfSuppliers() {
        return listOfSuppliers;
    }

    public void setListOfSuppliers(ArrayList<Supplier> listOfSuppliers) {
        this.listOfSuppliers = listOfSuppliers;
    }

    public HashMap<Supplier, Integer> getRemainedNumberForEachSupplier() {
        return remainedNumberForEachSupplier;
    }

    public void setRemainedNumberForEachSupplier(HashMap<Supplier, Integer> remainedNumberForEachSupplier) {
        this.remainedNumberForEachSupplier = remainedNumberForEachSupplier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, String> getSpecification() {
        return specification;
    }

    public void setSpecification(HashMap<String, String> specification) {
        this.specification = specification;
    }

    public String getRootProductId() {
        return rootProductId;
    }

    public void setRootProductId(String rootProductId) {
        this.rootProductId = rootProductId;
    }

    public String getFutureCategoryName() {
        return futureCategoryName;
    }

    public void setFutureCategoryName(String futureCategoryName) {
        this.futureCategoryName = futureCategoryName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Product convertJsonStringToProduct(String jsonString){
        return (Product) Utils.convertStringToObject(jsonString, "product.Product");
    }

    public int getPrice(Supplier supplier) {
        return priceForEachSupplier.get(supplier);
    }
}
