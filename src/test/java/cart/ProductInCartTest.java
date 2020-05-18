package cart;

import account.Supplier;
import database.ProductInCartDataBase;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import product.Product;

import java.util.ArrayList;

public class ProductInCartTest {
    private static final long beforeConstructionCreatedCount = ProductInCart.getNumberOfObjectCreated();
    private static final Supplier supplier1 = new Supplier("sup1", "fs1", "ls1",
            "1@sup.com", "09111111111", "1111", 111, "c1");
    private static final Product product1 = new Product(supplier1, "p1", "b1", 100,
            10, "A good product1", null, null,null);
    private static final ProductInCart productInCartDataBaseConstructedInstance =
            new ProductInCart("Instance C", product1, supplier1);
    private static final ArrayList<ProductInCart> testInstances = createTestInstances();

    public static ArrayList<ProductInCart> createTestInstances() {
        ProductInCartDataBase.createNewTable();
        ArrayList<ProductInCart> instances = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            instances.add(new ProductInCart(product1, supplier1));
        }
        return instances;
    }

    @Test
    public void testDataBaseConstructor() {
        Assert.assertEquals(product1, productInCartDataBaseConstructedInstance.getProduct());
        Assert.assertEquals(supplier1, productInCartDataBaseConstructedInstance.getSupplier());
        Assert.assertEquals("Instance C", productInCartDataBaseConstructedInstance.getIdentifier());
    }

    @Test
    public void testGetNumberOfObjectCreated() {
        Assert.assertEquals(10,
                ProductInCart.getNumberOfObjectCreated() - beforeConstructionCreatedCount);
    }

    @Test
    public void testGetProductInCartByIdentifier() {
        long objectsCreated = ProductInCart.getNumberOfObjectCreated();
        ProductInCart productInCart =
                ProductInCart.getProductInCartByIdentifier("T34PC" + String.format("%015d", objectsCreated));
        Assert.assertNull(ProductInCart.getProductInCartByIdentifier("T34PC" + String.format("%015d", objectsCreated + 1)));
        Assert.assertNotNull(productInCart);
        Assert.assertEquals("T34PC" + String.format("%015d", objectsCreated), productInCart.getIdentifier());
    }

    @Test
    public void testGenerateIdentifier() {
        ProductInCart productInCart = testInstances.get(1);
        Assert.assertEquals("T34PC" + String.format("%015d", beforeConstructionCreatedCount + 3),
                productInCart.getIdentifier());
    }

    @Test
    public void generateIdentifier() {
        Assert.assertNotNull(ProductInCart.generateIdentifier());
    }
}
