package com.jpm.moviesystem.model;

import com.jpm.moviesystem.exceptions.InvalidStateException;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Booking {



    private final String id;
    private final Show show;
    private final List<Seat> seatsBooked;
    private final String phoneNumber;
    private BookingStatus bookingStatus;
    private LocalDateTime bookingTime;

    public Booking(final String id, final Show show, final String phoneNumber,
                    final List<Seat> seatsBooked) {
        this.id = id;
        this.show = show;
        show.getSeats().stream().filter(seatsBooked::contains).forEach(seat -> seat.setStatus(SeatStatus.BOOKED));
        this.seatsBooked = seatsBooked;
        this.phoneNumber = phoneNumber;
        this.bookingStatus = BookingStatus.Confirmed;
        this.bookingTime = LocalDateTime.now();
    }

    public boolean isConfirmed() {
        return this.bookingStatus == BookingStatus.Confirmed;
    }

    public void confirmBooking() {
        if (this.bookingStatus != BookingStatus.Created) {
            throw new InvalidStateException("Booking cannot be confirmed");
        }
        this.bookingStatus = BookingStatus.Confirmed;
    }

    public void cancelBooking() {
        if (this.bookingStatus != BookingStatus.Confirmed) {
            throw new InvalidStateException("Booking cannot be cancelled");
        }
        this.bookingStatus = BookingStatus.Cancelled;
    }
    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", show=" + show.getShowName() +
                ", seatsBooked=" + seatsBooked +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bookingStatus=" + bookingStatus +
                '}';
    }
}
