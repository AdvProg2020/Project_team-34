package feedback;

import account.Customer;
import account.Supplier;
import org.junit.Assert;
import org.junit.Test;
import product.Product;

public class CommentTest {
    private static final Supplier supplier1 = new Supplier("sup1CLT", "fs1", "ls1",
            "1@sup.com", "09111111111", "1111", 111, "c1");
    private static final Customer customer1 = new Customer("aryanahadinia241CLT", "Aryan1",
            "Ahadinia1", "aryanahadinia24@gmail.com1", "11111111111", "1111",
            1000);
    private static final Product product1 = new Product(supplier1, "p1", "b1", 100,
            10, "A good product1", null);
    private static final Comment comment = new Comment(customer1,product1,"kheili bade","in 126 milyoone !",false);

    @Test
    public void testGetters(){
        Assert.assertEquals("T34C" + String.format("%015d",  1),comment.getCommentId());
        Assert.assertEquals("kheili bade", comment.getTitle());
        Assert.assertEquals("in 126 milyoone !", comment.getContent());
        Assert.assertEquals(product1,comment.getProduct());
        Assert.assertEquals(CommentState.CONFIRMED,comment.getState());
        Assert.assertEquals(false,comment.didCustomerBoughtThisProduct());
        Assert.assertEquals(customer1, comment.getCustomer());
        Assert.assertEquals(comment, Comment.getCommentsForProduct(product1).get(0));
        Assert.assertEquals(comment, Comment.getCommentByIdentifier("T34C" + String.format("%015d",  1)));
    }

}
