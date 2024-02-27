package com.self.tms.routers;

import com.self.tms.controllers.BookingController;
import com.self.tms.models.request.BookingCreateRequest;
import com.self.tms.models.request.ConcurrentBookingCreateRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Data
@Slf4j
@RestController
@RequestMapping("/api/v1/booking")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingRouter {
    @Autowired
    private final BookingController bookingController;


    @PostMapping("")
    public ResponseEntity createBooking(@RequestBody BookingCreateRequest bookingCreateRequest) {
        try {
            return bookingController.createBooking(bookingCreateRequest);
        } catch (Exception e) {
            log.error("Error occurred while creating booking: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("")
    public ResponseEntity getBookingDetails(@RequestParam("id") UUID bookingId) {
        try {
            log.info("Booking fetched for bookingId: " + bookingId);
            return bookingController.getBookingDetails(bookingId);
        } catch (Exception e) {
            log.error("Error occurred while fetching booking: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/concurrency")
    public ResponseEntity concurrencyTest(@RequestBody ConcurrentBookingCreateRequest concurrentBookingCreateRequest) {
        try {
            return bookingController.concurrencyTest(concurrentBookingCreateRequest);
        } catch (Exception e) {
            log.error("Error occurred while cancelling booking: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}
