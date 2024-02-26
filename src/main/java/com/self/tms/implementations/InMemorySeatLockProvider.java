package com.self.tms.implementations;

import com.self.tms.interfaces.SeatLockProvider;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.StampedLock;

@Data
@Service
public class InMemorySeatLockProvider<T> implements SeatLockProvider {

    @Override
    public Boolean lockSeats(final StampedLock lock, final List unavailableSeats, final List userSelectedSeats) {
        Boolean isSeatLockSuccess = false;
        long stampVal = lock.writeLock();
        try {
            if (!lock.validate(stampVal)) {
                throw new RuntimeException("Lock expired! Please try again.");
            }
            boolean anySeatAlreadyBooked = isAnySeatAlreadyBooked(unavailableSeats, userSelectedSeats);
            if (anySeatAlreadyBooked) {
                throw new RuntimeException("Requested Seats are not available");
            }

            blockSeats(unavailableSeats, userSelectedSeats);
            isSeatLockSuccess = true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlockWrite(stampVal);
        }
        return isSeatLockSuccess;
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
