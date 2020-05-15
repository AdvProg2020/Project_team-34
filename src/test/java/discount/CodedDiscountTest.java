package discount;

import account.Customer;
import account.Supplier;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

public class CodedDiscountTest {
    private static final int percent = 20;
    private static final Date start = new Date(System.currentTimeMillis()-1000000);
    private static final Date end = new Date(System.currentTimeMillis()+1000000);
    private static final Customer customer1 = new Customer("aryanahadinia241CLT", "Aryan1",
            "Ahadinia1", "aryanahadinia24@gmail.com1", "11111111111", "1111",
            1000);
    private static final Customer customer2 = new Customer("aryanahadinia242CLT", "Aryan2",
            "Ahadinia2", "aryanahadinia24@gmail.com2", "22222222222", "2222",
            2000);
    private static final HashMap<Customer, Integer>  max= new HashMap<Customer, Integer>(){{
        put(customer1, 5);
        put(customer2, 2);
    }};
    private static final CodedDiscount codedDiscount = new CodedDiscount("salam",start, end,percent,100,max);

    @Test
    public void testGetters(){
        Assert.assertEquals(percent, codedDiscount.getPercent());
        Assert.assertEquals(start, codedDiscount.getStart());
        Assert.assertEquals(end, codedDiscount.getEnd());
        Assert.assertEquals("salam", codedDiscount.getDiscountCode());
        Assert.assertEquals(100,codedDiscount.getMaxDiscountAmount());
        Assert.assertEquals(customer2,codedDiscount.getCustomers().get(0));
        codedDiscount.addUsedCountForCustomer(customer1);
        Assert.assertEquals("1", codedDiscount.getUsedDiscountPerCustomer().get(customer1).toString());
    }

    @Test
    public void testCanCustomerUseCode(){
        codedDiscount.addUsedCountForCustomer(customer2);
        codedDiscount.addUsedCountForCustomer(customer2);
        Assert.assertEquals(false,codedDiscount.canCustomerUseCode(customer2));
        Assert.assertEquals(true , codedDiscount.canCustomerUseCode(customer1));
    }

    @Test
    public void testGetCodeByCode(){
        Assert.assertEquals(codedDiscount, CodedDiscount.getCodedDiscountByCode("salam"));
    }
}
