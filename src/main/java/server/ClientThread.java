package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import controller.Controller;
import server.communications.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class ClientThread extends Thread {
    private final Server server;
    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    private final Controller controller;
    private Date lastRequestTime;
    private static final long  MAX_TIME_BETWEEN_TWO_REQUESTS = 60*60*1000;

    private String host;
    private int port;

    public ClientThread(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.host = null;
        this.port = -1;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.controller = new Controller(this, server.assignToken(this));
        this.lastRequestTime = new Date(System.currentTimeMillis());
    }

    public String getNewToken() {
        return server.changeToken(controller.getToken());
    }

    public Controller getController() {
        return controller;
    }

    public Server getServer() {
        return server;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            objectOutputStream.writeUTF(String.valueOf(controller.getIsFirstSupervisorCreated()));
            objectOutputStream.flush();
            objectOutputStream.writeUTF(String.valueOf(controller.getToken()));
            objectOutputStream.flush();
        } catch (IOException e) {
            System.err.println("Error, Initializing Client");
        }
        while (true) {
            try {
                if (!server.getDosBlocker().getIpPermissionForCommunication(socket.getInetAddress().getCanonicalHostName())) {
                    disconnect();
                    break;
                }
                int requestSize = Integer.parseInt(objectInputStream.readUTF());
                ArrayList<String> receiving = new ArrayList<>();
                for (int i = 0; i < requestSize; i++) {
                    receiving.add(objectInputStream.readUTF());
                }
                String requestString = Utils.join(receiving);
                if (requestString.equals("goodbye")) {
                    disconnect();
                    break;
                }
                try {
                    String response = analyseRequest(requestString).convertResponseToJsonString();
                    ArrayList<String> sendingArray = Utils.separate(response, 50000);
                    objectOutputStream.writeUTF(String.valueOf(sendingArray.size()));
                    objectOutputStream.flush();
                    for (String subString : sendingArray) {
                        objectOutputStream.writeUTF(subString);
                        objectOutputStream.flush();
                    }
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

    public boolean disconnect() {
        boolean status = true;
        server.getDosBlocker().reduceConnection(socket.getInetAddress().getCanonicalHostName());
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
        if(!token.equals(controller.getToken())){
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE,"Token is not Valid!", controller);
        }
        if(System.currentTimeMillis() - lastRequestTime.getTime() > MAX_TIME_BETWEEN_TWO_REQUESTS && controller.getAccountController().getInternalAccount() != null){
            lastRequestTime.setTime(System.currentTimeMillis());
            controller.getAccountController().controlLogout();
            return new Response(RequestStatus.EXCEPTIONAL_MASSAGE,"Your session has expired", controller);
        }
        lastRequestTime.setTime(System.currentTimeMillis());
        ArrayList<Class> params = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        JsonArray argumentsArray = (JsonArray)jsonParser.parse(request.getInputs());
        System.out.println(functionName);

        if(argumentsArray != null){
            for (JsonElement jsonElement : argumentsArray) {
                System.out.println(jsonElement.getAsString());
                values.add(jsonElement.getAsString());
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
