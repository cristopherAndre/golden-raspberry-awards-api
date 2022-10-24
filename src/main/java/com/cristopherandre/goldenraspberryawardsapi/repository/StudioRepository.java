package com.cristopherandre.goldenraspberryawardsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cristopherandre.goldenraspberryawardsapi.model.Studio;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {

    public Studio findByName(String name);

}
