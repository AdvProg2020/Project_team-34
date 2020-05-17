package controller;

import feedback.Score;
import product.Product;

import java.util.Comparator;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public enum SortType {
    BY_AVERAGE_SCORE,
    BY_TIME,
    BY_NUMBER_OF_VIEWS;

    private Comparator<Product> comparator;

    static {
        BY_AVERAGE_SCORE.comparator = new Comparator<Product>() {
            @Override
            public int compare(Product firstProduct, Product secondProduct) {
                return (int)((Score.getAverageScoreForProduct(firstProduct) - Score.getAverageScoreForProduct(secondProduct)) * 100);
            }
        };

        BY_NUMBER_OF_VIEWS.comparator = new Comparator<Product>() {
            @Override
            public int compare(Product firstProduct, Product secondProduct) {
                return firstProduct.getNumberOfViews() - secondProduct.getNumberOfViews();
            }
        };

        BY_TIME.comparator = new Comparator<Product>() {
            @Override
            public int compare(Product firstProduct, Product secondProduct) {
                return convertProductIdToInt(firstProduct.getProductId())- convertProductIdToInt(secondProduct.getProductId());
            }

            private int convertProductIdToInt(String productId){
                return Integer.parseInt(productId.substring(4));
            }
        };
    }

    private String printableType;

    static {
        BY_NUMBER_OF_VIEWS.printableType = "by number of views";
        BY_AVERAGE_SCORE.printableType = "by score";
        BY_TIME.printableType = "by time added";
    }

    public Comparator<Product> getComparator() {
        return comparator;
    }

    public String getPrintableType() {
        return printableType;
    }
}
