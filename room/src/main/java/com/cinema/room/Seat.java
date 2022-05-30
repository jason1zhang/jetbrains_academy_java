package com.cinema.room;

public class Seat {
    private int row;
    private int column;
    
    public Seat(int row, int col) {
        this.row = row;
        this.column = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setCol(int column) {
        this.column = column;
    }
}
