package com.cinema.room;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CinemaController {
    
    @Autowired
    private CinemaService cinemaService;    

    @GetMapping("/seats")
    public Map<String, Object> getMapping() {
        return this.cinemaService.getMapping();
    }      
    
    /*
    @PostMapping("/purchase")
    public ResponseEntity purchaseTicket(@RequestBody Seat seat) {
              
        return "Not found";
    }      
    */
}
