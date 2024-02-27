package com.self.tms.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Booking {
    public UUID id;
    public UUID showId;
    public UUID userId;
    public BookingStatus bookingStatus;
    public List<String> allocatedSeats;

    public static BookingStatus isConfirmed() {
        return BookingStatus.Confirmed;
    }

}
