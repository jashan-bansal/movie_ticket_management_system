package com.self.tms.models;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Show {
    UUID showId;
    UUID movieId;

    UUID theatreId;
    UUID screenId;
    Date showStartTime;

    public Show(UUID movieId, UUID screenId, Date showStartTime, UUID theatreId) {
        this.showId = UUID.randomUUID();
        this.movieId = movieId;
        this.screenId = screenId;
        this.showStartTime = showStartTime;
        this.theatreId = theatreId;
    }
}
