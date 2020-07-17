package cart;

import account.Supplier;
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

    public static ProductInCart convertJsonStringToProdcutInCart(String jsonString){
        return (ProductInCart) Utils.convertStringToObject(jsonString, "cart.ProductInCart");
    }
}
