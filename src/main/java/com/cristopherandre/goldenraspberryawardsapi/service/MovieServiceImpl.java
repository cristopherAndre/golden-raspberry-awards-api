package com.cristopherandre.goldenraspberryawardsapi.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristopherandre.goldenraspberryawardsapi.exceptions.RecordNotFoundException;
import com.cristopherandre.goldenraspberryawardsapi.model.Movie;
import com.cristopherandre.goldenraspberryawardsapi.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository repository;

    @Override
    public Movie saveMovie(Movie movie) {
        return repository.save(movie);
    }

    @Override
    public Movie updateMovie(Long id, Movie movie) {
        if (!repository.findById(id).isPresent())
            throw new RecordNotFoundException(id);
        return repository.save(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        Optional<Movie> movie = repository.findById(id);
        if (!movie.isPresent())
            throw new RecordNotFoundException(id);
        repository.delete(movie.get());

    }

    @Override
    public Collection<Movie> fetchMovies() {
        return repository.findAll();
    }

}
