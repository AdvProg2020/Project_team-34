package sortproduct;

import product.Product;

import java.util.Comparator;

/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class SortByNumberOfViews implements Comparator<Product> {
    @Override
    public int compare(Product firstProduct, Product secondProduct) {
        return firstProduct.getNumberOfViews() - secondProduct.getNumberOfViews();
    }
}
