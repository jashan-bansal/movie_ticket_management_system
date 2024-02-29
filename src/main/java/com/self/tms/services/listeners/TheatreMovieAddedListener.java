package com.self.tms.services.listeners;

import com.self.tms.services.TheatreService;
import com.self.tms.models.events.MovieAddedEvent;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class TheatreMovieAddedListener {

    private final TheatreService theatreService;

    @Autowired
    public TheatreMovieAddedListener(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    /*@EventListener
    public void handleMovieAddedEvent(@NotNull MovieAddedEvent movieAddedEvent) {
        try {
            log.info("Events is listened here");
            theatreService.initialiseMovieInTheatre(movieAddedEvent.getMovie());
        } catch (Exception e) {
            log.error("Exception caught: ", e);
        }
    }*/


    /*@Override
    public void onApplicationEvent(MovieAddedEvent event) {
        log.info("Events is listened here");
        //theatreService.initialiseMovieInTheatre(event.getMovie());
    }*/
}
