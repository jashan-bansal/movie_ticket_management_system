package com.self.tms.services;


import com.self.tms.models.Seat;
import com.self.tms.models.Show;
import com.self.tms.models.Theatre;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
@Data
public class ShowService {
    private Map<UUID, Show> showMap;
    private static final String MOVIE = "movie";
    private static final String THEATRE = "theatre";

    private Map<UUID, List<Seat>> showLockedSeatMap;

    private final TheatreServices theatreServices;


    @Autowired
    public ShowService(TheatreServices theatreServices) {
        this.theatreServices = theatreServices;
        showMap = new HashMap<>();
        showLockedSeatMap = new HashMap<>();
    }

    public void addShow(UUID movieId, Theatre.Screen screen, Date showStartTime, UUID theatreId) {
        Show show = new Show(movieId, screen, showStartTime, theatreId);
        showMap.put(show.getShowId(), show);
        showLockedSeatMap.put(show.getShowId(), new ArrayList<>());
    }

    public List<Seat> getShowLockedSeatMap(UUID showId) {
        if (Objects.isNull(showId)) {
            throw new RuntimeException("Invalid show id");
        }
        return showLockedSeatMap.getOrDefault(showId, new ArrayList<>());
    }

    public Show getShow(UUID showId) {
        return showMap.get(showId);
    }

    public List<Show> getShowsInTheatre(UUID theatreId){
        List<Show> runningShows = new ArrayList<>();
        showMap.forEach((showId, show) -> {
            if (show.getTheatreId().equals(theatreId)) {
                runningShows.add(show);
            }
        });
        return runningShows;
    }

    public List<Show> getShowsForMovie(UUID movieId){
        List<Show> runningShows = new ArrayList<>();
        showMap.forEach((showId, show) -> {
            if (show.getMovieId().equals(movieId)) {
                runningShows.add(show);
            }
        });
        return runningShows;
    }

    public List<Show> getShows(UUID id, String searchType) {
        List <Show> shows = new ArrayList<>();
        log.info("Fetching shows for id: {} and searchType: {}", id, searchType);

        switch (searchType) {
            case MOVIE:
                shows = getShowsForMovie(id);
            case THEATRE:
                shows = getShowsInTheatre(id);
            default:
                shows = Arrays.asList(getShow(id));
        }

        log.info("Fetched shows: {} for id: {} and searchType: {}", shows, id, searchType);
        return shows;
    }

    public List<Show> getShowsForMovieAndTheatre(UUID movieId, UUID theatreId) {
        List<Show> runningShows = new ArrayList<>();
        showMap.forEach((showId, show) -> {
            if (show.getMovieId().equals(movieId) && show.getTheatreId().equals(theatreId)) {
                runningShows.add(show);
            }
        });
        return runningShows;
    }

    public void initializeShows() {
        log.info("Initializing shows");
        theatreServices.getTheatreInCity("bangalore").forEach(theatre -> {
            addShow(theatre.getRunningMovies().get(0).getMovieId(), theatre.getScreens().get(0), new Date(), theatre.getId());
        });
    }

}

