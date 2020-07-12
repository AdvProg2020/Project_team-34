package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private final ServerSocket serverSocket;
    private boolean unlocked;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(8080);
        this.unlocked = true;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    @Override
    public void run() {
        while (unlocked) {
            try {
                Socket clientSocket = serverSocket.accept();
                try {
                    ClientThread clientThread = new ClientThread(this, clientSocket);
                    clientThread.start();
                } catch (IOException e) {
                    System.err.println("Error: ClientThread start");
                }
            } catch (IOException e) {
                System.err.println("Error: accepting client socket");
            }
        }
    }
}
