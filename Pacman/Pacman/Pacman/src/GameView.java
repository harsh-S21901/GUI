import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    static JTable gameBoard;
    private static JLabel scoreLabel;
    private static JLabel lifeLabel;
    private static int highScore = 0;
    final boolean[] movingUp = {false};
    final boolean[] movingDown = {false};
    final boolean[] movingLeft = {false};
    final boolean[] movingRight = {false};
    JPanel gamePanel = new JPanel(new BorderLayout()) {
        @Override
        public boolean isFocusable() {
            return true;
        }
    };

    public GameView() {

        gameBoard.setRowHeight(20);
        gameBoard.getColumnModel().getColumns().asIterator().forEachRemaining(x -> x.setPreferredWidth(20));
        gameBoard.setBackground(Color.black);
        gameBoard.setShowGrid(false);
        gameBoard.setDefaultRenderer(Object.class, new GameModel.CustomCellRenderer(GameModel.tableModel));

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 15));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        lifeLabel = new JLabel("3");
        ImageIcon heartIcon = new ImageIcon("src/assets/heart.png");
        lifeLabel.setIcon(heartIcon);
        lifeLabel.setFont(new Font("Arial", Font.BOLD, 15));
        lifeLabel.setForeground(Color.white);
        lifeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel highScoreLabel = new JLabel("High Score: " + getHighScore());
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 15));
        highScoreLabel.setForeground(Color.WHITE);
        highScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gamePanel.add(gameBoard, BorderLayout.CENTER);

        JPanel counterPanel = new JPanel(new GridLayout(1, 3));
        counterPanel.add(scoreLabel);
        counterPanel.add(lifeLabel);
        counterPanel.add(highScoreLabel);
        counterPanel.setPreferredSize(new Dimension(50, 50));

        counterPanel.setBackground(Color.BLACK);
        gamePanel.add(counterPanel, BorderLayout.SOUTH);

        getContentPane().add(gamePanel);
        pack();
        gamePanel.requestFocusInWindow();

        Thread movementThread = new Thread(() -> {
            while (true) {
                if (movingUp[0]) {
                    GameController.movePacman(-1, 0);
                } else if (movingDown[0]) {
                    GameController.movePacman(1, 0);
                } else if (movingLeft[0]) {
                    GameController.movePacman(0, -1);
                } else if (movingRight[0]) {
                    GameController.movePacman(0, 1);
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        movementThread.start();
        Thread ghostMovementThread = new Thread(() -> {
            long lastUpgradeDropTime = System.currentTimeMillis();
            while (true) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastUpgradeDropTime >= 5000) {
                    if (GameController.shouldDropUpgrade()) {
                        GameModel.dropUpgrade();
                        lastUpgradeDropTime = currentTime;
                    }
                }
                for (Ghost ghost : GameModel.ghosts) {
                    GameController.moveGhost(ghost);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        ghostMovementThread.start();
        updateScore(GameModel.score);
        updateLives(GameModel.lives);
    }


    static void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    static void updateLives(int lives) {
        lifeLabel.setText(String.valueOf(lives));
    }

    static int getHighScore() {
        if (HighScoreEntry.loadHighScore().size() == 0) {
            highScore = 0;
        } else {
            highScore = HighScoreEntry.loadHighScore().get(0).getScore();
        }
        return highScore;
    }

}
