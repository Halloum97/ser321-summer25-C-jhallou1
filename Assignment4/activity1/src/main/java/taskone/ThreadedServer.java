package taskone;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;

public class ThreadedServer {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        int port = PORT;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        StringList sharedState = new StringList();
        System.out.println("Threaded Server started on port " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket, sharedState)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
