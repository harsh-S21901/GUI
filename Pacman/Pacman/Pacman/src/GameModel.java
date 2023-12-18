import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

        ghosts[0] = new Ghost(1, 1, GhostIcon.CYAN_GHOST);
        ghosts[1] = new Ghost(numRows - 2, numCols - 2, GhostIcon.RED_GHOST);
        ghosts[2] = new Ghost(1, numCols - 2, GhostIcon.SHADY_RED_GHOST);
        ghosts[3] = new Ghost(numRows - 2, 1, GhostIcon.YELLOW_RED_GHOST);
    }

    static void dropUpgrade() {
        for (Ghost ghost : ghosts) {
            int numCols = ghost.getCol();
            int numRows = ghost.getRow();
            if (!GameModel.tableModel.isObstacle(numRows, numCols)) {
                ImageIcon upgradeIcon = new ImageIcon("src/assets/heart.png");
                GameModel.tableModel.setUpgrade(numRows, numCols, upgradeIcon);
            }
        }
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
        Random random = new Random();
        int numBlocksToRemove = (int) (0.1 * numRows * numCols);
        for (int i = 0; i < numBlocksToRemove; i++) {
            int randomRow = 1 + random.nextInt(numRows - 2);
            int randomCol = 1 + random.nextInt(numCols - 2);
            maze[randomRow][randomCol] = false;
        }

        return maze;
    }

    public enum PacmanIcon {
        PACMAN_UP_OPEN("src/assets/pacmanUpOpen.png"),
        PACMAN_UP_CLOSED("src/assets/pacmanUpClosed.png"),
        PACMAN_DOWN_OPEN("src/assets/pacmanDownOpen.png"),
        PACMAN_DOWN_CLOSED("src/assets/pacmanDownClosed.png"),
        PACMAN_LEFT_OPEN("src/assets/pacmanLeftOpen.png"),
        PACMAN_LEFT_CLOSED("src/assets/pacmanLeftClosed.png"),
        PACMAN_RIGHT_OPEN("src/assets/Pacman_HD.png"),
        PACMAN_RIGHT_CLOSED("src/assets/Pacman_Closed_HD.png"),
        PACMAN_DEFAULT("src/assets/Pacman_HD.png");

        private final String imagePath;

        PacmanIcon(String imagePath) {
            this.imagePath = imagePath;
        }

        public ImageIcon getIcon() {
            return new ImageIcon(imagePath);
        }
    }

    public static class CustomTableModel extends AbstractTableModel implements Runnable {
        final int numRows;
        final int numCols;
        private final Object[][] data;
        private final boolean[][] obstacles;
        private final Ghost[][] ghosts;
        private final List<Pellet> pellets;
        private final boolean[][] upgrades;
        ImageIcon pacmanIcon;
        int pacmanRow;
        int pacmanCol;
        private boolean isAnimating;
        private int animationDelay = 200;
        private int pacmanAnimationFrame;
        private boolean pacmanMouthOpen;
        private int score;

        public CustomTableModel(int numRows, int numCols, boolean[][] obstacles) {
            this.numRows = numRows;
            this.numCols = numCols;
            this.obstacles = obstacles;
            upgrades = new boolean[numRows][numCols];
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

        @Override
        public void run() {
            isAnimating = true;
            while (isAnimating) {
                pacmanAnimationFrame = (pacmanAnimationFrame + 1) % 2;
                pacmanMouthOpen = !pacmanMouthOpen;
                tableModel.fireTableDataChanged();

                try {
                    Thread.sleep(animationDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void startAnimation(int animationDelay) {
            this.animationDelay = animationDelay;
            new Thread(this).start();
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

        public boolean hasUpgrade(int row, int col) {
            Object value = data[row][col];
            return value instanceof ImageIcon && upgrades[row][col];
        }


        public void removeUpgrade(int row, int col) {
            upgrades[row][col] = false; // Remove the upgrade at the specified position
            fireTableCellUpdated(row, col);
        }

        public boolean isUpgrade(int row, int col) {
            return upgrades[row][col];
        }


        public void setUpgrade(int row, int col, ImageIcon upgradeIcon) {
            upgrades[row][col] = true;
            data[row][col] = upgradeIcon;
            fireTableCellUpdated(row, col);
        }


        public boolean isPelletEaten(int row, int col) {
            for (Pellet pellet : pellets) {
                if (pellet.getRow() == row && pellet.getColumn() == col && !upgrades[row][col]) {
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
                if (pacmanMouthOpen) {
                    if (GameController.view.movingUp[0]) {
                        return PacmanIcon.PACMAN_UP_OPEN.getIcon();
                    } else if (GameController.view.movingDown[0]) {
                        return PacmanIcon.PACMAN_DOWN_OPEN.getIcon();
                    } else if (GameController.view.movingLeft[0]) {
                        return PacmanIcon.PACMAN_LEFT_OPEN.getIcon();
                    } else if (GameController.view.movingRight[0]) {
                        return PacmanIcon.PACMAN_RIGHT_OPEN.getIcon();
                    } else {
                        return PacmanIcon.PACMAN_DEFAULT.getIcon();
                    }
                } else {
                    if (GameController.view.movingUp[0]) {
                        return PacmanIcon.PACMAN_UP_CLOSED.getIcon();
                    } else if (GameController.view.movingDown[0]) {
                        return PacmanIcon.PACMAN_DOWN_CLOSED.getIcon();
                    } else if (GameController.view.movingLeft[0]) {
                        return PacmanIcon.PACMAN_LEFT_CLOSED.getIcon();
                    } else if (GameController.view.movingRight[0]) {
                        return PacmanIcon.PACMAN_RIGHT_CLOSED.getIcon();
                    } else {
                        return PacmanIcon.PACMAN_DEFAULT.getIcon();
                    }
                }
            } else if (ghost != null) {
                GhostIcon ghostIcon = ghost.getIcon();
                return convertGhostIconToImageIcon(ghostIcon);
            }
            return data[row][col];
        }


        public ImageIcon convertGhostIconToImageIcon(GhostIcon ghostIcon) {
            String imagePath = ghostIcon.getImagePath();
            return new ImageIcon(imagePath);
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            data[rowIndex][columnIndex] = value;
            if (value instanceof ImageIcon) {
                if (hasUpgrade(rowIndex, columnIndex)) {
                    removeUpgrade(rowIndex, columnIndex);
                } else {
                    isAnimating = true;
                    new Thread(this).start();
                }
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
        private ImageIcon obstacleIcon = new ImageIcon("src/assets/block.png");

        public CustomCellRenderer(CustomTableModel model) {
            this.model = model;
            Image smallObstacle = obstacleIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
            this.obstacleIcon = new ImageIcon(smallObstacle);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (model.isObstacle(row, column)) {
                component.setBackground(table.getBackground());
                setIcon(obstacleIcon);
            } else if (model.isPelletEaten(row, column)) {
                component.setBackground(table.getBackground());
                setIcon(null);
            } else {
                component.setBackground(table.getBackground());
                setIcon((Icon) value);
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
