package com.jpm.moviesystem.command;

import com.jpm.moviesystem.exceptions.IncorrectParametersException;
import com.jpm.moviesystem.exceptions.NotFoundException;
import com.jpm.moviesystem.service.ShowService;

public class ViewShowAvailableSeatsCommand implements Command {


    ShowService showService;
    String showId;

    public ViewShowAvailableSeatsCommand(ShowService service, String[] inputs) throws IncorrectParametersException {
        this.showService = service;
        if (inputs == null || inputs.length < 1)
            throw new IncorrectParametersException("Availability    <Show Number>");
        showId = inputs[0];
    }

    @Override
    public void execute() {
        try {
            System.out.println("Available Seats are :");
            showService.getAvailableSeats(showId).forEach(
                    (seat) ->
                    {
                        System.out.print("Row" + seat.getRowNo() + "  Seat:" + seat.getSeatNo() + " -> ");
                    });

        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }


    }
}
