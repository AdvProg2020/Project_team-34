package server.communications;

import account.Customer;
import account.Supervisor;
import account.Supplier;
import com.google.gson.*;
import discount.CodedDiscount;
import discount.Sale;
import exceptionalMassage.ExceptionalMassage;
import feedback.Comment;
import javafx.scene.image.Image;
import product.Category;
import product.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class Utils {

    public static String convertObjectToJsonString(Object object) {
        if (object instanceof Product) {
            return ((Product) object).toJson();
        }
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

    public static ArrayList<String> convertJsonElementToStringArrayList(JsonElement jsonElement) {
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

    public static ArrayList<Product> convertJsonElementToProductArrayList(JsonElement jsonElement) {
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

    public static ArrayList<CodedDiscount> convertJsonElementToCodedDiscountArrayList(JsonElement jsonElement) {
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

    public static ArrayList<Sale> convertJsonElementToSaleArrayList(JsonElement jsonElement) {
        ArrayList<Sale> saleArrayList = new ArrayList<>();
        for (JsonElement element : jsonElement.getAsJsonArray()) {
            saleArrayList.add(Sale.convertJsonStringToSale(element.getAsString()));
        }
        return saleArrayList;
    }

    public static JsonElement convertSupervisorArrayListToJsonElement(ArrayList<Supervisor> supervisorArrayList) {
        JsonArray jsonArray = new JsonArray();
        for (Supervisor supervisor : supervisorArrayList) {
            jsonArray.add(convertObjectToJsonString(supervisor));
        }
        return jsonArray;
    }

    public static ArrayList<Supervisor> convertJsonElementToSupervisorArrayList(JsonElement jsonElement) {
        ArrayList<Supervisor> supervisorArrayList = new ArrayList<>();
        for (JsonElement element : jsonElement.getAsJsonArray()) {
            supervisorArrayList.add(Supervisor.convertJsonStringToSupervisor(element.getAsString()));
        }
        return supervisorArrayList;
    }

    public static JsonElement convertSupplierArrayListToJsonElement(ArrayList<Supplier> supplierArrayList) {
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

    public static JsonElement convertCustomerArrayListToJsonElement(ArrayList<Customer> customerArrayList){
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

    public static JsonElement convertCategoryArrayListToJsonElement(ArrayList<Category> categories) {
        JsonArray jsonArray = new JsonArray();
        for (Category category : categories) {
            jsonArray.add(convertObjectToJsonString(category));
        }
        return jsonArray;
    }

    public static ArrayList<Category> convertJsonElementToCategoryArrayList(JsonElement jsonElement) {
        ArrayList<Category> categoryArrayList = new ArrayList<>();
        for (JsonElement element : jsonElement.getAsJsonArray()) {
            categoryArrayList.add(Category.convertJsonStringToCategory(element.getAsString()));
        }
        return categoryArrayList;
    }

    public static JsonElement convertCommentArrayListToJsonElement(ArrayList<Comment> comments) {
        JsonArray jsonArray = new JsonArray();
        for (Comment comment : comments) {
            jsonArray.add(convertObjectToJsonString(comment));
        }
        return jsonArray;
    }

    public static ArrayList<Comment> convertJsonElementToCommentArrayList(JsonElement jsonElement) {
        ArrayList<Comment> commentArrayList = new ArrayList<>();
        for (JsonElement element : jsonElement.getAsJsonArray()) {
            commentArrayList.add(Comment.convertJsonStringToComment(element.getAsString()));
        }
        return commentArrayList;
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

    public static JsonElement convertStringToStringArrayListHashMapToJsonElement(HashMap<String, ArrayList<String>> hashMap){
        JsonObject jsonObject = new JsonObject();
        for (String key : hashMap.keySet()) {
            jsonObject.add(key, Utils.convertStringArrayListToJsonElement(hashMap.get(key)));
        }
        return jsonObject;
    }

    public static HashMap<String, ArrayList<String>> convertJsonElementToStringToStringArrayListHashMap(JsonElement jsonElement) {
        HashMap<String, ArrayList<String>> hashMap = new HashMap<>();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (String key : jsonObject.keySet()) {
            hashMap.put(key, Utils.convertJsonElementToStringArrayList(jsonObject.get(key)));
        }
        return hashMap;
    }

    public static JsonElement convertSupplierToIntegerHashMapToJsonElement(HashMap<Supplier, Integer> hashMap){
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        for (Supplier key : hashMap.keySet()) {
            jsonObject.add(Utils.convertObjectToJsonString(key), jsonParser.parse(Utils.
                    convertObjectToJsonString(hashMap.get(key))));
        }
        return jsonObject;
    }

    public static HashMap<Supplier, Integer> convertJsonElementSupplierToIntegerHashMap(JsonElement jsonElement) {
        HashMap<Supplier, Integer> hashMap = new HashMap<>();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (String key : jsonObject.keySet()) {
            hashMap.put(Supplier.convertJsonStringToSupplier(key), (Integer) convertStringToObject(jsonObject.get(key).getAsString(), "java.lang.Integer"));
        }
        return hashMap;
    }

    private static String encodeImage(byte[] imageByteArray) {
        return Base64.getEncoder().encodeToString(imageByteArray);
    }

    private static byte[] decodeImage(String imageDataString) {
        return Base64.getDecoder().decode(imageDataString);
    }

    public static JsonElement convertImageToJsonElement(String path) throws ExceptionalMassage {
        try {
            File file = new File(path);
            FileInputStream imageInFile = new FileInputStream(file);
            byte[] imageData = new byte[(int) file.length()];
            int status = imageInFile.read(imageData);
            String imageDataString = encodeImage(imageData);
            imageInFile.close();
            return new JsonParser().parse(convertObjectToJsonString(imageDataString));
        } catch (FileNotFoundException e) {
            throw new ExceptionalMassage("File not found");
        } catch (IOException e) {
            throw new ExceptionalMassage("IOException");
        }
    }

    public static Image convertStringToImage(String imageBytes) {
        return new Image(new ByteArrayInputStream(decodeImage(imageBytes)));
    }

    public static void convertAndSaveJsonElementToFile(String imageBytes, String filePath) throws ExceptionalMassage {
        try {
            byte[] imageByteArray = decodeImage(imageBytes);
            FileOutputStream imageOutFile = new FileOutputStream(filePath);
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
        } catch (FileNotFoundException e) {
            throw new ExceptionalMassage("File not found");
        } catch (IOException e) {
            throw new ExceptionalMassage("IOException");
        }
    }
}
