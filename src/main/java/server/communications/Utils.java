package server.communications;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import discount.CodedDiscount;
import discount.Sale;
import product.Product;

import java.util.ArrayList;

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

    public static JsonElement convertStringArrayListToJsonElement(ArrayList<String> stringArrayList) {
        JsonArray jsonArray = new JsonArray();
        for (String string : stringArrayList) {
            jsonArray.add(string);
        }
        return jsonArray;
    }

    public static ArrayList<String> convertJasonObjectToStringArrayList(JsonElement jsonElement) {
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
}
