package com.jpm.moviesystem.command;

import com.jpm.moviesystem.exceptions.IncorrectParametersException;
import com.jpm.moviesystem.exceptions.NotFoundException;
import com.jpm.moviesystem.model.Booking;
import com.jpm.moviesystem.service.BookingService;
import com.jpm.moviesystem.service.ShowService;

public class BookShowCommand implements Command {


    BookingService bookingService;
    ShowService showService;
    String showId;
    String phoneNumber;
    String[] seats;

    public BookShowCommand(BookingService bookingService, ShowService showService, String[] inputs) throws IncorrectParametersException {
        this.bookingService = bookingService;
        this.showService = showService;
        if (inputs == null || inputs.length < 3)
            throw new IncorrectParametersException("Book  <Show Number> <Phone#> <Comma separated list of seats>");
        showId = inputs[0];
        phoneNumber = inputs[1];
        seats = inputs[2].split(",");
    }

    @Override
    public void execute() {
        try {
            Booking booking = bookingService.createBooking(showService.getShow(showId), phoneNumber, seats);
            System.out.println("Booking created with booking id :" + booking);

        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }


    }
}
