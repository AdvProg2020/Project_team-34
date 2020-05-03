package cart;

import account.Supplier;
import product.Product;

public class ProductInCart {
    private static long numberOfObjectCreated;

    private final String identifier;
    private final Product product;
    private final Supplier supplier;

    public ProductInCart(Product product, Supplier supplier) {
        this.identifier = generateIdentifier();
        this.product = product;
        this.supplier = supplier;
        numberOfObjectCreated++;
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
}
