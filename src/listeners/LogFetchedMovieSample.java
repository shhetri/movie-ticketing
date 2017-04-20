package listeners;

import com.got.event.contracts.Event;
import com.got.event.contracts.Listener;
import events.MovieWasFetchedSample;

public class LogFetchedMovieSample implements Listener{
    @Override
    public void handle(Event event) {
        MovieWasFetchedSample fetchedMovies = (MovieWasFetchedSample) event;
        System.out.println(fetchedMovies.movies);
    }
}
