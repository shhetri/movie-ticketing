package services;

import dto.Movie;

import java.util.List;

public interface MovieServiceInterface {

    List<Movie> getAll();

    void save(Movie movie);
}
