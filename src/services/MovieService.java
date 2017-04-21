package services;

import com.got.container.contracts.Container;
import com.got.container.contracts.ContainerAware;
import com.got.event.contracts.EventDispatcher;
import dto.Movie;
import events.MovieWasFetched;
import repositories.MovieRepository;

import java.util.List;

public class MovieService implements ContainerAware{
    private MovieRepository movieRepository;
    private EventDispatcher dispatcher;
    private Container container;

    public MovieService(MovieRepository movieRepository, EventDispatcher dispatcher) {
        this.movieRepository = movieRepository;
        this.dispatcher = dispatcher;
    }

    public List<Movie> getAll() {
        List<Movie> movies = movieRepository.getAll();
        dispatcher.dispatch(new MovieWasFetched(movies));
        return movies;
    }

    public void save(Movie movie){
        movieRepository.save(movie);
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }
}
