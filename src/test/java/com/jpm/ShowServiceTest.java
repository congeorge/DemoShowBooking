package com.jpm;

import com.jpm.moviesystem.exceptions.IncorrectParametersException;
import com.jpm.moviesystem.exceptions.InvalidStateException;
import com.jpm.moviesystem.exceptions.NoSuchCommandException;
import com.jpm.moviesystem.exceptions.NotFoundException;
import com.jpm.moviesystem.model.Show;
import com.jpm.moviesystem.service.ShowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.jpm.moviesystem.model.Show.MAXNOSOFROWS;
import static com.jpm.moviesystem.model.Show.MAXSEATPERROWS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShowServiceTest {


    private ShowService showService;


    @BeforeEach
    void setUp() throws Exception {
        showService = new ShowService();
    }

    @Test
    void ShowServiceTest_AddAndGetShow_AddOne() {
     //given a showServive

     // when you add a show
      showService.addShow("1","ShowName1",2,5,1);

      // Expected
        Show expectedShow = new Show("1","ShowName1",2,5,1);

      // then you should be able to get the show
        assertEquals(expectedShow,showService.getShow("1"));
        assertEquals(1,showService.listShows().size());
        assertEquals(expectedShow.getShowId(),showService.getShow("1").getShowId());
        assertEquals(expectedShow.getShowName(),showService.getShow("1").getShowName());
        assertEquals(expectedShow.getSeats().size(),showService.getShow("1").getSeats().size());
        assertEquals(expectedShow.getCancellationWindowInMins(),showService.getShow("1").getCancellationWindowInMins());
        assertEquals(expectedShow.getNumberOfRows(),showService.getShow("1").getNumberOfRows());
        assertEquals(expectedShow.getNumberOfSeatPerRow(),showService.getShow("1").getNumberOfSeatPerRow());
    }


    @Test
    void ShowServiceTest_AddAndGetShow_AddMultiple()  {
        //given a showService

        // when you add a show
        showService.addShow("1","ShowName1",2,5,1);
        showService.addShow("2","ShowName2",2,5,1);
        showService.addShow("3","ShowName3",2,5,1);


        // Expected
        int expectedNumberShows = 3;

        // validate the list of shows
        assertEquals(expectedNumberShows,showService.listShows().size());

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
    void ShowServiceTest_CreateShow_AboveSeatingLimits_ShouldThrowException() throws Exception {
        //given a showService
        // add a show
        // then accessing a show that does not exist gets an exception

        Exception seatException = assertThrows(IncorrectParametersException.class, () ->showService.addShow("1","ShowName1",10,MAXSEATPERROWS+1,1));
        assertEquals("Incorrect size for rows and seats", seatException.getMessage());


        Exception rowException = assertThrows(IncorrectParametersException.class, () ->showService.addShow("1","ShowName1",MAXNOSOFROWS+1,5,1));
        assertEquals("Incorrect size for rows and seats", rowException.getMessage());
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
