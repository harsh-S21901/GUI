public class Pellet {
    private int row;
    private int column;
    private boolean eaten;

    public Pellet(int row, int column) {
        this.row = row;
        this.column = column;
        this.eaten = false;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isEaten() {
        return eaten;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }
}

