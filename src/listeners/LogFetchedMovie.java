package listeners;

import com.got.event.contracts.Event;
import com.got.event.contracts.Listener;
import events.MovieWasFetched;

public class LogFetchedMovie implements Listener{
    @Override
    public void handle(Event event) {
        MovieWasFetched fetchedMovies = (MovieWasFetched) event;
        System.out.println(fetchedMovies.movies);
    }
}
