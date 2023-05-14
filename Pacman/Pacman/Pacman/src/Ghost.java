import java.awt.*;

public class Ghost {
    private int row;
    private int col;
    private Color color;

    public Ghost(int row, int col, Color color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void move(int targetRow, int targetCol) {
        if (row < targetRow) {
            row++;
        } else if (row > targetRow) {
            row--;
        } else if (col < targetCol) {
            col++;
        } else if (col > targetCol) {
            col--;
        }
    }
}
