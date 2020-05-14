package feedback;

import account.Customer;
import account.Supplier;
import org.junit.Assert;
import org.junit.Test;
import product.Product;

public class ScoreTest {
    private static final Supplier supplier1 = new Supplier("sup1CLT", "fs1", "ls1",
            "1@sup.com", "09111111111", "1111", 111, "c1");
    private static final Customer customer1 = new Customer("aryanahadinia241CLT", "Aryan1",
            "Ahadinia1", "aryanahadinia24@gmail.com1", "11111111111", "1111",
            1000);
    private static final Product product1 = new Product(supplier1, "p1", "b1", 100,
            10, "A good product1", null);
    private static final Score score = new Score((float) 3.4,customer1,product1);

    @Test
    public void testGetters(){
        Assert.assertEquals("T34P" + String.format("%015d",1),score.getIdentifier());
        Assert.assertEquals(product1,score.getProduct());
        Assert.assertEquals(customer1,score.getCustomer());
        Assert.assertEquals(score, Score.getScoreByIdentifier("T34P" + String.format("%015d",1)));
    }



}
