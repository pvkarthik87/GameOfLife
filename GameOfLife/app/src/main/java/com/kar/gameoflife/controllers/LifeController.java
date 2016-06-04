package com.kar.gameoflife.controllers;

import android.content.Context;
import android.util.Log;

import com.kar.gameoflife.models.Cell;
import com.kar.gameoflife.models.Constants;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Karthik on 5/31/2016.
 */
public class LifeController {

    private final static String TAG = LifeController.class.getSimpleName();

    private int mNumCells;
    private int mNumRows, mNumCols;

    private Cell[][] mCells;

    private Map<Integer, Cell> mAliveCellMap;
    private Map<Integer, Cell> mNextCellMap;

    public interface ILifeControllerListener {
        void onDataInitilaized();
        void onDataChanged(int position);
    }

    private ILifeControllerListener mListener;

    public LifeController() {
    }

    public int init(final int row, final int col) {
        if(row < 0 || col < 0) return Constants.FAILED;
        new Thread(new Runnable() {
            @Override
            public void run() {
                mNumCells = row * col;
                mNumRows = row;
                mNumCols = col;
                mCells = new Cell[row][col];
                mAliveCellMap = new HashMap<>(4);
                mNextCellMap = new HashMap<>(4);
                for(int i=0;i<row;i++) {
                    for (int j = 0; j < col; j++) {
                        Cell cell =  new Cell(i, j);
                        mCells[i][j] = cell;
                        //mCellMap.put((i*col) + j, cell);
                    }
                }
                if(mListener != null) {
                    mListener.onDataInitilaized();
                }
            }
        }).start();
        return Constants.SUCCESS;
    }

    public int deinit() {
        mCells = null;
        return Constants.SUCCESS;
    }

    public int getCount() {
        return mNumCells;
    }

    public boolean isAlive(int position) {
        return (mAliveCellMap != null)?mAliveCellMap.containsKey(position):false;
    }

    public void toggleState(int position) {
        if(mAliveCellMap == null) {
            return;
        }
        if(!mAliveCellMap.containsKey(position)) {
            int row = getRow(position);
            int column = getCol(position);
            mAliveCellMap.put(position, mCells[row][column]);
        }
        else {
            mAliveCellMap.remove(position);
        }
    }

    public void setListener(ILifeControllerListener listener) {
        mListener = listener;
    }

    private int getRow(int pos) {
        return pos/mNumCols;
    }

    private int getCol(int pos) {
        return pos%mNumCols;
    }

    public void next() {
        if(mAliveCellMap == null || mNextCellMap == null) {
            return;
        }
        mNextCellMap.clear();
        Iterator<Map.Entry<Integer, Cell>> it = mAliveCellMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<Integer, Cell> entry = it.next();
            Cell cell = entry.getValue();
            cell.neighbor = 0;
        }

        it = mAliveCellMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<Integer, Cell> entry = it.next();
            Cell cell = entry.getValue();
            addNeighbor(cell.row - 1, cell.col - 1);
            addNeighbor(cell.row - 1, cell.col);
            addNeighbor(cell.row - 1, cell.col + 1);
            addNeighbor(cell.row, cell.col - 1);
            addNeighbor(cell.row, cell.col + 1);
            addNeighbor(cell.row + 1, cell.col - 1);
            addNeighbor(cell.row + 1, cell.col);
            addNeighbor(cell.row + 1, cell.col + 1);
        }

        it = mAliveCellMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<Integer, Cell> entry = it.next();
            Cell cell = entry.getValue();
            int position = entry.getKey();
            if(cell.neighbor != 3 && cell.neighbor != 2) {
                it.remove();
                if(mListener != null) {
                    mListener.onDataChanged(position);
                }
            }
        }

        it = mNextCellMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<Integer, Cell> entry = it.next();
            Cell cell = entry.getValue();
            int position = entry.getKey();
            if(cell.neighbor == 3) {
                mAliveCellMap.put(position, cell);
                if(mListener != null) {
                    mListener.onDataChanged(position);
                }
            }
        }
    }

    private void addNeighbor(int row, int col) {
        if(row <0 || row >= mNumRows || col < 0 || col >= mNumCols) return;
        try {
            int position = (row * mNumCols) + col;
            Cell cell = mCells[row][col];
            if (!mNextCellMap.containsKey(position)) {
                cell.neighbor = 1;
                mNextCellMap.put(position, cell);
            }
            else {
                cell.neighbor++;
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }

}
