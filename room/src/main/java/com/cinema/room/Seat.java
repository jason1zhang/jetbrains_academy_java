package com.cinema.room;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seat {
    private int row;
    private int column;

    @JsonIgnore
    private boolean isTaken;
    
    public Seat() {}

    public Seat(int row, int col) {
        this.row = row;
        this.column = col;
        this.isTaken = false;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return this.row <= 4 ? 10 : 8;
    }

    public boolean getIsTaken() {
        return this.isTaken;
    }

    public void setTaken(boolean isTaken) {
        this.isTaken = isTaken;
    }     
}
