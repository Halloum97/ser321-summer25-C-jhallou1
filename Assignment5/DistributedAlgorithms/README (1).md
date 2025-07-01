# Distributed Algorithm Implementation

## Overview
This project implements a distributed system that demonstrates parallel computation by distributing number summation tasks across multiple nodes. The system follows a Leader-Client-Node architecture, includes a simple consensus verification mechanism, and compares the performance of sequential vs. distributed processing.

## Prerequisites
- Java 17 or higher
- Gradle 7.4.1 or higher
- Available ports:
  - 5000 (Leader)
  - 5001-5003 (Nodes)

## Building and Running

### 1. Build the Project
```bash
gradle build
```

### 2. Start the Leader
```bash
gradle runLeader
```

### 3. Start at Least 3 Nodes
```bash
gradle runNode1
gradle runNode2
gradle runNode3
```

If a node gives an error on the first try, rerun it until it binds successfully.

### 4. Start the Client
```bash
gradle runClient
```

### 5. Run a Faulty Node (Optional)
```bash
gradle runNode -Pwrong=1
```

## Protocol Description
The system implements a distributed sum calculation protocol:

1. The Leader partitions input numbers across nodes.
2. Each Node processes its portion and returns the partial sum.
3. The Leader aggregates the results and performs a consensus check.
4. The final result and timing data are sent to the Client.

## Communication Flow
1. **Client → Leader:** List of numbers and delay time.
2. **Leader → Nodes:** Partitioned lists and delay values.
3. **Nodes → Leader:** Partial sums.
4. **Leader → Nodes (for consensus):** Peer’s list and result.
5. **Nodes → Leader:** Validation of the peer’s result.
6. **Leader → Client:** Final output and performance data.

## Workflow Details

### Client Operation
- Accepts comma-separated list of numbers and delay value.
- Sends data to the Leader.
- Displays the final sum and timing comparison.

### Leader Operation
- Performs single-threaded summation with delays.
- Distributes tasks to nodes using threads.
- Gathers partial sums and calculates the total.
- Performs a consensus check across nodes.
- Sends the final result or an error to the client.

### Node Operation
- Receives a number list and delay value.
- Computes the sum with simulated delay.
- Validates peer results during consensus.
- Optionally simulates faulty behavior with `-Pwrong=1`.

## Requirements Fulfilled

- [x] Gradle project with build.gradle setup
- [x] Leader-Client-Node architecture
- [x] Task distribution using multiple nodes
- [x] Threaded distributed processing
- [x] Fault simulation using `-Pwrong=1`
- [x] Consensus mechanism with threaded validation
- [x] Performance comparison (single vs. distributed)
- [x] Error handling (invalid input, insufficient nodes, consensus failure)
- [x] Test cases and README with run instructions
- [x] Descriptive println statements for visibility

## Debug Output (`println`)
Each component prints out important steps to visualize the computation:

- **Leader:** Sending/receiving data, summation, consensus steps, errors.
- **Node:** Receiving input, performing sum, validating results.
- **Client:** Inputs, outputs, and performance comparison.

These outputs help verify threading and message flow during execution.

## Testing Instructions

To test:

1. Run the Leader:
```bash
gradle runLeader
```

2. Start 3 Nodes:
```bash
gradle runNode1
gradle runNode2
gradle runNode3
```

3. Optionally start a faulty node:
```bash
gradle runNode -Pwrong=1
```

4. Run the Client and enter:
   - List: `[1,2,3,4,5,6,7,8,9]`
   - Delay: `50`

### Expected Results:
- ✅ Success with valid input and 3+ working nodes.
- ❌ Failure if fewer than 3 nodes.
- ❌ Consensus failure if any node is faulty.

## Demonstration
[Screencast Video](https://somup.com/cTiVVaLHAT)

## Performance Analysis

The system demonstrates that:
- **Distributed calculation** improves performance with larger datasets and higher delays due to parallelism.
- **Sequential calculation** is slower for large inputs with delay per operation.
- **Threaded communication** enables efficient distributed execution.

Tests with varying delay values (e.g., 10ms vs 100ms) show clearer performance advantages for distributed processing at higher delays.

## Error Handling
Robust error handling is included:
- Leader shuts down with <3 connected nodes.
- Faulty nodes are detected in consensus.
- Communication and parsing errors are gracefully managed.
- Invalid client inputs are handled with user-friendly messages.