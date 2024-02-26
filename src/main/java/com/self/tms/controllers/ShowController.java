package com.self.tms.controllers;

import com.self.tms.models.Show;
import com.self.tms.models.Theatre;
import com.self.tms.models.request.ShowCreateRequest;
import com.self.tms.services.ShowService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@Data
public class ShowController {

    @Autowired
    private final ShowService showService;

    public ResponseEntity addShow(ShowCreateRequest showCreateRequest) {
        try {
            UUID movieId = showCreateRequest.getMovieId();
            Theatre.Screen screen = showCreateRequest.getScreen();
            Date showStartTime = showCreateRequest.getShowStartTime();
            UUID theatreId = showCreateRequest.getTheatreId();

            if (movieId == null || screen == null || showStartTime == null || theatreId == null) {
                log.error("Invalid request to add show: {}", showCreateRequest);
                return ResponseEntity.badRequest().build();
            }

            showService.addShow(movieId, screen, showStartTime, theatreId);
            return ResponseEntity.ok("Show added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ResponseEntity getShows(UUID id, String searchType) {
        try {
            if (id == null || searchType == null) {
                log.error("Invalid request to fetch shows for id: {} and searchType: {}", id, searchType);
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok(showService.getShows(id, searchType));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ResponseEntity fetchTheatreMovieShows(UUID movieId, UUID theatreId) {
        try {
            if (movieId == null || theatreId == null) {
                log.error("Invalid request to fetch shows for movieId: {} and theatreId: {}", movieId, theatreId);
                return ResponseEntity.badRequest().build();
            }

            List<Show> shows = showService.getShowsForMovieAndTheatre(movieId, theatreId);
            return ResponseEntity.ok(shows);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ResponseEntity initialize() {
        try {
            showService.initializeShows();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
