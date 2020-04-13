package discount;

import account.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author soheil
 * @since 0.01
 */

public class CodedDiscount extends Discount{
    private static ArrayList<CodedDiscount> codedDiscounts;
    private String discountCode;
    private int maxDiscountPercent;
    private HashMap<Customer, Integer> customers;


    public CodedDiscount(Date start, Date end, int percent, String discountCode, int maxDiscountPercent) {
        super(start, end, percent);
        this.discountCode = discountCode;
        this.maxDiscountPercent = maxDiscountPercent;
    }

    public void addCustomer(Customer customer){

    }

    public void addUsedCountForCustomer(){

    }

    public boolean canCustomerUseCode(Customer customer){
        return true;
    }

    public static ArrayList<CodedDiscount> getCodedDiscounts() {
        return codedDiscounts;
    }

    public void removeCodeFromList(CodedDiscount codedDiscount){

    }

    public static void importAllData(){

    }

    /**
     *
     * @return returns the String form of a JSON object for storing in the database.
     */
    @Override
    public String toString() {
        return "CodedDiscount{" +
                "discountCode='" + discountCode + '\'' +
                ", maxDiscountPercent=" + maxDiscountPercent +
                ", customers=" + customers +
                ", percent=" + percent +
                '}';
    }
}
