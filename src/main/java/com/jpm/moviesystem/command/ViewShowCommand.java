package com.jpm.moviesystem.command;

import com.jpm.moviesystem.exceptions.IncorrectParametersException;
import com.jpm.moviesystem.exceptions.NotFoundException;
import com.jpm.moviesystem.service.ShowService;

public class ViewShowCommand implements Command {


    ShowService showService;
    String showId;

    public ViewShowCommand(ShowService service, String[] inputs) throws IncorrectParametersException {
        this.showService = service;
        if (inputs == null || inputs.length < 1)
            throw new IncorrectParametersException("View   <Show Number>");
        showId = inputs[0];
    }

    @Override
    public void execute() {
        try {
            System.out.println((showService.getShow(showId)));
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }


    }
}
