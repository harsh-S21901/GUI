//import javax.swing.*;
//import javax.swing.table.AbstractTableModel;
//import javax.swing.table.DefaultTableCellRenderer;
//import java.awt.*;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class PacmanGame extends JFrame {
//    private final ImageIcon heartIcon = new ImageIcon("src/assets/heart.png");
//    static JTable gameBoard;
//    static CustomTableModel tableModel;
//    private static JLabel scoreLabel;
//    private static JLabel lifeLabel;
//    private JLabel highScoreLabel;
//    private static int lives = 3;
//    private static int score = 0;
//    private boolean[][] obstacles;
//    private Ghost[] ghosts;
//
//    public PacmanGame(int numRows, int numCols) {
//        setTitle("Pacman Game");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        boolean[][] maze = generateMaze(numRows, numCols);
//        obstacles = maze;
//        final boolean[] movingUp = {false};
//        final boolean[] movingDown = {false};
//        final boolean[] movingLeft = {false};
//        final boolean[] movingRight = {false};
//
//
//        JPanel gamePanel = new JPanel(new BorderLayout()) {
//            @Override
//            public boolean isFocusable() {
//                return true;
//            }
//        };
//
//        // Create a new instance of your custom table model and pass it to the JTable constructor
//        tableModel = new CustomTableModel(numRows, numCols, obstacles);
//        // Add the custom JTable component
//        gameBoard = new JTable(tableModel);
//
//        ghosts = new Ghost[4];
//
//        // Create and initialize the ghosts
//        ghosts[0] = new Ghost(1, 1, GhostIcon.CYAN_GHOST);
//        ghosts[1] = new Ghost(numRows - 2, numCols - 2, GhostIcon.RED_GHOST);
//        ghosts[2] = new Ghost(1, numCols - 2, GhostIcon.SHADY_RED_GHOST);
//        ghosts[3] = new Ghost(numRows - 2, 1, GhostIcon.YELLOW_RED_GHOST);
//
//        // Customize the JTable appearance and behavior
//        gameBoard.setRowHeight(20);
//        gameBoard.getColumnModel().getColumns().asIterator().forEachRemaining(x -> x.setPreferredWidth(20));
//        gameBoard.setBackground(Color.black);
//        gameBoard.setShowGrid(false);
//
//        // Create a JLabel to display the score counter
//        scoreLabel = new JLabel("Score: 0");
//        scoreLabel.setFont(new Font("Arial", Font.BOLD, 15));
//        scoreLabel.setForeground(Color.WHITE);
//        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
//
//        // Create a JLabel to display the life counter
//        lifeLabel = new JLabel("3");
//        lifeLabel.setIcon(heartIcon);
//        lifeLabel.setFont(new Font("Arial", Font.BOLD, 15));
//        lifeLabel.setForeground(Color.white);
//        lifeLabel.setHorizontalAlignment(SwingConstants.CENTER);
//
//        highScoreLabel = new JLabel("High Score: " + StartWindow.getHighScore());
//        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 15));
//        highScoreLabel.setForeground(Color.WHITE);
//        highScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
//
//        // Add the JTable component to the JPanel
//        gamePanel.add(gameBoard, BorderLayout.CENTER);
//
//        // Set the custom cell renderer for the game board
//        gameBoard.setDefaultRenderer(Object.class, new CustomCellRenderer(tableModel));
//
//        // Add the score and life counters to the JPanel
//        JPanel counterPanel = new JPanel(new GridLayout(1, 3));
//        counterPanel.add(scoreLabel);
//        counterPanel.add(lifeLabel);
//        counterPanel.add(highScoreLabel);
//        counterPanel.setPreferredSize(new Dimension(numCols, 50));
//
//        counterPanel.setBackground(Color.BLACK);
//        gamePanel.add(counterPanel, BorderLayout.SOUTH);
//
//        getContentPane().add(gamePanel);
//        pack();
//        gamePanel.requestFocusInWindow();
//        gamePanel.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                int keyCode = e.getKeyCode();
//
//                // Set the corresponding moving flags based on the arrow keys
//                if (keyCode == KeyEvent.VK_UP) {
//                    movingUp[0] = true;
//                } else if (keyCode == KeyEvent.VK_DOWN) {
//                    movingDown[0] = true;
//                } else if (keyCode == KeyEvent.VK_LEFT) {
//                    movingLeft[0] = true;
//                } else if (keyCode == KeyEvent.VK_RIGHT) {
//                    movingRight[0] = true;
//                }
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//                int keyCode = e.getKeyCode();
//                // Reset the corresponding moving flags when the arrow key is released
//                if (keyCode == KeyEvent.VK_UP) {
//                    movingUp[0] = false;
//                } else if (keyCode == KeyEvent.VK_DOWN) {
//                    movingDown[0] = false;
//                } else if (keyCode == KeyEvent.VK_LEFT) {
//                    movingLeft[0] = false;
//                } else if (keyCode == KeyEvent.VK_RIGHT) {
//                    movingRight[0] = false;
//                }
//            }
//        });
//        // Start a separate thread for continuous movement
//        Thread movementThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    // Move Pacman based on the moving flags
//                    if (movingUp[0]) {
//                        movePacman(-1, 0);
//                    } else if (movingDown[0]) {
//                        movePacman(1, 0);
//                    } else if (movingLeft[0]) {
//                        movePacman(0, -1);
//                    } else if (movingRight[0]) {
//                        movePacman(0, 1);
//                    }
//
//                    // Delay between each movement update
//                    try {
//                        Thread.sleep(200); // Adjust the delay as needed
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        movementThread.start();
//        // Start a separate thread for continuous ghost movement
//        Thread ghostMovementThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    for (Ghost ghost : ghosts) {
//                        moveGhost(ghost);
//                    }
//
//                    // Delay between each ghost movement update
//                    try {
//                        Thread.sleep(500); // Adjust the delay as needed
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        ghostMovementThread.start();
//        setVisible(true);
//        updateScore(score);
//        updateLives(lives);
//    }
//
//    static void moveGhost(Ghost ghost) {
//        int row = ghost.getRow();
//        int col = ghost.getCol();
//
//        List<int[]> emptyNeighbors = new ArrayList<>();  // Determine the valid neighboring cells
//        if (row > 0 && !PacmanGame.tableModel.isObstacle(row - 1, col)) {
//            emptyNeighbors.add(new int[]{row - 1, col}); // Up
//        }
//        if (row < tableModel.getRowCount() - 1 && !tableModel.isObstacle(row + 1, col)) {
//            emptyNeighbors.add(new int[]{row + 1, col}); // Down
//        }
//        if (col > 0 && !tableModel.isObstacle(row, col - 1)) {
//            emptyNeighbors.add(new int[]{row, col - 1}); // Left
//        }
//        if (col < tableModel.getColumnCount() - 1 && !tableModel.isObstacle(row, col + 1)) {
//            emptyNeighbors.add(new int[]{row, col + 1}); // Right
//        }
//        // Shuffle the list of empty neighbors
//        Collections.shuffle(emptyNeighbors);
//
//        if (!emptyNeighbors.isEmpty()) {          // Choose a neighboring cell with empty space if available
//            int[] nextCell = emptyNeighbors.get(0); // Select the first shuffled neighbor
//            int nextRow = nextCell[0];
//            int nextCol = nextCell[1];
//
//            tableModel.clearGhost(row, col);
//            tableModel.setGhost(nextRow, nextCol, ghost);
//            ghost.setRow(nextRow);
//            ghost.setCol(nextCol);
//
//            if (nextRow == tableModel.pacmanRow && nextCol == tableModel.pacmanCol) {
//                handleCollision();
//            }
//            tableModel.fireTableDataChanged();
//            gameBoard.repaint();
//        } else {
//            // The ghost has collided with an obstacle or there are no empty neighbors, so it stays in the current position
//        }
//    }
//
//    static void movePacman(int rowOffset, int colOffset) {
//        int newRow = tableModel.pacmanRow + rowOffset;
//        int newCol = tableModel.pacmanCol + colOffset;
//
//        if (isValidMove(newRow, newCol)) {
//            if (tableModel.isPellet(newRow, newCol) && !tableModel.isPelletEaten(newRow, newCol)) {// Check if a pellet exists at the new position and it's not eaten
//                tableModel.setPelletEaten(newRow, newCol);
//                tableModel.increaseScore(10); // Increase the score by 10 for each pellet
//                updateScore(tableModel.getScore()); // Update the score in the UI
//            }
//            tableModel.setValueAt("", tableModel.pacmanRow, tableModel.pacmanCol);
//            tableModel.setValueAt("P", newRow, newCol);
//            tableModel.pacmanRow = newRow;
//            tableModel.pacmanCol = newCol;
//            if (tableModel.isObstacle(newRow, newCol)) {
//                handleCollision();
//            }
//            tableModel.fireTableDataChanged(); // Notify the table model that the data has changed
//            gameBoard.repaint();// Refresh the game board
//        }
//    }
//
//    private static boolean isValidMove(int row, int col) {
//        return row >= 0 && row < tableModel.getRowCount() &&
//                col >= 0 && col < tableModel.getColumnCount() &&
//                !tableModel.isObstacle(row, col);
//    }
//
//    private static void handleCollision() {
//        lives--;
//        score -= 10; // Decrease the score by 10
//        updateLives(lives);
//        if (lives == 0) {
//            JOptionPane.showMessageDialog(null, "Game Over! Your score: " + score);
//            resetGame(); // Implement the resetGame() method to reset the game state
//        }
//    }
//    private static void resetGame() {
//        PacmanGame pacmanGame = new PacmanGame(tableModel.numRows, tableModel.numCols);
//    }
//
//    private boolean[][] generateMaze(int numRows, int numCols) {
//        boolean[][] maze = new boolean[numRows][numCols];
//
//        // Initialize maze with walls
//        for (int i = 0; i < numRows; i++) {
//            for (int j = 0; j < numCols; j++) {
//                maze[i][j] = true;
//            }
//        }
//        // Generate maze starting from cell (1, 1)
//        generateMazeRecursive(maze, 1, 1);
//        return maze;
//    }
//
//    static void generateMazeRecursive(boolean[][] maze, int row, int col) {
//        // Mark current cell as visited
//        maze[row][col] = false;
//        // Create a randomized list of neighbors
//        List<int[]> neighbors = new ArrayList<>();
//        neighbors.add(new int[]{row - 2, col}); // Up
//        neighbors.add(new int[]{row + 2, col}); // Down
//        neighbors.add(new int[]{row, col - 2}); // Left
//        neighbors.add(new int[]{row, col + 2}); // Right
//        Collections.shuffle(neighbors);
//        // Visit each neighbor
//        for (int[] neighbor : neighbors) {
//            int nextRow = neighbor[0];
//            int nextCol = neighbor[1];
//
//            // Check if neighbor is within bounds
//            if (nextRow >= 1 && nextRow < maze.length - 1 && nextCol >= 1 && nextCol < maze[0].length - 1) {
//                // Check if neighbor is unvisited
//                if (maze[nextRow][nextCol]) {
//                    // Determine the cell to remove between the current cell and the neighbor
//                    int removeRow = row + (nextRow - row) / 2;
//                    int removeCol = col + (nextCol - col) / 2;
//                    // Remove the block between the current cell and the neighbor
//                    maze[removeRow][removeCol] = false;
//                    // Recursively visit the neighbor
//                    generateMazeRecursive(maze, nextRow, nextCol);
//                }
//            }
//        }
//    }
//
//    static void updateScore(int score) {
//        scoreLabel.setText("Score: " + score);
//    }
//
//    static void updateLives(int lives) {
//        lifeLabel.setText(String.valueOf(lives));
//    }
//
//    public static class CustomTableModel extends AbstractTableModel {
//        private final int numRows;
//        private final int numCols;
//        private final Object[][] data;
//        private final boolean[][] obstacles;
//        private final Ghost[][] ghosts;
//        private final ImageIcon pacmanIcon;
//        int pacmanRow;
//        int pacmanCol;
//        private List<Pellet> pellets;
//        private final ImageIcon pelletIcon;
//        private int score;
//
//        public CustomTableModel(int numRows, int numCols, boolean[][] obstacles) {
//            this.numRows = numRows;
//            this.numCols = numCols;
//            this.obstacles = obstacles;
//            pacmanIcon = new ImageIcon("src/assets/Pacman_HD.png");
//            pelletIcon = new ImageIcon("src/assets/pellet.png");
//            Image smallPelletImage = pelletIcon.getImage().getScaledInstance(5, 5, Image.SCALE_DEFAULT);
//            ImageIcon smallPelletIcon = new ImageIcon(smallPelletImage);
//            // Create a list to hold the ghosts
//            ghosts = new Ghost[numRows][numCols];
//
//
//            pellets = new ArrayList<>();
//            data = new Object[numRows][numCols];
//            score = 0;
//// Add pellets to the list and set the corresponding data value
//            for (int i = 0; i < numRows; i++) {
//                for (int j = 0; j < numCols; j++) {
//                    if (!obstacles[i][j]) {
//                        pellets.add(new Pellet(i, j));
//                        data[i][j] = smallPelletIcon;
//                    }
//                }
//            }
//            pacmanRow = numRows / 2;
//            pacmanCol = numCols / 2;
//            data[pacmanRow][pacmanCol] = new ImageIcon("src/assets/Pacman_HD.png");
//
//        }
//
//        public boolean isPellet(int row, int col) {
//            for (Pellet pellet : pellets) {
//                if (pellet.getRow() == row && pellet.getColumn() == col) {
//                    return true; // Found a pellet at the specified position
//                }
//            }
//            return false; // No pellet found at the specified position
//        }
//
//        public int getScore() {
//            return score;
//        }
//
//        public void increaseScore(int amount) {
//            score += amount;
//        }
//
//        public void setGhost(int row, int col, Ghost ghost) {
//            if (obstacles[row][col]) {
//                return; // Skip if the cell is an obstacle
//            }
//
//            if (ghosts[row][col] == null) {
//                ghosts[row][col] = ghost;
//                fireTableCellUpdated(row, col);
//            }
//        }
//
//        public void setPelletEaten(int row, int col) {
//            for (Pellet pellet : pellets) {
//                if (pellet.getRow() == row && pellet.getColumn() == col) {
//                    pellet.setEaten(true);
//                    break;
//                }
//            }
//        }
//
//        public boolean isPelletEaten(int row, int col) {
//            for (Pellet pellet : pellets) {
//                if (pellet.getRow() == row && pellet.getColumn() == col) {
//                    return pellet.isEaten();
//                }
//            }
//            return false;
//        }
//
//        public void clearGhost(int row, int col) {
//            ghosts[row][col] = null;
//        }
//
//        public Ghost getGhostAt(int row, int col) {
//            return ghosts[row][col];
//        }
//
//        public boolean isObstacle(int row, int col) {
//            return obstacles[row][col];
//        }
//
//        @Override
//        public int getRowCount() {
//            return numRows;
//        }
//
//        @Override
//        public int getColumnCount() {
//            return numCols;
//        }
//
//
//        @Override
//        public Object getValueAt(int row, int col) {
//            Ghost ghost = ghosts[row][col]; // Move this line above the return statements
//
//            if (row == pacmanRow && col == pacmanCol) {
//                return pacmanIcon; // Return the Pacman image icon
//            } else if (ghost != null) {
//                GhostIcon ghostIcon = ghost.getIcon();
//                ImageIcon icon = convertGhostIconToImageIcon(ghostIcon); // Helper method to convert GhostIcon to ImageIcon
//                return icon;
//            } else {
//                return data[row][col]; // Return the pellet or null if no pellet is present
//            }
//        }
//
//
//        public ImageIcon convertGhostIconToImageIcon(GhostIcon ghostIcon) {
//            String imagePath = ghostIcon.getImagePath();
//            return new ImageIcon(imagePath);
//        }
//
//
//        @Override
//        public void setValueAt(Object value, int rowIndex, int columnIndex) {
//            data[rowIndex][columnIndex] = value;
//            if (value instanceof ImageIcon) {
//                // Update Pacman's position in the table model
//                JLabel label = new JLabel();
//                label.setIcon(new ImageIcon("src/assets/Pacman_HD.png"));
//                data[pacmanRow][pacmanCol] = null; // Clear the previous position
//                pacmanRow = rowIndex;
//                pacmanCol = columnIndex;
//            } else if (value instanceof Ghost) {
//                Ghost ghost = (Ghost) value;
//                // Clear the previous position of the ghost
//                if (ghosts[rowIndex][columnIndex] != null) {
//                    data[ghosts[rowIndex][columnIndex].getRow()][ghosts[rowIndex][columnIndex].getCol()] = null;
//                }
//                ghosts[rowIndex][columnIndex] = ghost;
//            } else {
//                // Clear the cell if it's not Pacman or a ghost
//                data[rowIndex][columnIndex] = null;
//            }
//            fireTableCellUpdated(rowIndex, columnIndex);
//        }
//
//        @Override
//        public boolean isCellEditable(int rowIndex, int columnIndex) {
//            return true;
//        }
//
//        @Override
//        public Class<?> getColumnClass(int columnIndex) {
//            return String.class;
//        }
//    }
//
//    static class CustomCellRenderer extends DefaultTableCellRenderer {
//        private final CustomTableModel model;
//        public CustomCellRenderer(CustomTableModel model) {
//            this.model = model;
//        }
//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//            // Customize the rendering of obstacle cells
//            if (model.isObstacle(row, column)) {
//                component.setBackground(Color.BLUE);
//            } else if (model.isPelletEaten(row, column)) {
//                component.setBackground(table.getBackground());
//            } else {
//                component.setBackground(table.getBackground());
//            }
//            Ghost ghost = model.getGhostAt(row, column);
//            if (ghost != null) {
//                component.setBackground(table.getBackground());
//            }
//            // Customize the rendering of the icon
//            if (value instanceof ImageIcon) {
//                JLabel label = new JLabel((ImageIcon) value);
//                return label;
//            }
//            return component;
//        }
//    }
//}
