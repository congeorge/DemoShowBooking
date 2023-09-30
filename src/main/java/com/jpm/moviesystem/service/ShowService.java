package com.jpm.moviesystem.service;

import com.jpm.moviesystem.exceptions.InvalidStateException;
import com.jpm.moviesystem.exceptions.NotFoundException;
import com.jpm.moviesystem.model.Seat;
import com.jpm.moviesystem.model.SeatStatus;
import com.jpm.moviesystem.model.Show;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ShowService {

    private final Map<String, Show> showsmap;

    public ShowService() {
        this.showsmap = new ConcurrentHashMap<>();

    }

    public List<Show> listShows() {
        return showsmap.values().stream().toList();

    }

    public Show addShow(String showId, String showName, int numberOfRows, int numberOfSeatPerRow, int cancellationWindowInMins) {
        if (showsmap.containsKey(showId))
            throw new InvalidStateException("Show already exists");
        Show show = new Show(showId, showName, numberOfRows, numberOfSeatPerRow, cancellationWindowInMins);
        showsmap.put(showId, show);
        return show;
    }


    public Show getShow(String showId) throws NotFoundException {
        if (!showsmap.containsKey(showId))
            throw new NotFoundException("No such show exists");
        return showsmap.get(showId);
    }

    public List<Seat> getAvailableSeats(String showId) throws NotFoundException {
        if (!showsmap.containsKey(showId)) {
            throw new NotFoundException("No such show exists");
        }
        return showsmap.get(showId).getSeats().stream().filter(s -> (s.getStatus().equals(SeatStatus.AVAILABLE))).toList();
    }


    public List<Seat> getBookedSeats(String showId) throws NotFoundException {
        if (!showsmap.containsKey(showId))
            throw new NotFoundException("No such show exists");
        return showsmap.get(showId).getSeats().stream().filter(s -> (s.getStatus().equals(SeatStatus.BOOKED))).toList();
    }
}
