# gRPC Distributed System - Spring 2025

## 👇 Project Description

This project implements a distributed client-server system using gRPC in Java. It builds on the starter code provided and adds the following services:

### Implemented Services:
1. **Echo Service** – Repeats a message sent by the client.
2. **Joke Service** – Sends jokes, allows new ones to be added.
3. **Dice Service** – Simulates rolling single or multiple types of dice.
4. **Caesar Password Service** – Lets users store and retrieve encrypted passwords by name.
5. **Todo Service (Custom)** – A custom service that allows users to add, view, and remove to-do tasks.

This project fulfills all of the requirements for **Task 1**, **Task 2**, and supports testing through unit tests.

---

## ▶️ How to Run the Project (Without Registry)

### Run the Server (Node)
```bash
gradle runNode
```

This runs the Node on the default port `9099`.

### Run the Client (manually interact)
```bash
gradle runClient
```

Or specify host/port (if needed):
```bash
gradle runClient -Phost=localhost -Pport=9099
```

### Run Unit Tests
```bash
gradle test
```

---

## 🧑‍💻 How to Work With the Program

After running the server and client:

1. The client will:
   - Ask how many jokes you'd like
   - Add a sample joke
   - Ask if you want to use the Dice Service (type "yes")
   - Ask if you want to use the Password Service (type "yes")
   - Ask if you want to use the Todo Service (type "yes")

2. Depending on your choices, you'll be prompted to:
   - Enter input values (e.g. number of dice, password name, task description)
   - See responses from the server (e.g. dice rolls, decrypted password, list of tasks)

---

## ✅ Requirements Fulfilled

- [x] `gradle runNode` and `gradle runClient` start the services correctly
- [x] Implemented **two gRPC services** from the provided `.proto` files (`Dice` and `Caesar`)
- [x] Created a **custom gRPC service** (`Todo`) with:
  - Multiple requests
  - Required inputs
  - Repeated response field
  - Persistent server-side data
- [x] User-friendly terminal interaction
- [x] Robust error handling
- [x] Implemented **unit tests** to simulate both success and failure cases

---

## Screen cast: [https://somup.com/cTilf6N1Ik]


## 🌐 Task 3 – Node Online

### Services Implemented:
- Echo
- Joke
- Dice
- Caesar (Password)
- Todo (Custom)

### Client Command to Connect:
```bash
gradle runClient -Phost=113.016.38.20 -Pport=9099
```

