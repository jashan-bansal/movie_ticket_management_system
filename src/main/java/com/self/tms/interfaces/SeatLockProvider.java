package com.self.tms.interfaces;

import com.self.tms.exceptions.BookingCreateException;
import com.self.tms.models.Seat;

import java.util.List;
import java.util.concurrent.locks.StampedLock;

public interface SeatLockProvider<T> {
    void lockSeats(StampedLock lock, List<T> unavailableSeats, List<T> userSelectedSeats) throws BookingCreateException;
}
