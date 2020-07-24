package controller;

import account.Account;
import com.google.gson.JsonArray;
import communications.*;
import exceptionalMassage.ExceptionalMassage;
import peer.PeerNode;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Controller {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private String token;
    private boolean isFirstSupervisorCreated;
    private PeerNode peerNode;

    private final AccountController accountController;
    private final ProductController productController;
    private final OffController offController;

    public Controller() {
        peerNode = new PeerNode("localhost", 0);
        try {
            this.socket = new Socket("0.tcp.ngrok.io", 15171);
            try {
                this.inputStream = new ObjectInputStream(socket.getInputStream());
                this.outputStream = new ObjectOutputStream(socket.getOutputStream());
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
        try {
            this.isFirstSupervisorCreated = Boolean.parseBoolean(inputStream.readUTF());
            this.token = inputStream.readUTF();
            System.out.println(token);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public PeerNode getPeerNode() {
        return peerNode;
    }

    public String getToken() {
        return token;
    }

    public Account getAccount() {
        try {
            return accountController.getAccount();
        } catch (ExceptionalMassage exceptionalMassage) {
            return null;
        }
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void disconnect() throws IOException {
        outputStream.writeUTF("1");
        outputStream.writeUTF("goodbye");
        outputStream.close();
        inputStream.close();
        socket.close();
        System.exit(0);
    }

    public Response communication(String function, JsonArray inputs, ControllerSource source) throws ExceptionalMassage {
        Request request = new Request(getToken(), function, inputs.toString(), source);
        try {
            String sendingString = Utils.convertObjectToJsonString(request);
            ArrayList<String> sendingArray = Utils.separate(sendingString, 50000);
            System.out.println(sendingArray);
            outputStream.writeUTF(String.valueOf(sendingArray.size()));
            outputStream.flush();
            for (String subString : sendingArray) {
                outputStream.writeUTF(subString);
                outputStream.flush();
            }
            try {
                int responseSize = Integer.parseInt(inputStream.readUTF());
                ArrayList<String> receiving = new ArrayList<>();
                for (int i = 0; i < responseSize; i++) {
                    receiving.add(inputStream.readUTF());
                }
                String responseString = Utils.join(receiving);
                Response response = Response.convertJsonStringToResponse(responseString);
                setToken(response.getNextToken());
                if (response.getStatus() == RequestStatus.EXCEPTIONAL_MASSAGE) {
                    if(response.getContent().equals("Your session has expired")){
                        System.exit(0);
                    }
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
