package server;

import java.net.*;
import java.io.*;
import java.util.*;

import client.Player;

import java.lang.*;

import operation.RequestProtos.*;
import operation.ResponseProtos.*;


class SockBaseServer {
    static String logFilename = "logs.txt";

    ServerSocket socket = null;
    InputStream in = null;
    OutputStream out = null;
    Socket clientSocket = null;
    int port = 9099; // default port
    Game game;


    public SockBaseServer(Socket sock, Game game){
        this.clientSocket = sock;
        this.game = game;

        try {
            in = clientSocket.getInputStream();
            out = clientSocket.getOutputStream();
        } catch (Exception e){
            System.out.println("Error in constructor: " + e);
        }
    }

    // Handles the communication right now it just accepts one input and then is done you should make sure the server stays open
    // can handle multiple requests and does not crash when the client crashes
    // you can use this server as based or start a new one if you prefer. 
public void handleRequests() {
    String playerName = "";
    int wins = 0;
    Player player = null;

    // Shared leaderboard (static)
    Map<String, Player> leaderboard = SockBaseServerState.leaderboard;

    try {
        while (true) {
            Request request = Request.parseDelimitedFrom(in);
            if (request == null) break;

            Response response = null;

            switch (request.getOperationType()) {
                case NAME:
                    playerName = request.getName();
                    if (!leaderboard.containsKey(playerName)) {
                        player = new Player(playerName, 0);
                        leaderboard.put(playerName, player);
                    } else {
                        player = leaderboard.get(playerName);
                    }

                    writeToLog(playerName, Message.CONNECT);

                    response = Response.newBuilder()
                            .setResponseType(Response.ResponseType.WELCOME)
                            .setHello("Hello " + playerName + ", welcome to the game!")
                            .build();
                    break;

                case LEADERBOARD:
                    Response.Builder lb = Response.newBuilder()
                            .setResponseType(Response.ResponseType.LEADERBOARD);
                    for (Player p : leaderboard.values()) {
                        Leader l = Leader.newBuilder()
                                .setName(p.toString())
                                .setWins(p.getWins())
                                .setLogins(1) // optional field, adjust as needed
                                .build();
                        lb.addLeaderboard(l);
                    }
                    response = lb.build();
                    break;

                case START:
                    game.newGame();
                    response = Response.newBuilder()
                            .setResponseType(Response.ResponseType.TASK)
                            .setPhrase(game.getPhrase())
                            .setTask(game.getTask())
                            .build();
                    break;

                case GUESS:
                    String guess = request.getGuess();
                    if (guess == null || guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
                        response = Response.newBuilder()
                                .setResponseType(Response.ResponseType.ERROR)
                                .setMessage("Invalid guess. Please enter one letter.")
                                .setErrorCode(4)
                                .build();
                        break;
                    }

                    boolean revealed = game.processGuess(guess.charAt(0));
                    if (game.isWon()) {
                        player = leaderboard.get(playerName);
                        player = new Player(playerName, game.getPoints());
                        leaderboard.put(playerName, player);
                        writeToLog(playerName, Message.WIN);
                        response = Response.newBuilder()
                                .setResponseType(Response.ResponseType.WON)
                                .setPhrase(game.getPhrase())
                                .setMessage("You won! Your score: " + game.getPoints())
                                .build();
                    } else if (game.isLost()) {
                        response = Response.newBuilder()
                                .setResponseType(Response.ResponseType.LOST)
                                .setPhrase(game.getAnswer())
                                .setMessage("You lost and got 0 points.")
                                .build();
                    } else {
                        response = Response.newBuilder()
                                .setResponseType(Response.ResponseType.TASK)
                                .setPhrase(game.getPhrase())
                                .setTask(game.getTask())
                                .setEval(revealed)
                                .build();
                    }
                    break;

                case QUIT:
                    response = Response.newBuilder()
                            .setResponseType(Response.ResponseType.BYE)
                            .setMessage("Goodbye " + playerName + ", thanks for playing!")
                            .build();
                    out.write(response.toByteArray());
                    return;

                default:
                    response = Response.newBuilder()
                            .setResponseType(Response.ResponseType.ERROR)
                            .setMessage("Unsupported request")
                            .setErrorCode(2)
                            .build();
            }

            if (response != null) {
                response.writeDelimitedTo(out);
            }
        }
    } catch (IOException e) {
        System.out.println("Client disconnected or crashed.");
    } finally {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error closing connection.");
        }
    }
}


    private Response nameRequest(Request op) throws IOException {
        String name = op.getName(); // get name from proto
        Response response; // create new response

        // writing a connect message to the log with name and CONNECT
        writeToLog(name, Message.CONNECT);
        System.out.println("Got a connection and a name: " + name);

        // sett fields for response
        return response = Response.newBuilder()
            .setResponseType(Response.ResponseType.WELCOME)
            .setHello("Hello " + name + " and welcome. \n")
            .build();
    }

    /**
     * Writing a new entry to our log
     * @param name - Name of the person logging in
     * @param message - type Message from Protobuf which is the message to be written in the log (e.g. Connect) 
     * @return String of the new hidden image
     */
    public static void writeToLog(String name, Message message){
        try {
            // read old log file 
            Logs.Builder logs = readLogFile();

            // get current time and data
            Date date = java.util.Calendar.getInstance().getTime();

            // we are writing a new log entry to our log
            // add a new log entry to the log list of the Protobuf object
            logs.addLog(date.toString() + ": " +  name + " - " + message);

            // open log file
            FileOutputStream output = new FileOutputStream(logFilename);
            Logs logsObj = logs.build();

            // This is only to show how you can iterate through a Logs object which is a protobuf object
            // which has a repeated field "log"
            for (String log: logsObj.getLogList()){
                System.out.println(log);
            }

            // write to log file
            logsObj.writeTo(output);
        }catch(Exception e){
            System.out.println("Issue while trying to save");
        }
    }

    /**
     * Reading the current log file
     * @return Logs.Builder a builder of a logs entry from protobuf
     */
    public static Logs.Builder readLogFile() throws Exception{
        Logs.Builder logs = Logs.newBuilder();

        try {
            // just read the file and put what is in it into the logs object
            return logs.mergeFrom(new FileInputStream(logFilename));
        } catch (FileNotFoundException e) {
            System.out.println(logFilename + ": File not found.  Creating a new file.");
            return logs;
        }
    }

    public static void main (String args[]) throws Exception {
        Game game = new Game();
        game.newGame();

        if (args.length != 2) {
            System.out.println("Expected arguments: <port(int)> <delay(int)>");
            System.exit(1);
        }
        int port = 9099; // default port
        Socket clientSocket = null;
        ServerSocket socket = null;

        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.exit(2);
        }
        try {
            socket = new ServerSocket(port);
            System.out.println("Server Started...");
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(2);
        }

        while (true) {
            try{
                System.out.println("Accepting a Client...");
                clientSocket = socket.accept();
                SockBaseServer server = new SockBaseServer(clientSocket, game);
                server.handleRequests();
            }
            catch(Exception e){
                System.out.println("Server encountered an error while connecting to a client.");
            }
            
        }

    }

}

