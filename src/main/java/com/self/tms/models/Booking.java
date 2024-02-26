package com.self.tms.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Booking {
    public UUID id;

    public UUID showId;
    public UUID userId;
    public BookingStatus bookingStatus;
    public List<Seat> allocatedSeats;

    public static BookingStatus isConfirmed() {
        return BookingStatus.Confirmed;
    }

}
