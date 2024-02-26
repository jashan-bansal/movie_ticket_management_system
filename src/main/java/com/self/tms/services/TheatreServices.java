package com.self.tms.services;

import com.self.tms.models.Movie;
import com.self.tms.models.Theatre;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

@Data
public class TheatreServices {
    Map<String, List<Theatre>> cityToTheatresMap;
    Map<UUID, Theatre> theatreMap;
    private final static Integer MAX_SCREEN_SEAT_CAPACITY = 100;
    private final MovieService movieService;

    @Autowired
    public TheatreServices (MovieService movieService) {
        this.movieService = movieService;
        cityToTheatresMap = new HashMap<>();
        theatreMap = new HashMap<>();
        initializeTheatres();
    }

    public void addTheatreInCity(String city, Theatre theatre) {
        List<Theatre> theatres = cityToTheatresMap.getOrDefault(city, new ArrayList<>());
        theatres.add(theatre);
        cityToTheatresMap.put(city, theatres);
    }

    public List<Theatre> getTheatreInCity(String city) {
        if (cityToTheatresMap.containsKey(city)) {
            throw new RuntimeException("Sorry! We are not operational in this city yet.");
        }

        List<Theatre> theatres = cityToTheatresMap.getOrDefault(city, new ArrayList<>());
        if (theatres.size() == 0) {
            throw new RuntimeException("No theatre in city!");
        }

        return theatres;
    }

    public void addTheatre(String theatreName, String cityName) {
        Theatre theatre = Theatre.builder().name(theatreName).build();
        addTheatreInCity(cityName, theatre);
    }

    public void addTheatre(String theatreName, String cityName, Theatre.Screen screen) {
        Theatre theatre = Theatre.builder().name(theatreName).screens(Arrays.asList(screen)).build();
        addTheatreInCity(cityName, theatre);
    }

    public void initializeTheatres(){
        addTheatre("PVR", "Bangalore", new Theatre.Screen(MAX_SCREEN_SEAT_CAPACITY));
        addTheatre("INOX", "Bangalore", new Theatre.Screen(MAX_SCREEN_SEAT_CAPACITY));
        addTheatre("PVR", "Mumbai", new Theatre.Screen(MAX_SCREEN_SEAT_CAPACITY));
    }

    public void addMovieInTheatre(UUID theatreId, UUID movieId, UUID screenId) {
        if (!theatreMap.containsKey(theatreId)) {
            throw new RuntimeException("Theatre not found");
        }
        Theatre theatre = theatreMap.get(theatreId);
        Movie movie = movieService.getMovie(movieId);
        theatre.addMovie(movie);
    }

}
