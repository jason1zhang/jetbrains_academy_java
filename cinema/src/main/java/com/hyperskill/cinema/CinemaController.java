package com.hyperskill.cinema;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;    

    @GetMapping("/seats")
    public Map<String, Object> getMapping() {
        return this.cinemaService.getMapping();
    }      
        
    @PostMapping("/purchase")
    public ResponseEntity<Seat> purchaseTicket(@RequestBody Seat seat) {
        return new ResponseEntity<Seat>(this.cinemaService.purchaseTicket(seat), HttpStatus.OK);
    }      
}
