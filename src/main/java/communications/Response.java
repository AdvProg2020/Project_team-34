package communications;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exceptionalMassage.ExceptionalMassage;

/**
 * @author Aryan Ahadinia
 * @author Soheil Mahdi Zadeh
 * @author Roozbeh PirAyadi
 * @since 0.0.3
 */

public class Response {
    private RequestStatus status;
    private String content;
    private String nextToken;

    public Response(RequestStatus status, String content, String nextToken) {
        this.status = status;
        this.content = content;
        this.nextToken = nextToken;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNextToken() {
        return nextToken;
    }

    public String convertResponseToJsonString(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status", status.toString());
        jsonObject.addProperty("content", content);
        return jsonObject.toString();
    }

    public static Response convertJsonStringToResponse(String jsonString){
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
        RequestStatus requestStatus = RequestStatus.valueOf(jsonObject.get("status").getAsString());
        String content = jsonObject.get("content").getAsString();
        String nextToken = jsonObject.get("nextToken").getAsString();
        return new Response(requestStatus, content, nextToken);
    }

//    public static Response createResponseFromExceptionalMassage(ExceptionalMassage exceptionalMassage){
//        return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, exceptionalMassage.getMessage());
//    }
//
//    public static Response createResponseFromExceptionalMassage(String message){
//        return new Response(RequestStatus.EXCEPTIONAL_MASSAGE, message);
//    }
//
//    public static Response createSuccessResponse(){
//        return new Response(RequestStatus.SUCCESSFUL, "");
//    }
}
