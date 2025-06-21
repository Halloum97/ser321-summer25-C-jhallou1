## Activity 2 - Threads and Protobuf

### Overview

This activity implements a simplified "Wheel of Fortune" game using Java sockets and Protobuf for structured communication between client and server. Multiple clients can play simultaneously, with individual game states and a shared leaderboard.

### Features Implemented

* `NAME` request and `WELCOME` response
* Main menu: Leaderboard, Play Game, Quit
* Phrase guessing game with scoring rules
* Logging user connections and wins
* Persistent, thread-safe leaderboard
* Support for concurrent clients

### How to Run

All commands assume you are inside the `activity2/` directory.

```bash
# Run the server
gradle runServer -Pport=9099

# Run the client
gradle runClient -Phost=localhost -Pport=9099
```

### Protocol Support Summary (Requirements Fulfilled)

1. Protobuf protocol followed ✔
2. Client menu implemented ✔
3. NAME request, WELCOME response ✔
4. LEADERBOARD support ✔
5. Shared, thread-safe leaderboard ✔
6. Random phrases ✔
7. Concurrent clients ✔
8. Task, WON, LOST, ERROR responses ✔
9. 10-point system ✔
10. Logging (connect, win) ✔
11. Client exits gracefully ✔
12. Phrase shown at game end ✔
13. Server handles disconnects ✔
14. AWS-hosted server tested (manually) ✔
15. Client tested with 2+ other servers ✔

### Screencast

\[https://somup.com/cT1revLwSY]

### Notes

* Ensure `phrases.txt` and `logs.txt` are in `/resources` folder.
* Server prints the actual phrase for grading convenience.
* Protobuf `.proto` files are located in `proto/` folder.

