package com.self.tms.routers;


import com.self.tms.controllers.MovieController;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Slf4j
@Data
@RestController
@RequestMapping("api/v1/movie")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MovieRouter {

    @Autowired
    private final MovieController movieController;

    @GetMapping("/all")
    public ResponseEntity getAllMovie() {
        try {
            return movieController.getAllMovie();
        } catch (Exception e) {
            log.error("Error occurred while fetching all movies: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("")
    public ResponseEntity getMovieByName(@RequestParam("name") String name) {
        try {
            return movieController.getMovieDetailsByName(name);
        } catch (Exception e) {
            log.error("Error occurred while fetching all movies: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}
