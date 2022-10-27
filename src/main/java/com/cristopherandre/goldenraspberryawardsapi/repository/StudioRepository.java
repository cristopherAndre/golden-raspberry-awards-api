package com.cristopherandre.goldenraspberryawardsapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cristopherandre.goldenraspberryawardsapi.model.Studio;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {

    public Optional<Studio> findByName(String name);

}
