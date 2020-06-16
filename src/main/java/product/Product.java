package product;

import account.Supplier;
import database.ProductDataBase;
import exceptionalMassage.ExceptionalMassage;
import state.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
    private String description;
    private HashMap<String, String> specification; //method check
    private String rootProductId ;
    private String futureCategoryName ;



    public Product(Supplier supplier, String name, String nameOfCompany, int price, int remainedNumber, String description,String rootProductId,String futureCategoryName, HashMap<String,String> specification) {
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
        this.description = description;
        this.rootProductId = rootProductId;
        this.futureCategoryName = futureCategoryName;
        this.specification = specification;
        allCreatedProductNum ++;
        allProduct.add(this);
        ProductDataBase.add(this);
    }

    public Product(String name, String nameOfCompany, HashMap<Supplier,Integer> priceForEachSupplier, ArrayList<Supplier> listOfSuppliers,
                   HashMap<Supplier,Integer> remainedNumberForEachSupplier, String description,
                   int numberOfViews , String productId,State state,String rootProductId,String futureCategoryName, HashMap<String, String> specification) {
        this.productState = state;
        this.productId = productId;
        this.name = name;
        this.nameOfCompany = nameOfCompany;
        this.priceForEachSupplier = priceForEachSupplier;
        this.listOfSuppliers = listOfSuppliers;
        this.remainedNumberForEachSupplier = remainedNumberForEachSupplier;
        this.description = description;
        this.numberOfViews= numberOfViews;
        this.rootProductId = rootProductId;
        this.futureCategoryName = futureCategoryName;
        this.specification = specification;
        allCreatedProductNum ++;
        allProduct.add(this);
    }

    public Product(Product product) {
        this.productId = generateIdentifier();
        this.name = product.getName();
        this.nameOfCompany = product.getNameOfCompany();
        this.priceForEachSupplier = product.getPriceForEachSupplier();
        this.listOfSuppliers = product.getListOfSuppliers();
        this.remainedNumberForEachSupplier = product.getRemainedNumberForEachSupplier();
        this.description = product.getDescription();
        this.numberOfViews= product.getNumberOfViews();
        this.rootProductId = product.getProductId();
        this.productState = State.PREPARING_TO_EDIT;
        this.specification = new HashMap<>(product.getSpecification());
        this.futureCategoryName = product.getFutureCategoryName();
        allCreatedProductNum ++;
        allProduct.add(this);
        ProductDataBase.add(this);
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

    public String getRootProductId() {
        return rootProductId;
    }

    public HashMap<String, String> getSpecification() {
        return specification;
    }

    public int getPrice(Supplier supplier) {
        return priceForEachSupplier.get(supplier);
    }

    public int getRemainedNumber(Supplier supplier) {
        return remainedNumberForEachSupplier.get(supplier);
        //aryan
    }

    public String getFutureCategoryName() {
        return futureCategoryName;
    }

    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
        ProductDataBase.update(this);
    }

    public void setProductState(State productState) {
        this.productState = productState;
        ProductDataBase.update(this);
    }

    public void setName(String name) {
        this.name = name;
        ProductDataBase.update(this);
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
        ProductDataBase.update(this);
    }

    public void setDescription(String description) {
        this.description = description;
        ProductDataBase.update(this);
    }

    public void setPriceForEachSupplier(HashMap<Supplier, Integer> priceForEachSupplier) {
        this.priceForEachSupplier = priceForEachSupplier;
        ProductDataBase.update(this);
    }

    public void setRemainedNumberForEachSupplier(HashMap<Supplier, Integer> remainedNumberForEachSupplier) {
        this.remainedNumberForEachSupplier = remainedNumberForEachSupplier;
        ProductDataBase.update(this);
    }

    public void setSpecification(HashMap<String, String> specification) {
        this.specification = specification;
    }

    public void setFutureCategoryName(String futureCategoryName) {
        this.futureCategoryName = futureCategoryName;
    }

    public ArrayList<Supplier> getListOfSuppliers() {
        return listOfSuppliers;
    }

    private static ArrayList<Product> getConfirmedProducts(){
        ArrayList<Product> confirmedProducts = new ArrayList<>();
        for (Product eachProduct : allProduct) {
            if(eachProduct.getProductState() == State.CONFIRMED)
                confirmedProducts.add(eachProduct);
        }
        return confirmedProducts;
    }


    public static Product getProductById(String productId){
        for (Product eachProduct : allProduct) {
            if(eachProduct.getProductId().equals(productId))
                return eachProduct;
        }
        return null;
    }

    public static Product getConFirmedProductById(String productId){
        ArrayList<Product> confirmedProducts = getConfirmedProducts();
        for (Product eachProduct : confirmedProducts) {
            if(eachProduct.getProductId().equals(productId))
                return eachProduct;
        }
        return null;
    }


    public void removeProduct() throws ExceptionalMassage {
        setProductState(State.DELETED);
        Category.getProductCategory(this).removeProduct(this);
    }

    public void addProduct(){
        productState = State.CONFIRMED;
    }

    public boolean doesSupplierSellThisProduct (Supplier supplier){
        return listOfSuppliers.contains(supplier);
    }

    public static ArrayList<Product> getProductForSupplier(Supplier supplier){
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : allProduct) {
            if(product.doesSupplierSellThisProduct(supplier))
                result.add(product);
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
        ProductDataBase.update(this);
    }

    public void editSpecialField(String field, String value) throws ExceptionalMassage {
        if(!specification.containsKey(field))
            throw new ExceptionalMassage("No such field was found");
        specification.put(field,value);

    }


    public void addNewSupplierForProduct(Supplier supplier, int price , int remainedNumber){
        Product newProduct = new Product(this);
        newProduct.getListOfSuppliers().add(supplier);
        newProduct.getPriceForEachSupplier().put(supplier,price);
        newProduct.getRemainedNumberForEachSupplier().put(supplier,remainedNumber);
        ProductDataBase.update(newProduct);
    }

    public boolean isProductAvailableNow() {
        for (Supplier supplier : remainedNumberForEachSupplier.keySet()) {
            if(remainedNumberForEachSupplier.get(supplier) > 0)
                return true;
        }
        return false;
    }

    public boolean isProductProvidedInPriceLowerThan(int upperBound) {
        for (Supplier supplier : priceForEachSupplier.keySet()) {
            if(priceForEachSupplier.get(supplier) <= upperBound )
                return true;
        }
        return false;
    }

    public boolean isProductProvidedInPriceUpperThan(int lowerBound) {
        for (Supplier supplier : priceForEachSupplier.keySet()) {
            if(priceForEachSupplier.get(supplier) >= lowerBound)
                return true;
        }
        return false;
    }

    public static ArrayList<String> getAllProductRequestId(){
        ArrayList<String > result = new ArrayList<>();
        for (Product eachProduct : allProduct) {
            if(eachProduct.getProductState() == State.PREPARING_TO_EDIT || eachProduct.getProductState() == State.PREPARING_TO_BUILD){
                result.add(convertProductIdToRequestId(eachProduct.getProductId()));
            }
        }
        return result;
    }

    public static String getDetailsForProductRequest(String requestId){
        Product product = Product.getProductById(convertRequestIdToProductId(requestId));
        StringBuilder result = new StringBuilder();
        if(product.getProductState() == State.PREPARING_TO_EDIT){
            Product rootProduct = Product.getProductById(product.getRootProductId());
            result.append("name= ").append(rootProduct.getName()).append("==>").append(product.getName()).append("\n");
            result.append("nameOfCompany= ").append(rootProduct.getNameOfCompany()).append("==>").append(product.getNameOfCompany()).append("\n");
            result.append("description= ").append(rootProduct.getDescription()).append("==>").append(product.getDescription()).append("\n");
            HashMap<String, String> firstSpecification = rootProduct.getSpecification();
            HashMap<String, String> secondSpecification = product.getSpecification();
            for (String specialField : firstSpecification.keySet()) {
                result.append(specialField).append("= ").append(firstSpecification.get(specialField)).append("==>").append(secondSpecification.get(specialField)).append("\n");
            }
        }
        else {
            result.append(product.toString());
        }
        return String.valueOf(result);
    }

    private static void removeProductRequest(Product productRequest){
        allProduct.remove(productRequest);
        ProductDataBase.delete(productRequest.getProductId());
    }

    public static void acceptOrDeclineRequest(String requestId, boolean isAccepted) throws  ExceptionalMassage{
        Product productRequest = Product.getProductById(convertRequestIdToProductId(requestId));
        if(productRequest == null)
            throw new ExceptionalMassage("Invalid request identifier");
        if(!isAccepted) {
            removeProductRequest(productRequest);
            return;
        }
        if(productRequest.getProductState() == State.PREPARING_TO_BUILD) {
            productRequest.setProductState(State.CONFIRMED);
            Category.getCategoryByName(productRequest.getFutureCategoryName()).addProduct(productRequest);
            ProductDataBase.update(productRequest);
        }
        else {
            setRequestValuesInRealProduct(productRequest);
            removeProductRequest(productRequest);
            ProductDataBase.update(Product.getProductById(productRequest.getRootProductId()));
        }

    }

    private static void setRequestValuesInRealProduct(Product productRequest){
        Product realProduct = Product.getProductById(productRequest.getRootProductId());
        realProduct.setName(productRequest.getName());
        realProduct.setNameOfCompany(productRequest.getNameOfCompany());
        realProduct.setDescription(productRequest.getDescription());
        realProduct.setPriceForEachSupplier(productRequest.getPriceForEachSupplier());
        realProduct.setRemainedNumberForEachSupplier(productRequest.getRemainedNumberForEachSupplier());
    }

    private static String convertProductIdToRequestId(String requestId){
        return  "T34PR"+requestId.substring(4);
    }

    private static String convertRequestIdToProductId(String productId){
        return  "T34P"+productId.substring(5);
    }

    @Override
    public String toString() {
        String returning =  "Product{" +
                "numberOfViews=" + numberOfViews +
                ", productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", nameOfCompany='" + nameOfCompany + '\'' +
                ", priceForEachSupplier=" + priceForEachSupplier +
                ", remainedNumberForEachSupplier=" + remainedNumberForEachSupplier +
                ", description='" + description + '\'' +
                ", specification=" + specification +
                '}' + '\'';
        for (Supplier listOfSupplier : listOfSuppliers) {
            returning += listOfSupplier.getUserName() + '\n';
        }
        return returning;
    }
}
