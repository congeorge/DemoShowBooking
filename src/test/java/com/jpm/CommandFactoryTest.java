package com.jpm;


import com.jpm.moviesystem.command.*;
import com.jpm.moviesystem.exceptions.NoSuchCommandException;
import com.jpm.moviesystem.service.BookingService;
import com.jpm.moviesystem.service.ShowService;
import com.jpm.moviesystem.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandFactoryTest {
    private CommandFactory factory;
    private ShowService showService;
    private BookingService bookingService;



    @BeforeEach
    void setUp() throws Exception {
        factory = new CommandFactoryImpl();
        showService = new ShowService();
        bookingService=new BookingService();
        UserService.isAdminUser=true;




    }

    @Test
    void CommandFactoryTest_InvalidCommand_ShouldThrowException() throws Exception {
        Exception exception = assertThrows(NoSuchCommandException.class, () ->factory.buildCommand(new String[]{"Draw","1","abhi","2","5", "1"}));
        assertEquals("Not a valid command", exception.getMessage());

    }

    @Test
    void CommandFactory_Build_SetupShowCommand_WithCorrectParams() throws Exception {

        Command command =factory.buildCommand(new String[]{"Setup","1","abhi","2","5", "1"});

        SetupShowCommand expectedCommand = new SetupShowCommand( showService,new String[]{"1","abhi","2","5", "1"});

        Assertions.assertTrue((command instanceof SetupShowCommand));
      }

    @Test
    void CommandFactory_Build_ViewShowCommand_WithCorrectParams() throws Exception {

        Command command =factory.buildCommand(new String[]{"View","1"});

        ViewShowCommand expectedCommand = new ViewShowCommand(showService,new String[]{"1"});

        Assertions.assertTrue((command instanceof ViewShowCommand));
    }

    @Test
    void CommandFactory_Build_ViewShowAvailableSeatsCommand_WithCorrectParams() throws Exception {

        Command command =factory.buildCommand(new String[]{"Availability","1"});

        ViewShowAvailableSeatsCommand expectedCommand = new ViewShowAvailableSeatsCommand(showService,new String[]{"1"});

        Assertions.assertTrue((command instanceof ViewShowAvailableSeatsCommand));
    }

    @Test
    void CommandFactory_Build_BookShowCommand_WithCorrectParams() throws Exception {

        Command command =factory.buildCommand(new String[]{"BookShow","1","90230943","11","12"});

        BookShowCommand expectedCommand = new BookShowCommand(bookingService,showService,new String[]{"1","90230943","11","12"});

        Assertions.assertTrue((command instanceof BookShowCommand));
    }

    @Test
    void CommandFactory_Build_CancelBookingCommand_WithCorrectParams() throws Exception {

        Command command =factory.buildCommand(new String[]{"CancelBooking","190230943" ,"902309431"});

        CancelBookingCommand expectedCommand = new CancelBookingCommand(bookingService,new String[]{"CancelBooking", "190230943" ,"902309431"});


        Assertions.assertTrue((command instanceof CancelBookingCommand));
    }

}



