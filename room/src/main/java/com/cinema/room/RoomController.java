package com.cinema.room;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RoomController {
    
    private final static int SIZE = 9;

    private Room room;

    public RoomController() {
        this.room = new Room(SIZE, SIZE) ;
    }

    @GetMapping("/seats")
    public Map<String, Object> getSeats() {
        return this.room.getMapping();
    }    
}
