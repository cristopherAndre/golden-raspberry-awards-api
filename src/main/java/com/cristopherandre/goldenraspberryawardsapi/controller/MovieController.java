package com.cristopherandre.goldenraspberryawardsapi.controller;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristopherandre.goldenraspberryawardsapi.dto.MovieDTO;
import com.cristopherandre.goldenraspberryawardsapi.mapper.MovieMapper;
import com.cristopherandre.goldenraspberryawardsapi.model.Movie;
import com.cristopherandre.goldenraspberryawardsapi.service.MovieService;

/**
 * @author Cristopher Andre
 */
@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @Autowired
    private MovieMapper mapper;

    // List all Movies
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        Collection<Movie> movies = service.findAll();
        if (movies.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(movies.stream().map(m -> mapper.toMovieDTO(m)).toList());
    }

    // List Movie by Id
    @GetMapping(value = "/getById/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable(value = "id") String id) {
        Movie movie = service.findById(Long.valueOf(id));
        if (Objects.isNull(movie)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toMovieDTO(movie));
    }

    // List Movie by Title
    @GetMapping(value = "/getByTile/{title}")
    public ResponseEntity<MovieDTO> getMovieByName(@PathVariable(value = "title") String title) {
        Movie movie = service.findByTitle(title);
        if (Objects.isNull(movie)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toMovieDTO(movie));
    }

    // Save Movie
    @PostMapping(value = "/createMovie")
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO) {
        Movie movie = service.saveMovie(mapper.toMovie(movieDTO));
        if (Objects.isNull(movie)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toMovieDTO(movie));
    }

    // Update Movie
    @PutMapping(value = "/updateMovie/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable(value = "id") String id, @RequestBody MovieDTO movieDTO) {
        Movie movie = service.updateMovie(Long.valueOf(id, 0), mapper.toMovie(movieDTO));
        if (Objects.isNull(movie)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toMovieDTO(movie));
    }

    // Delete Movie
    @DeleteMapping(value = "/deleteMovie/{id}")
    public void deleteMovie(@PathVariable(value = "id") String id) {
        service.deleteMovie(Long.valueOf(id));
    }

}
