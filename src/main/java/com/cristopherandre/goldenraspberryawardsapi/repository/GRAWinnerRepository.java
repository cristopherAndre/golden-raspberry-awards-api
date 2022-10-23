package com.cristopherandre.goldenraspberryawardsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cristopherandre.goldenraspberryawardsapi.model.GRAWinner;

@Repository
public interface GRAWinnerRepository extends JpaRepository<GRAWinner, Long> {

}
