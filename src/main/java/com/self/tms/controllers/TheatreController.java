package com.self.tms.controllers;

import com.self.tms.models.request.TheatreCreateRequest;
import com.self.tms.services.TheatreService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.Objects;

@Component
@Data
@Slf4j
public class TheatreController {

    @Autowired
    private final TheatreService theatreService;

    public ResponseEntity createTheatre(TheatreCreateRequest theatreCreateRequest) {
        try {
            if (Objects.isNull(theatreCreateRequest)) {
                log.error("Invalid request to create theatre: {}", theatreCreateRequest);
                return ResponseEntity.badRequest().build();
            }

            String theatreName = theatreCreateRequest.getTheatreName();
            String cityName = theatreCreateRequest.getCityName();
            if (theatreName == null || cityName == null) {
                ResponseEntity.badRequest().build();
            }

            theatreService.addTheatre(theatreName, cityName);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ResponseEntity getTheatresInCity(String city) {
        try {
            if (city == null) {
                log.error("Invalid request to fetch theatres for city: {}", city);
                return ResponseEntity.status(400).body("Invalid request");
            }
            return ResponseEntity.ok(theatreService.getTheatreInCity(city));
        } catch (NotFoundException e) {
            e.printStackTrace();
            log.error("Error occurred while fetching theatres: " + e.getMessage());
            return ResponseEntity.status(Response.Status.NOT_FOUND.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error occurred while fetching theatres: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error occurred while fetching theatres.");
        }
    }

    public ResponseEntity initializeTheatre() {
        try {
            theatreService.initializeTheatres();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error occurred while initializing theatres.");
        }
    }

    public ResponseEntity initialiseMovieInTheatre() {
        try {
            theatreService.initialiseMovieInTheatre();
            return ResponseEntity.ok().body("Movie initialised in theatre");
        } catch (NotFoundException e) {
            log.error("Error occurred while initializing movie in theatre: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(Response.Status.NOT_FOUND.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error occurred while initializing movie in theatre: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error occurred while initializing movie in theatre.");
        }
    }

}
