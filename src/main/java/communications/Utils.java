package communications;

import account.Customer;
import account.Supplier;
import com.google.gson.*;
import discount.CodedDiscount;
import discount.Sale;
import product.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class Utils {

    public static String convertObjectToJsonString(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static Object convertStringToObject(String jsonString, String completeClassName) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonString, Class.forName(completeClassName));
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    //check
    public static JsonElement convertStringArrayListToJsonElement(ArrayList<String> stringArrayList) {
        JsonArray jsonArray = new JsonArray();
        for (String string : stringArrayList) {
            jsonArray.add(string);
        }
        return jsonArray;
    }

    public static ArrayList<String> convertJasonElementToStringArrayList(JsonElement jsonElement) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (JsonElement element : jsonElement.getAsJsonArray()) {
            stringArrayList.add(element.getAsString());
        }
        return stringArrayList;
    }

    public static JsonElement convertProductArrayListToJsonElement(ArrayList<Product> productArrayList) {
        JsonArray jsonArray = new JsonArray();
        for (Product product : productArrayList) {
            jsonArray.add(convertObjectToJsonString(product));
        }
        return jsonArray;
    }

    public static ArrayList<Product> convertJasonElementToProductArrayList(JsonElement jsonElement) {
        ArrayList<Product> productArrayList = new ArrayList<>();
        for (JsonElement element : jsonElement.getAsJsonArray()) {
            productArrayList.add(Product.convertJsonStringToProduct(element.getAsString()));
        }
        return productArrayList;
    }

    public static JsonElement convertCodedDiscountArrayListToJsonElement(ArrayList<CodedDiscount> codedDiscountArray) {
        JsonArray jsonArray = new JsonArray();
        for (CodedDiscount codedDiscount : codedDiscountArray) {
            jsonArray.add(convertObjectToJsonString(codedDiscount));
        }
        return jsonArray;
    }

    public static ArrayList<CodedDiscount> convertJasonElementToCodedDiscountArrayList(JsonElement jsonElement) {
        ArrayList<CodedDiscount> codedDiscountArrayList = new ArrayList<>();
        for (JsonElement element : jsonElement.getAsJsonArray()) {
            codedDiscountArrayList.add(CodedDiscount.convertJsonStringToCodedDiscount(element.getAsString()));
        }
        return codedDiscountArrayList;
    }

    public static JsonElement convertSaleArrayListToJsonElement(ArrayList<Sale> saleArrayList) {
        JsonArray jsonArray = new JsonArray();
        for (Sale sale : saleArrayList) {
            jsonArray.add(convertObjectToJsonString(sale));
        }
        return jsonArray;
    }

    public static ArrayList<Sale> convertJasonElementToSaleArrayList(JsonElement jsonElement) {
        ArrayList<Sale> saleArrayList = new ArrayList<>();
        for (JsonElement element : jsonElement.getAsJsonArray()) {
            saleArrayList.add(Sale.convertJsonStringToSale(element.getAsString()));
        }
        return saleArrayList;
    }

    public static JsonElement convertStringToStringHashMapToJsonElement(HashMap<String, String> hashMap) {
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        for (String key : hashMap.keySet()) {
            jsonObject.add(key, jsonParser.parse(hashMap.get(key)));
        }
        return jsonObject;
    }

    public static HashMap<String, String> convertJsonElementStringToStringToHashMap(JsonElement jsonElement) {
        HashMap<String, String> hashMap = new HashMap<>();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (String key : jsonObject.keySet()) {
            hashMap.put(key, jsonObject.get(key).getAsString());
        }
        return hashMap;
    }

    public static JsonElement convertCustomerToJsonElement(ArrayList<Customer> customerArrayList) {
        JsonArray jsonArray = new JsonArray();
        for (Customer customer : customerArrayList) {
            jsonArray.add(convertObjectToJsonString(customer));
        }
        return jsonArray;
    }

    public static ArrayList<Customer> convertJsonElementToCustomerArrayList(JsonElement jsonElement) {
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        for (JsonElement element : jsonElement.getAsJsonArray()) {
            customerArrayList.add(Customer.convertJsonStringToCustomer(element.getAsString()));
        }
        return customerArrayList;
    }

    public static JsonElement convertSupplierToJsonElement(ArrayList<Supplier> supplierArrayList) {
        JsonArray jsonArray = new JsonArray();
        for (Supplier supplier : supplierArrayList) {
            jsonArray.add(convertObjectToJsonString(supplier));
        }
        return jsonArray;
    }

    public static ArrayList<Supplier> convertJsonElementToSupplierArrayList(JsonElement jsonElement) {
        ArrayList<Supplier> supplierArrayList = new ArrayList<>();
        for (JsonElement element : jsonElement.getAsJsonArray()) {
            supplierArrayList.add(Supplier.convertJsonStringToSupplier(element.getAsString()));
        }
        return supplierArrayList;
    }
}
