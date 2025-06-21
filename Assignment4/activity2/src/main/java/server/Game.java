package server;

import java.util.*; 
import java.io.*;

/**
 * Class: Game 
 * Description: Game class that can load a phrase
 * Class can be used to hold the persistent state for a game for different threads
 * synchronization is not taken care of .
 * You can change this Class in any way you like or decide to not use it at all
 * I used this class in my SockBaseServer to create a new game and keep track of the current image evenon differnt threads. 
 * My threads each get a reference to this Game
 */

public class Game {
    private int length;
    private char[] originalPhrase;
    private char[] hiddenPhrase;
    private List<String> phrases = new ArrayList<>();
    private Set<Character> guessedLetters = new HashSet<>();
    private String currentTask;
    private int points;
    private final int MAX_POINTS = 10;

    public Game() {
        currentTask = "";
        length = 0;
        loadPhrases("phrases.txt");
    }

    public void newGame() {
        currentTask = "Guess a letter";
        getRandomPhrase();
        guessedLetters.clear();
        points = MAX_POINTS;
    }

    public void loadPhrases(String filename) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                phrases.add(line);
                System.out.println("Added Phrase: " + line);
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("File load error");
        }
    }


    private void getRandomPhrase() {
        try {
            Random rand = new Random();
            int randIndex = rand.nextInt(phrases.size());
            String phrase = phrases.get(randIndex);
            System.out.println("Starting new game with phrase: " + phrase);

            length = phrase.length();
            originalPhrase = phrase.toCharArray();
            hiddenPhrase = new char[length];

            for (int i = 0; i < length; i++) {
                hiddenPhrase[i] = (originalPhrase[i] == ' ') ? ' ' : '_';
            }
        } catch (Exception e) {
            System.out.println("Error generating random phrase");
            System.exit(1);
        }
    }

    /**
     * Process a guess.
     * Returns true if a new letter was revealed, false otherwise.
     * Deducts 1 point for incorrect or repeated guesses.
     */
    public boolean processGuess(char guess) {
        guess = Character.toLowerCase(guess);
        boolean correct = false;

        if (guessedLetters.contains(guess)) {
            points--; // duplicate guess penalty
            return false;
        }

        guessedLetters.add(guess);

        for (int i = 0; i < length; i++) {
            if (Character.toLowerCase(originalPhrase[i]) == guess) {
                hiddenPhrase[i] = originalPhrase[i];
                correct = true;
            }
        }

        if (!correct) {
            points--; // incorrect guess penalty
        }

        return correct;
    }

    public String getPhrase() {
        return String.valueOf(hiddenPhrase);
    }

    public String getTask() {
        return currentTask;
    }

    public boolean isWon() {
        return Arrays.equals(originalPhrase, hiddenPhrase);
    }

    public boolean isLost() {
        return points <= 0;
    }

    public int getPoints() {
        return points;
    }

    public String getAnswer() {
        return String.valueOf(originalPhrase);
    }

    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }
}
