package com.self.tms.controllers;

import com.self.tms.services.MovieService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

@Component
@Slf4j
@Data
public class MovieController {

    @Autowired
    private final MovieService movieService;

    public ResponseEntity getAllMovie() {
        try {
            log.info("Request received to fetch all movies");
            return ResponseEntity.ok(movieService.getAllMovies());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error occurred while fetching movies: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Error occurred while fetching movies.");
        }
    }

    public ResponseEntity getMovieDetailsByName(String name) {
        try {
            log.info("Request received to fetch movie details for movieName: {}", name);
            if (name == null) {
                log.error("Invalid request to fetch movie details for movieName: {}", name);
                return ResponseEntity.badRequest().body("Movie Id is required! Please provide valid movieId");
            }
            return ResponseEntity.ok(movieService.getMoviesNameToMovieMap(name));
        } catch (NotFoundException e) {
            e.printStackTrace();
            log.error("Error occurred while fetching movie details: " + e.getMessage());
            return ResponseEntity.status(Response.Status.NOT_FOUND.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error occurred while fetching movie details: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

}
