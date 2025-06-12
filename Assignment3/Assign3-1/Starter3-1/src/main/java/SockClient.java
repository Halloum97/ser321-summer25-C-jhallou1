import org.json.JSONArray;
import org.json.JSONObject;
import java.net.*;
import java.io.*;
import java.util.Scanner;

class SockClient {
  static Socket sock;
  static ObjectOutputStream os;
  static DataInputStream in;

  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("Expected arguments: <host> <port>");
      System.exit(1);
    }
    String host = args[0];
    int port;
    try {
      port = Integer.parseInt(args[1]);
    } catch (NumberFormatException nfe) {
      System.out.println("Port must be an integer");
      return;
    }

    try {
      sock = new Socket(host, port);
      os = new ObjectOutputStream(sock.getOutputStream());
      in = new DataInputStream(sock.getInputStream());
      System.out.println("Connected to server at " + host + ":" + port);

      Scanner scanner = new Scanner(System.in);
      boolean running = true;
      while (running) {
        System.out.println("Choose service: 1-echo, 2-add, 3-addmany, 4-tempconvert, 5-dadjoke, 0-quit");
        int choice = Integer.parseInt(scanner.nextLine());
        JSONObject req = new JSONObject();

        switch (choice) {
          case 0:
            System.out.println("Goodbye!");
            running = false;
            break;

          case 1:
            System.out.println("Enter text to echo:");
            String text = scanner.nextLine();
            req.put("type", "echo");
            req.put("data", text);
            break;

          case 2:
            System.out.println("Enter first number:");
            String n1 = scanner.nextLine();
            System.out.println("Enter second number:");
            String n2 = scanner.nextLine();
            req.put("type", "add");
            req.put("num1", n1);
            req.put("num2", n2);
            break;

          case 3:
            System.out.println("Enter numbers one by one, '0' to finish:");
            JSONArray arr = new JSONArray();
            while (true) {
              String val = scanner.nextLine();
              if (val.equals("0")) break;
              arr.put(val);
            }
            req.put("type", "addmany");
            req.put("nums", arr);
            break;

          case 4:
            System.out.println("Enter temperature value (double):");
            double value = Double.parseDouble(scanner.nextLine());
            System.out.println("From unit (C, F, K):");
            String from = scanner.nextLine();
            System.out.println("To unit (C, F, K):");
            String to = scanner.nextLine();
            req.put("type", "tempconvert");
            req.put("value", value);
            req.put("fromUnit", from);
            req.put("toUnit", to);
            break;

          case 5:
            System.out.println("DadJoke service - action? (add, get, rate):");
            String action = scanner.nextLine();
            req.put("type", "dadjoke");
            req.put("action", action);
            if (action.equals("add")) {
              System.out.println("Enter joke text:");
              String joke = scanner.nextLine();
              req.put("joke", joke);
            } else if (action.equals("rate")) {
              System.out.println("Enter jokeID to rate:");
              int id = Integer.parseInt(scanner.nextLine());
              System.out.println("Enter your rating (0-5):");
              String rating = scanner.nextLine();
              req.put("jokeID", id);
              req.put("rating", rating);
            }
            // for "get" no extra fields
            break;

          default:
            System.out.println("Invalid choice. Try again.");
            continue;
        }

        if (!running) break;

        // send request
        os.writeObject(req.toString());
        os.flush();

        // receive response
        String respStr = in.readUTF();
        JSONObject res = new JSONObject(respStr);
        System.out.println(res);

        if (!res.getBoolean("ok")) {
          System.out.println("Error: " + res.getString("message"));
        } else {
          String type = res.optString("type");
          switch (type) {
            case "echo":
              System.out.println(res.getString("echo"));
              break;
            case "add":
            case "addmany":
              System.out.println("Result: " + res.getInt("result"));
              break;
            case "tempconvert":
              System.out.println("Converted: " + res.getDouble("result"));
              break;
            case "dadjoke":
              if (res.has("message")) {
                System.out.println(res.getString("message"));
              } else {
                System.out.println("JokeID: " + res.getInt("jokeID"));
                System.out.println("Joke: " + res.getString("joke"));
                System.out.println("Rating: " + res.getDouble("rating"));
              }
              break;
          }
        }
      }

      // cleanup
      in.close();
      os.close();
      sock.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
