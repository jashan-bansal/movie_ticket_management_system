package com.self.tms.models.events;

import com.self.tms.models.Movie;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ApplicationContextEvent;

@Slf4j
public class MovieAddedEvent extends ApplicationEvent {
    private Movie movie;

    public MovieAddedEvent(Object source, Movie movie) {
        super(source);
        log.info("Events is for movie added");
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }
}
