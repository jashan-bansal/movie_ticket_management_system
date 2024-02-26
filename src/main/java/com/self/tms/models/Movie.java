package com.self.tms.models;

import lombok.Data;

import java.util.UUID;

@Data
public class Movie {
    private UUID id;
    private String movieName;
    private int movieDurationInMinutes;

    //other details like Genre, description, rating, Language etc.

    public Movie(String movieName, int movieDurationInMinutes) {
        this.id = UUID.randomUUID();
        this.movieName = movieName;
        this.movieDurationInMinutes = movieDurationInMinutes;
    }

    public UUID getMovieId() {
        return id;
    }

    public void setMovieId(UUID movieId) {
        this.id = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getMovieDuration() {
        return movieDurationInMinutes;
    }

    public void setMovieDuration(int movieDuration) {
        this.movieDurationInMinutes = movieDuration;
    }
}
