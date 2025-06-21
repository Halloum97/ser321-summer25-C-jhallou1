package client;

import operation.RequestProtos.*;
import operation.ResponseProtos.*;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SockBaseClient {

    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 9099;

        if (args.length >= 1) host = args[0];
        if (args.length >= 2) port = Integer.parseInt(args[1]);

        try (
            Socket socket = new Socket(host, port);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();

            // Send NAME request
            Request nameRequest = Request.newBuilder()
                    .setOperationType(Request.OperationType.NAME)
                    .setName(name)
                    .build();
            nameRequest.writeDelimitedTo(out);

            Response welcome = Response.parseDelimitedFrom(in);
            System.out.println(welcome.getHello());

            boolean running = true;
            while (running) {
                System.out.println("\nMenu:");
                System.out.println("1. View Leaderboard");
                System.out.println("2. Play Game");
                System.out.println("3. Quit");
                System.out.print("Choice: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        Request lbRequest = Request.newBuilder()
                                .setOperationType(Request.OperationType.LEADERBOARD)
                                .build();
                        lbRequest.writeDelimitedTo(out);

                        Response lbResponse = Response.parseDelimitedFrom(in);
                        System.out.println("\n--- Leaderboard ---");
                        for (Leader l : lbResponse.getLeaderboardList()) {
                            System.out.println(l.getName() + " - Wins: " + l.getWins());
                        }
                        break;

                    case "2":
                        Request startRequest = Request.newBuilder()
                                .setOperationType(Request.OperationType.START)
                                .build();
                        startRequest.writeDelimitedTo(out);

                        Response task = Response.parseDelimitedFrom(in);
                        System.out.println("\n" + task.getTask());
                        System.out.println(task.getPhrase());

                        boolean inGame = true;
                        while (inGame) {
                            System.out.print("Enter a letter (or type 'exit' to quit): ");
                            String guess = scanner.nextLine().trim();

                            if (guess.equalsIgnoreCase("exit")) {
                                Request quit = Request.newBuilder()
                                        .setOperationType(Request.OperationType.QUIT)
                                        .build();
                                quit.writeDelimitedTo(out);
                                Response bye = Response.parseDelimitedFrom(in);
                                System.out.println(bye.getMessage());
                                return;
                            }

                            Request guessReq = Request.newBuilder()
                                    .setOperationType(Request.OperationType.GUESS)
                                    .setGuess(guess)
                                    .build();
                            guessReq.writeDelimitedTo(out);

                            Response guessRes = Response.parseDelimitedFrom(in);
                            switch (guessRes.getResponseType()) {
                                case TASK:
                                    System.out.println(guessRes.getTask());
                                    System.out.println(guessRes.getPhrase());
                                    break;
                                case WON:
                                case LOST:
                                    System.out.println(guessRes.getMessage());
                                    System.out.println("Phrase was: " + guessRes.getPhrase());
                                    inGame = false;
                                    break;
                                case ERROR:
                                    System.out.println("Error: " + guessRes.getMessage());
                                    break;
                            }
                        }
                        break;

                    case "3":
                        Request quitReq = Request.newBuilder()
                                .setOperationType(Request.OperationType.QUIT)
                                .build();
                        quitReq.writeDelimitedTo(out);
                        Response quitRes = Response.parseDelimitedFrom(in);
                        System.out.println(quitRes.getMessage());
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}
