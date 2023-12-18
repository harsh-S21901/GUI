import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    public static HighScoreEntry highScoreEntry = new HighScoreEntry("", 0);
    static GameModel model;
    static GameView view;

    public GameController(GameModel model, GameView view) {
        GameController.model = model;
        GameController.view = view;
        StartWindow startWindow = new StartWindow();
        view.gamePanel.addKeyListener(new GameKeyListener());
        GameModel.tableModel.startAnimation(100);
    }

    public static HighScoreEntry getHighScoreEntry() {
        return highScoreEntry;
    }

    static void moveGhost(Ghost ghost) {
        int row = ghost.getRow();
        int col = ghost.getCol();
        int pacmanRow = GameModel.tableModel.pacmanRow;
        int pacmanCol = GameModel.tableModel.pacmanCol;

        List<int[]> neighbors = new ArrayList<>();
        if (row > 0 && !GameModel.tableModel.isObstacle(row - 1, col)) {
            neighbors.add(new int[]{row - 1, col}); // Up
        }
        if (row < GameModel.tableModel.getRowCount() - 1 && !GameModel.tableModel.isObstacle(row + 1, col)) {
            neighbors.add(new int[]{row + 1, col}); // Down
        }
        if (col > 0 && !GameModel.tableModel.isObstacle(row, col - 1)) {
            neighbors.add(new int[]{row, col - 1}); // Left
        }
        if (col < GameModel.tableModel.getColumnCount() - 1 && !GameModel.tableModel.isObstacle(row, col + 1)) {
            neighbors.add(new int[]{row, col + 1}); // Right
        }

        int minDistance = Integer.MAX_VALUE;
        int[] nextCell = null;
        //Chasing pacman
        for (int[] neighbor : neighbors) {
            int neighborRow = neighbor[0];
            int neighborCol = neighbor[1];
            int distance = Math.abs(pacmanRow - neighborRow) + Math.abs(pacmanCol - neighborCol);
            if (distance < minDistance) {
                minDistance = distance;
                nextCell = neighbor;
            }
        }

        if (nextCell != null) {
            int nextRow = nextCell[0];
            int nextCol = nextCell[1];
            GameModel.tableModel.clearGhost(row, col);
            GameModel.tableModel.setGhost(nextRow, nextCol, ghost);
            ghost.setRow(nextRow);
            ghost.setCol(nextCol);

            if (nextRow == pacmanRow && nextCol == pacmanCol) {
                handleCollision();
                GameModel.tableModel.clearGhost(ghost.getRow(), ghost.getCol());
            }
            GameModel.tableModel.fireTableDataChanged();
            GameView.gameBoard.repaint();
        }
    }

    static boolean shouldDropUpgrade() {
        double dropProbability = 0.25;
        double randomValue = Math.random();
        return randomValue < dropProbability;
    }

    static void movePacman(int rowOffset, int colOffset) {
        int newRow = GameModel.tableModel.pacmanRow + rowOffset;
        int newCol = GameModel.tableModel.pacmanCol + colOffset;

        if (isValidMove(newRow, newCol)) {
            if (GameModel.tableModel.isPellet(newRow, newCol) && !GameModel.tableModel.isPelletEaten(newRow, newCol)) {
                GameModel.tableModel.setPelletEaten(newRow, newCol);
                GameModel.tableModel.increaseScore(10);
                GameView.updateScore(GameModel.tableModel.getScore());
            }
            if (GameModel.tableModel.isUpgrade(newRow, newCol)) {
                GameModel.lives++;
                GameView.updateLives(GameModel.lives);
            }
            GameModel.tableModel.setValueAt("", GameModel.tableModel.pacmanRow, GameModel.tableModel.pacmanCol);
            GameModel.tableModel.setValueAt("P", newRow, newCol);
            GameModel.tableModel.pacmanRow = newRow;
            GameModel.tableModel.pacmanCol = newCol;
            if (GameModel.tableModel.isObstacle(newRow, newCol)) {
                handleCollision();
            }
            GameModel.tableModel.fireTableDataChanged();
            GameView.gameBoard.repaint();
        }
    }

    private static boolean isValidMove(int row, int col) {
        return row >= 0 && row < GameModel.tableModel.getRowCount() &&
                col >= 0 && col < GameModel.tableModel.getColumnCount() &&
                !GameModel.tableModel.isObstacle(row, col);
    }

    static void handleCollision() {
        GameModel.lives--;
        GameView.updateLives(GameModel.lives);
        GameModel.ghosts[0] = new Ghost(1, 1, GhostIcon.CYAN_GHOST);
        GameModel.ghosts[1] = new Ghost(GameModel.tableModel.numRows - 2, GameModel.tableModel.numRows - 2, GhostIcon.RED_GHOST);
        GameModel.ghosts[2] = new Ghost(1, GameModel.tableModel.numRows - 2, GhostIcon.SHADY_RED_GHOST);
        GameModel.ghosts[3] = new Ghost(GameModel.tableModel.numRows - 2, 1, GhostIcon.YELLOW_RED_GHOST);
        if (GameModel.lives == 0) {
            JOptionPane.showMessageDialog(null, "Game Over! Your score: " + GameModel.tableModel.getScore());
            String playerName = JOptionPane.showInputDialog(null, "Enter your name:");
            if (playerName != null && !playerName.isEmpty()) {
                HighScoreEntry entry = new HighScoreEntry(playerName, GameModel.tableModel.getScore());
                highScoreEntry.addHighScore(entry);
            }
            resetGame();
        }
    }

    static void resetGame() {
        view.setVisible(false);
        GameModel gameModel = new GameModel(GameModel.tableModel.numRows, GameModel.tableModel.numCols);
        GameView gameView = new GameView();
        GameController gameController = new GameController(gameModel, gameView);
        GameModel.lives = 3;
    }

    static class GameKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_UP) {
                view.movingUp[0] = true;
            } else if (keyCode == KeyEvent.VK_DOWN) {
                view.movingDown[0] = true;
            } else if (keyCode == KeyEvent.VK_LEFT) {
                view.movingLeft[0] = true;
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                view.movingRight[0] = true;
            } else if (e.isControlDown() && e.isShiftDown() && keyCode == KeyEvent.VK_Q) {
                view.setVisible(false);
                StartWindow startWindow = new StartWindow();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_UP) {
                view.movingUp[0] = false;
            } else if (keyCode == KeyEvent.VK_DOWN) {
                view.movingDown[0] = false;
            } else if (keyCode == KeyEvent.VK_LEFT) {
                view.movingLeft[0] = false;
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                view.movingRight[0] = false;
            }
        }
    }
}