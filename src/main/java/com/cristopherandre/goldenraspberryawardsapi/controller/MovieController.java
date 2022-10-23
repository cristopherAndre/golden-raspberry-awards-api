package com.cristopherandre.goldenraspberryawardsapi.controller;

import java.util.Collection;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristopherandre.goldenraspberryawardsapi.dto.MovieDTO;
import com.cristopherandre.goldenraspberryawardsapi.mapper.MovieMapper;
import com.cristopherandre.goldenraspberryawardsapi.model.Movie;
import com.cristopherandre.goldenraspberryawardsapi.service.MovieService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @Autowired
    private MovieMapper mapper;

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getMovies() {
        Collection<Movie> movies = service.fetchMovies();

        if (movies.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(movies.stream().map(m -> mapper.toMovieDTO(m)).toList());
    }

    @PostMapping
    public ResponseEntity<MovieDTO> postMethodName(@RequestBody MovieDTO movieDTO) {
        Movie movie = service.saveMovie(mapper.toMovie(movieDTO));
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toMovieDTO(movie));
    }

}
