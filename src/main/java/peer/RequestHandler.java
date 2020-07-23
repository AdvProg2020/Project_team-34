package peer;

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
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        PublicKey publicKey = (PublicKey) object;


        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String filePath = bufferedReader.readLine();
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

        dataOutputStream.writeBytes(statusLine);
        dataOutputStream.flush();

        if (fileExists) {
            sendBytes(fis, dataOutputStream, publicKey);
            fis.close();
        }

        dataOutputStream.close();
        bufferedReader.close();
        socket.close();

    }


    private static void sendBytes(FileInputStream fileInputStream, OutputStream outputStream, PublicKey publicKey)
            throws Exception
            {
        byte[] buffer = new byte[1024];
        byte[] encrypted = new byte[1024];
        int bytes = 0;
        while ((bytes = fileInputStream.read(buffer)) > 0) {
            encrypted = Asymmetric.do_RSAEncryption(buffer,publicKey );
            outputStream.write(encrypted, 0, bytes);
        }
    }
}