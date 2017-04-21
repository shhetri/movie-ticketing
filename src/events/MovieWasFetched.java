package events;

import com.got.event.contracts.Event;
import dto.Movie;

import java.util.List;

public class MovieWasFetched implements Event{
    public List<Movie> movies;

    public MovieWasFetched(List<Movie> movies) {
        this.movies = movies;
    }
}
