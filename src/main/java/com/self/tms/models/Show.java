package com.self.tms.models;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Show {
    UUID showId;
    UUID movieId;

    UUID theatreId;
    Theatre.Screen screen;
    Date showStartTime;

    public Show(UUID movieId, Theatre.Screen screen, Date showStartTime, UUID theatreId) {
        this.showId = UUID.randomUUID();
        this.movieId = movieId;
        this.screen = screen;
        this.showStartTime = showStartTime;
        this.theatreId = theatreId;
    }
}
