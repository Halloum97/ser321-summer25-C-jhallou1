package taskone;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolServer {

    private static final int PORT = 8000;
    private static final int DEFAULT_POOL_SIZE = 5;

    public static void main(String[] args) {
        int port = PORT;
        int poolSize = DEFAULT_POOL_SIZE;

        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }
        if (args.length >= 2) {
            poolSize = Integer.parseInt(args[1]);
        }

        StringList sharedState = new StringList();
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        System.out.println("ThreadPool Server started on port " + port + " with pool size " + poolSize);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.execute(new ClientHandler(clientSocket, sharedState));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
