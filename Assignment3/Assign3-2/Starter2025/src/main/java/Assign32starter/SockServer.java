package Assign32starter;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;

public class SockServer {
    private enum State { MENU, PLAYING, ADD_RIDDLE, ADD_ANSWER, VOTE }

    private static class Player {
        String name;
        int score = 0;
        State state = State.MENU;
        Riddle current;
        String tempRiddle;
        Riddle tempVote;

        Player(String name) { this.name = name; }
    }

    private static final List<Riddle> active = new ArrayList<>();
    private static final Stack<Riddle> candidates = new Stack<>();
    private static final Map<String, Player> sessions = new HashMap<>();
    private static final Map<String, Integer> leaderboard = new LinkedHashMap<>();

    public static void main(String[] args) {
        try {
            // Base riddles
            active.addAll(Arrays.asList(
                new Riddle("I dry as I get wetter.",          "Towel"),
                new Riddle("The building that has the most stories.","Library"),
                new Riddle("The pot called me black... Then, I made some tea.","Kettle"),
                new Riddle("Seeing double? Check me to spot your doppelganger.","Mirror"),
                new Riddle("I have eyes but cannot see.",      "Potatoe")
            ));

            ServerSocket serv = new ServerSocket(8888);
            System.out.println("Server ready");

            while (true) {
                try (Socket sock = serv.accept();
                     ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
                     PrintWriter out = new PrintWriter(sock.getOutputStream(), true))
                {
                    String s = (String) in.readObject();
                    JSONObject req  = new JSONObject(s);
                    JSONObject resp = new JSONObject();

                    String type = req.optString("type", "");
                    String name = req.optString("name", "");

                    switch (type) {
                      case "start":
                        resp.put("type","hello")
                            .put("value","Hello! Please tell me your name.");
                        break;

                      case "name":
                        Player p = sessions.computeIfAbsent(name, Player::new);
                        p.score = 0;
                        p.state = State.MENU;
                        resp.put("type","menu")
                            .put("message","Welcome, "+name+"! Please choose:")
                            .put("options", new JSONArray(Arrays.asList(
                              "1. Play Game",
                              "2. View Leaderboard",
                              "3. Add Riddle",
                              "4. Vote Riddle",
                              "5. Quit"
                        )));
                        break;

                      case "menuSelection":
                        p = sessions.get(name);
                        handleMenuSelection(req.getString("selection"), p, resp);
                        break;

                      case "guess":
                        p = sessions.get(name);
                        if (p.state == State.PLAYING) handleGuess(req.getString("guess"), p, resp);
                        break;

                      case "next":
                        p = sessions.get(name);
                        if (p.state == State.PLAYING) handleNext(p, resp);
                        break;

                      case "exitGame":
                        p = sessions.get(name);
                        handleExit(p, resp);
                        break;

                      case "addRiddle":
                        p = sessions.get(name);
                        if (p.state == State.ADD_RIDDLE) {
                          p.tempRiddle = req.getString("riddle");
                          p.state      = State.ADD_ANSWER;
                          resp.put("type","addAnswerPrompt")
                              .put("message","Enter the answer for the riddle:");
                        }
                        break;

                      case "addAnswer":
                        p = sessions.get(name);
                        if (p.state == State.ADD_ANSWER) {
                          candidates.push(new Riddle(p.tempRiddle, req.getString("answer")));
                          p.state = State.MENU;
                          resp.put("type","message")
                              .put("message","Riddle added for voting.")
                              .put("options", new JSONArray(Arrays.asList(
                                "1. Play Game",
                                "2. View Leaderboard",
                                "3. Add Riddle",
                                "4. Vote Riddle",
                                "5. Quit"
                          )));
                        }
                        break;

                      case "vote":
                        p = sessions.get(name);
                        if (p.state == State.VOTE) {
                          if (req.getString("vote").equalsIgnoreCase("yes"))
                            active.add(p.tempVote);
                          p.tempVote = null;
                          p.state    = State.MENU;
                          resp.put("type","message")
                              .put("message","Thank you. Returning to menu.")
                              .put("options", new JSONArray(Arrays.asList(
                                "1. Play Game",
                                "2. View Leaderboard",
                                "3. Add Riddle",
                                "4. Vote Riddle",
                                "5. Quit"
                          )));
                        }
                        break;

                      default:
                        resp.put("type","error")
                            .put("message","Unknown request type.");
                    }

                    out.println(resp.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Helpers for server-side logic */
    private static void handleMenuSelection(String sel, Player p, JSONObject resp) {
        switch (sel) {
          case "1":
            p.state   = State.PLAYING;
            Riddle r  = active.get(new Random().nextInt(active.size()));
            p.current = r;
            resp.put("type","riddle")
                .put("message","Here is your riddle:")
                .put("riddle", r.getRiddle());
            break;

          case "2":
            resp.put("type","leaderboard");
            JSONArray arr = new JSONArray();
            leaderboard.forEach((n,s) -> {
              arr.put(new JSONObject().put("name",n).put("score",s));
            });
            resp.put("board", arr);
            break;

          case "3":
            p.state = State.ADD_RIDDLE;
            resp.put("type","addRiddlePrompt")
                .put("message","Enter the new riddle:");
            break;

          case "4":
            if (candidates.isEmpty()) {
              resp.put("type","message")
                  .put("message","No riddles to vote on.");
            } else {
              p.state    = State.VOTE;
              p.tempVote = candidates.pop();
              resp.put("type","votePrompt")
                  .put("message","Approve this riddle? yes/no")
                  .put("riddle", p.tempVote.getRiddle());
            }
            break;

          case "5":
            resp.put("type","quit")
                .put("message","Goodbye!");
            break;

          default:
            resp.put("type","error")
                .put("message","Invalid selection.");
        }
    }

    private static void handleGuess(String guess, Player p, JSONObject resp) {
        if (guess.equalsIgnoreCase(p.current.getAnswer())) {
            p.score += 10;
            resp.put("type","correct")
                .put("message","Correct! +10 points.")
                .put("riddle", active.get(new Random().nextInt(active.size())).getRiddle());
        } else {
            p.score -= 1;
            resp.put("type","incorrect")
                .put("message","Incorrect. -1 point. Try again.");
        }
    }

    private static void handleNext(Player p, JSONObject resp) {
        p.score -= 5;
        Riddle r = active.get(new Random().nextInt(active.size()));
        p.current = r;
        resp.put("type","riddle")
            .put("message","Here is your next riddle:")
            .put("riddle", r.getRiddle());
    }

    private static void handleExit(Player p, JSONObject resp) {
        p.state = State.MENU;
        leaderboard.put(
          p.name,
          Math.max(leaderboard.getOrDefault(p.name,0), p.score)
        );
        resp.put("type","end")
            .put("message","Game over. Your score: "+p.score)
            .put("options", new JSONArray(Arrays.asList(
              "1. Play Game",
              "2. View Leaderboard",
              "3. Add Riddle",
              "4. Vote Riddle",
              "5. Quit"
        )));
    }
}
