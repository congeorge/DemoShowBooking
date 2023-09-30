package com.jpm.moviesystem.exceptions;

public class SeatsUnavailableException extends BookingSystemException {
    public SeatsUnavailableException(String message)
    {
        super(message);
    }
}
