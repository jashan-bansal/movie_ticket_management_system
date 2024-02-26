package com.self.tms.factories;

import com.self.tms.implementations.InMemorySeatLockProvider;
import com.self.tms.interfaces.SeatLockProvider;
import com.self.tms.models.LockStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SeatLockingProviderFactory {

    public static SeatLockProvider getLockingStrategy(LockStrategy strategy) {
        switch (strategy) {
            case IN_MEMORY:
                return new InMemorySeatLockProvider();
            case TRANSIENT_STORE:
            case PERSISTENT_STORE:
            default:
                log.error("Invalid Locking Strategy: " + strategy);
                return null;
        }
    }
}
