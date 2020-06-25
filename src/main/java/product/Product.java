package product;

import account.Supplier;
import database.ProductDataBase;
import exceptionalMassage.ExceptionalMassage;
import state.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class Product {
    private static ArrayList<Product> allProduct = new ArrayList<>();
    private static int allCreatedProductNum = 0;
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


    public Product(Supplier supplier, String name, String nameOfCompany, int price, int remainedNumber, String description, String rootProductId, String futureCategoryName, HashMap<String, String> specification) {
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
        this.remainedNumberForEachSupplier.put(supplier, remainedNumber);
        this.description = description;
        this.rootProductId = rootProductId;
        this.futureCategoryName = futureCategoryName;
        this.specification = specification;
        allCreatedProductNum++;
        allProduct.add(this);
        ProductDataBase.add(this);
    }

    public Product(String name, String nameOfCompany, HashMap<Supplier, Integer> priceForEachSupplier, ArrayList<Supplier> listOfSuppliers,
                   HashMap<Supplier, Integer> remainedNumberForEachSupplier, String description,
                   int numberOfViews, String productId, State state, String rootProductId, String futureCategoryName, HashMap<String, String> specification) {
        this.productState = state;
        this.productId = productId;
        this.name = name;
        this.nameOfCompany = nameOfCompany;
        this.priceForEachSupplier = priceForEachSupplier;
        this.listOfSuppliers = listOfSuppliers;
        this.remainedNumberForEachSupplier = remainedNumberForEachSupplier;
        this.description = description;
        this.numberOfViews = numberOfViews;
        this.rootProductId = rootProductId;
        this.futureCategoryName = futureCategoryName;
        this.specification = specification;
        allCreatedProductNum++;
        allProduct.add(this);
    }

    public Product(Product product) {
        this.productId = generateIdentifier();
        this.name = product.getName();
        this.nameOfCompany = product.getNameOfCompany();
        this.priceForEachSupplier = new HashMap<>(product.getPriceForEachSupplier());
        this.listOfSuppliers = new ArrayList<>(product.getListOfSuppliers());
        this.remainedNumberForEachSupplier = new HashMap<>(product.getRemainedNumberForEachSupplier());
        this.description = product.getDescription();
        this.numberOfViews = product.getNumberOfViews();
        this.rootProductId = product.getProductId();
        this.productState = State.PREPARING_TO_EDIT;
        this.specification = new HashMap<>(product.getSpecification());
        this.futureCategoryName = product.getFutureCategoryName();
        this.imageUrl = product.getImageUrl();
        allCreatedProductNum++;
        allProduct.add(this);
        ProductDataBase.add(this);
    }

    public Product(Supplier supplier, String name, String nameOfCompany, int price, int remainedNumber, String description, String rootProductId, String futureCategoryName, HashMap<String, String> specification, String imageUrl) {
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
        this.remainedNumberForEachSupplier.put(supplier, remainedNumber);
        this.description = description;
        this.rootProductId = rootProductId;
        this.futureCategoryName = futureCategoryName;
        this.specification = specification;
        allCreatedProductNum++;
        allProduct.add(this);
        this.imageUrl = imageUrl;
        ProductDataBase.add(this);
    }

    public Product(String name, String nameOfCompany, HashMap<Supplier, Integer> priceForEachSupplier, ArrayList<Supplier> listOfSuppliers,
                   HashMap<Supplier, Integer> remainedNumberForEachSupplier, String description,
                   int numberOfViews, String productId, State state, String rootProductId, String futureCategoryName, HashMap<String, String> specification, String imageUrl) {
        this.productState = state;
        this.productId = productId;
        this.name = name;
        this.nameOfCompany = nameOfCompany;
        this.priceForEachSupplier = priceForEachSupplier;
        this.listOfSuppliers = listOfSuppliers;
        this.remainedNumberForEachSupplier = remainedNumberForEachSupplier;
        this.description = description;
        this.numberOfViews = numberOfViews;
        this.rootProductId = rootProductId;
        this.futureCategoryName = futureCategoryName;
        this.specification = specification;
        allCreatedProductNum++;
        this.imageUrl = imageUrl;
        allProduct.add(this);
    }

    public Product(Product product, String imageUrl) {
        this.productId = generateIdentifier();
        this.name = product.getName();
        this.nameOfCompany = product.getNameOfCompany();
        this.priceForEachSupplier = new HashMap<>(product.getPriceForEachSupplier());
        this.listOfSuppliers = new ArrayList<>(listOfSuppliers);
        this.remainedNumberForEachSupplier = new HashMap<>(product.getRemainedNumberForEachSupplier());
        this.description = product.getDescription();
        this.numberOfViews = product.getNumberOfViews();
        this.rootProductId = product.getProductId();
        this.productState = State.PREPARING_TO_EDIT;
        this.specification = new HashMap<>(product.getSpecification());
        this.futureCategoryName = product.getFutureCategoryName();
        allCreatedProductNum++;
        allProduct.add(this);
        this.imageUrl = imageUrl;
        ProductDataBase.add(this);
    }

    private String generateIdentifier() {
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

    public void setListOfSuppliers(ArrayList<Supplier> listOfSuppliers) {
        this.listOfSuppliers = listOfSuppliers;
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

    public String getImageUrl() {
        return imageUrl;
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public void setRootProductId(String rootProductId) {
        this.rootProductId = rootProductId;
    }

    public ArrayList<Supplier> getListOfSuppliers() {
        return listOfSuppliers;
    }

    private static ArrayList<Product> getShouldBeShownProducts() {
        ArrayList<Product> confirmedProducts = new ArrayList<>();
        for (Product eachProduct : allProduct) {
            if (eachProduct.getProductState() == State.CONFIRMED /*|| eachProduct.getProductState() == State.PREPARING_TO_BE_DELETED*/)
                confirmedProducts.add(eachProduct);
        }
        return confirmedProducts;
    }


    public static Product getProductById(String productId) {
        for (Product eachProduct : allProduct) {
            if (eachProduct.getProductId().equals(productId))
                return eachProduct;
        }
        return null;
    }

    public static Product getShouldBeShownProductById(String productId) {
        ArrayList<Product> confirmedProducts = getShouldBeShownProducts();
        for (Product eachProduct : confirmedProducts) {
            if (eachProduct.getProductId().equals(productId))
                return eachProduct;
        }
        return null;
    }


    public void removeProduct() {
        setProductState(State.PREPARING_TO_BE_DELETED);
    }

    public void addProduct() {
        productState = State.CONFIRMED;
    }

    public boolean doesSupplierSellThisProduct(Supplier supplier) {
        return listOfSuppliers.contains(supplier);
    }

    public static ArrayList<Product> getProductForSupplier(Supplier supplier) {
        ArrayList<Product> result = new ArrayList<>();
        ArrayList<Product> shouldBeShownProducts = getShouldBeShownProducts();
        for (Product product : shouldBeShownProducts) {
            if (product.doesSupplierSellThisProduct(supplier))
                result.add(product);
        }
        return result;
    }

    public static Product getProductByName(String name) {
        for (Product product : allProduct) {
            if (product.getName().equals(name))
                return product;
        }
        return null;
    }

    public void reduceRemainedNumber(Supplier supplier, int amount) {
        int remainedNumber = remainedNumberForEachSupplier.get(supplier);
        remainedNumberForEachSupplier.put(supplier, remainedNumber - amount);
        ProductDataBase.update(this);
    }

    public void editSpecialField(String field, String value) throws ExceptionalMassage {
        if (!specification.containsKey(field))
            throw new ExceptionalMassage("No such field was found");
        specification.put(field, value);

    }


    public void addNewSupplierForProduct(Supplier supplier, int price, int remainedNumber) {
        Product newProduct = new Product(this);
        newProduct.getListOfSuppliers().add(supplier);
        newProduct.getPriceForEachSupplier().put(supplier, price);
        newProduct.getRemainedNumberForEachSupplier().put(supplier, remainedNumber);
        ProductDataBase.update(newProduct);
    }

    public boolean isProductAvailableNow() {
        for (Supplier supplier : remainedNumberForEachSupplier.keySet()) {
            if (remainedNumberForEachSupplier.get(supplier) > 0)
                return true;
        }
        return false;
    }

    public ArrayList<Supplier> getAllSuppliersThatHaveAvailableProduct() {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        for (Supplier supplier : remainedNumberForEachSupplier.keySet()) {
            if (remainedNumberForEachSupplier.get(supplier) > 0)
                suppliers.add(supplier);
        }
        return suppliers;
    }

    public boolean isProductProvidedInPriceLowerThan(int upperBound) {
        for (Supplier supplier : priceForEachSupplier.keySet()) {
            if (priceForEachSupplier.get(supplier) <= upperBound)
                return true;
        }
        return false;
    }

    public boolean isProductProvidedInPriceUpperThan(int lowerBound) {
        for (Supplier supplier : priceForEachSupplier.keySet()) {
            if (priceForEachSupplier.get(supplier) >= lowerBound)
                return true;
        }
        return false;
    }

    public static ArrayList<String> getAllProductRequestId() {
        ArrayList<String> result = new ArrayList<>();
        for (Product eachProduct : allProduct) {
            if (eachProduct.getProductState() == State.PREPARING_TO_EDIT || eachProduct.getProductState() == State.PREPARING_TO_BUILD || eachProduct.getProductState() == State.PREPARING_TO_BE_DELETED) {
                result.add(convertProductIdToRequestId(eachProduct.getProductId()));
            }
        }
        return result;
    }

    public static String getDetailsForProductRequest(String requestId) {
        Product product = Product.getProductById(convertRequestIdToProductId(requestId));
        StringBuilder result = new StringBuilder();
        if (product.getProductState() == State.PREPARING_TO_EDIT) {
            Product rootProduct = Product.getProductById(product.getRootProductId());
            result.append("name= ").append(rootProduct.getName()).append("==>").append(product.getName()).append("\n");
            result.append("nameOfCompany= ").append(rootProduct.getNameOfCompany()).append("==>").append(product.getNameOfCompany()).append("\n");
            result.append("description= ").append(rootProduct.getDescription()).append("==>").append(product.getDescription()).append("\n");
            HashMap<String, String> firstSpecification = rootProduct.getSpecification();
            HashMap<String, String> secondSpecification = product.getSpecification();
            for (String specialField : firstSpecification.keySet()) {
                result.append(specialField).append("= ").append(firstSpecification.get(specialField)).append("==>").append(secondSpecification.get(specialField)).append("\n");
            }
        } else {
            result.append(product.toString());
        }
        return String.valueOf(result);
    }

    private static void removeProductRequest(Product productRequest) {
        productRequest.setProductState(State.DELETED);
        ProductDataBase.update(productRequest);
    }

    private static void declineRequest(Product product){
        switch (product.getProductState()){
            case PREPARING_TO_BUILD:
                product.setProductState(State.BUILD_DECLINED);
                break;
            case PREPARING_TO_EDIT:
                product.setProductState(State.EDIT_DECLINED);
                break;
            case PREPARING_TO_BE_DELETED:
                product.setProductState(State.DELETE_DECLINED);
        }
    }

    public static void acceptOrDeclineRequest(String requestId, boolean isAccepted) throws ExceptionalMassage {
        Product productRequest = Product.getProductById(convertRequestIdToProductId(requestId));
        if (productRequest == null)
            throw new ExceptionalMassage("Invalid request identifier");
        if (!isAccepted) {
            declineRequest(productRequest);
//            removeProductRequest(productRequest);
//            productRequest.setProductState(State.REQUEST_DECLINED);
            return;
        }
        if (productRequest.getProductState() == State.PREPARING_TO_BUILD) {
            Product productGotByName = Product.getConfirmedProductByName(productRequest.getName());
            if (productGotByName == null) {
                Product newProduct = new Product(productRequest);
                newProduct.setProductState(State.CONFIRMED);
                Category.getCategoryByName(newProduct.getFutureCategoryName()).addProduct(newProduct);
            } else {
                productGotByName.getListOfSuppliers().addAll(productRequest.getListOfSuppliers());
                productGotByName.getPriceForEachSupplier().putAll(productRequest.getPriceForEachSupplier());
                productGotByName.getRemainedNumberForEachSupplier().putAll(productRequest.getRemainedNumberForEachSupplier());
//                productRequest.setProductState(State.REQUEST_ACCEPTED);
            }
            productRequest.setProductState(State.BUILD_ACCEPTED);
        } else if (productRequest.getProductState() == State.PREPARING_TO_EDIT && Product.getProductById(productRequest.getRootProductId()).getProductState() != State.DELETED  && Product.getProductById(productRequest.getRootProductId()).getProductState() != State.BUILD_DECLINED) {
            setRequestValuesInRealProduct(productRequest);
//            removeProductRequest(productRequest);
            productRequest.setProductState(State.EDIT_ACCEPTED);
            ProductDataBase.update(Product.getProductById(productRequest.getRootProductId()));
        } else {
            productRequest.setProductState(State.DELETED);
            Product rootProduct = Product.getProductById(productRequest.getRootProductId());
            if (rootProduct.getListOfSuppliers().size() == 1) {
                rootProduct.setProductState(State.DELETED);
                Category.getProductCategory(rootProduct).removeProduct(rootProduct);
            }
            else {
                Supplier supplier = productRequest.getListOfSuppliers().get(0);
                rootProduct.getListOfSuppliers().remove(supplier);
                rootProduct.getPriceForEachSupplier().remove(supplier);
                rootProduct.getRemainedNumberForEachSupplier().remove(supplier);
            }
//            Category.getProductCategory(Product.getProductById(productRequest.getRootProductId())).removeProduct(Product.getProductById(productRequest.getRootProductId()));
            productRequest.setProductState(State.DELETE_ACCEPTED);
        }

    }

    public static Product getConfirmedProductByName(String productName) {
        for (Product eachProduct : allProduct) {
            if (eachProduct.getProductState() == State.CONFIRMED && eachProduct.getName().equals(productName))
                return eachProduct;
        }
        return null;
    }

    private static void setRequestValuesInRealProduct(Product productRequest) {
        Product realProduct = Product.getProductById(productRequest.getRootProductId());
        realProduct.setName(productRequest.getName());
        realProduct.setNameOfCompany(productRequest.getNameOfCompany());
        realProduct.setDescription(productRequest.getDescription());
        realProduct.setPriceForEachSupplier(productRequest.getPriceForEachSupplier());
        realProduct.setRemainedNumberForEachSupplier(productRequest.getRemainedNumberForEachSupplier());
    }

    public static ArrayList<Product> getRequestsForThisSupplier(Supplier supplier){
        ArrayList<Product> result = new ArrayList<>();
        for (Product eachProduct : allProduct) {
            if(eachProduct.isRequest() && eachProduct.getListOfSuppliers().contains(supplier))
                result.add(eachProduct);
        }
        return result;
    }

    private boolean isRequest(){
        State state = this.getProductState();
        if( state == State.PREPARING_TO_BUILD || state == State.PREPARING_TO_EDIT || state == State.PREPARING_TO_BE_DELETED||
                state == State.BUILD_ACCEPTED|| state == State.BUILD_DECLINED|| state == State.EDIT_ACCEPTED
                || state == State.EDIT_DECLINED|| state == State.DELETE_ACCEPTED|| state == State.DELETE_DECLINED)
            return true;
        return false;
    }

    public static String convertProductIdToRequestId(String requestId) {
        return "T34PR" + requestId.substring(4);
    }

    public static String convertRequestIdToProductId(String productId) {
        return "T34P" + productId.substring(5);
    }

    @Override
    public String toString() {
        String returning = "Product{" +
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product)) {
            return false;
        }
        return getProductId().equals(((Product) o).getProductId());
    }
}
