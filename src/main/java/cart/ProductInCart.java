package cart;

import account.Supplier;
import database.ProductInCartDataBase;
import product.Product;
import server.communications.Response;

import java.util.ArrayList;

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
    public static String generateIdentifier() {
        return "T34PC" + String.format("%015d", numberOfObjectCreated + 1);
    }

    public static ProductInCart getProductInCartByIdentifier(String identifier) {
        for (ProductInCart productInCart : allProductInCarts) {
            if (productInCart.getIdentifier().equals(identifier))
                return productInCart;
        }
        return null;
    }

    public static ProductInCart convertJsonStringToProdcutInCart(String jsonString){
        return (ProductInCart) Response.convertStringToObject(jsonString, "cart.ProductInCart");
    }

}
