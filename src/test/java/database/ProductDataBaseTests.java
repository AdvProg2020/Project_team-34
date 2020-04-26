package database;

import org.junit.Assert;
import org.junit.Test;
import product.Product;

import java.util.ArrayList;

public class ProductDataBaseTests {
    @Test
    public void testAddAndSelectAll (){
        ProductDateBase.createNewTable();
        ProductDateBase productDateBase = new ProductDateBase();
        Product product1 = new Product("product1", "company1", 90, 5,
                "0001", "be described");
        Product product2 = new Product("product2", "company2", 91, 6,
                "0003", "description");
        Product repeatedProduct = new Product("product3","company3",93,null,
                4,"0004","description",null,4,product1.getProductId());
        productDateBase.add(product1);
        productDateBase.add(product2);
        productDateBase.add(repeatedProduct);

        ArrayList<Product> actualProducts= productDateBase.getAllProducts();
        int [] actualPrice = new int[2];
        for (int i=0 ; i< 2; i++) {
            actualPrice[i] = actualProducts.get(i).getPrice();
        }
        Assert.assertArrayEquals(new int [] {90, 91}, actualPrice);
    }
}
