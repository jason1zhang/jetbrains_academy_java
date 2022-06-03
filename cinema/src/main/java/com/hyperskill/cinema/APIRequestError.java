package com.hyperskill.cinema;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class APIRequestError {
    String error;

    @JsonIgnore
    HttpStatus status;

    @JsonIgnore
    LocalDateTime timestamp;

    public APIRequestError(String error, HttpStatus status, LocalDateTime timestamp) {
        this.error = error;
        this.status = status;
        this.timestamp = timestamp;
    }

    public APIRequestError() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
