package server;

import discount.PeriodicCodedDiscountGenerator;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;

public class Server extends Thread {
    private final ServerSocket serverSocket;
    private final HashMap<String, ClientThread> tokenToClientThreadHashMap;
    private boolean unlocked;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(8088);
        tokenToClientThreadHashMap = new HashMap<>();
        this.unlocked = true;
        new PeriodicCodedDiscountGenerator(true).start();
    }

    public synchronized String assignToken(ClientThread clientThread) {
        String token;
        do {
            token = generateRandomToken();
        } while (tokenToClientThreadHashMap.containsKey(token));
        tokenToClientThreadHashMap.put(token, clientThread);
        return token;
    }

    public synchronized String changeToken(String token) {
        ClientThread clientThread = tokenToClientThreadHashMap.get(token);
        tokenToClientThreadHashMap.remove(token);
        return assignToken(clientThread);
    }

    public void clientGoodbye(String token) {
        tokenToClientThreadHashMap.remove(token);
    }

    public String generateRandomToken() {
        return "";
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    @Override
    public void run() {
        try {
            System.out.println("Server local ip: " + InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            System.err.println("Error: localhost UnknownHostException");
        }
        System.out.println("Server is up");
        while (unlocked) {
            try {
                Socket clientSocket = serverSocket.accept();
                try {
                    new ClientThread(this, clientSocket).start();
                } catch (IOException e) {
                    System.err.println("Error: ClientThread start");
                }
            } catch (IOException e) {
                System.err.println("Error: accepting client socket");
            }
        }
    }
}
