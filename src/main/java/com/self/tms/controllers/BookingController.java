package com.self.tms.controllers;

import com.self.tms.models.Seat;
import com.self.tms.models.request.BookingCreateRequest;
import com.self.tms.services.BookingService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
@Slf4j
@Data
public class BookingController {

    @Autowired
    private final BookingService bookingService;

    public ResponseEntity createBooking(BookingCreateRequest bookingCreateRequest) {
        try {
            if (bookingCreateRequest == null) {
                log.error("Invalid request to create booking: {}", bookingCreateRequest);
                return ResponseEntity.badRequest().body("Invalid request");
            }

            UUID showId = bookingCreateRequest.getShowId();
            List<Seat> userSelectedSeats = bookingCreateRequest.getSeats();
            UUID userId = bookingCreateRequest.getUserId();

            if (showId == null || userSelectedSeats == null || userId == null) {
                log.error("Invalid request to create booking: {}", bookingCreateRequest);
                return ResponseEntity
                        .badRequest()
                        .body("Invalid request! Please provide valid showId, seats and userId");
            }

            bookingService.createBooking(showId, userSelectedSeats, userId);
            log.info("Booking created for showId: " + showId + " for userId: " + userId);
            return ResponseEntity.ok("Booking created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ResponseEntity getBookingDetails(UUID bookingId) {
        try {
            if (bookingId == null) {
                log.error("Invalid request to fetch booking details for bookingId: {}", bookingId);
                return ResponseEntity.badRequest().body("Booking Id is required! Please provide valid bookingId");
            }
            return ResponseEntity.ok(bookingService.getBookingDetails(bookingId));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
