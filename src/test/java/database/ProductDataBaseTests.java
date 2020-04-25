package database;

import org.junit.Test;
import product.Product;

public class ProductDataBaseTests {
    @Test
    public void testCreateNewTable (){
        ProductDateBase.createNewTable();
        ProductDateBase productDateBase = new ProductDateBase();
        //productDateBase.add(new Product("product","company",5000, ));
        //new Product()
    }
}
