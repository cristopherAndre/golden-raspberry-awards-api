package com.cristopherandre.goldenraspberryawardsapi.service;

import java.util.Collection;

import com.cristopherandre.goldenraspberryawardsapi.model.Producer;

public interface ProducerService {

    public abstract void saveProducer(Producer producer);

    public abstract void updateProducer(Long id, Producer producer);

    public abstract void deleteProducer(Long id);

    public abstract Collection<Producer> fetchProducers();
    
}
