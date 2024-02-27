package com.self.tms.models;

import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
public class Theatre {
    private UUID id;
    private String name;
    private List<Screen> screens;
    private List<Movie> runningMovies;

    @PostConstruct
    public void Theatre() {
        id = UUID.randomUUID();
    }

    public void addScreen(Screen screen){
        if (Objects.isNull(screens)) {
            screens = new ArrayList<>();
        }
        screens.add(screen);
    }

    public void addMovie(Movie movie){
        if (Objects.isNull(runningMovies)) {
            runningMovies = new ArrayList<>();
        }
        runningMovies.add(movie);
    }

    @Data
    public static class Screen {
        UUID screenId;
        List<Seat> seats = new ArrayList<>();

        public Screen(int seatCapacity) {
            screenId = UUID.randomUUID();
            seats = initialiseSeats(seatCapacity);
        }

        public UUID getScreenId() {
            return screenId;
        }

        public void setScreenId(UUID screenId) {
            this.screenId = screenId;
        }

        public List<Seat> getSeats() {
            return seats;
        }

        public void setSeats(List<Seat> seats) {
            this.seats = seats;
        }

        private List<Seat> initialiseSeats(int seatCapacity) {
            List<Seat> seats = new ArrayList<>();
            int rows = seatCapacity / 10;
            char rowSeries = 'A';

            for (int row = 0; row < rows; row++) {
                for (int seatNumber = 0; seatNumber < 10; seatNumber++) {
                    seats.add(new Seat( rowSeries + String.valueOf(seatNumber)));
                }
                rowSeries++;
            }

            return seats;
        }
    }

}
