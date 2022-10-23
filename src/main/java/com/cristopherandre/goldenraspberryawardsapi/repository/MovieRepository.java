package com.cristopherandre.goldenraspberryawardsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cristopherandre.goldenraspberryawardsapi.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
