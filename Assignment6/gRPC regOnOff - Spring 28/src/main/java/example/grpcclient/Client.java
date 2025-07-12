package example.grpcclient;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import service.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.protobuf.Empty; // needed to use Empty


/**
 * Client that requests `parrot` method from the `EchoServer`.
 */
public class Client {
  private final EchoGrpc.EchoBlockingStub blockingStub;
  private final JokeGrpc.JokeBlockingStub blockingStub2;
  private final RegistryGrpc.RegistryBlockingStub blockingStub3;
  private final RegistryGrpc.RegistryBlockingStub blockingStub4;
  private final DiceGrpc.DiceBlockingStub diceStub;
  private final CaesarGrpc.CaesarBlockingStub passwordStub;
  private final TodoGrpc.TodoBlockingStub todoStub;


  /** Construct client for accessing server using the existing channel. */
  public Client(Channel channel, Channel regChannel) {
    // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's
    // responsibility to
    // shut it down.

    // Passing Channels to code makes code easier to test and makes it easier to
    // reuse Channels.
    blockingStub = EchoGrpc.newBlockingStub(channel);
    blockingStub2 = JokeGrpc.newBlockingStub(channel);
    blockingStub3 = RegistryGrpc.newBlockingStub(regChannel);
    blockingStub4 = RegistryGrpc.newBlockingStub(channel);
    diceStub = DiceGrpc.newBlockingStub(channel);
    todoStub = TodoGrpc.newBlockingStub(channel);
    passwordStub = CaesarGrpc.newBlockingStub(channel);

  }

  /** Construct client for accessing server using the existing channel. */
  public Client(Channel channel) {
    // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's
    // responsibility to
    // shut it down.

    // Passing Channels to code makes code easier to test and makes it easier to
    // reuse Channels.
    blockingStub = EchoGrpc.newBlockingStub(channel);
    blockingStub2 = JokeGrpc.newBlockingStub(channel);
    diceStub = DiceGrpc.newBlockingStub(channel);
    passwordStub = CaesarGrpc.newBlockingStub(channel);
    todoStub = TodoGrpc.newBlockingStub(channel);
    blockingStub3 = null;
    blockingStub4 = null;
  }

  public void askServerToParrot(String message) {

    ClientRequest request = ClientRequest.newBuilder().setMessage(message).build();
    ServerResponse response;
    try {
      response = blockingStub.parrot(request);
    } catch (Exception e) {
      System.err.println("RPC failed: " + e.getMessage());
      return;
    }
    System.out.println("Received from server: " + response.getMessage());
  }

  public void askForJokes(int num) {
    JokeReq request = JokeReq.newBuilder().setNumber(num).build();
    JokeRes response;

    // just to show how to use the empty in the protobuf protocol
    Empty empt = Empty.newBuilder().build();

    try {
      response = blockingStub2.getJoke(request);
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
    System.out.println("Your jokes: ");
    for (String joke : response.getJokeList()) {
      System.out.println("--- " + joke);
    }
  }

  public void rollDice(BufferedReader reader) throws Exception {
    System.out.println("Dice Service:\n1. Single Roll\n2. Triple Roll");
    String choiceStr = reader.readLine();
    int choice = Integer.parseInt(choiceStr.trim());

    if (choice == 1) {
        System.out.print("Enter dice type (e.g. 6 for d6): ");
        int type = Integer.parseInt(reader.readLine().trim());
        System.out.print("Enter number of dice: ");
        int num = Integer.parseInt(reader.readLine().trim());

        SingleRequest req = SingleRequest.newBuilder().setType(type).setNum(num).build();
        DiceResponse res = diceStub.singleroll(req);
        System.out.println("Rolled: " + res.getDiceList());

    } else if (choice == 2) {
        System.out.print("Enter 3 dice types (e.g. 4, 6, 8):\nType 1: ");
        int t1 = Integer.parseInt(reader.readLine().trim());
        System.out.print("Type 2: ");
        int t2 = Integer.parseInt(reader.readLine().trim());
        System.out.print("Type 3: ");
        int t3 = Integer.parseInt(reader.readLine().trim());
        System.out.print("Enter number of total dice: ");
        int num = Integer.parseInt(reader.readLine().trim());

        TripleRequest req = TripleRequest.newBuilder()
            .setType1(t1).setType2(t2).setType3(t3).setNum(num).build();
        DiceResponse res = diceStub.tripleroll(req);
        System.out.println("Rolled: " + res.getDiceList());
    } else {
        System.out.println("Invalid option.");
    }
  }

  public void handleTodoService(BufferedReader reader) throws Exception {
    System.out.println("Todo Service:\n1. Add Task\n2. List Tasks\n3. Remove Task");
    String option = reader.readLine().trim();

    switch (option) {
        case "1":
            System.out.print("Enter task description: ");
            String desc = reader.readLine().trim();
            TaskRequest addReq = TaskRequest.newBuilder().setDescription(desc).build();
            TaskResponse addRes = todoStub.addTask(addReq);
            if (addRes.getOk()) {
                System.out.println("Task added.");
            } else {
                System.out.println("Failed to add task: " + addRes.getError());
            }
            break;

        case "2":
            TaskList list = todoStub.listTasks(Empty.newBuilder().build());
            if (list.getTasksList().isEmpty()) {
                System.out.println("No tasks found.");
            } else {
                System.out.println("Current Tasks:");
                for (String task : list.getTasksList()) {
                    System.out.println("- " + task);
                }
            }
            break;

        case "3":
            System.out.print("Enter task description to remove: ");
            String toRemove = reader.readLine().trim();
            RemoveRequest remReq = RemoveRequest.newBuilder().setDescription(toRemove).build();
            TaskResponse remRes = todoStub.removeTask(remReq);
            if (remRes.getOk()) {
                System.out.println("Task removed.");
            } else {
                System.out.println("Failed to remove task: " + remRes.getError());
            }
            break;

        default:
            System.out.println("Invalid option.");
    }
  }


  public void handlePasswordService(BufferedReader reader) throws Exception {
    System.out.println("Password Service:\n1. Save Password\n2. Retrieve Password\n3. List Saved Names");
    String option = reader.readLine().trim();

    switch (option) {
        case "1":
            System.out.print("Enter name for password: ");
            String name = reader.readLine().trim();
            System.out.print("Enter password: ");
            String password = reader.readLine().trim();

            SaveReq saveReq = SaveReq.newBuilder().setName(name).setPassword(password).build();
            SaveRes saveRes = passwordStub.encrypt(saveReq);

            if (saveRes.getOk()) {
                System.out.println("Password saved successfully!");
            } else {
                System.out.println("Failed to save password: " + saveRes.getError());
            }
            break;

        case "2":
            System.out.print("Enter name to retrieve password: ");
            String retrieveName = reader.readLine().trim();

            PasswordReq req = PasswordReq.newBuilder().setName(retrieveName).build();
            PasswordRes res = passwordStub.decrypt(req);

            if (res.getOk()) {
                System.out.println("Retrieved password: " + res.getPassword());
            } else {
                System.out.println("Failed to retrieve password: " + res.getError());
            }
            break;

        case "3":
            PasswordList list = passwordStub.listPasswords(Empty.newBuilder().build());
            System.out.println("Stored password names:");
            for (String storedName : list.getPassListList()) {
                System.out.println("- " + storedName);
            }
            break;

        default:
            System.out.println("Invalid option.");
          }
    }



  public void setJoke(String joke) {
    JokeSetReq request = JokeSetReq.newBuilder().setJoke(joke).build();
    JokeSetRes response;

    try {
      response = blockingStub2.setJoke(request);
      System.out.println(response.getOk());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void getNodeServices() {
    GetServicesReq request = GetServicesReq.newBuilder().build();
    ServicesListRes response;
    try {
      response = blockingStub4.getServices(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void getServices() {
    GetServicesReq request = GetServicesReq.newBuilder().build();
    ServicesListRes response;
    try {
      response = blockingStub3.getServices(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void findServer(String name) {
    FindServerReq request = FindServerReq.newBuilder().setServiceName(name).build();
    SingleServerRes response;
    try {
      response = blockingStub3.findServer(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void findServers(String name) {
    FindServersReq request = FindServersReq.newBuilder().setServiceName(name).build();
    ServerListRes response;
    try {
      response = blockingStub3.findServers(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 6) {
      System.out
          .println("Expected arguments: <host(String)> <port(int)> <regHost(string)> <regPort(int)> <message(String)> <regOn(bool)>");
      System.exit(1);
    }
    int port = 9099;
    int regPort = 9003;
    String host = args[0];
    String regHost = args[2];
    String message = args[4];
    try {
      port = Integer.parseInt(args[1]);
      regPort = Integer.parseInt(args[3]);
    } catch (NumberFormatException nfe) {
      System.out.println("[Port] must be an integer");
      System.exit(2);
    }

    // Create a communication channel to the server (Node), known as a Channel. Channels
    // are thread-safe
    // and reusable. It is common to create channels at the beginning of your
    // application and reuse
    // them until the application shuts down.
    String target = host + ":" + port;
    ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS
        // to avoid
        // needing certificates.
        .usePlaintext().build();

    String regTarget = regHost + ":" + regPort;
    ManagedChannel regChannel = ManagedChannelBuilder.forTarget(regTarget).usePlaintext().build();
    try {

      // ##############################################################################
      // ## Assume we know the port here from the service node it is basically set through Gradle
      // here.
      // In your version you should first contact the registry to check which services
      // are available and what the port
      // etc is.

      /**
       * Your client should start off with 
       * 1. contacting the Registry to check for the available services
       * 2. List the services in the terminal and the client can
       *    choose one (preferably through numbering) 
       * 3. Based on what the client chooses
       *    the terminal should ask for input, eg. a new sentence, a sorting array or
       *    whatever the request needs 
       * 4. The request should be sent to one of the
       *    available services (client should call the registry again and ask for a
       *    Server providing the chosen service) should send the request to this service and
       *    return the response in a good way to the client
       * 
       * You should make sure your client does not crash in case the service node
       * crashes or went offline.
       */

      // Just doing some hard coded calls to the service node without using the
      // registry
      // create client
      Client client = new Client(channel, regChannel);

      // call the parrot service on the server
      client.askServerToParrot(message);

      // ask the user for input how many jokes the user wants
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

      // Reading data using readLine
      System.out.println("How many jokes would you like?"); // NO ERROR handling of wrong input here.
      String num = reader.readLine();

      // calling the joked service from the server with num from user input
      client.askForJokes(Integer.valueOf(num));

      // adding a joke to the server
      client.setJoke("I made a pencil with two erasers. It was pointless.");

      System.out.println("Would you like to try the Dice Service? (yes/no): ");
      String diceAnswer = reader.readLine();
      if (diceAnswer.trim().equalsIgnoreCase("yes")) {
          client.rollDice(reader);
      }

      // Password Service
      System.out.println("Would you like to use the Password Service? (yes/no): ");
      String passAnswer = reader.readLine();
      if (passAnswer.trim().equalsIgnoreCase("yes")) {
          client.handlePasswordService(reader);
      }
      
      // Todo Service
      System.out.println("Would you like to use the Todo Service? (yes/no): ");
      String todoAnswer = reader.readLine();
      if (todoAnswer.trim().equalsIgnoreCase("yes")) {
      client.handleTodoService(reader);
      }




      // showing 6 joked
      client.askForJokes(Integer.valueOf(6));

      // list all the services that are implemented on the node that this client is connected to

      System.out.println("Services on the connected node. (without registry)");
      client.getNodeServices(); // get all registered services 

      // ############### Contacting the registry just so you see how it can be done

      if (args[5].equals("true")) { 
        // Comment these last Service calls while in Activity 1 Task 1, they are not needed and wil throw issues without the Registry running
        // get thread's services
        client.getServices(); // get all registered services 

        // get parrot
        client.findServer("services.Echo/parrot"); // get ONE server that provides the parrot service
        
        // get all setJoke
        client.findServers("services.Joke/setJoke"); // get ALL servers that provide the setJoke service

        // get getJoke
        client.findServer("services.Joke/getJoke"); // get ALL servers that provide the getJoke service

        // does not exist
        client.findServer("random"); // shows the output if the server does not find a given service
      }

    } finally {
      // ManagedChannels use resources like threads and TCP connections. To prevent
      // leaking these
      // resources the channel should be shut down when it will no longer be used. If
      // it may be used
      // again leave it running.
      channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
      if (args[5].equals("true")) { 
        regChannel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
      }
    }
  }
}
