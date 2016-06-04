package com.kar.gameoflife.models;

/**
 * Created by Karthik on 5/31/2016.
 */
public class Cell {

    public int row;
    public int col;
    private boolean mIsAlive;
    public int neighbor;

    public Cell(int rowPos, int colPos) {
        row = rowPos;
        col = colPos;
    }

    public boolean isAlive() {
        return mIsAlive;
    }

    public void setAlive(boolean isAlive) {
        mIsAlive = isAlive;
    }
}
