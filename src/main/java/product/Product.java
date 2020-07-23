package product;

import account.Supplier;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import communications.Utils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import state.State;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class Product {
    private String productId;
    private String name;
    private String nameOfCompany;
    private State productState;
    private ArrayList<Supplier> listOfSuppliers;
    private HashMap<Supplier, Integer> priceForEachSupplier;
    private HashMap<Supplier, Integer> remainedNumberForEachSupplier;
    private String description;
    private HashMap<String, String> specification; //method check
    private String rootProductId;
    private String futureCategoryName;
    private String imageInStringForm;
    private int numberOfViews;
    private String filePath;
    private int supplierPort;

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getSupplierPort() {
        return supplierPort;
    }

    public void setSupplierPort(int supplierPort) {
        this.supplierPort = supplierPort;
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

    public static Product convertJsonStringToProduct(String jsonString){
        return new Product(jsonString);
        //return (Product) Utils.convertStringToObject(jsonString, "product.Product");
    }

    public int getPrice(Supplier supplier) {
        return priceForEachSupplier.get(supplier);
    }

    public boolean isProductAvailableNow() {
        for (Supplier supplier : remainedNumberForEachSupplier.keySet()) {
            if (remainedNumberForEachSupplier.get(supplier) > 0 && supplier.isAvailable())
                return true;
        }
        return false;
    }

    public int getMinimumPrice() {
        int minimumPrice = 100000;
        for (Supplier supplier : priceForEachSupplier.keySet()) {
            if (this.getRemainedNumberForEachSupplier().get(supplier) != 0 && this.getPrice(supplier) < minimumPrice)
                minimumPrice = this.getPrice(supplier);
        }
        return minimumPrice;
    }

    public Image getImage() {
        return Utils.convertStringToImage(imageInStringForm);
    }

    public ImageView getImageView() {
        return new ImageView(getImage());
    }

    public static String convertProductIdToRequestId(String requestId) {
        return "T34PR" + requestId.substring(4);
    }

    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        jsonObject.add("productId", jsonParser.parse(Utils.convertObjectToJsonString(productId)));
        jsonObject.add("name", jsonParser.parse(Utils.convertObjectToJsonString(name)));
        jsonObject.add("nameOfCompany", jsonParser.parse(Utils.convertObjectToJsonString(nameOfCompany)));
        jsonObject.add("productState", jsonParser.parse(Utils.convertObjectToJsonString(String.
                valueOf(productState))));
        jsonObject.add("listOfSuppliers", Utils.convertSupplierArrayListToJsonElement(listOfSuppliers));
        jsonObject.add("priceForEachSupplier", jsonParser.parse(Utils.
                convertSupplierToIntegerHashMapToJsonElement(priceForEachSupplier).toString()));
        jsonObject.add("remainedNumberForEachSupplier", jsonParser.parse(Utils.
                convertSupplierToIntegerHashMapToJsonElement(remainedNumberForEachSupplier).toString()));
        jsonObject.add("description", jsonParser.parse(Utils.convertObjectToJsonString(description)));
        jsonObject.add("specification", jsonParser.parse(Utils.
                convertStringToStringHashMapToJsonElement(specification).toString()));
        jsonObject.add("rootProductId", jsonParser.parse(Utils.convertObjectToJsonString(rootProductId)));
        jsonObject.add("futureCategoryName", jsonParser.parse(Utils.convertObjectToJsonString(futureCategoryName)));
        jsonObject.add("imageInStringForm", jsonParser.parse(Utils.convertObjectToJsonString(imageInStringForm)));
        jsonObject.add("numberOfViews", jsonParser.parse(Utils.convertObjectToJsonString(numberOfViews)));
        jsonObject.add("filePath", jsonParser.parse(Utils.convertObjectToJsonString(filePath)));
        jsonObject.add("supplierPort", jsonParser.parse(Utils.convertObjectToJsonString(String.valueOf(supplierPort))));
        return jsonObject.toString();
    }

    public Product(String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        this.productId = jsonObject.get("productId").getAsString();
        this.name = jsonObject.get("name").getAsString();
        this.nameOfCompany = jsonObject.get("nameOfCompany").getAsString();
        this.productState = State.valueOf(jsonObject.get("productState").getAsString());
        this.listOfSuppliers = Utils.convertJsonElementToSupplierArrayList(jsonObject.get("listOfSuppliers"));
        this.priceForEachSupplier = Utils.convertJsonElementSupplierToIntegerHashMap(jsonObject.get("priceForEachSupplier"));
        this.remainedNumberForEachSupplier = Utils.convertJsonElementSupplierToIntegerHashMap(jsonObject.
                get("remainedNumberForEachSupplier"));
        this.description = jsonObject.get("description").getAsString();
        this.specification = Utils.convertJsonElementStringToStringToHashMap(jsonObject.get("specification"));
        this.rootProductId = jsonObject.get("rootProductId").getAsString();
        this.futureCategoryName = jsonObject.get("futureCategoryName").getAsString();
        this.imageInStringForm = jsonObject.get("imageInStringForm").getAsString();
        this.numberOfViews = jsonObject.get("numberOfViews").getAsInt();
        this.filePath = jsonObject.get("filePath").getAsString();
        this.supplierPort = Integer.parseInt(jsonObject.get("supplierPort").getAsString());
    }

    @Override
    public String toString() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Supplier listOfSupplier : listOfSuppliers) {
            stringArrayList.add(listOfSupplier.getUserName());
        }
        StringBuilder returning = new StringBuilder("Product{" +
                "numberOfViews=" + numberOfViews +
                ", productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", nameOfCompany='" + nameOfCompany + '\'' +
                ", ListOfSuppliersUserName=" + stringArrayList + '\'' +
                ", description='" + description + '\'' +
                ", specification=" + specification +
                '}' + '\'');

        return returning.toString();
    }
}