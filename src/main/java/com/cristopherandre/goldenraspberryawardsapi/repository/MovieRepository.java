package com.cristopherandre.goldenraspberryawardsapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cristopherandre.goldenraspberryawardsapi.model.Movie;

/**
 * @author Cristopher Andre
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    public Optional<Movie> findByTitle(String title);

}
