package events;

import com.got.event.contracts.Event;
import dto.Movie;

import java.util.List;

public class MovieWasFetchedSample implements Event{
    public List<Movie> movies;

    public MovieWasFetchedSample(List<Movie> movies) {
        this.movies = movies;
    }
}
