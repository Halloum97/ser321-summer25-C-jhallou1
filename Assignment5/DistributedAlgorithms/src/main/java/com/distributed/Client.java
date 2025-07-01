package com.distributed;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private static final String LEADER_HOST = "localhost";
    private static final int LEADER_PORT = 5000; // Ensure this matches the Leader's port

    public static void main(String[] args) {
        try (Socket socket = new Socket(LEADER_HOST, LEADER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the Leader.");

            // Get input from the user
            System.out.println("Enter a list of numbers separated by commas (e.g., 1,2,3,4): ");
            String numbersInput = scanner.nextLine();
            System.out.println("Enter a delay in milliseconds (e.g., 50): ");
            int delay = scanner.nextInt();

            // Parse the input into a list of integers
            List<Integer> numbers = new ArrayList<>();
            for (String num : numbersInput.split(",")) {
                numbers.add(Integer.parseInt(num.trim()));
            }

            // Send the input to the leader
            HashMap<String, Object> data = new HashMap<>();
            data.put("numbers", numbers);
            data.put("delay", delay);
            out.writeObject(data);
            out.flush();

            // Read and print the response from the leader
            Object response = in.readObject();
            if (response instanceof String) {
                System.out.println("Error: " + response);
            } else if (response instanceof HashMap) {
                HashMap<String, Object> result = (HashMap<String, Object>) response;
                System.out.println("Sum: " + result.get("sum"));
                System.out.println("Single-Sum Time: " + result.get("singleSumTime") + "ms");
                System.out.println("Distributed-Sum Time: " + result.get("distributedSumTime") + "ms");
                if (!(boolean) result.get("consensus")) {
                    System.out.println("Error: Consensus failed. Unable to verify results.");
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: Unable to connect to the Leader.");
            e.printStackTrace();
        }
    }
}