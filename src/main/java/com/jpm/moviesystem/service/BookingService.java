package com.jpm.moviesystem.service;

import com.jpm.moviesystem.exceptions.InvalidStateException;
import com.jpm.moviesystem.exceptions.NotFoundException;
import com.jpm.moviesystem.exceptions.SeatsUnavailableException;
import com.jpm.moviesystem.model.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class BookingService {

    private final Map<String, Booking> showBookings;

    public BookingService() {
        this.showBookings = new ConcurrentHashMap<>();
    }

    public Booking getBooking(final String bookingId) {
        isExistingBooking(bookingId);
        return showBookings.get(bookingId);
    }

    private void isExistingBooking(String bookingId) {
        if (!showBookings.containsKey(bookingId)) {
            throw new NotFoundException("No Such Booking Exists");
        }
    }

    public Booking cancelBooking(final String bookingId, String phoneNumber) {
        isExistingBooking(bookingId);
        Booking booking = showBookings.get(bookingId);
        if (!canBeCancelled(booking))
            throw new InvalidStateException("Booking cannot be cancelled after  :" + booking.getShow().getCancellationWindowInMins() + " minutes of booking");
        booking.cancelBooking();
        booking.getShow().getSeats().stream().
                filter(seat -> booking.getSeatsBooked().contains(seat)).forEach(seat -> seat.setStatus(SeatStatus.AVAILABLE));
        return booking;


    }

    private boolean canBeCancelled(Booking booking) {
        LocalDateTime bookingTime = booking.getBookingTime();
        int cancelWindow = booking.getShow().getCancellationWindowInMins();
        return bookingTime.plusMinutes(cancelWindow).isAfter(LocalDateTime.now());
    }

    public List<Booking> getAllBookings(final Show show) {
        return showBookings.values().stream().filter(booking -> booking.getShow().equals(show)).toList();

    }

    public Booking createBooking(final Show show, final String phoneNumber,
                                 final String[] seats) {
        final String bookingId = show.getShowId() + phoneNumber;
        if (showBookings.containsKey(bookingId) && !showBookings.get(bookingId).getBookingStatus().equals(BookingStatus.Cancelled))
            throw new InvalidStateException("Booking already exists");

        List<Seat> seatsList = getSeatsAsList(show, seats);
        if(!isValidSeats(show,seatsList))
            throw new NotFoundException("These are invalid Seats for the show");

        if (isAnySeatAlreadyBooked(show, seatsList)) {
            throw new SeatsUnavailableException("Some Seat/Seats are already booked");
        }

        final Booking newBooking = new Booking(bookingId, show, phoneNumber, seatsList);
        showBookings.put(bookingId, newBooking);
        return newBooking;

    }

    private List<Seat> getSeatsAsList(Show show, String[] seats) {
        return Arrays.stream(seats).map((String s) ->
        {
            String[] splits = s.split("");
            int row = Integer.parseInt(splits[0]);
            int seat = Integer.parseInt(splits[1]);
            return new Seat(row, seat, show.getShowId());

        }).toList();
    }

    public List<Seat> getBookedSeats(final Show show) {
        return show.getSeats().stream().filter(seat -> seat.getStatus().equals(SeatStatus.BOOKED)).toList();

    }

    private boolean isAnySeatAlreadyBooked(final Show show, final List<Seat> seats) {
        final List<Seat> bookedSeats = getBookedSeats(show);
        return seats.stream().anyMatch(bookedSeats::contains);

    }

    private boolean isValidSeats(final Show show, final List<Seat> seats) {
        return show.getSeats().stream().allMatch(seats::contains);
      }
}
