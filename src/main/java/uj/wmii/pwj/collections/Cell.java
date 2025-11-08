package uj.wmii.pwj.collections;

import java.util.Random;

public class Cell {

    public int row;
    public int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int calcStringPosition() {
        return 10 * (row - 1) + col - 1;
    }

    public Cell() {
        Random rnd = new Random();
        this.row = rnd.nextInt(10) + 1;
        this.col = rnd.nextInt(10) + 1;
    }
}
