package com.self.tms.services;

import com.self.tms.exceptions.BookingCreateException;
import com.self.tms.factories.SeatLockingProviderFactory;
import com.self.tms.interfaces.SeatLockProvider;
import com.self.tms.models.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.*;
import java.util.concurrent.locks.StampedLock;



@Service
@Data
@Slf4j
public class BookingService {
    private Map <UUID, Booking> bookingsToIdMap;
    private final SeatLockProvider<String> seatLockProvider;
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

    public Booking createBooking(UUID showId, List<String> userSelectedSeats, UUID userId) throws BookingCreateException {
        try {
            log.info("Request received to create booking: showId: {}, userSelectedSeats: {}, userId: {}",
                    showId, userSelectedSeats, userId);

            preBookingCreateValidations(showId, userSelectedSeats, userId);
            log.info("Pre validation check passed");
            List<String> unavailableSeats = validateShowAndGetUnavailbleSeats(showId);
            seatLockProvider.lockSeats(lock, unavailableSeats, userSelectedSeats);
            log.info("Seats locked successfully");

            Booking booking = Booking.builder()
                                .id(UUID.randomUUID())
                                .showId(showId)
                                .userId(userId)
                                .allocatedSeats(userSelectedSeats)
                                .bookingStatus(BookingStatus.Confirmed)
                                .build();
            addBooking(booking);
            System.out.println("Booking created successfully");

            return booking;
        } catch (BookingCreateException | Exception e) {
            throw e;
        }
    }

    private List<String> validateShowAndGetUnavailbleSeats(UUID showId) throws BookingCreateException {
        List<String> unavailableSeats = showService.getShowLockedSeatMap(showId);
        if (Objects.isNull(unavailableSeats)) {
            throw new BookingCreateException("Invalid show id! Please provide valid inputs.",
                    HttpStatus.NO_CONTENT.value());
        }

        return unavailableSeats;
    }

    private void preBookingCreateValidations(UUID showId, List<String> userSelectedSeats, UUID userId) throws BookingCreateException {
        log.info("Validating request to create booking: showId: {}, userSelectedSeats: {}, userId: {}",
                showId, userSelectedSeats, userId);

        if (Objects.isNull(showId) || Objects.isNull(userSelectedSeats) || Objects.isNull(userId)) {
            throw new BookingCreateException("Invalid request! Please provide valid showId, seats and userId",
                    HttpStatus.BAD_REQUEST.value());
        }

        User user = userService.getUser(userId);
        if (Objects.isNull(user)) {
            throw new BookingCreateException("Invalid userId! Please provide valid userId",
                    HttpStatus.NO_CONTENT.value());
        }

    }

    public Booking getBookingDetails(UUID bookingId) {
        if (!bookingsToIdMap.containsKey(bookingId)) {
            throw new NotFoundException("No such booking!");
        }
        return bookingsToIdMap.get(bookingId);
    }

}
