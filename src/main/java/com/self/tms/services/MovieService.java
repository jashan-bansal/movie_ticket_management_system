package com.self.tms.services;

import com.self.tms.models.Movie;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Data
public class MovieService {
    private Map<String, Movie> moviesNameToMovieMap;
    private Map<UUID, Movie> movieMap;

    public MovieService() {
        moviesNameToMovieMap = new HashMap<>();
        movieMap = new HashMap<>();
        initialise();
    }

    public void addMovie(Movie movie) {
        moviesNameToMovieMap.put(movie.getMovieName(), movie);
        movieMap.put(movie.getMovieId(), movie);
    }

    public void initialise() {
        Movie movie1 = new Movie("The Dark Knight", 152);
        Movie movie2 = new Movie("Inception", 148);
        addMovie(movie1);
        addMovie(movie2);
    }

    public Movie getMovie(UUID movieId) {
        return movieMap.get(movieId);
    }
}
