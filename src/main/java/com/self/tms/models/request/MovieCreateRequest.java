package com.self.tms.models.request;


import lombok.Data;

@Data
public class MovieCreateRequest {
    private String movieName;
    private int movieDurationInMinutes;
    //other details like Genre, description, rating, Language etc.

}
