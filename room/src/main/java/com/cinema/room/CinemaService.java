package com.cinema.room;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class CinemaService {

    private final static int SIZE = 9;

    private int totalRows;

    private int totalColumns;    

    private Map<String, Object> map;

    public CinemaService() {
        this.totalRows = SIZE;
        this.totalColumns = SIZE;

        List<Seat> availableSeats = new ArrayList<>();

        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                availableSeats.add(new Seat(i + 1, j + 1));
            }
        }

        map = new ConcurrentHashMap<>();

        map.put("total_rows", this.totalRows);
        map.put("total_columns", this.totalColumns);
        map.put("available_seats", availableSeats);
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public List<Seat> getAvailableSeats() {
        return (List<Seat>) this.map.get("available_seats");
    }  

    public Map<String, Object> getMapping() {
        return this.map;
    }
}
