package server;

import com.google.gson.*;
import controller.Controller;
import server.communications.ControllerSource;
import server.communications.Request;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThread extends Thread {
    private final Server server;
    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    private final Controller controller;

    public ClientThread(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.controller = new Controller();
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        System.err.println("Thread run");
    }

    public void analyseRequest(JsonElement requestJson){
        //This method needs modification!
        //Parsing the requestJson to Request!
        Gson gson = new Gson();
        Request request = gson.fromJson(requestJson,Request.class);
        String functionName = request.getFunction();
        String token = request.getToken();
        //Analysing the token and checking if it's valid
        ArrayList<Class> params = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonObject arguments = (JsonObject)jsonParser.parse(request.getInputs());
        JsonArray argumentsArray = arguments.getAsJsonArray("Arguments");

        if(argumentsArray != null){
            for (JsonElement jsonElement : argumentsArray) {
                values.add(jsonElement.getAsJsonObject().get("Value").getAsString());
                try {
                    params.add(Class.forName("java.lang.String"));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        Method method;
        try {
            if (request.getSource() == ControllerSource.ACCOUNT_CONTROLLER) {
                if (params.size() == 0) {
                    method = controller.getAccount().getClass().getMethod(functionName);
                } else {
                    method = controller.getAccount().getClass().getMethod(functionName, params.toArray(new Class[params.size()]));
                }
                method.invoke(controller.getAccount(),values.toArray());
            } else if (request.getSource() == ControllerSource.OFF_CONTROLLER) {
                if (params.size() == 0) {
                    method = controller.getAccount().getClass().getMethod(functionName);
                } else {
                    method = controller.getAccount().getClass().getMethod(functionName, params.toArray(new Class[params.size()]));
                }
                method.invoke(controller.getOffController(),values.toArray());
            } else if (request.getSource() == ControllerSource.PRODUCT_CONTROLLER) {
                if (params.size() == 0) {
                    method = controller.getAccount().getClass().getMethod(functionName);
                } else {
                    method = controller.getAccount().getClass().getMethod(functionName, params.toArray(new Class[params.size()]));
                }
                method.invoke(controller.getProductController(),values.toArray());
            }
        } catch (Exception ex){
            System.out.println("Can't call the method...; Error type:");
            System.out.println(ex.getMessage());

        }
    }


}
