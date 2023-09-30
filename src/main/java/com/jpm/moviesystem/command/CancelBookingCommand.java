package com.jpm.moviesystem.command;

import com.jpm.moviesystem.exceptions.IncorrectParametersException;
import com.jpm.moviesystem.model.Booking;
import com.jpm.moviesystem.service.BookingService;

public class CancelBookingCommand implements Command {


    BookingService bookingService;

    String bookingId;
    String phoneNumber;

    public CancelBookingCommand(BookingService bookingService, String[] inputs) throws IncorrectParametersException {
        this.bookingService = bookingService;
        if (inputs == null || inputs.length < 2)
            throw new IncorrectParametersException("Cancel  <Ticket#>  <Phone#>");
        bookingId = inputs[0];
        phoneNumber = inputs[1];

    }

    @Override
    public void execute() {

        Booking booking = bookingService.cancelBooking(bookingId, phoneNumber);
        System.out.println("Cancelled booking :" + booking);


    }
}
