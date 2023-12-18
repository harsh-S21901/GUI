public class Ghost {
    private int row;
    private int col;
    private GhostIcon icon;

    public Ghost(int row, int col, GhostIcon icon) {
        this.row = row;
        this.col = col;
        this.icon = icon;
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

    public GhostIcon getIcon() {
        return icon;
    }

    public void setIcon(GhostIcon icon) {
        this.icon = icon;
    }
}
