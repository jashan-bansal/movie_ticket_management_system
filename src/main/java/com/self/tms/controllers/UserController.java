package com.self.tms.controllers;

import com.self.tms.models.request.UserCreateRequest;
import com.self.tms.services.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Data
@Slf4j
public class UserController {
    @Autowired
    private final UserService userService;

    public ResponseEntity addUser(UserCreateRequest userCreateRequest) throws Exception {
        try {
            if (userCreateRequest == null) {
                return ResponseEntity.badRequest().body("Invalid create user request");
            }
            String name = userCreateRequest.getName();
            String email = userCreateRequest.getEmail();
            userService.addUser(name, email);
            return ResponseEntity.ok("User added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ResponseEntity getUserDetail(UUID userId) throws Exception {
        try {
            if (userId == null) {
                return ResponseEntity.badRequest().body("Missing userId!");
            }
            return ResponseEntity.ok(userService.getUser(userId));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ResponseEntity getAllUsers() throws Exception {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
