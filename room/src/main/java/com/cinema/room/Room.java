package com.cinema.room;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Room {
    private int totalRows;
    private int totalColumns;

    private List<Seat> availableSeats;

    public Room(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;

        availableSeats = new LinkedList<>();

        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                availableSeats.add(new Seat(i + 1, j + 1));
            }
        }
    }

    public Map<String, Object> getMapping() {
        Map<String, Object> map = new HashMap<>();
        map.put("total_rows", this.totalRows);
        map.put("total_columns", this.totalColumns);
        map.put("available_seats", availableSeats);

        return map;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public List<Seat> getAvailableSeats() {
        return this.availableSeats;
    }

    public void setAvailableSeats(List<Seat> availableSeats) {
        this.availableSeats = availableSeats;
    }       
}
