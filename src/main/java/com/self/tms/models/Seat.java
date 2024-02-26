package com.self.tms.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class Seat {
    private String seatNumber;

    public Seat(int seatNumber, char row) {
        this.seatNumber = row + String.valueOf(seatNumber);
    }

}
