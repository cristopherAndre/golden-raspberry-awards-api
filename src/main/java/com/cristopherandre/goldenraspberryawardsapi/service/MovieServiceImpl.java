package com.cristopherandre.goldenraspberryawardsapi.service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristopherandre.goldenraspberryawardsapi.exceptions.RecordNotFoundException;
import com.cristopherandre.goldenraspberryawardsapi.model.Movie;
import com.cristopherandre.goldenraspberryawardsapi.repository.MovieRepository;

/**
 * @author Cristopher Andre
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository repository;

    @Override
    public Movie findById(Long id) {
        Optional<Movie> movie = Optional.empty();
        if (Objects.nonNull(id)) {
            movie = repository.findById(id);
            if (!movie.isPresent())
                throw new RecordNotFoundException(id);
        }
        return movie.isPresent() ? movie.get() : null;
    }

    @Override
    public Movie findByTitle(String title) {
        Optional<Movie> movie = Optional.empty();
        if (Objects.nonNull(title)) {
            movie = repository.findByTitle(title);
            if (!movie.isPresent())
                throw new RecordNotFoundException(title);
        }
        return movie.isPresent() ? movie.get() : null;
    }

    @Override
    public Collection<Movie> findAll() {
        return repository.findAll();
    }

    @Override
    public Movie saveMovie(Movie movie) {
        Movie newMovie = null;
        if (Objects.nonNull(movie)) {
            newMovie = repository.save(movie);
        }
        return newMovie;
    }

    @Override
    public Movie updateMovie(Long id, Movie movie) {
        if (!repository.findById(id).isPresent())
            throw new RecordNotFoundException(id);
        return repository.save(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        if (Objects.nonNull(id)) {
            Optional<Movie> movie = repository.findById(id);
            if (!movie.isPresent())
                throw new RecordNotFoundException(id);
            repository.delete(movie.get());

        }
    }

}
