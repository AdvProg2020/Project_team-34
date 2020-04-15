package sortproduct;

import product.Product;

import java.util.Comparator;
/**
 * @author rpirayadi
 * @since 0.0.1
 */
public class SortByTime implements Comparator<Product> {
    @Override
    public int compare(Product firstProduct, Product secondProduct) {
        return convertProductIdToInt(firstProduct.getProductId())- convertProductIdToInt(secondProduct.getProductId());
    }

    private int convertProductIdToInt(String productId){
        return Integer.parseInt(productId.substring(4));
    }
}
