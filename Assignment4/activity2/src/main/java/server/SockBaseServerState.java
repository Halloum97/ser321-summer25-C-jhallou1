package server;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import client.Player;

public class SockBaseServerState {
    public static final Map<String, Player> leaderboard = new ConcurrentHashMap<>();
}
