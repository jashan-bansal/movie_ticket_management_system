package com.self.tms.routers;

import com.self.tms.controllers.TheatreController;
import com.self.tms.models.request.TheatreCreateRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Slf4j
@RestController
@RequestMapping("/api/v1/theatre")
@Data
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TheatreRouter {

    @Autowired
    private final TheatreController theatreController;

    @PostMapping("")
    public ResponseEntity createTheatre(@RequestBody TheatreCreateRequest theatreCreateRequest) {
        try {
            return theatreController.createTheatre(theatreCreateRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("")
    public ResponseEntity getTheatresInCity(@RequestParam("city") String city) {
        try {
            return theatreController.getTheatresInCity(city);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/initialiseMovieInTheatre")
    public ResponseEntity initialiseMovieInTheatre() {
        try {
            return theatreController.initialiseMovieInTheatre();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
