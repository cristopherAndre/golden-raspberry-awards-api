package com.cristopherandre.goldenraspberryawardsapi.service;

import java.util.Collection;

import com.cristopherandre.goldenraspberryawardsapi.model.Movie;

public interface MovieService {

    public abstract Movie findById(Long id);

    public abstract Movie findByTitle(String title);

    public abstract Collection<Movie> findAll();

    public abstract Movie saveMovie(Movie movie);

    public abstract Movie updateMovie(Long id, Movie movie);

    public abstract void deleteMovie(Long id);

}
