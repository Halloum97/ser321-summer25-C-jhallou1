package com.distributed;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

class NodeHandler {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public NodeHandler(Socket socket) throws IOException {
        this.socket = socket;
        // Important: Create output stream first to avoid deadlock
        this.out = new ObjectOutputStream(socket.getOutputStream());
        out.flush(); // Flush header
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public int process(List<Integer> numbers, int delay) throws IOException {
        System.out.println("Leader: Processing partition of size: " + numbers.size());
        System.out.println("Leader: Sending data to node...");
        
        // Send as separate list to avoid ArrayList$SubList serialization issues
        ArrayList<Integer> newList = new ArrayList<>(numbers);
        out.writeObject("TASK"); // Send message type
        out.writeObject(newList);
        out.writeInt(delay);
        out.flush();

        System.out.println("Leader: Waiting for result from node...");
        int result = in.readInt();
        System.out.println("Leader: Received result: " + result);
        return result;
    }

    public boolean verifyConsensus(List<Integer> numbers, int expectedSum) throws IOException {
        System.out.println("Leader: Sending data for consensus check...");
        out.writeObject("VERIFY"); // Send message type
        out.writeObject(new ArrayList<>(numbers));
        out.writeInt(expectedSum);
        out.flush();

        System.out.println("Leader: Waiting for consensus result from node...");
        return in.readBoolean();
    }

    public void close() throws IOException {
        try {
            out.writeObject("CLOSE");
            out.flush();
        } finally {
            out.close();
            in.close();
            socket.close();
        }
    }
}

public class Leader {
    private static final int NODE_COUNT = 3;
    private static final int PORT = 5000; // Ensure this matches the Client's port
    private final List<NodeHandler> nodes = new ArrayList<>();

    public static void main(String[] args) {
        new Leader().start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Leader started. Waiting for Client and Nodes...");

            // Accept connections from nodes
            for (int i = 0; i < NODE_COUNT; i++) {
                System.out.println("Leader: Waiting for node connection...");
                Socket nodeSocket = serverSocket.accept();
                System.out.println("Leader: Node connected.");
                nodes.add(new NodeHandler(nodeSocket));
            }

            // Process client requests
            while (true) {
                try {
                    System.out.println("Leader: Waiting for client connection...");
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Leader: Client connected.");
                    handleClient(clientSocket);
                } catch (Exception e) {
                    System.out.println("Leader: Error handling client: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close all node connections
            for (NodeHandler node : nodes) {
                try {
                    node.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {
    
            HashMap<String, Object> data = (HashMap<String, Object>) in.readObject();
            List<Integer> numbers = (List<Integer>) data.get("numbers");
            int delay = (int) data.get("delay");
    
            if (nodes.size() < NODE_COUNT) {
                out.writeObject("Error: Not enough nodes connected.");
                return;
            }
    
            // Single sum calculation
            System.out.println("Leader: Starting single sum calculation...");
            long startTime = System.currentTimeMillis();
            int singleSum = calculateSum(numbers, delay);
            long singleSumTime = System.currentTimeMillis() - startTime;
            System.out.println("Leader: Single sum calculation completed.");
    
            // Distributed sum calculation
            System.out.println("Leader: Starting distributed sum calculation...");
            startTime = System.currentTimeMillis();
            int distributedSum = distributeAndCalculateSum(numbers, delay);
            long distributedSumTime = System.currentTimeMillis() - startTime;
            System.out.println("Leader: Distributed sum calculation completed.");
    
            // Consensus check
            System.out.println("Leader: Starting consensus check...");
            boolean consensus = true;
            for (NodeHandler node : nodes) {
                try {
                    if (!node.verifyConsensus(numbers, distributedSum)) {
                        consensus = false;
                        break;
                    }
                } catch (IOException e) {
                    System.out.println("Leader: Error during consensus check: " + e.getMessage());
                    consensus = false;
                    break;
                }
            }
    
            // Compare performance and send results to client
            HashMap<String, Object> result = new HashMap<>();
            result.put("sum", distributedSum);
            result.put("singleSumTime", singleSumTime);
            result.put("distributedSumTime", distributedSumTime);
            result.put("consensus", consensus);
            out.writeObject(result);
            out.flush();
    
        } catch (Exception e) {
            System.out.println("Leader: Error handling client request: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int calculateSum(List<Integer> numbers, int delay) {
        int sum = 0;
        for (int num : numbers) {
            sum += num;
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return sum;
    }

    private int distributeAndCalculateSum(List<Integer> numbers, int delay) throws InterruptedException, ExecutionException {
        List<List<Integer>> partitions = partitionList(numbers, nodes.size());
        ExecutorService executor = Executors.newFixedThreadPool(nodes.size());
        List<Future<Integer>> results = new ArrayList<>();

        for (int i = 0; i < nodes.size(); i++) {
            final int index = i;
            results.add(executor.submit(() -> nodes.get(index).process(partitions.get(index), delay)));
        }

        int totalSum = 0;
        for (Future<Integer> result : results) {
            totalSum += result.get();
        }

        executor.shutdown();
        return totalSum;
    }

    private List<List<Integer>> partitionList(List<Integer> list, int parts) {
        List<List<Integer>> partitions = new ArrayList<>();
        int size = list.size();
        int chunkSize = (int) Math.ceil((double) size / parts);
        
        for (int i = 0; i < size; i += chunkSize) {
            List<Integer> partition = new ArrayList<>();
            for (int j = i; j < Math.min(i + chunkSize, size); j++) {
                partition.add(list.get(j));
            }
            partitions.add(partition);
        }
        
        return partitions;
    }
}