package server.communications;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

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
        //check
    }

    public static ArrayList<String> convertJasonObjectToStringArrayList(JsonElement jsonElement) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (JsonElement element : jsonElement.getAsJsonArray()) {
            stringArrayList.add(element.getAsString());
        }
        return stringArrayList;
        //check
    }
}
