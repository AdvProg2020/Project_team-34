package sortproduct;

import product.Product;

import java.util.Comparator;

public class SortByNumberOfViews implements Comparator<Product> {
    @Override
    public int compare(Product firstProduct, Product secondProduct) {
        return firstProduct.getNumberOfViews() - secondProduct.getNumberOfViews();
    }
}
