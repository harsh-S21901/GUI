import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class PacmanGame extends JFrame {

    private final ImageIcon heartIcon = new ImageIcon("src/assets/heart.png");
    JTable gameBoard;
    private JLabel scoreLabel;
    private JLabel lifeLabel;
    private JLabel highScoreLabel;
    private int lives = 3;
    private int score = 0;
    private boolean[][] obstacles;
    CustomTableModel tableModel;

    public PacmanGame(int numRows, int numCols) {
        setTitle("Pacman Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boolean[][] maze = generateMaze(numRows, numCols);
        obstacles = maze;
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

        // Create a new instance of your custom table model and pass it to the JTable constructor
        tableModel = new CustomTableModel(numRows, numCols, obstacles);
        // Add the custom JTable component
        gameBoard = new JTable(tableModel);


        // Customize the JTable appearance and behavior
        gameBoard.setRowHeight(20);
        gameBoard.getColumnModel().getColumns().asIterator().forEachRemaining(x -> x.setPreferredWidth(20));
        gameBoard.setBackground(Color.white);
        gameBoard.setShowGrid(false);

        // Create a JLabel to display the score counter
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 15));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create a JLabel to display the life counter
        lifeLabel = new JLabel("3");
        lifeLabel.setIcon(heartIcon);
        lifeLabel.setFont(new Font("Arial", Font.BOLD, 15));
        lifeLabel.setForeground(Color.BLACK);
        lifeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        highScoreLabel = new JLabel("High Score: " + StartWindow.getHighScore());
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 15));
        highScoreLabel.setForeground(Color.WHITE);
        highScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the JTable component to the JPanel
        gamePanel.add(gameBoard, BorderLayout.CENTER);

        // Set the custom cell renderer for the game board
        gameBoard.setDefaultRenderer(Object.class, new CustomCellRenderer(tableModel));

        // Add the score and life counters to the JPanel
        JPanel counterPanel = new JPanel(new GridLayout(1, 3));
        counterPanel.add(scoreLabel);
        counterPanel.add(lifeLabel);
        counterPanel.add(highScoreLabel);
        counterPanel.setPreferredSize(new Dimension(numCols, 50));

        counterPanel.setBackground(Color.BLACK);

        gamePanel.add(counterPanel, BorderLayout.SOUTH);


        getContentPane().add(gamePanel);
        pack();
        gamePanel.requestFocusInWindow();
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                // Set the corresponding moving flags based on the arrow keys
                if (keyCode == KeyEvent.VK_UP) {
                    movingUp[0] = true;
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    movingDown[0] = true;
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    movingLeft[0] = true;
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    movingRight[0] = true;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                // Reset the corresponding moving flags when the arrow key is released
                if (keyCode == KeyEvent.VK_UP) {
                    movingUp[0] = false;
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    movingDown[0] = false;
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    movingLeft[0] = false;
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    movingRight[0] = false;
                }
            }
        });
        // Start a separate thread for continuous movement
        Thread movementThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // Move Pacman based on the moving flags
                    if (movingUp[0]) {
                        movePacman(-1, 0);
                    } else if (movingDown[0]) {
                        movePacman(1, 0);
                    } else if (movingLeft[0]) {
                        movePacman(0, -1);
                    } else if (movingRight[0]) {
                        movePacman(0, 1);
                    }

                    // Delay between each movement update
                    try {
                        Thread.sleep(200); // Adjust the delay as needed
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        movementThread.start();


        setVisible(true);

        updateScore(score);
        updateLives(lives);

    }

    private void movePacman(int rowOffset, int colOffset) {
        int newRow = tableModel.pacmanRow + rowOffset;
        int newCol = tableModel.pacmanCol + colOffset;

        if (isValidMove(newRow, newCol)) {
            // Clear the current position of Pacman
            tableModel.setValueAt("", tableModel.pacmanRow, tableModel.pacmanCol);

            // Update Pacman's position
            tableModel.setValueAt("P", newRow, newCol);

            // Update the table model
            tableModel.pacmanRow = newRow;
            tableModel.pacmanCol = newCol;

            // Check for collision with obstacles
            if (tableModel.isObstacle(newRow, newCol)) {
                handleCollision();
            }

            // Notify the table model that the data has changed
            tableModel.fireTableDataChanged();

            // Refresh the game board
            gameBoard.repaint();
        }
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < tableModel.getRowCount() &&
                col >= 0 && col < tableModel.getColumnCount() &&
                !tableModel.isObstacle(row, col);
    }

    private void handleCollision() {
        // Handle collision with obstacles
        // For example, decrement lives and update the UI accordingly
        lives--;
        updateLives(lives);

        if (lives == 0) {
            // Game over logic
        }
    }


    private boolean[][] generateMaze(int numRows, int numCols) {
        boolean[][] maze = new boolean[numRows][numCols];

        // Initialize maze with walls
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                maze[i][j] = true;
            }
        }

        // Generate maze starting from cell (1, 1)
        generateMazeRecursive(maze, 1, 1);

        return maze;
    }

    private void generateMazeRecursive(boolean[][] maze, int row, int col) {
        // Mark current cell as visited
        maze[row][col] = false;

        // Create a randomized list of neighbors
        List<int[]> neighbors = new ArrayList<>();
        neighbors.add(new int[]{row - 2, col}); // Up
        neighbors.add(new int[]{row + 2, col}); // Down
        neighbors.add(new int[]{row, col - 2}); // Left
        neighbors.add(new int[]{row, col + 2}); // Right
        Collections.shuffle(neighbors);

        // Visit each neighbor
        for (int[] neighbor : neighbors) {
            int nextRow = neighbor[0];
            int nextCol = neighbor[1];

            // Check if neighbor is within bounds
            if (nextRow >= 1 && nextRow < maze.length - 1 && nextCol >= 1 && nextCol < maze[0].length - 1) {
                // Check if neighbor is unvisited
                if (maze[nextRow][nextCol]) {
                    // Determine the cell to remove between the current cell and the neighbor
                    int removeRow = row + (nextRow - row) / 2;
                    int removeCol = col + (nextCol - col) / 2;
                    maze[removeRow][removeCol] = false;

                    // Recursively visit the neighbor
                    generateMazeRecursive(maze, nextRow, nextCol);
                }
            }
        }
    }





    private void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    private void updateLives(int lives) {
        lifeLabel.setText(String.valueOf(lives));
    }


    private class CustomCellRenderer extends DefaultTableCellRenderer {
        private final CustomTableModel model;


        public CustomCellRenderer(CustomTableModel model) {
            this.model = model;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Customize the rendering of obstacle cells
            if (model.isObstacle(row, column)) {
                component.setBackground(Color.BLUE);
            } else {
                component.setBackground(table.getBackground());
            }
            return component;
        }
    }

    static class CustomTableModel extends AbstractTableModel {

        private final int numRows;
        private final int numCols;
        private final Object[][] data;
        private final boolean[][] obstacles;


        int pacmanRow;
        int pacmanCol;


        public CustomTableModel(int numRows, int numCols,boolean[][] obstacles) {
            this.numRows = numRows;
            this.numCols = numCols;
            this.obstacles = obstacles;


            // Initialize the data array with empty cells
            data = new Object[numRows][numCols];
            for (Object[] row : data) {
                Arrays.fill(row, "");
            }

            pacmanRow = numRows / 2;
            pacmanCol = numCols / 2;
            data[pacmanRow][pacmanCol] = "P";
        }


        public void setObstacle(int row, int col) {
            obstacles[row][col] = true;
        }

        public void clearObstacle(int row, int col) {
            obstacles[row][col] = false;
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
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }


        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            data[rowIndex][columnIndex] = value;
            if (value.equals("P")) {
                // Update Pacman's position in the table model
                data[pacmanRow][pacmanCol] = ""; // Clear the previous position
                pacmanRow = rowIndex;
                pacmanCol = columnIndex;
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
}
