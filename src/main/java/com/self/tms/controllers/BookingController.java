package com.self.tms.controllers;

import com.self.tms.exceptions.BookingCreateException;
import com.self.tms.models.Booking;
import com.self.tms.models.request.BookingCreateRequest;
import com.self.tms.models.request.ConcurrentBookingCreateRequest;
import com.self.tms.services.BookingService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
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
            log.info("Request received to create booking: {}", bookingCreateRequest);

            if (bookingCreateRequest == null) {
                log.error("Invalid request to create booking: {}", bookingCreateRequest);
                return ResponseEntity.badRequest().body("Invalid request");
            }

            UUID showId = bookingCreateRequest.getShowId();
            List<String> userSelectedSeats = bookingCreateRequest.getSeats();
            UUID userId = bookingCreateRequest.getUserId();

            if (showId == null || userSelectedSeats == null || userId == null) {
                log.error("Invalid request to create booking: {}", bookingCreateRequest);
                return ResponseEntity
                        .badRequest()
                        .body("Invalid request! Please provide valid showId, seats and userId");
            }
            Booking booking = bookingService.createBooking(showId, userSelectedSeats, userId);
            log.info("Booking created successfully: {}", booking);

            return ResponseEntity.ok().body(booking);
        } catch (BookingCreateException e) {
            log.error("Error occurred while creating booking: " + e.getMessage());
            return ResponseEntity.status(Response.Status.BAD_REQUEST.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error occurred while creating booking: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Error occurred while creating booking.");
        }
    }

    public ResponseEntity getBookingDetails(UUID bookingId) {
        try {
            log.info("Request received to fetch booking details for bookingId: {}", bookingId);
            if (bookingId == null) {
                log.error("Invalid request to fetch booking details for bookingId: {}", bookingId);
                return ResponseEntity.badRequest().body("Booking Id is required! Please provide valid bookingId");
            }
            return ResponseEntity.ok(bookingService.getBookingDetails(bookingId));
        } catch (NotFoundException e) {
            log.error("Error occurred while fetching booking details: " + e.getMessage());
            return ResponseEntity.status(Response.Status.NOT_FOUND.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error occurred while fetching booking details.");
        }
    }

    public ResponseEntity concurrencyTest(ConcurrentBookingCreateRequest concurrentBookingCreateRequest) throws InterruptedException {
        try {
            if (concurrentBookingCreateRequest == null) {
                log.error("Invalid request to create booking: {}", concurrentBookingCreateRequest);
                return ResponseEntity.badRequest().body("Invalid request");
            }

            UUID showId = concurrentBookingCreateRequest.getShowId();
            List<String> userSelectedSeats = concurrentBookingCreateRequest.getSeats();
            List<UUID> userIds = concurrentBookingCreateRequest.getUserIds();

            if (showId == null || userSelectedSeats == null || userIds == null) {
                log.error("Invalid request to create booking: {}", concurrentBookingCreateRequest);
                return ResponseEntity
                        .badRequest()
                        .body("Invalid request! Please provide valid showId, seats and userId");
            }

            HashMap<UUID, String> userBookingMap = new HashMap<>();
            Thread bookingCreateRequest1 = new Thread(() -> {
                try {
                    bookingService.createBooking(showId, userSelectedSeats, userIds.get(0));
                    userBookingMap.put(userIds.get(0), "Booking created successfully");
                } catch (BookingCreateException e) {
                    userBookingMap.put(userIds.get(0), "Error occurred while creating booking via thread1: " + e.getMessage());
                    log.error("Error occurred while creating booking via thread1: " + e.getMessage());
                }
            });

            Thread bookingCreateRequest2 = new Thread(() -> {
                try {
                    bookingService.createBooking(showId, userSelectedSeats, userIds.get(1));
                    userBookingMap.put(userIds.get(1), "Booking created successfully");
                } catch (BookingCreateException e) {
                    userBookingMap.put(userIds.get(1), "Error occurred while creating booking via thread1: " + e.getMessage());
                    log.error("Error occurred while creating booking via thread2: " + e.getMessage());
                }
            });

            bookingCreateRequest1.start();
            bookingCreateRequest2.start();

            try {
                bookingCreateRequest1.join();
                bookingCreateRequest2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw e;
            }

            return ResponseEntity.ok().body(userBookingMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


}
