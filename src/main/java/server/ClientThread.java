package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import controller.Controller;
import server.communications.ControllerSource;
import server.communications.Request;
import server.communications.RequestStatus;
import server.communications.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.controller = new Controller(this, server.assignToken(this));
    }

    public String getNewToken() {
        return server.changeToken(controller.getToken());
    }

    @Override
    public void run() {
        try {
            objectOutputStream.writeUTF(String.valueOf(controller.getIsFirstSupervisorCreated()));
            objectOutputStream.flush();
        } catch (IOException e) {
            System.err.println("Error, Initializing Client");
        }
        while (true) {
            try {
                String requestString = objectInputStream.readUTF();
                if (requestString.equals("goodbye")) {
                    disconnect();
                    break;
                }
                try {
                    objectOutputStream.writeUTF(analyseRequest(requestString).convertResponseToJsonString());
                    objectOutputStream.flush();
                } catch (IOException e) {
                    System.err.println("Error, OutputStream");
                    break;
                }
            } catch (IOException e) {
                try {
                    if (objectInputStream.read() == -1) {
                        disconnect();
                        System.err.println("InputStream IOException, user eventually disconnected, Thread disconnected");
                        break;
                    }
                } catch (IOException ioException) {
                    System.err.println("Error reading InputStream status");
                    break;
                }
                System.err.println("Error, InputStream");
            }
        }
    }

    private boolean disconnect() {
        boolean status = true;
        try {
            socket.close();
        } catch (IOException e) {
            status = false;
        }
        try {
            objectOutputStream.close();
        } catch (IOException e) {
            status = false;
        }
        try {
            objectInputStream.close();
        } catch (IOException e) {
            status = false;
        }
        server.clientGoodbye(controller.getToken());
        return status;
    }

    private Response analyseRequest(String requestStringJson){
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonElement requestJson = jsonParser.parse(requestStringJson);
        Request request = gson.fromJson(requestJson,Request.class);
        String functionName = request.getFunction();
        String token = request.getToken();
//        if(!token.equals(controller.getToken())){
//            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE,"Token is not Valid!");
//        }
        ArrayList<Class> params = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        JsonArray argumentsArray = (JsonArray)jsonParser.parse(request.getInputs());

        if(argumentsArray != null){
            for (JsonElement jsonElement : argumentsArray) {
                values.add(jsonElement.getAsString());
                try {
                    params.add(Class.forName("java.lang.String"));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(params);
        System.out.println(values);
        Method method;
        try {
            if (request.getSource() == ControllerSource.ACCOUNT_CONTROLLER) {
                if (params.size() == 0) {
                    method = controller.getAccountController().getClass().getMethod(functionName);
                } else {
                    method = controller.getAccountController().getClass().getMethod(functionName, params.toArray(new Class[0]));
                }
                return (Response) method.invoke(controller.getAccountController(),values.toArray());
            } else if (request.getSource() == ControllerSource.OFF_CONTROLLER) {
                if (params.size() == 0) {
                    method = controller.getOffController().getClass().getMethod(functionName);
                } else {
                    method = controller.getOffController().getClass().getMethod(functionName, params.toArray(new Class[0]));
                }
                return (Response) method.invoke(controller.getOffController(),values.toArray());
            } else if (request.getSource() == ControllerSource.PRODUCT_CONTROLLER) {
                if (params.size() == 0) {
                    method = controller.getProductController().getClass().getMethod(functionName);
                } else {
                    method = controller.getProductController().getClass().getMethod(functionName, params.toArray(new Class[0]));
                }
                return (Response) method.invoke(controller.getProductController(),values.toArray());
            }
        } catch (Exception ex){
            System.out.println("Can't call the method...; Error type:");
            ex.printStackTrace();
        }
        return null;
    }
}
