package peer;

import communications.Utils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.PublicKey;
import java.util.ArrayList;

public class PeerNode
{
    private String host;
    private int port;
    private static ArrayList<PeerNode> contacts = new ArrayList<>();
    private Asymmetric asymmetric;

    public int getPort() {
        return port;
    }

    public PeerNode(String host, int port)
    {
        this.host = host;
        this.port = port;
        new Thread(new Runnable() {
            public void run() {
                startClientServer( port );
            }
        }).start();
        asymmetric = new Asymmetric();
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

                RequestHandler request = new RequestHandler( connection );
                Thread thread = new Thread(request);
                thread.start();
                System.out.println("Thread started for "+ port);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void sendRequest(String filePath, String host, int port) throws Exception {
        System.out.println("trying to connect to " + port);
        Socket socket = new Socket(host, port);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(asymmetric.getPublicKey());
        objectOutputStream.flush();

        objectOutputStream.writeObject(filePath);


        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String response = bufferedReader.readLine();
        System.out.println(response);

        if(response.equals("Successful")) {
            String[] data = filePath.split("\\\\");
            String myFilePath = ".\\src\\main\\resources\\download\\" + data[data.length-1 ];

            byte[] buffer = new byte[256];
            byte[] decrypted ;
            int bytes = 256;
            System.out.println("onja");
            FileOutputStream fileOutputStream = new FileOutputStream(new File(myFilePath));

            boolean b = (bytes = inputStream.read(buffer)) > 0 ;
            System.out.println(bytes);
            while ((bytes = inputStream.read(buffer)) > 0 ) {
                System.out.println(bytes);
                System.out.println(new String(buffer));
                decrypted = asymmetric.do_RSADecryption(buffer);
                System.out.println(new String(decrypted));
                fileOutputStream.write(decrypted);
            }

            fileOutputStream.close();
        }
        bufferedReader.close();
        socket.close();

        }
    public static ArrayList<PeerNode> getContacts() {
        return contacts;
    }
}