package cart;

import account.Supplier;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import database.ProductInCartDataBase;
import product.Product;
import server.communications.Utils;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class ProductInCart {
    private static final ArrayList<ProductInCart> allProductInCarts = new ArrayList<>();
    private static long numberOfObjectCreated = 0;

    private final String identifier;
    private final Product product;
    private final Supplier supplier;

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

    public ProductInCart(Product product, Supplier supplier) {
        this.identifier = generateIdentifier();
        this.product = product;
        this.supplier = supplier;
        allProductInCarts.add(this);
        numberOfObjectCreated++;
        ProductInCartDataBase.add(this);
    }

    public ProductInCart(String identifier, Product product, Supplier supplier) {
        this.identifier = identifier;
        this.product = product;
        this.supplier = supplier;
        allProductInCarts.add(this);
        numberOfObjectCreated++;
    }

    //Getters:
    public String getIdentifier() {
        return identifier;
    }

    public Product getProduct() {
        return product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public static long getNumberOfObjectCreated() {
        return numberOfObjectCreated;
    }

    //Modeling methods:
    public static synchronized String generateIdentifier() {
        return "T34PC" + String.format("%015d", numberOfObjectCreated + 1);
    }

    public static ProductInCart getProductInCartByIdentifier(String identifier) {
        for (ProductInCart productInCart : allProductInCarts) {
            if (productInCart.getIdentifier().equals(identifier))
                return productInCart;
        }
        return null;
    }

    public static ProductInCart convertJsonStringToProductInCart(String jsonString){
        return new ProductInCart(jsonString);
//        return (ProductInCart) Utils.convertStringToObject(jsonString, "cart.ProductInCart");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInCart that = (ProductInCart) o;
        return Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
