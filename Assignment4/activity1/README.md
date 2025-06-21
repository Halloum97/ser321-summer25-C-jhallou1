## Activity 1 - Threads

### Overview

This activity demonstrates how to evolve a single-threaded server into two multi-threaded versions using Java sockets. The client can send text-based commands to manipulate a shared string list using the defined protocol.

### Tasks Completed

* **Task 1:** Enhanced `Performer.java` to handle ADD, DISPLAY, SEARCH, REVERSE commands.
* **Task 2:** Created `ThreadedServer.java` to support unbounded client connections using individual threads.
* **Task 3:** Created `ThreadPoolServer.java` to support a fixed number of concurrent clients using a thread pool.

### How to Run

All commands assume you are inside the `activity1/` directory.

```bash
# Run the basic single-threaded server
gradle runTask1

# Run the unbounded multi-threaded server
gradle runTask2

# Run the thread-pool server (default 5 threads)
gradle runTask3 -Ppool=5

# Run the client
gradle runClient
```

### Protocol Commands (from client input)

* `ADD <text>` – Adds a string
* `DISPLAY` – Displays the list
* `SEARCH <term>` – Searches for substring
* `REVERSE` – Reverses the list
* `QUIT` – Ends connection

### Screencast

\[https://somup.com/cT1rffLwW0]

