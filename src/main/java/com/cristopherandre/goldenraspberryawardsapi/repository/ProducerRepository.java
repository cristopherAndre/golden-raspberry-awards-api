package com.cristopherandre.goldenraspberryawardsapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cristopherandre.goldenraspberryawardsapi.model.Producer;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {

    public Optional<Producer> findByName(String name);

}
