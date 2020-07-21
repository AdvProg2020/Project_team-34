package peer;

import java.io.*;
import java.net.Socket;

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
            sendBytes(fis, dataOutputStream);
            fis.close();
        }

        dataOutputStream.close();
        bufferedReader.close();
        socket.close();

    }


    private static void sendBytes(FileInputStream fileInputStream, OutputStream outputStream)
            throws Exception
            {
        byte[] buffer = new byte[1024];
        int bytes = 0;
        while ((bytes = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytes);
        }
    }
}