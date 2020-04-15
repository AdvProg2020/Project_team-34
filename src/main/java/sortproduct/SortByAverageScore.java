package sortproduct;

import feedback.Score;
import product.Product;

import java.util.Comparator;

public class SortByAverageScore implements Comparator<Product> {

    @Override
    public int compare(Product firstProduct, Product secondProduct) {
        return (int)((Score.getAverageScoreForProduct(firstProduct) - Score.getAverageScoreForProduct(secondProduct)) * 100);
    }
}
