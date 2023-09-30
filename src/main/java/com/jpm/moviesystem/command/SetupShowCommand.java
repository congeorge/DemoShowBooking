package com.jpm.moviesystem.command;

import com.jpm.moviesystem.exceptions.IncorrectParametersException;
import com.jpm.moviesystem.service.ShowService;
import lombok.Setter;

@Setter
public class SetupShowCommand implements Command{

     public void setShowService(ShowService showService) {
        this.showService = showService;
    }

    ShowService showService;
    private String showId;


    private  String showName;
    private int numberOfRows;
    private int numberOfSeatPerRow;

    private int cancellationWindowInMins;




    public SetupShowCommand(ShowService service,String[] inputs) throws IncorrectParametersException {
        this.showService=service;
        if(inputs==null || inputs.length < 5)
            throw new IncorrectParametersException("Setup :<Show Number> <Number of Rows> <Number of seats per row>  <Cancellation window in minutes>");
        showId = inputs[0];
        showName = inputs[1];
        numberOfRows = Integer.parseInt((String)inputs[2]);
        numberOfSeatPerRow = Integer.parseInt((String)inputs[3]);
        cancellationWindowInMins = Integer.parseInt((String)inputs[4]);
        if(numberOfRows<0 || numberOfSeatPerRow<0)
            throw new IncorrectParametersException("Enter Valid rows and seats");
    }



    @Override
    public void execute() {
        showService.addShow(showId, showName, numberOfRows, numberOfSeatPerRow, cancellationWindowInMins);
        showService.listShows().forEach(System.out::println);
    }
}
