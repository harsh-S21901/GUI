import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

public class StartWindow extends JFrame implements ActionListener {

    private final JButton newGameButton;
    private final JButton highScoreButton;
    private final JButton exitButton;
    private final JTextField rowSizeField;
    private final JTextField colSizeField;
    private static int highScore;

    public static int getHighScore(){
        return highScore;
    }

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            if(rowSizeField.getText().isBlank() || colSizeField.getText().isBlank()){
                JOptionPane.showMessageDialog(this, "Invalid board size! Please enter a number between 10 and 100.");
            }
            int row = Integer.parseInt(rowSizeField.getText());
            int col = Integer.parseInt(colSizeField.getText());
            if (row >= 10 && row <= 100 || col >= 10 && col <= 100) {
                // start new game with board size
                dispose();
                new PacmanGame(row, col);
            }
            else {
                JOptionPane.showMessageDialog(this, "Invalid board size! Please enter a number between 10 and 100.");
            }
        } else if (e.getSource() == highScoreButton) {
            try {
                // read high scores from file using Java serialization
                FileInputStream fileIn = new FileInputStream("highscores.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                ArrayList<Integer> highScores = (ArrayList<Integer>) in.readObject();
                in.close();
                fileIn.close();
                highScores.sort(Collections.reverseOrder());
                highScore = highScores.get(0);
                StringBuilder message = new StringBuilder("Highest Scores:\n");
                int count = 1;
                for (int score : highScores) {
                    message.append(count).append(". ").append(score).append("\n");
                    count++;
                }
                JOptionPane.showMessageDialog(this, message.toString());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "No high scores yet!");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}
