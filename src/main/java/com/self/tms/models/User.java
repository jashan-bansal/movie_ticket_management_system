package com.self.tms.models;

import lombok.Data;

import java.util.UUID;

@Data
public class User {
    public UUID id;
    public String name;
    public String email;

    public User(String name, String email) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
    }

}
