package com.hyperskill.cinema;

public class SeatIsTakenException extends RuntimeException {

    public SeatIsTakenException() {
        super();
    }

    public SeatIsTakenException(String arg0) {
        super(arg0);
    }
}
