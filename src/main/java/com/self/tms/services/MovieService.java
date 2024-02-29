package com.self.tms.services;

import com.self.tms.models.Movie;
import com.self.tms.services.publishers.EventPublisher;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.*;

@Service
@Data
@Slf4j
public class MovieService {
    private Map<String, Movie> moviesNameToMovieMap;
    private final EventPublisher eventPublisher;
    private Map<UUID, Movie> movieMap;

    @Autowired
    public MovieService(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        moviesNameToMovieMap = new HashMap<>();
        movieMap = new HashMap<>();
    }

    public void addMovie(Movie movie) {
        moviesNameToMovieMap.put(movie.getMovieName().toLowerCase(), movie);
        movieMap.put(movie.getMovieId(), movie);
        //eventPublisher.publishMovieAddedEvent(movie);
    }

    public void createMovie(String movieName, int movieDurationInMinutes) {
        Movie movie = new Movie(movieName, movieDurationInMinutes);
        addMovie(movie);
    }

    public Movie getMoviesNameToMovieMap(String movieName) {
        movieName = movieName.toLowerCase();
        if (!moviesNameToMovieMap.containsKey(movieName)) {
            throw new NotFoundException("Movie not found");
        }
        return moviesNameToMovieMap.get(movieName);
    }

    @PostConstruct
    public void init() {
        initialiseMovies();
    }

    public void initialiseMovies() {
        log.info("Initialising movies");
        Movie movie1 = new Movie("The Dark Knight", 152);
        Movie movie2 = new Movie("Inception", 148);
        addMovie(movie1);
        addMovie(movie2);
    }

    public Movie getMovie(UUID movieId) {
        return movieMap.get(movieId);
    }

    public List<Movie> getAllMovies() {
        return new ArrayList<>(movieMap.values());
    }
}
