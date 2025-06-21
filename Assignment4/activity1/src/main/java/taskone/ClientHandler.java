package taskone;

import java.io.*;
import java.net.Socket;
import org.json.JSONObject;

public class ClientHandler implements Runnable {

    private Socket socket;
    private Performer performer;

    public ClientHandler(Socket socket, StringList sharedState) {
        this.socket = socket;
        this.performer = new Performer(sharedState);
    }

    @Override
    public void run() {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String line;
                while ((line = reader.readLine()) != null) {
                    JSONObject request = new JSONObject(line);
                    JSONObject response;

                    try {
                        int selected = request.getInt("selected");
                        String data = request.getString("data");

                        switch (selected) {
                            case 1:
                                response = performer.add(data);
                                break;
                            case 2:
                                response = performer.display();
                                break;
                            case 3:
                                response = performer.count();
                                break;
                            case 0:
                                response = performer.quit();
                                socket.close();
                                return;
                            default:
                                response = Performer.error("Invalid selection: " + selected + " is not an option");
                        }
                    } catch (Exception e) {
                        response = Performer.error("Error: " + e.getMessage());
                    }

                    writer.println(response.toString());
                }

        } catch (IOException e) {
            System.out.println("Client disconnected.");
        }
    }
}
