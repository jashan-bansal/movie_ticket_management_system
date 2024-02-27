package com.self.tms.routers;


import com.self.tms.controllers.ShowController;
import com.self.tms.models.request.ShowCreateRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Data
@RestController
@RequestMapping("api/v1/show")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ShowRouter {

    @Autowired
    private ShowController showController;

    @PostMapping("")
    public ResponseEntity addShow(@RequestBody ShowCreateRequest showCreateRequest) {
        try {
            if (Objects.isNull(showCreateRequest)) {
                log.error("Invalid request to add show: {}", showCreateRequest);
                return ResponseEntity.badRequest().build();
            }

            return showController.addShow(showCreateRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("")
    public ResponseEntity fetchTheatreMovieShows(@RequestParam("movieId") UUID movieId, @RequestParam("theatreId") UUID theatreId) {
        try {
            return showController.fetchTheatreMovieShows(movieId, theatreId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/initialize")
    public ResponseEntity initialize() {
        try {
            showController.initialize();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
