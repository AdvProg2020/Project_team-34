package discount;

import account.Customer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import communications.Utils;

import java.util.*;

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

    public CodedDiscount(String json) {
        super(new Date(Long.parseLong((new JsonParser().parse(json).getAsJsonObject()).get("start").getAsString())),
                new Date(Long.parseLong((new JsonParser().parse(json).getAsJsonObject()).get("end").getAsString())),
                Integer.parseInt((new JsonParser().parse(json).getAsJsonObject()).get("percent").getAsString()));
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        this.discountCode = jsonObject.get("discountCode").getAsString();
        this.maxDiscountAmount = Integer.parseInt(jsonObject.get("maxDiscountAmount").getAsString());
        this.usedDiscountPerCustomer = Utils.convertJsonElementCustomerToIntegerHashMap(jsonObject.get("usedDiscountPerCustomer"));
        this.maximumNumberOfUsagePerCustomer = Utils.convertJsonElementCustomerToIntegerHashMap(jsonObject.get("maximumNumberOfUsagePerCustomer"));
        this.customers = Utils.convertJsonElementToCustomerArrayList(jsonObject.get("customers"));
    }

    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        jsonObject.add("start", jsonParser.parse(Utils.convertObjectToJsonString(String.valueOf(start.getTime()))));
        jsonObject.add("end", jsonParser.parse(Utils.convertObjectToJsonString(String.valueOf(end.getTime()))));
        jsonObject.add("percent", jsonParser.parse(Utils.convertObjectToJsonString(String.valueOf(percent))));
        jsonObject.add("discountCode", jsonParser.parse(Utils.convertObjectToJsonString(discountCode)));
        jsonObject.add("maxDiscountAmount", jsonParser.parse(Utils.convertObjectToJsonString(String.valueOf(
                maxDiscountAmount))));
        jsonObject.add("usedDiscountPerCustomer", Utils.convertCustomerToIntegerHashMapToJsonElement(usedDiscountPerCustomer));
        jsonObject.add("maximumNumberOfUsagePerCustomer", Utils.convertCustomerToIntegerHashMapToJsonElement(maximumNumberOfUsagePerCustomer));
        jsonObject.add("customers", Utils.convertCustomerArrayListToJsonElement(customers));
        return jsonObject.toString();
    }
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
        return new CodedDiscount(jsonString);
//        return (CodedDiscount) Utils.convertStringToObject(jsonString, "discount.CodedDiscount");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodedDiscount that = (CodedDiscount) o;
        return Objects.equals(discountCode, that.discountCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountCode);
    }

    @Override
    public String toString() {

        String returning =
                "Discount Code :" + discountCode + '\n' +
                        ", Max discount percent: " + maxDiscountAmount + '\n' +
                        ", Percent: " + percent + '\n' +
                        ", Customers :\n";
        for (Customer customer : customers) {
            returning += customer.getUserName() + "\n";
        }
        return returning;
    }
}
