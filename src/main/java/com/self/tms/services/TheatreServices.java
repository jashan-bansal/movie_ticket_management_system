package com.self.tms.services;

import com.self.tms.models.Movie;
import com.self.tms.models.Theatre;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.*;

@Service

@Data
public class TheatreServices {
    private Map<String, List<Theatre>> cityToTheatresMap;
    private Map<UUID, Theatre> theatreMap;
    private final static Integer MAX_SCREEN_SEAT_CAPACITY = 30;
    private final MovieService movieService;

    @Autowired
    public TheatreServices (MovieService movieService) {
        this.movieService = movieService;
        cityToTheatresMap = new HashMap<>();
        theatreMap = new HashMap<>();
        initializeTheatres();
    }

    public void addTheatreInCity(String city, Theatre theatre) {
        city = city.toLowerCase();

        List<Theatre> theatres = cityToTheatresMap.getOrDefault(city, new ArrayList<>());
        theatres.add(theatre);

        cityToTheatresMap.put(city, theatres);
        theatreMap.put(theatre.getId(), theatre);
    }

    public List<Theatre> getTheatreInCity(String city) {
        city = city.toLowerCase();
        if (!cityToTheatresMap.containsKey(city)) {
            throw new NotFoundException("Sorry! We are not operational in this city yet.");
        }

        List<Theatre> theatres = cityToTheatresMap.getOrDefault(city, new ArrayList<>());
        if (theatres.size() == 0) {
            throw new NotFoundException("No theatre in city!");
        }

        return theatres;
    }

    public void addTheatre(String theatreName, String cityName) {
        Theatre theatre = Theatre
                            .builder()
                            .id(UUID.randomUUID())
                            .name(theatreName)
                            .screens(Arrays.asList(new Theatre.Screen(MAX_SCREEN_SEAT_CAPACITY)))
                            .build();
        addTheatreInCity(cityName, theatre);
    }

    public void initializeTheatres(){
        addTheatre("PVR", "Bangalore");
        addTheatre("INOX", "Bangalore");
        addTheatre("PVR", "Mumbai");
    }

    public void addMovieInTheatre(UUID theatreId, UUID movieId, UUID screenId) {
        if (!theatreMap.containsKey(theatreId)) {
            throw new NotFoundException("Theatre not found");
        }
        Theatre theatre = theatreMap.get(theatreId);
        Movie movie = movieService.getMovie(movieId);
        theatre.addMovie(movie);
    }

    public void initialiseMovieInTheatre(){
        theatreMap.keySet().forEach(id -> {
            Theatre theatre = theatreMap.get(id);
            movieService.getMovieMap().keySet().forEach(movieId -> {
                addMovieInTheatre(id, movieId, theatre.getScreens().get(0).getScreenId());
            });
        });

    }

}
