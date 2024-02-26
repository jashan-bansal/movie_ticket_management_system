package com.self.tms.controllers;

import com.self.tms.models.request.TheatreCreateRequest;
import com.self.tms.services.TheatreServices;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Data
@Slf4j
public class TheatreController {

    @Autowired
    private final TheatreServices theatreService;

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
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(theatreService.getTheatreInCity(city));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ResponseEntity initializeTheatre() {
        try {
            theatreService.initializeTheatres();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
