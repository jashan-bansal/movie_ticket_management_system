package com.self.tms.implementations;

import com.self.tms.exceptions.BookingCreateException;
import com.self.tms.interfaces.SeatLockProvider;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.StampedLock;

@Data
@Service
public class InMemorySeatLockProvider<T> implements SeatLockProvider {

    @Override
    public void lockSeats(final StampedLock lock, final List unavailableSeats, final List userSelectedSeats) throws BookingCreateException {
        long stampVal = lock.writeLock();
        try {
            if (!lock.validate(stampVal)) {
                throw new BookingCreateException("Booking time expired. Please try again!",
                        HttpStatus.INTERNAL_SERVER_ERROR.value());
            }

            boolean anySeatAlreadyBooked = isAnySeatAlreadyBooked(unavailableSeats, userSelectedSeats);
            if (anySeatAlreadyBooked) {
                throw new BookingCreateException("Requested seats are already booked!",
                        HttpStatus.INTERNAL_SERVER_ERROR.value());
            }

            blockSeats(unavailableSeats, userSelectedSeats);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } catch (BookingCreateException e) {
            throw e;
        } finally {
            lock.unlockWrite(stampVal);
        }
    }

    private void blockSeats(final List<T> unavailableSeats, final List<T> seats) {
        seats.forEach((seat) -> unavailableSeats.add(seat));
    }

    private boolean isAnySeatAlreadyBooked(final List<T> unavailableSeats, final List<T> seats) {
        for (T seatId : seats) {
            if (unavailableSeats.contains(seatId)) {
                return true;
            }
        }
        return false;
    }

}
