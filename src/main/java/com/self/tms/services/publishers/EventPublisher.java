package com.self.tms.services.publishers;

import com.self.tms.models.Movie;
import com.self.tms.models.events.MovieAddedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishMovieAddedEvent(Movie movie) {
        log.info("Events is for movie publishing");
        applicationEventPublisher.publishEvent(new MovieAddedEvent(this, movie));
    }

}
