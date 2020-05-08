package discount;

import account.Customer;
import com.google.gson.JsonObject;



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
    private static ArrayList<CodedDiscount> codedDiscounts;
    private String discountCode;
    private int maxDiscountPercent;
    private HashMap<Customer, Integer> usedDiscountPerCustomer;
    private ArrayList<Customer> customers;


    public CodedDiscount(Date start, Date end, int percent, int maxDiscountPercent) {
        super(start, end, percent);
        this.discountCode = codeGenerator();
        this.maxDiscountPercent = maxDiscountPercent;
    }

    public CodedDiscount(Date start, Date end, int percent) {
        super(start, end, percent);
    }

    public static String codeGenerator(){
        return null;
    }

    public String getDiscountCode() {
        Random rand = new Random();
        int upperBound = LETTERS_SET.length()-1;
        String code = "";
        for(int i = 0;i < 8;i++){
            code += LETTERS_SET.charAt(rand.nextInt(upperBound));
        }
        return code;
    }

    public int getMaxDiscountPercent() {
        return maxDiscountPercent;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
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


    public void setMaxDiscountPercent(int maxDiscountPercent) {
        this.maxDiscountPercent = maxDiscountPercent;
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
