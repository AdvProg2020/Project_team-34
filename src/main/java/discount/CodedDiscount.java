package discount;

import account.Customer;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * @author soheil
 * @since 0.01
 */

public class CodedDiscount extends Discount{
    private static final String LETTERS_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static ArrayList<CodedDiscount> codedDiscounts = new ArrayList<>();
    private String discountCode;
    private int maxDiscountAmount;
    private HashMap<Customer, Integer> usedDiscountPerCustomer;
    private HashMap<Customer, Integer> maximumNumberOfUsagePerCustomer;
    private ArrayList<Customer> customers;


    public CodedDiscount(String code, Date start, Date end, int percent, int maxDiscountAmount,
                         HashMap<Customer, Integer> maximumNumberOfUsagePerCustomer) {
        super(start, end, percent);
        this.discountCode = code;
        this.maxDiscountAmount = maxDiscountAmount;
        this.maximumNumberOfUsagePerCustomer = maximumNumberOfUsagePerCustomer;
        codedDiscounts.add(this);
    }

    // Added by rpirayadi
    public CodedDiscount(Date start, Date end, int percent, String discountCode, int maxDiscountAmount, HashMap<Customer,
            Integer> usedDiscountPerCustomer, ArrayList<Customer> customers) {
        super(start, end, percent);
        this.discountCode = discountCode;
        this.maxDiscountAmount = maxDiscountAmount;
        this.usedDiscountPerCustomer = usedDiscountPerCustomer;
        this.customers = customers;
    }

    public static String codeGenerator(){
        Random rand = new Random();
        int upperBound = LETTERS_SET.length()-1;
        String code = "";
        for(int i = 0;i < 8;i++){
            code += LETTERS_SET.charAt(rand.nextInt(upperBound));
        }
        return code;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public int getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public HashMap<Customer, Integer> getUsedDiscountPerCustomer() {
        return usedDiscountPerCustomer;
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void removeCustomer(Customer customer){
        customers.remove(customer);
    }

    public void addUsedCountForCustomer(Customer customer){
        usedDiscountPerCustomer.put(customer, usedDiscountPerCustomer.get(customer)+1);
    }

    public boolean canCustomerUseCode(Customer customer){
        if(usedDiscountPerCustomer.get(customer) == 1){
            return false;
        }   else    {
            return true;
        }
    }

    public static ArrayList<CodedDiscount> getCodedDiscounts() {
        return codedDiscounts;
    }

    public static void removeCodeFromList(CodedDiscount codedDiscount){
        codedDiscounts.remove(codedDiscount);
    }

    public static CodedDiscount getCodedDiscountByCode(String code){
        ///signed as static by AA
        for (CodedDiscount codedDiscount : codedDiscounts) {
            if(codedDiscount.getDiscountCode().equals(code)){
                return codedDiscount;
            }
        }
        return null;
    }



    public static void importAllData(){

    }


    public void setMaxDiscountAmount(int maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    /**
     *
     * @return returns the String form of a JSON object for storing in the database.
     */
    @Override
    public String toString() {
        return "CodedDiscount{" +
                "discountCode='" + discountCode + '\'' +
                ", maxDiscountPercent=" + maxDiscountAmount +
                ", customers=" + customers +
                ", percent=" + percent +
                '}';
    }
}
