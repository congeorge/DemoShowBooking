package com.jpm;

import com.jpm.moviesystem.exceptions.InvalidStateException;
import com.jpm.moviesystem.exceptions.NotFoundException;
import com.jpm.moviesystem.exceptions.SeatsUnavailableException;
import com.jpm.moviesystem.model.Booking;
import com.jpm.moviesystem.model.BookingStatus;
import com.jpm.moviesystem.model.Show;
import com.jpm.moviesystem.service.BookingService;
import com.jpm.moviesystem.service.ShowService;
import lombok.ToString;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookingServiceTest {


    private ShowService showService;
    private BookingService bookingService;


    @BeforeEach
    void setUp() throws Exception {
        showService = new ShowService();
        bookingService = new BookingService();
    }

    @Test
    void BookingServiceTest_CreateBooking_Valid(){
     //given a show is setup
        Show show_1 =showService.addShow("1","ShowName1",2,5,2);

     // when you book two available seats

        String phoneNumber = "90230943";
        String[] seats = "11,12".split(",");
        int countAvailableSeat = showService.getAvailableSeats("1").size();
        Booking booking= bookingService.createBooking(show_1,phoneNumber,seats);

     // two seats should be booked and booking is  confirmed
        assertEquals(BookingStatus.Confirmed,booking.getBookingStatus());
        assertEquals(2,booking.getSeatsBooked().size());
        assertEquals(show_1,booking.getShow());
        assertEquals(2,showService.getBookedSeats("1").size());

      // reduced available seats by 2
        int expectedcountAvailableSeat = showService.getAvailableSeats("1").size();
        assertEquals(countAvailableSeat-2,expectedcountAvailableSeat);

    }

    @Test
    void BookingServiceTest_CreateBooking_MultiPleBookingForSameUserSameShow_ShouldThrowException(){
        //given a show is setup
        Show show_1 =showService.addShow("1","ShowName1",2,5,2);

        // when you book two available seats
        /// first booking
        String phoneNumber = "90230943";
        String[] seats = "11,12".split(",");
        int countAvailableSeat = showService.getAvailableSeats("1").size();
        Booking booking= bookingService.createBooking(show_1,phoneNumber,seats);

        /// Second booking should fail // only one booking per user phone number.
        String[] newseatseats = "10".split(",");
        Exception exception = assertThrows(InvalidStateException.class, () ->bookingService.createBooking(show_1,phoneNumber, newseatseats));
        assertEquals("Booking already exists", exception.getMessage());

    }


    @Test
    void BookingServiceTest_CreateBooking_BookAlreadyBookedSeats_ShouldThrowException(){
        //given a show is setup
        Show show_1 =showService.addShow("1","ShowName1",2,5,2);

        // when you book two available seats
        /// first booking
        String phoneNumber = "90230943";
        String[] seats = "11,12".split(",");
        int countAvailableSeat = showService.getAvailableSeats("1").size();
        Booking booking= bookingService.createBooking(show_1,phoneNumber,seats);

        /// Second booking should fail // only one booking per user phone number.
        // second user
        String phoneNumber_user2 = "93343434";
        String[] seats_user2 = "11".split(",");
        Exception exception = assertThrows(SeatsUnavailableException.class, () ->bookingService.createBooking(show_1,phoneNumber_user2, seats_user2));
        assertEquals("Some Seat/Seats are already booked", exception.getMessage());

    }

    @Test  // to add ignore since this delays test run
    void BookingServiceTest_CancelBooking_WithinTimeLimit(){
        //given a show is setup
        Show show_1 =showService.addShow("1","ShowName1",2,5,2);
        int origAvailableSeat = showService.getAvailableSeats("1").size();
        // when you book two available seats
        ///
        String phoneNumber = "90230943";
        String[] seats = "11,12".split(",");
        int countAvailableSeat = showService.getAvailableSeats("1").size();
        Booking booking= bookingService.createBooking(show_1,phoneNumber,seats);

        // cancel booking within time limit
        bookingService.cancelBooking(booking.getId(),phoneNumber);

        // booking should be cancelled and all seats are available for booking
        assertEquals(BookingStatus.Cancelled,booking.getBookingStatus());
        assertEquals(0,showService.getBookedSeats("1").size());
        assertEquals(origAvailableSeat, showService.getAvailableSeats("1").size());

    }


     // to add ignore since this delays test run
    @Test
    void BookingServiceTest_CancelBooking_OutSideOfTimeLimit(){
        //given a show is setup
        Show show_1 =showService.addShow("1","ShowName1",2,5,0);
        int origAvailableSeat = showService.getAvailableSeats("1").size();
        // when you book two available seats
        ///
        String phoneNumber = "90230943";
        String[] seats = "11,12".split(",");
        int countAvailableSeat = showService.getAvailableSeats("1").size();
        Booking booking= bookingService.createBooking(show_1,phoneNumber,seats);
        // dodgy sleep to simulate time passing
   /*     try {
            TimeUnit.m.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        // cancel booking after time limit
        Exception exception = assertThrows(InvalidStateException.class, () -> bookingService.cancelBooking(booking.getId(),phoneNumber));
        assertEquals("Booking cannot be cancelled after  :0 minutes of booking", exception.getMessage());


    }

    @Test
    void ShowServiceTest_AddDuplicateShow_ShouldThrowException() throws Exception {
        //given a showService
        showService.addShow("1","ShowName1",2,5,1);
        // then adding a duplicate gets an exception
        Exception exception = assertThrows(InvalidStateException.class, () ->showService.addShow("1","ShowName1",2,5,1));
        assertEquals("Show already exists", exception.getMessage());

    }

    @Test
    void ShowServiceTest_GetAvailableSeatsForShow() throws Exception {
        //given a showService
        showService.addShow("1","ShowName1",2,5,1);

        // Expected number of seats = 2*5 = 10.. all seats being available till booked.
        int expectedNumberOfSeats = 10;
        int actual =showService.getAvailableSeats("1").size();

        assertEquals(expectedNumberOfSeats, actual);

    }

    @Test
    void ShowServiceTest_GetInvalidShowDetails_ShouldThrowException() throws Exception {
        //given a showService
        // add a show
        showService.addShow("1","ShowName1",2,5,1);


        // then accessing a show that does not exist gets an exception
        Exception exception = assertThrows(NotFoundException.class, () ->showService.getShow("2"));
        assertEquals("No such show exists", exception.getMessage());

    }

    @Test
    void ShowServiceTest_GetAvaialableSeatsForNonExistingShow_ShouldThrowException() throws Exception {
        //given a showService
        // add a show
        // then accessing a show that does not exist gets an exception
        Exception exception = assertThrows(NotFoundException.class, () ->showService.getAvailableSeats("2"));
        assertEquals("No such show exists", exception.getMessage());

    }
}
