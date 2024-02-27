package com.self.tms.models.request;

import com.self.tms.models.Seat;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BookingCreateRequest {
    UUID showId;
    List<String> seats;
    UUID userId;
}
