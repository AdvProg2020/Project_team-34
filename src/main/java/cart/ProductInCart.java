package cart;

import account.Supplier;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import communications.Utils;
import product.Product;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class ProductInCart {
    private String identifier;
    private Product product;
    private Supplier supplier;

    public ProductInCart(String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        this.identifier = jsonObject.get("identifier").getAsString();
        this.product = Product.convertJsonStringToProduct(jsonObject.get("product").toString());
        this.supplier = Supplier.convertJsonStringToSupplier(jsonObject.get("supplier").toString());
    }

    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        jsonObject.add("identifier", jsonParser.parse(Utils.convertObjectToJsonString(identifier)));
        jsonObject.add("product", jsonParser.parse(product.toJson()));
        jsonObject.add("supplier", jsonParser.parse(Utils.convertObjectToJsonString(supplier)));
        return jsonObject.toString();
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public static ProductInCart convertJsonStringToProductInCart(String jsonString){
        //return (ProductInCart) Utils.convertStringToObject(jsonString, "cart.ProductInCart");
        return new ProductInCart(jsonString);
    }
}
