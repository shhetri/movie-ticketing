package controllers;

import com.got.container.ContainerFactory;
import com.got.container.contracts.Container;
import javafx.fxml.Initializable;
import services.MovieService;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Container container = ContainerFactory.getDefaultContainer();
        MovieService movieService = container.make(MovieService.class);

        System.out.println(movieService.getAll());
//        Movie movie = container.make(Movie.class);
//        movie.setGenre("sumit");
//        movie.setName("Logan");
//        movie.setLength("3 hours and 15 mins");
//        movieService.save(movie);
    }
}
