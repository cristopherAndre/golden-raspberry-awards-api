package com.cristopherandre.goldenraspberryawardsapi.service;

import java.util.Collection;
import java.util.Set;

import com.cristopherandre.goldenraspberryawardsapi.model.Producer;

public interface ProducerService {

    public abstract Producer findById(Long id);

    public abstract Producer findByName(String name);

    public abstract Collection<Producer> findAll();

    public abstract Producer saveProducer(Producer producer);

    public abstract Set<Producer> saveProducers(String producers);

    public abstract Producer updateProducer(Long id, Producer producer);

    public abstract void deleteProducer(Long id);

}
