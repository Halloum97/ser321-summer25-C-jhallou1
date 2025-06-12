package Assign32starter;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JDialog;
import javax.swing.WindowConstants;
import org.json.JSONArray;
import org.json.JSONObject;

public class ClientGui implements OutputPanel.EventHandlers {
    private enum State {
        ENTER_NAME, MENU, PLAYING, ADD_RIDDLE, ADD_ANSWER, VOTE
    }

    private final String host;
    private final int port;
    private State state;
    private String playerName;

    // UI components
    private final JDialog frame;
    private final PicturePanel picPanel;
    private final OutputPanel outputPanel;

    public ClientGui(String host, int port) throws IOException {
        this.host = host;
        this.port = port;

        // —— BUILD UI ——
        frame = new JDialog();
        frame.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        picPanel = new PicturePanel();
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0; c.weighty = 0.25;
        frame.add(picPanel, c);

        c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 1; c.weightx = 1; c.weighty = 0.75;
        c.fill = GridBagConstraints.BOTH;
        outputPanel = new OutputPanel();
        outputPanel.addEventHandlers(this);
        frame.add(outputPanel, c);

        // —— INITIAL HANDSHAKE ——
        JSONObject startReq = new JSONObject().put("type", "start");
        JSONObject startResp = sendRequest(startReq);
        outputPanel.appendOutput(startResp.getString("value"));

        state = State.ENTER_NAME;
    }

    /** Show the GUI. */
    public void show(boolean modal) {
        frame.pack();
        frame.setModal(modal);
        frame.setVisible(true);
    }

    /** Fired when user clicks Submit. */
    @Override
    public void submitClicked() {
        try {
            String input = outputPanel.getInputText().trim();
            if (input.isEmpty()) return;

            JSONObject req = new JSONObject();
            switch (state) {
                case ENTER_NAME:
                    playerName = input;
                    req.put("type", "name").put("name", playerName);
                    state = State.MENU;
                    break;

                case MENU:
                    req.put("type", "menuSelection")
                       .put("name", playerName)
                       .put("selection", input);
                    break;

                case PLAYING:
                    if (input.equalsIgnoreCase("exit")) {
                        req.put("type", "exitGame").put("name", playerName);
                        state = State.MENU;
                    } else if (input.equalsIgnoreCase("next")) {
                        req.put("type", "next").put("name", playerName);
                    } else {
                        req.put("type", "guess")
                           .put("name", playerName)
                           .put("guess", input);
                    }
                    break;

                case ADD_RIDDLE:
                    req.put("type", "addRiddle")
                       .put("name", playerName)
                       .put("riddle", input);
                    state = State.ADD_ANSWER;
                    break;

                case ADD_ANSWER:
                    req.put("type", "addAnswer")
                       .put("name", playerName)
                       .put("answer", input);
                    state = State.MENU;
                    break;

                case VOTE:
                    req.put("type", "vote")
                       .put("name", playerName)
                       .put("vote", input);
                    state = State.MENU;
                    break;
            }

            JSONObject resp = sendRequest(req);
            handleResponse(resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** No-op for live input updates. */
    @Override
    public void inputUpdated(String input) { }

    /**
     * Opens a socket, sends a single JSON request, reads one JSON line response,
     * then closes everything and returns the parsed JSON.
     */
    private JSONObject sendRequest(JSONObject req) throws IOException {
        try (Socket sock = new Socket(host, port);
             ObjectOutputStream os = new ObjectOutputStream(sock.getOutputStream());
             BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream())))
        {
            os.writeObject(req.toString());
            os.flush();
            String line = reader.readLine();
            return new JSONObject(line);
        }
    }

    /** Update UI & state based on server’s JSON reply. */
    private void handleResponse(JSONObject resp) {
        String type = resp.optString("type", "");

        if (resp.has("message")) {
            outputPanel.appendOutput(resp.getString("message"));
        }
        if (resp.has("riddle")) {
            outputPanel.appendOutput("Riddle: " + resp.getString("riddle"));
            state = State.PLAYING;
        }
        if (resp.has("options")) {
            JSONArray opts = resp.getJSONArray("options");
            for (int i = 0; i < opts.length(); i++) {
                outputPanel.appendOutput(opts.getString(i));
            }
        }
        if (type.equals("leaderboard") && resp.has("board")) {
            JSONArray board = resp.getJSONArray("board");
            for (int i = 0; i < board.length(); i++) {
                JSONObject entry = board.getJSONObject(i);
                outputPanel.appendOutput(
                  entry.getString("name") + ": " + entry.getInt("score")
                );
            }
        }
        if (type.equals("addRiddlePrompt"))  state = State.ADD_RIDDLE;
        if (type.equals("addAnswerPrompt"))  state = State.ADD_ANSWER;
        if (type.equals("votePrompt"))       state = State.VOTE;
        if (type.equals("end"))              state = State.MENU;
    }

    public static void main(String[] args) throws IOException {
        ClientGui gui = new ClientGui("localhost", 8888);
        gui.show(true);
    }
}
