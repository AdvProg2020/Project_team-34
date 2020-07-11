package Server;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private final Server server;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;

    public ClientThread(Socket socket, Server server) throws IOException {
        this.server = server;
        this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    @Override
    public void run() {
        System.out.println("Thread run");
    }
}
