package com.jpm.moviesystem.model;


import com.jpm.moviesystem.exceptions.IncorrectParametersException;
import com.jpm.moviesystem.utils.PropertyLoader;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class Show {

    private String showId;




    private  String showName;
    private int numberOfRows;



    private int numberOfSeatPerRow;

    private int cancellationWindowInMins;
    private final List<Seat> seats;

    // Todo: Add show time
    private LocalDateTime showTime;
    // Todo: Add show duration
     private int showDurationinMinutes;
    public static final int MAXNOSOFROWS  = Integer.parseInt(PropertyLoader.getProperty("MaximumNoOfRows"));

    public static final int MAXSEATPERROWS =Integer.parseInt(PropertyLoader.getProperty("MaximumSeatsPerRow"));


    public Show(@NonNull final String showId, @NonNull final String showName, @NonNull final int numberOfRows, @NonNull int numberOfSeatPerRow , @NonNull int cancellationWindowInMins)
    {
        this.showId = showId;
        this.showName = showName;
        if(!validateShow(numberOfRows, numberOfSeatPerRow))
            throw new IncorrectParametersException("Incorrect size for rows and seats");

        this.numberOfRows = numberOfRows;
        this.numberOfSeatPerRow = numberOfSeatPerRow;
        this.cancellationWindowInMins=cancellationWindowInMins;
        this.seats=new ArrayList<>();
        initializeSeat(numberOfRows, numberOfSeatPerRow);


    }

    private void initializeSeat(int numberOfRows, int numberOfSeatPerRow) {
        for (int row = 0; row < numberOfRows; row++) {
            for (int seatNo = 0; seatNo < numberOfSeatPerRow; seatNo++) {
                Seat seat = new Seat(row, seatNo,showId);
                seats.add(seat);
            }
        }
    }

    private boolean validateShow(int numberOfRows, int numberOfSeatPerRow) {
        return numberOfRows <= MAXNOSOFROWS && numberOfSeatPerRow <= MAXSEATPERROWS;

    }



    @Override
    public String toString() {
        return "Show{" +
                "showId=" + showId +
                ", showName='" + showName + '\'' +
                ", numberOfRows=" + numberOfRows +
                ", numberOfSeatPerRow=" + numberOfSeatPerRow +
                ", cancellationWindowInMins=" + cancellationWindowInMins +
                ", seats=" + seats.stream().map(Seat::toString).toList() +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return numberOfRows == show.numberOfRows && numberOfSeatPerRow == show.numberOfSeatPerRow && cancellationWindowInMins == show.cancellationWindowInMins && showId.equals(show.showId) && showName.equals(show.showName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showId, showName, numberOfRows, numberOfSeatPerRow, cancellationWindowInMins);
    }


}
