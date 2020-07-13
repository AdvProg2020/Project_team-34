package communications;

import com.google.gson.Gson;

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
}
