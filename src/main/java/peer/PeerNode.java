package peer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class PeerNode
{
    private int port;
    private static ArrayList<PeerNode> contacts = new ArrayList<>();
    PeerNode preNode;
    PeerNode postNode;
    private String directoryLocation = "";

    public int getPort() {
        return port;
    }

    public PeerNode(int port)
    {
        this.port = port;
        new Thread(new Runnable() {
            public void run() {
                startClientServer( port );
            }
        }).start();
        contacts.add(this);
    }
    private void startClientServer( int portNum )
    {
        try
        {
            ServerSocket server = new ServerSocket( portNum );
            port = server.getLocalPort();
            System.out.println("listening on port " + server.getLocalPort());

            while( true )
            {
                Socket connection = server.accept();

                // Construct an object to process the HTTP request message.
                RequestHandler request = new RequestHandler( connection );

                // Create a new thread to process the request.
                Thread thread = new Thread(request);

                System.out.println("hi");
                // Start the thread.
                thread.start();
                System.out.println("bye");

                System.out.println(server.getLocalPort());


                System.out.println("Thread started for "+ portNum);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void sendRequest(String filePath, String host, int port) throws IOException
    {
        System.out.println(port);
        Socket socket = new Socket(host, port);//machine name, port number
        PrintWriter out = new PrintWriter( socket.getOutputStream(), true );
        out.println(filePath);

        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // Get the request line of the HTTP request message.
        String requestLine = br.readLine();
        System.out.println(requestLine);
        requestLine = br.readLine();
        System.out.println(requestLine);
        requestLine = br.readLine();
        System.out.println(requestLine);

        byte[] buffer = new byte[1024];
        int bytes = 0;

        FileOutputStream fileOutputStream = new FileOutputStream(new File(".\\hi"));

        // Copy requested file into the socket's output stream.
        while ((bytes = is.read(buffer)) != -1) {
            System.out.println(buffer[0]);
            fileOutputStream.write(buffer, 0, bytes);
        }


        fileOutputStream.close();
        out.close();
        socket.close();

    }
    public static ArrayList<PeerNode> getContacts() {
        return contacts;
    }
}