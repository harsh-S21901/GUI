import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StartWindow extends JFrame implements ActionListener {

    private static int highScore;
    private final JButton newGameButton;
    private final JButton highScoreButton;
    private final JButton exitButton;
    private final JTextField rowSizeField;
    private final JTextField colSizeField;

    public StartWindow() {
        setTitle("Pacman Game");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JLabel title = new JLabel("Welcome to Pacman Game!");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel rowSizeLabel = new JLabel("Enter board rows (10-100): ");
        rowSizeLabel.setForeground(Color.WHITE);
        rowSizeField = new JTextField(10);

        JLabel colSizeLabel = new JLabel("Enter board columns (10-100): ");
        colSizeLabel.setForeground(Color.WHITE);
        colSizeField = new JTextField(10);

        newGameButton = new JButton("New Game");
        newGameButton.setBackground(Color.GRAY);
        newGameButton.setForeground(Color.WHITE);
        newGameButton.addActionListener(this);

        highScoreButton = new JButton("High Scores");
        highScoreButton.setBackground(Color.darkGray);
        highScoreButton.setForeground(Color.WHITE);
        highScoreButton.addActionListener(this);

        exitButton = new JButton("Exit");
        exitButton.setBackground(Color.BLACK);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBackground(Color.DARK_GRAY);
        panel.add(title);
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(Color.DARK_GRAY);
        inputPanel.add(rowSizeLabel);
        inputPanel.add(rowSizeField);
        inputPanel.add(colSizeLabel);
        inputPanel.add(colSizeField);
        panel.add(inputPanel);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.add(newGameButton);
        buttonPanel.add(highScoreButton);
        buttonPanel.add(exitButton);
        panel.add(buttonPanel);

        add(panel);
        setVisible(true);
    }

    public static int getHighScore() {
        return highScore;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            if (rowSizeField.getText().isBlank() || colSizeField.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Invalid board size! Please enter a number between 10 and 100.");
            }
            int row = Integer.parseInt(rowSizeField.getText());
            int col = Integer.parseInt(colSizeField.getText());
            if (row >= 10 && row <= 100 || col >= 10 && col <= 100) {
                dispose();
                GameModel gameModel = new GameModel(row, col);
                GameView gameView = new GameView();
                GameController gameController = new GameController(gameModel, gameView);
                gameView.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(this, "Invalid board size! Please enter a number between 10 and 100.");
            }
        } else if (e.getSource() == highScoreButton) {
            List<HighScoreEntry> highScores = HighScoreEntry.loadHighScore();
            if (highScores == null) {
                JOptionPane.showMessageDialog(this, "No High Scores Yet!!", "High Scores", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            HighScoreUI highScoreUI = new HighScoreUI();
            highScoreUI.updateHighScores(highScores);
            JOptionPane.showMessageDialog(this, HighScoreUI.getHighScoresScrollPane(), "High Scores", JOptionPane.PLAIN_MESSAGE);
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}
