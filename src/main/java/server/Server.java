package server;

import account.*;
import discount.PeriodicCodedDiscountGenerator;
import server.security.DenialOfServiceBlocker;
import server.security.DynamicPasswordManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class Server extends Thread {
    private static final String LETTERS_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final ServerSocket serverSocket;
    private final HashMap<String, ClientThread> tokenToClientThreadHashMap;
    private final DenialOfServiceBlocker dosBlocker;
    private final DynamicPasswordManager dynamicPasswordManager;
    private boolean unlocked;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(8081);
        this.tokenToClientThreadHashMap = new HashMap<>();
        this.unlocked = true;
        new PeriodicCodedDiscountGenerator(true).start();
        this.dosBlocker = new DenialOfServiceBlocker();
        this.dynamicPasswordManager = new DynamicPasswordManager();
    }

    public DynamicPasswordManager getDynamicPasswordManager() {
        return dynamicPasswordManager;
    }

    public DenialOfServiceBlocker getDosBlocker() {
        return dosBlocker;
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

    public ArrayList<Customer> getOnlineCustomers() {
        ArrayList<Customer> onlineCustomers = new ArrayList<>();
        for (String token : tokenToClientThreadHashMap.keySet()) {
            Account account = tokenToClientThreadHashMap.get(token).getController().getAccount();
            if (account instanceof Customer) {
                onlineCustomers.add((Customer) account);
            }
        }
        return onlineCustomers;
    }

    public ArrayList<Supplier> getOnlineSuppliers() {
        ArrayList<Supplier> onlineSuppliers = new ArrayList<>();
        for (String token : tokenToClientThreadHashMap.keySet()) {
            Account account = tokenToClientThreadHashMap.get(token).getController().getAccount();
            if (account instanceof Supplier) {
                onlineSuppliers.add((Supplier) account);
            }
        }
        return onlineSuppliers;
    }

    public ArrayList<Supervisor> getOnlineSupervisors() {
        ArrayList<Supervisor> onlineSupervisors = new ArrayList<>();
        for (String token : tokenToClientThreadHashMap.keySet()) {
            Account account = tokenToClientThreadHashMap.get(token).getController().getAccount();
            if (account instanceof Supervisor) {
                onlineSupervisors.add((Supervisor) account);
            }
        }
        return onlineSupervisors;
    }

    public ArrayList<Supporter> getOnlineSupporters() {
        ArrayList<Supporter> onlineSupporters = new ArrayList<>();
        for (String token : tokenToClientThreadHashMap.keySet()) {
            Account account = tokenToClientThreadHashMap.get(token).getController().getAccount();
            if (account instanceof Supporter) {
                onlineSupporters.add((Supporter) account);
            }
        }
        return onlineSupporters;
    }

    public String generateRandomToken() {
        Random rand = new Random();
        int upperBound = LETTERS_SET.length()-1;
        StringBuilder code = new StringBuilder();
        for(int i = 0;i < 10;i++){
            code.append(LETTERS_SET.charAt(rand.nextInt(upperBound)));
        }
        return code.toString();
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public void disconnectUser(String username) {
        Collection<ClientThread> clients = tokenToClientThreadHashMap.values();
        for (ClientThread thread : clients) {
            if (thread.getController().getAccount().getUserName().equals(username)) {
                thread.disconnect();
            }
        }
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
                boolean permission = dosBlocker.getIpPermissionForConnection(clientSocket.getInetAddress().getCanonicalHostName());
                System.out.println(permission);
                if (permission) {
                    try {
                        new ClientThread(this, clientSocket).start();
                    } catch (IOException e) {
                        System.err.println("Error: ClientThread start");
                        dosBlocker.reduceConnection(clientSocket.getInetAddress().getCanonicalHostName());
                    }
                } else {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.err.println("Error: accepting client socket");
            }
        }
    }
}
