import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameModel {
    public static Ghost[] ghosts;
    static int lives = 3;
    static int score = 0;
    static CustomTableModel tableModel;

    public GameModel(int numRows, int numCols) {
        boolean[][] maze = generateMaze(numRows, numCols);
        tableModel = new CustomTableModel(numRows, numCols, maze);
        GameView.gameBoard = new JTable(tableModel);
        ghosts = new Ghost[4];
        List<Upgrade> upgrades = new ArrayList<>();

        ghosts[0] = new Ghost(1, 1, GhostIcon.CYAN_GHOST);
        ghosts[1] = new Ghost(numRows - 2, numCols - 2, GhostIcon.RED_GHOST);
        ghosts[2] = new Ghost(1, numCols - 2, GhostIcon.SHADY_RED_GHOST);
        ghosts[3] = new Ghost(numRows - 2, 1, GhostIcon.YELLOW_RED_GHOST);
    }

    static void moveGhost(Ghost ghost) {
        int row = ghost.getRow();
        int col = ghost.getCol();

        List<int[]> emptyNeighbors = new ArrayList<>();
        if (row > 0 && !tableModel.isObstacle(row - 1, col)) {
            emptyNeighbors.add(new int[]{row - 1, col}); // Up
        }
        if (row < tableModel.getRowCount() - 1 && !tableModel.isObstacle(row + 1, col)) {
            emptyNeighbors.add(new int[]{row + 1, col}); // Down
        }
        if (col > 0 && !tableModel.isObstacle(row, col - 1)) {
            emptyNeighbors.add(new int[]{row, col - 1}); // Left
        }
        if (col < tableModel.getColumnCount() - 1 && !tableModel.isObstacle(row, col + 1)) {
            emptyNeighbors.add(new int[]{row, col + 1}); // Right
        }
        Collections.shuffle(emptyNeighbors);

        if (!emptyNeighbors.isEmpty()) {
            int[] nextCell = emptyNeighbors.get(0);
            int nextRow = nextCell[0];
            int nextCol = nextCell[1];

            tableModel.clearGhost(row, col);
            tableModel.setGhost(nextRow, nextCol, ghost);
            ghost.setRow(nextRow);
            ghost.setCol(nextCol);

            if (nextRow == tableModel.pacmanRow && nextCol == tableModel.pacmanCol) {
                handleCollision();
            }
            tableModel.fireTableDataChanged();
            GameView.gameBoard.repaint();
        } else {
        }
    }

    static void movePacman(int rowOffset, int colOffset) {
        int newRow = tableModel.pacmanRow + rowOffset;
        int newCol = tableModel.pacmanCol + colOffset;

        if (isValidMove(newRow, newCol)) {
            if (tableModel.isPellet(newRow, newCol) && !tableModel.isPelletEaten(newRow, newCol)) {
                tableModel.setPelletEaten(newRow, newCol);
                tableModel.increaseScore(10);
                GameView.updateScore(tableModel.getScore());
            }
            tableModel.setValueAt("", tableModel.pacmanRow, tableModel.pacmanCol);
            tableModel.setValueAt("P", newRow, newCol);
            tableModel.pacmanRow = newRow;
            tableModel.pacmanCol = newCol;
            if (tableModel.isObstacle(newRow, newCol)) {
                handleCollision();
            }
            tableModel.fireTableDataChanged();
            GameView.gameBoard.repaint();
        }
    }

    private static boolean isValidMove(int row, int col) {
        return row >= 0 && row < tableModel.getRowCount() &&
                col >= 0 && col < tableModel.getColumnCount() &&
                !tableModel.isObstacle(row, col);
    }

    private static void handleCollision() {
        lives--;
        score -= 10;
        GameView.updateLives(lives);
        if (lives == 0) {
            JOptionPane.showMessageDialog(null, "Game Over! Your score: " + score);
            resetGame();
        }
    }

    static void resetGame() {
        GameModel gameModel = new GameModel(tableModel.numRows, tableModel.numCols);
    }

    static void generateMazeRecursive(boolean[][] maze, int row, int col) {
        maze[row][col] = false;
        List<int[]> neighbors = new ArrayList<>();
        neighbors.add(new int[]{row - 2, col}); // Up
        neighbors.add(new int[]{row + 2, col}); // Down
        neighbors.add(new int[]{row, col - 2}); // Left
        neighbors.add(new int[]{row, col + 2}); // Right
        Collections.shuffle(neighbors);
        for (int[] neighbor : neighbors) {
            int nextRow = neighbor[0];
            int nextCol = neighbor[1];
            if (nextRow >= 1 && nextRow < maze.length - 1 && nextCol >= 1 && nextCol < maze[0].length - 1) {
                if (maze[nextRow][nextCol]) {
                    int removeRow = row + (nextRow - row) / 2;
                    int removeCol = col + (nextCol - col) / 2;
                    maze[removeRow][removeCol] = false;
                    generateMazeRecursive(maze, nextRow, nextCol);
                }
            }
        }
    }

    private boolean[][] generateMaze(int numRows, int numCols) {
        boolean[][] maze = new boolean[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                maze[i][j] = true;
            }
        }
        generateMazeRecursive(maze, 1, 1);
        return maze;
    }

    public static class CustomTableModel extends AbstractTableModel {
        private final int numRows;
        private final int numCols;
        private final Object[][] data;
        private final boolean[][] obstacles;
        private final Ghost[][] ghosts;
        private final ImageIcon pacmanIcon;
        int pacmanRow;
        int pacmanCol;
        private final List<Pellet> pellets;
        private int score;

        public CustomTableModel(int numRows, int numCols, boolean[][] obstacles) {
            this.numRows = numRows;
            this.numCols = numCols;
            this.obstacles = obstacles;
            pacmanIcon = new ImageIcon("src/assets/Pacman_HD.png");
            ImageIcon pelletIcon = new ImageIcon("src/assets/pellet.png");
            Image smallPelletImage = pelletIcon.getImage().getScaledInstance(5, 5, Image.SCALE_DEFAULT);
            ImageIcon smallPelletIcon = new ImageIcon(smallPelletImage);
            ghosts = new Ghost[numRows][numCols];
            pellets = new ArrayList<>();
            data = new Object[numRows][numCols];
            score = 0;
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    if (!obstacles[i][j]) {
                        pellets.add(new Pellet(i, j));
                        data[i][j] = smallPelletIcon;
                    }
                }
            }
            pacmanRow = numRows / 2;
            pacmanCol = numCols / 2;
            data[pacmanRow][pacmanCol] = new ImageIcon("src/assets/Pacman_HD.png");
        }

        public boolean isPellet(int row, int col) {
            for (Pellet pellet : pellets) {
                if (pellet.getRow() == row && pellet.getColumn() == col) {
                    return true;
                }
            }
            return false;
        }

        public int getScore() {
            return score;
        }

        public void increaseScore(int amount) {
            score += amount;
        }

        public void setGhost(int row, int col, Ghost ghost) {
            if (obstacles[row][col]) {
                return;
            }
            if (ghosts[row][col] == null) {
                ghosts[row][col] = ghost;
                fireTableCellUpdated(row, col);
            }
        }

        public void setPelletEaten(int row, int col) {
            for (Pellet pellet : pellets) {
                if (pellet.getRow() == row && pellet.getColumn() == col) {
                    pellet.setEaten(true);
                    break;
                }
            }
        }

        public boolean isPelletEaten(int row, int col) {
            for (Pellet pellet : pellets) {
                if (pellet.getRow() == row && pellet.getColumn() == col) {
                    return pellet.isEaten();
                }
            }
            return false;
        }

        public void clearGhost(int row, int col) {
            ghosts[row][col] = null;
        }

        public Ghost getGhostAt(int row, int col) {
            return ghosts[row][col];
        }

        public boolean isObstacle(int row, int col) {
            return obstacles[row][col];
        }

        @Override
        public int getRowCount() {
            return numRows;
        }

        @Override
        public int getColumnCount() {
            return numCols;
        }

        @Override
        public Object getValueAt(int row, int col) {
            Ghost ghost = ghosts[row][col];

            if (row == pacmanRow && col == pacmanCol) {
                return pacmanIcon;
            } else if (ghost != null) {
                GhostIcon ghostIcon = ghost.getIcon();
                return convertGhostIconToImageIcon(ghostIcon);
            } else {
                return data[row][col];
            }
        }

        public ImageIcon convertGhostIconToImageIcon(GhostIcon ghostIcon) {
            String imagePath = ghostIcon.getImagePath();
            return new ImageIcon(imagePath);
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            data[rowIndex][columnIndex] = value;
            if (value instanceof ImageIcon) {
                JLabel label = new JLabel();
                label.setIcon(new ImageIcon("src/assets/Pacman_HD.png"));
                data[pacmanRow][pacmanCol] = null;
                pacmanRow = rowIndex;
                pacmanCol = columnIndex;
            } else if (value instanceof Ghost ghost) {
                if (ghosts[rowIndex][columnIndex] != null) {
                    data[ghosts[rowIndex][columnIndex].getRow()][ghosts[rowIndex][columnIndex].getCol()] = null;
                }
                ghosts[rowIndex][columnIndex] = ghost;
            } else {
                data[rowIndex][columnIndex] = null;
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }
    }

    static class CustomCellRenderer extends DefaultTableCellRenderer {
        private final CustomTableModel model;
        public CustomCellRenderer(CustomTableModel model) {
            this.model = model;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (model.isObstacle(row, column)) {
                component.setBackground(Color.BLUE);
            } else if (model.isPelletEaten(row, column)) {
                component.setBackground(table.getBackground());
            } else {
                component.setBackground(table.getBackground());
            }
            Ghost ghost = model.getGhostAt(row, column);
            if (ghost != null) {
                component.setBackground(table.getBackground());
            }
            if (value instanceof ImageIcon) {
                return new JLabel((ImageIcon) value);
            }
            return component;
        }
    }
}
