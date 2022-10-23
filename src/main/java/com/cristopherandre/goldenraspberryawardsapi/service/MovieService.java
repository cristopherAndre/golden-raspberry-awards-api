package com.cristopherandre.goldenraspberryawardsapi.service;

import java.util.Collection;

import com.cristopherandre.goldenraspberryawardsapi.model.Movie;

public interface MovieService {

    public abstract Movie saveMovie(Movie movie);

    public abstract Movie updateMovie(Long id, Movie movie);

    public abstract void deleteMovie(Long id);

    public abstract Collection<Movie> fetchMovies();
    
}
