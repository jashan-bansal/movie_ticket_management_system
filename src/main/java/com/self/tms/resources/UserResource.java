package com.self.tms.resources;

import com.self.tms.controllers.UserController;
import com.self.tms.models.request.UserCreateRequest;
import com.self.tms.services.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Data
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Autowired
    private final UserController userController;

    @PostMapping("")
    public ResponseEntity createUser(@RequestBody UserCreateRequest userCreateRequest) {
        try {
            return userController.addUser(userCreateRequest);
        } catch (Exception e) {
           log.error("Error occurred while creating user: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity getUserDetails(@QueryParam("id") UUID userId) {
        try {
            return userController.getUserDetail(userId);
        } catch (Exception e) {
            log.error("Error occurred while fetching user: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllUsers() {
        try {
            return userController.getAllUsers();
        } catch (Exception e) {
            log.error("Error occurred while fetching all users: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
