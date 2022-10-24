package com.cristopherandre.goldenraspberryawardsapi.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristopherandre.goldenraspberryawardsapi.exceptions.RecordNotFoundException;
import com.cristopherandre.goldenraspberryawardsapi.model.Producer;
import com.cristopherandre.goldenraspberryawardsapi.repository.ProducerRepository;

@Service
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    private ProducerRepository repository;

    @Override
    public void saveProducer(Producer producer) {
        repository.save(producer);
    }

    @Override
    public void updateProducer(Long id, Producer producer) {
        if (!repository.findById(id).isPresent())
            throw new RecordNotFoundException(id);
        repository.save(producer);
    }

    @Override
    public void deleteProducer(Long id) {
        Optional<Producer> producer = repository.findById(id);
        if (!producer.isPresent())
            throw new RecordNotFoundException(id);
        repository.delete(producer.get());
    }

    @Override
    public Collection<Producer> fetchProducers() {
        return repository.findAll();
    }

    @Override
    public Set<Producer> saveProducers(String producers) {
        Set<Producer> ret = new HashSet<>();
        String[] producerNames = Arrays.stream(producers.split(",|\\ and ")).map(String::trim).toArray(String[]::new);

        if (Objects.nonNull(producerNames) && producerNames.length > 0) {
            for (String producerName : producerNames) {
                Producer producerModel = repository.findByName(producerName);
                if (Objects.isNull(producerModel)) {
                    Producer producer = Producer.builder().name(producerName).build();
                    producerModel = repository.save(producer);
                }
                ret.add(producerModel);
            }
        }
        return ret;
    }

}
