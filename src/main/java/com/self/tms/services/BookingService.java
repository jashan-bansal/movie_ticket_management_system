package com.self.tms.services;

import com.self.tms.factories.SeatLockingProviderFactory;
import com.self.tms.interfaces.SeatLockProvider;
import com.self.tms.models.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.StampedLock;



@Service
@Data
public class BookingService {
    private Map <UUID, Booking> bookingsToIdMap;
    private final SeatLockProvider<Seat> seatLockProvider;
    private StampedLock lock;
    private final UserService userService;
    private final ShowService showService;
    private Map<UUID, List<Booking>> userBookingMap;
    private final LockStrategy LOCK_STRATEGY = LockStrategy.IN_MEMORY;

    @Autowired
    public BookingService(UserService userService, ShowService showService) {
        this.showService = showService;
        this.bookingsToIdMap = new HashMap<>();
        this.seatLockProvider = SeatLockingProviderFactory.getLockingStrategy(LOCK_STRATEGY);
        this.lock = new StampedLock();
        this.userService = userService;
        this.userBookingMap = new HashMap<>();
    }

    private void addBooking(Booking booking) {
        bookingsToIdMap.put(booking.getId(), booking);
        addUserBooking(booking);
    }

    private void addUserBooking(Booking booking) {
        List<Booking> bookings = userBookingMap.getOrDefault(booking.getUserId(), new ArrayList<>());
        bookings.add(booking);
        userBookingMap.put(booking.getUserId(), bookings);
    }

    public void createBooking(UUID showId, List<Seat> userSelectedSeats, UUID userId) {
        try {
            List<Seat> lockedSeats = showService.getShowLockedSeatMap(showId);

            if (Objects.isNull(lockedSeats) || Objects.isNull(userId)) {
                throw new RuntimeException("Invalid show or user");
            }

            Boolean isSeatLockSuccessful = seatLockProvider.lockSeats(lock, lockedSeats, userSelectedSeats);
            if (!isSeatLockSuccessful) {
                throw new RuntimeException("Seats Allocation Failed! Please try again.");
            }
            Booking booking = new Booking(UUID.randomUUID(), showId, userId, Booking.isConfirmed(), userSelectedSeats);
            addBooking(booking);
            System.out.println("Booking created successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Booking getBookingDetails(UUID bookingId) {
        if (!bookingsToIdMap.containsKey(bookingId)) {
            throw new RuntimeException("No such booking!");
        }
        return bookingsToIdMap.get(bookingId);
    }

}
