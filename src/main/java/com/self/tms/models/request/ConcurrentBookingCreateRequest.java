package com.self.tms.models.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ConcurrentBookingCreateRequest extends BookingCreateRequest{
    List<UUID> userIds;
}
