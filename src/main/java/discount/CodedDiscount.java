package discount;

import account.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CodedDiscount extends Discount{
    private String discountCode;
    private int maxDiscountPercent;
    private HashMap<Customer, Integer> customers;
    private static ArrayList<CodedDiscount> codedDiscounts;

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
}
