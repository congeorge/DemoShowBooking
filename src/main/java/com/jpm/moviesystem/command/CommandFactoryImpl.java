package com.jpm.moviesystem.command;


import com.jpm.moviesystem.exceptions.BookingSystemException;
import com.jpm.moviesystem.exceptions.NoSuchCommandException;
import com.jpm.moviesystem.service.BookingService;
import com.jpm.moviesystem.service.ShowService;
import com.jpm.moviesystem.service.UserService;

import java.util.Arrays;

/**
 * Factory implementation for creating Command objects.
 *  // TODO bit of a cludge with adding security here ..
 */
public class CommandFactoryImpl implements CommandFactory {


    public final ShowService showService = new ShowService();
    public final BookingService bookingService = new BookingService();
    public final static String SETUP = "Setup";
    public final static String VIEW = "View";
    public final static String AVAILABILITY = "Availability";
    public final static String BOOKSHOW = "BookShow";
    public final static String CANCELBOOKING = "CancelBooking";


    // TODO bit of a cludge with adding security here ..
    public Command buildCommand(String[] inputArgs) throws BookingSystemException {
        Command operation = null;
        String[] commandParams = Arrays.copyOfRange(inputArgs, 1, inputArgs.length);
        System.out.println("command : " + inputArgs[0]);
        switch (inputArgs[0]) {
            case SETUP: {
                if (UserService.isAdminUser)
                    operation = new SetupShowCommand(showService, commandParams);
                else throw new NoSuchCommandException("Command only Valid for Admin User");
            }
            break;
            case VIEW: {
                if (UserService.isAdminUser)
                    operation = new ViewShowCommand(showService, commandParams);
                else throw new NoSuchCommandException("Command only Valid for Admin User");
            }
            break;
            case AVAILABILITY:
                operation = new ViewShowAvailableSeatsCommand(showService, commandParams);
                break;
            case BOOKSHOW:
                operation = new BookShowCommand(bookingService, showService, commandParams);
                break;
            case CANCELBOOKING:
                operation = new CancelBookingCommand(bookingService, commandParams);
                break;
            default:
                throw new NoSuchCommandException("Not a valid command");
        }
        return operation;
    }


}
