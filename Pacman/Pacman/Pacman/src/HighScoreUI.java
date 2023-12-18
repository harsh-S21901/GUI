import javax.swing.*;
import java.util.List;

public class HighScoreUI {
    private static HighScoreEntry highScoreEntry;
    private static JList<String> highScoresList;
    private static DefaultListModel<String> listModel;

    public HighScoreUI() {
        listModel = new DefaultListModel<>();
        highScoresList = new JList<>(listModel);
        highScoresList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        highScoresList.setVisibleRowCount(10);
    }

    public static JScrollPane getHighScoresScrollPane() {
        return new JScrollPane(highScoresList);
    }

    public void updateHighScores(List<HighScoreEntry> highScores) {
        listModel.clear();  // Clear the existing entries
        int count = 1;
        for (HighScoreEntry entry : highScores) {
            String scoreEntry = count + ". " + entry.getPlayerName() + ": " + entry.getScore();
            listModel.addElement(scoreEntry);
            count++;
        }
    }

}
