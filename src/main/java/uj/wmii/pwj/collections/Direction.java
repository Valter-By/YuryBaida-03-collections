package uj.wmii.pwj.collections;

public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    private final int deltaRow;
    private final int deltaCol;

    Direction(int dRow, int dCol) {
        this.deltaRow = dRow;
        this.deltaCol = dCol;
    }

    public int deltaRow() {
        return deltaRow;
    }

    public int deltaCol() {
        return deltaCol;
    }
}
