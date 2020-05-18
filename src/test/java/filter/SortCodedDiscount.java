package filter;

import account.Customer;
import database.CodedDiscountDataBase;
import discount.CodedDiscount;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SortCodedDiscount {
    private static final int percent = 10;
    private static final Date start = new Date(System.currentTimeMillis()-1000000);
    private static final Date end = new Date(System.currentTimeMillis()+1000000);
    private static final Customer customer11 = new Customer("aryanahadinia241CLT", "Aryan1",
            "Ahadinia1", "aryanahadinia24@gmail.com1", "11111111111", "1111",
            1000);
    private static final Customer customer12 = new Customer("aryanahadinia242CLT", "Aryan2",
            "Ahadinia2", "aryanahadinia24@gmail.com2", "22222222222", "2222",
            2000);
    private static final HashMap<Customer, Integer> max= new HashMap<Customer, Integer>(){{
        put(customer11, 5);
        put(customer12, 2);
    }};
    private static final CodedDiscount codedDiscount = new CodedDiscount("A",start, end,percent,100,max);

    private static final int percent2 = 20;
    private static final Date start22 = new Date(System.currentTimeMillis()-1000000);
    private static final Date end2 = new Date(System.currentTimeMillis()+1000000);
    private static final Customer customer21 = new Customer("aryanahadinia241CLT", "Aryan1",
            "Ahadinia1", "aryanahadinia24@gmail.com1", "11111111111", "1111",
            1000);
    private static final Customer customer22 = new Customer("aryanahadinia242CLT", "Aryan2",
            "Ahadinia2", "aryanahadinia24@gmail.com2", "22222222222", "2222",
            2000);
    private static final HashMap<Customer, Integer>  max2= new HashMap<Customer, Integer>(){{
        put(customer21, 5);
        put(customer22, 2);
    }};
    private static final CodedDiscount codedDiscount2 = new CodedDiscount("B2",start, end,percent2,100,max);

    private static final int percent3 = 30;
    private static final Date start3 = new Date(System.currentTimeMillis()-1000000);
    private static final Date end3 = new Date(System.currentTimeMillis()+1000000);
    private static final Customer customer31 = new Customer("aryanahadinia241CLT", "Aryan1",
            "Ahadinia1", "aryanahadinia24@gmail.com1", "11111111111", "1111",
            1000);
    private static final Customer customer32 = new Customer("aryanahadinia242CLT", "Aryan2",
            "Ahadinia2", "aryanahadinia24@gmail.com2", "22222222222", "2222",
            2000);
    private static final HashMap<Customer, Integer>  max3 = new HashMap<Customer, Integer>(){{
        put(customer31, 5);
        put(customer32, 2);
    }};
    private static final CodedDiscount codedDiscount3 = new CodedDiscount("C3",start, end,percent3,100,max);


   @Test
    public void test(){
       CodedDiscountDataBase.createNewTable();
       ArrayList<String > arrayList = new ArrayList<>();
       arrayList.add("C3" );
       arrayList.add("B2");
       ArrayList<CodedDiscount> codedDiscounts = CodedDiscountDataBase.sortCodedDiscount("discountCode",arrayList);
       for (CodedDiscount discount : codedDiscounts) {
           System.out.println(discount.getPercent() + "+");
       }
   }
}
