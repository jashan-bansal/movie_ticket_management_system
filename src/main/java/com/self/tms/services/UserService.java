package com.self.tms.services;

import com.self.tms.models.User;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.*;

@Service
@Data
public class UserService {
    private Map<UUID, User> userMap;

    public UserService () {
        userMap = new HashMap<>();
        initialize();
    }

    public void addUser(String name, String email) {
        User user = new User(name, email);
        userMap.put(user.getId(), user);
    }

    public void initialize(){
        addUser( "user1", "user1@gmail.com");
        addUser( "user2", "user2@gmail.com");
    }

    public User getUser(UUID userId){
        if (!userMap.containsKey(userId)) {
            throw new NotFoundException("User not found");
        }
        return userMap.get(userId);
    }

    public List<User> getAllUsers(){
        return userMap.values().stream().toList();
    }

}
