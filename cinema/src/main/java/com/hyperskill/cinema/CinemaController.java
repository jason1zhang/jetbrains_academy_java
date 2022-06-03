package com.hyperskill.cinema;

import java.util.Map;
import java.util.UUID;

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
    public ResponseEntity<Map<String, Object>> purchaseTicket(@RequestBody Seat seat) {
        return new ResponseEntity<Map<String, Object>>(this.cinemaService.purchaseTicket(seat), HttpStatus.OK);
    }     

    @PostMapping("/return")
    public ResponseEntity<Map<String, Object>> returnTicket(@RequestBody Token token) {
        return new ResponseEntity<Map<String, Object>>(this.cinemaService.returnTicket(token), HttpStatus.OK);
    }     
}
