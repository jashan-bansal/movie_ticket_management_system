package com.self.tms.models.request;

import com.self.tms.models.Theatre;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ShowCreateRequest {
    UUID movieId;
    Theatre.Screen screen;
    Date showStartTime;
    UUID theatreId;
}
