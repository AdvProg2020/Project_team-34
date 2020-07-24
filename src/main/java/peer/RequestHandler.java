package peer;

import javafx.scene.chart.PieChart;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import java.io.*;
import java.net.Socket;
import java.security.PublicKey;

public class RequestHandler implements Runnable {
    private final static String CRLF = "\r\n";
    private Socket socket;

    public RequestHandler(Socket socket) throws Exception {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            processRequest();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void processRequest() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        System.err.println("==================");
        Object object = objectInputStream.readObject();
        System.err.println("}}}}}}}}}}}}}}}}}}}");
        PublicKey publicKey = (PublicKey) object;


        String filePath = (String) objectInputStream.readObject();
        System.out.println(filePath);

        // Open the requested file.
        FileInputStream fis = null;
        boolean fileExists = true;
        try
        {
            fis = new FileInputStream(filePath);
        }
        catch (FileNotFoundException e)
        {
            fileExists = false;
        }

        String statusLine = null;
        if (fileExists)
        {
            statusLine = "Successful" + CRLF;
        }
        else
        {
            statusLine = "Error" + CRLF;
        }

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        dataOutputStream.writeBytes(statusLine);
        dataOutputStream.flush();

        if (fileExists) {
            sendBytes(fis, dataOutputStream, publicKey);
            fis.close();
        }

        dataOutputStream.close();
        socket.close();

    }


    private static void sendBytes(FileInputStream fileInputStream, OutputStream outputStream, PublicKey publicKey)
            throws Exception {
        System.out.println("i came to send ");
        byte[] buffer = new byte[64];
        byte[] encrypted;
        int bytes = 0;
        while ((bytes = fileInputStream.read(buffer)) > 0) {
            System.out.println(new String(buffer));
            encrypted = Asymmetric.do_RSAEncryption(buffer,publicKey);
            System.out.println(encrypted.length);
            System.out.println(new String(encrypted));
            outputStream.write(encrypted);
            outputStream.flush();
        }
    }
}