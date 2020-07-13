package server;

import discount.PeriodicCodedDiscountGenerator;

import java.io.IOException;
import java.net.*;

public class Server extends Thread {
    private final ServerSocket serverSocket;
    private boolean unlocked;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(8080);
        this.unlocked = true;
        new PeriodicCodedDiscountGenerator(true).start();
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
