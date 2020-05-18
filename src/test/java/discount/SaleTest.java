package discount;

import account.Supplier;
import org.junit.Assert;
import org.junit.Test;
import product.Product;
import state.State;

import java.util.Date;

public class SaleTest {
    private static final Supplier supplier1 = new Supplier("sup1CLT", "fs1", "ls1",
            "1@sup.com", "09111111111", "1111", 111, "c1");
    private static final int percent = 20;
    private static final Date start = new Date(System.currentTimeMillis()-1000000);
    private static final Date end = new Date(System.currentTimeMillis()+1000000);
    private static final Sale sale = new Sale(supplier1,start,end,percent,null);
    private static final Product product1 = new Product(supplier1, "p1", "b1", 100,
            10, "A good product1", null, null,null);

    @Test
    public void testIdGenerator(){
        Assert.assertEquals("T34S" + String.format("%015d",1), sale.getOffId());
    }

    @Test
    public void testActiveSales(){
        sale.setState(State.CONFIRMED);
        Sale sale1 = Sale.getActiveSales().get(0);
        Assert.assertEquals(sale1, sale);
    }

    @Test
    public void testRemoveSale(){
        Sale.removeSale(sale);
        int size = Sale.getActiveSales().size();
        Assert.assertEquals(0, size);
    }

    @Test
    public void testGetSaleById(){
        Assert.assertEquals(sale, Sale.getSaleById("T34S" + String.format("%015d",1)));
    }

    @Test
    public void testGetProductsSale(){
        sale.addProductToSale(product1);
        sale.setState(State.CONFIRMED);
        Assert.assertEquals(sale, Sale.getProductSale(product1,supplier1));
    }

}
