import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighScoreEntry implements Serializable {
    private static final String HIGH_SCORE_PERSISTENCE_PATH = "src/Data/highScore.txt";
    private String playerName;
    private int score;
    private static List<HighScoreEntry> highScores = new ArrayList<>();

    public HighScoreEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public List<HighScoreEntry> getHighScores() {
        return highScores;
    }

    public static HighScoreEntry getHighScore(){
        return highScores.get(0);
    }

    public void addHighScore(HighScoreEntry entry) {
        highScores.clear();
        highScores.add(entry);
        sortHighScores();
        saveHighScore();
    }

    public static void sortHighScores() {
        highScores.sort((entry1, entry2) -> entry2.getScore() - entry1.getScore());
    }

    public static void saveHighScore() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(HIGH_SCORE_PERSISTENCE_PATH, true))) {
            for(HighScoreEntry he : highScores) {
                bufferedWriter.write(he.getPlayerName() + "," + he.getScore());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<HighScoreEntry> loadHighScore() {
        List<HighScoreEntry> loadedHighScores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORE_PERSISTENCE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null){
                    String[] parts = line.split(",");
                    HighScoreEntry highScoreEntry = new HighScoreEntry(parts[0], Integer.parseInt(parts[1]));
                    loadedHighScores.add(highScoreEntry);
                }
        }  catch (IOException | ArrayIndexOutOfBoundsException e) {
            return null;
        }
        loadedHighScores.sort(Comparator.comparingInt(HighScoreEntry::getScore).reversed());
        return loadedHighScores;
    }
}
