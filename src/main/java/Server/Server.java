package Server;

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
                ClientThread clientThread = new ClientThread(clientSocket, this);
                clientThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
