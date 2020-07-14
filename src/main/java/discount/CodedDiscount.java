package discount;

import account.Customer;
import communications.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * @author soheil
 * @since 0.01
 */

public class CodedDiscount extends Discount{
    private String discountCode;
    private int maxDiscountAmount;
    private HashMap<Customer, Integer> usedDiscountPerCustomer;
    private HashMap<Customer, Integer> maximumNumberOfUsagePerCustomer;
    private ArrayList<Customer> customers;

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public int getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(int maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public HashMap<Customer, Integer> getUsedDiscountPerCustomer() {
        return usedDiscountPerCustomer;
    }

    public void setUsedDiscountPerCustomer(HashMap<Customer, Integer> usedDiscountPerCustomer) {
        this.usedDiscountPerCustomer = usedDiscountPerCustomer;
    }

    public HashMap<Customer, Integer> getMaximumNumberOfUsagePerCustomer() {
        return maximumNumberOfUsagePerCustomer;
    }

    public void setMaximumNumberOfUsagePerCustomer(HashMap<Customer, Integer> maximumNumberOfUsagePerCustomer) {
        this.maximumNumberOfUsagePerCustomer = maximumNumberOfUsagePerCustomer;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public static CodedDiscount convertJsonStringToCodedDiscount(String jsonString){
        return (CodedDiscount) Utils.convertStringToObject(jsonString, "discount.CodedDiscount");
    }
}
