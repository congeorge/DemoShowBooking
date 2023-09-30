package com.jpm.moviesystem.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
public class Seat {

    private String id;

    private int seatNo;

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + rowNo +
                ", seat=" + seatNo +
                '}';
    }

    private int rowNo;


    SeatStatus status;


    private String showId;

    // TODO : Add lock_until and version for optimistic locking
    Timestamp lockUntil;
    int version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return seatNo == seat.getSeatNo() && rowNo == seat.getRowNo() && showId.equals(seat.getShowId());
    }

    public Seat(int rowNo, int seatNo, String showId) {
        this.rowNo = rowNo;
        this.seatNo = seatNo;
        this.showId = showId;
        this.status = SeatStatus.AVAILABLE;
        this.version = 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatNo, rowNo, showId);
    }


}
