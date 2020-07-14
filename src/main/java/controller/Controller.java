package controller;

import com.google.gson.JsonArray;
import communications.*;
import exceptionalMassage.ExceptionalMassage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Controller {
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    private String token;
    private final boolean isFirstSupervisorCreated;

    private final AccountController accountController;
    private final ProductController productController;
    private final OffController offController;

    public Controller() {
        try {
            this.socket = new Socket("localhost", 8080);
            try {
                this.objectInputStream = new ObjectInputStream(socket.getInputStream());
                this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                System.err.println("Error, IOStream can't connect");
                System.exit(1);
            }
        } catch (UnknownHostException e) {
            System.err.println("Error, UnknownHostException");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error, Socket Connection");
            System.exit(1);
        }
        this.isFirstSupervisorCreated = false;
        this.token = null;
        this.accountController = new AccountController(this);
        this.productController = new ProductController(this);
        this.offController = new OffController(this);
    }

    public AccountController getAccountController() {
        return accountController;
    }

    public ProductController getProductController() {
        return productController;
    }

    public OffController getOffController() {
        return offController;
    }

    public boolean getIsFirstSupervisorCreated() {
        return isFirstSupervisorCreated;
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void disconnect() throws IOException {
        objectOutputStream.writeUTF("goodbye");
        objectOutputStream.close();
        objectInputStream.close();
        socket.close();
        System.exit(0);
    }

    public Response communication(String function, JsonArray inputs, ControllerSource source) throws ExceptionalMassage {
        Request request = new Request(getToken(), function, inputs.toString(), source);
        try {
            objectOutputStream.writeUTF(Utils.convertObjectToJsonString(request));
            objectOutputStream.flush();
            try {
                String responseString = objectInputStream.readUTF();
                Response response = Response.convertJsonStringToResponse(responseString);
                if (response.getStatus() == RequestStatus.EXCEPTIONAL_MASSAGE) {
                    throw new ExceptionalMassage(response.getContent());
                }
                return response;
            } catch (IOException e) {
                throw new ExceptionalMassage("Response not received");
            }
        } catch (IOException e) {
            throw new ExceptionalMassage("Request failed");
        }
    }
}
