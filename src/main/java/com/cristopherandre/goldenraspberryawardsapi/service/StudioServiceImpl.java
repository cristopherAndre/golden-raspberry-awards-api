package com.cristopherandre.goldenraspberryawardsapi.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristopherandre.goldenraspberryawardsapi.exceptions.RecordNotFoundException;
import com.cristopherandre.goldenraspberryawardsapi.model.Studio;
import com.cristopherandre.goldenraspberryawardsapi.repository.StudioRepository;

@Service
public class StudioServiceImpl implements StudioService {

    @Autowired
    private StudioRepository repository;

    @Override
    public void saveStudio(Studio studio) {
        repository.save(studio);
    }

    @Override
    public void updateStudio(Long id, Studio studio) {
        if (!repository.findById(id).isPresent())
            throw new RecordNotFoundException(id);
        repository.save(studio);
    }

    @Override
    public void deleteStudio(Long id) {
        Optional<Studio> studio = repository.findById(id);
        if (!studio.isPresent())
            throw new RecordNotFoundException(id);
        repository.delete(studio.get());
    }

    @Override
    public Collection<Studio> fetchStudios() {
        return repository.findAll();
    }

}
