package com.distributed;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Node {
    private static final int LEADER_PORT = 5000;
    private static final String LEADER_HOST = "localhost";
    private final boolean isFaulty;

    public Node(boolean isFaulty) {
        this.isFaulty = isFaulty;
    }

    public static void main(String[] args) {
        boolean isFaulty = false;
        if (args.length > 1 && args[1].equalsIgnoreCase("-Pwrong=1")) {
            isFaulty = true;
        }
        Node node = new Node(isFaulty);
        node.start();
    }

    public void start() {
        try (Socket socket = new Socket(LEADER_HOST, LEADER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Connected to the leader.");
            out.flush();

            while (true) {
                try {
                    Object message = in.readObject();
                    if (!(message instanceof String)) {
                        System.out.println("Node: Unexpected message type");
                        continue;
                    }

                    String messageType = (String) message;
                    System.out.println("Node: Received message type: " + messageType);

                    switch (messageType) {
                        case "TASK":
                            handleTask(in, out);
                            break;
                        case "VERIFY":
                            handleVerification(in, out);
                            break;
                        case "CLOSE":
                            System.out.println("Node: Received close command");
                            return;
                        default:
                            System.out.println("Node: Unknown message type: " + messageType);
                    }
                } catch (EOFException e) {
                    System.out.println("Node: Connection closed by leader");
                    break;
                } catch (Exception e) {
                    System.out.println("Node: Error processing message: " + e.getMessage());
                    e.printStackTrace();
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Node: Error connecting to leader: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleTask(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        @SuppressWarnings("unchecked")
        List<Integer> numbers = (List<Integer>) in.readObject();
        int delay = in.readInt();
        
        System.out.println("Node: Received numbers: " + numbers);
        System.out.println("Node: Received delay: " + delay);
        
        int sum = calculateSum(numbers, delay);

        if (isFaulty) {
            sum += ThreadLocalRandom.current().nextInt(1, 10);
            System.out.println("Node: Simulating faulty behavior");
        }

        System.out.println("Node: Sending result back to leader: " + sum);
        out.writeInt(sum);
        out.flush();
    }

    private void handleVerification(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        @SuppressWarnings("unchecked")
        List<Integer> numbers = (List<Integer>) in.readObject();
        int expectedSum = in.readInt();

        System.out.println("Node: Verifying sum " + expectedSum + " for numbers: " + numbers);
        int calculatedSum = calculateSum(numbers, 0);
        boolean agrees = calculatedSum == expectedSum && !isFaulty;
        
        System.out.println("Node: Sending verification result: " + agrees);
        out.writeBoolean(agrees);
        out.flush();
    }

    private int calculateSum(List<Integer> numbers, int delay) {
        int sum = 0;
        for (int num : numbers) {
            sum += num;
            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return sum;
    }
}