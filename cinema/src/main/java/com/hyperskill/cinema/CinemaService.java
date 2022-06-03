package com.hyperskill.cinema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
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

    public Map<String, Object> purchaseTicket(Seat seat) throws SeatIsTakenException, SeatNotFoundException {
        if (seat.getRow() > SIZE || seat.getRow() <= 0 || seat.getColumn() > SIZE || seat.getColumn() <= 0) {
            throw new SeatNotFoundException("The number of a row or a column is out of bounds!");
        }

        Optional<Seat> opt = getAvailableSeats()
                .stream()
                .filter(mySeat -> mySeat.equals(seat) && mySeat.getIsTaken() == false)
                .findAny();

        if (opt.isPresent()) {
            UUID uuid = UUID.randomUUID();

            Seat seatTaken = opt.get();
            seatTaken.setTaken(true);
            seatTaken.setUuid(uuid);

            Map<String, Object> map = new HashMap<>();
            map.put("token", uuid);
            map.put("ticket", seatTaken);

            return map;
        } else {
            throw new SeatIsTakenException("The ticket has been already purchased!");
        }
    }

    public Map<String, Object> returnTicket(Token token) throws TokenExpiredException {
        Optional<Seat> opt = getAvailableSeats()
                .stream()
                .filter(seat -> token.getToken().equals(seat.getUuid()))
                .findAny();

        if (opt.isPresent())  {
            Seat seatToken = opt.get();
            seatToken.setUuid(null);
            seatToken.setTaken(false);  // make the seat available again

            Map<String, Object> map = new HashMap<>();
            map.put("returned_ticket", seatToken);

            return map;
        } else {
            throw new TokenExpiredException("Wrong token!");
        }
    }
}
