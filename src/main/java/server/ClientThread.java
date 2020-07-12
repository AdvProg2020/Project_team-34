package server;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private final Server server;
    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;

    public ClientThread(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        System.err.println("Thread run");
    }
}
