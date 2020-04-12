package feedback;

import account.Customer;
import product.Product;

import java.util.ArrayList;


public class Score {
    private Customer customer;
    private Product product;
    private static ArrayList<Score> scores;

    public Score(Customer customer, Product product) {
        this.customer = customer;
        this.product = product;
    }

    public boolean canCustomerRateThisProduct(Customer customer){
        return false;
    }

    public static float getAverageScoreForProduct(Product product){
        return 1;
    }
}
