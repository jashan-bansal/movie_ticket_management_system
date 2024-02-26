package com.self.tms.models;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Theatre {
    UUID id;
    String name;
    List<Screen> screens;
    List<Movie> runningMovies;

    public void addScreen(Screen screen){
        this.screens.add(screen);
    }

    public void addMovie(Movie movie){
        this.runningMovies.add(movie);
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
                for (int j = 0; j < 10; j++) {
                    seats.add(new Seat(row, rowSeries));
                }
                rowSeries++;
            }

            return seats;
        }
    }

}
