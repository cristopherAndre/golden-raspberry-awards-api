package com.cristopherandre.goldenraspberryawardsapi.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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

    @Override
    public Set<Studio> saveStudios(String studios) {
        Set<Studio> ret = new HashSet<>();
        String[] studioNames = studios.split(",|\\ and ");

        if(Objects.nonNull(studioNames) && studioNames.length > 0){
            for (String studioName : studioNames) {
                Studio studioModel = repository.findByName(studioName);                
                if(Objects.isNull(studioModel)){
                    Studio studio = Studio.builder().name(studioName).build();
                    studioModel = repository.save(studio);                                     
                }
                ret.add(studioModel);
            }
        }
        return ret;
    }

}
