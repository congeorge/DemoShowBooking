package com.jpm.moviesystem.exceptions;

public class InvalidStateException extends BookingSystemException {
    public InvalidStateException(String message)
    {
        super(message);
    }
}
