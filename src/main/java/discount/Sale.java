package discount;

import account.Supplier;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import communications.Utils;
import product.Product;
import state.State;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * @author soheil
 * @since 0.01
 */

public class Sale extends Discount {
    private String offId;
    private String rootSaleId;
    private ArrayList<Product> products;
    private State state;
    private Supplier supplier;

    public Sale(String json) {
        super(new Date(Long.parseLong((new JsonParser().parse(json).getAsJsonObject()).get("start").getAsString())),
                new Date(Long.parseLong((new JsonParser().parse(json).getAsJsonObject()).get("end").getAsString())),
                Integer.parseInt((new JsonParser().parse(json).getAsJsonObject()).get("percent").getAsString()));
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        this.offId = jsonObject.get("offId").getAsString();
        this.rootSaleId = jsonObject.get("rootSaleId").toString();
        if(jsonObject.get("products") instanceof JsonNull){
            products = new ArrayList<>();
        } else {
            this.products = Utils.convertJsonElementToProductArrayList(jsonObject.get("products"));
        }
        this.state = State.valueOf(jsonObject.get("state").getAsString());
        this.supplier = Supplier.convertJsonStringToSupplier(jsonObject.get("supplier").toString());
    }

    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        jsonObject.add("start", jsonParser.parse(Utils.convertObjectToJsonString(String.valueOf(start.getTime()))));
        jsonObject.add("end", jsonParser.parse(Utils.convertObjectToJsonString(String.valueOf(end.getTime()))));
        jsonObject.add("percent", jsonParser.parse(Utils.convertObjectToJsonString(String.valueOf(percent))));
        jsonObject.add("offId", jsonParser.parse(Utils.convertObjectToJsonString(offId)));
        jsonObject.add("rootSaleId", jsonParser.parse(Utils.convertObjectToJsonString(rootSaleId)));
        jsonObject.add("products", Utils.convertProductArrayListToJsonElement(products));
        jsonObject.add("state", jsonParser.parse(Utils.convertObjectToJsonString(String.valueOf(state))));
        jsonObject.add("supplier", jsonParser.parse(Utils.convertObjectToJsonString(supplier)));
        return jsonObject.toString();
    }

    public String getOffId() {
        return offId;
    }

    public void setOffId(String offId) {
        this.offId = offId;
    }

    public String getRootSaleId() {
        return rootSaleId;
    }

    public void setRootSaleId(String rootSaleId) {
        this.rootSaleId = rootSaleId;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public static String convertSaleIdToRequestId(String requestId) {
        return "T34SAR" + requestId.substring(5);
    }

    public static Sale convertJsonStringToSale(String jsonString){
        if (new JsonParser().parse(jsonString) instanceof JsonNull)
            return null;
        return new Sale(jsonString);
//        return (Sale) Utils.convertStringToObject(jsonString, "discount.Sale");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(offId, sale.offId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offId);
    }

    @Override
    public String toString() {
        String returning = "ID :" + offId + "\n" +
                ", State:" + state +
                ", Off percent:" + percent + "\n" +
                ", Products In sale: \n"
                ;

        for (Product product : products) {
            returning += product.getName() + '\n';
        }
        return returning;
    }
}
