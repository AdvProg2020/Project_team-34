package cart;

import account.Supplier;
import product.Product;

import java.util.ArrayList;

public class ProductInCart {

    private static ArrayList<ProductInCart> allProductInCarts = new ArrayList<>();
    private static long numberOfObjectCreated =0;


    private final String identifier;
    private final Product product;
    private final Supplier supplier;

    public ProductInCart(Product product, Supplier supplier) {
        this.identifier = generateIdentifier();
        this.product = product;
        this.supplier = supplier;
        numberOfObjectCreated++;
        allProductInCarts.add(this);
    }

    public ProductInCart(String identifier, Product product, Supplier supplier) {
        this.identifier = identifier;
        this.product = product;
        this.supplier = supplier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Product getProduct() {
        return product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public String generateIdentifier() {
        return "T34PC" + String.format("%015d", numberOfObjectCreated + 1);
    }

    // Added By rpirayadi
    public static ProductInCart getProductInCartById(String identifier){
        for (ProductInCart productInCart : allProductInCarts) {
            if(productInCart.getIdentifier().equals(identifier)){
                return productInCart;
            }
        }
        return null;
    }

}
