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
import com.cristopherandre.goldenraspberryawardsapi.model.Studio;
import com.cristopherandre.goldenraspberryawardsapi.repository.StudioRepository;

/**
 * @author Cristopher Andre
 */
@Service
public class StudioServiceImpl implements StudioService {

    @Autowired
    private StudioRepository repository;

    @Override
    public Studio findById(Long id) {
        Optional<Studio> studio = Optional.empty();
        if (Objects.nonNull(id)) {
            studio = repository.findById(id);
            if (!studio.isPresent())
                throw new RecordNotFoundException(id);
        }
        return studio.isPresent() ? studio.get() : null;
    }

    @Override
    public Studio findByName(String name) {
        Optional<Studio> studio = Optional.empty();
        if (Objects.nonNull(name)) {
            studio = repository.findByName(name);
            if (!studio.isPresent())
                throw new RecordNotFoundException(name);
        }
        return studio.isPresent() ? studio.get() : null;
    }

    @Override
    public Collection<Studio> findAll() {
        return repository.findAll();
    }

    @Override
    public Studio saveStudio(Studio studio) {
        Studio newStudio = null;
        if (Objects.nonNull(studio)) {
            newStudio = repository.save(studio);
        }
        return newStudio;
    }

    @Override
    public Set<Studio> saveStudios(String studios) {
        Set<Studio> ret = new HashSet<>();
        String[] studioNames = Arrays.stream(studios.split(",|\\ and ")).map(String::trim).toArray(String[]::new);

        if (Objects.nonNull(studioNames) && studioNames.length > 0) {
            for (String studioName : studioNames) {
                Optional<Studio> studioModelOptional = repository.findByName(studioName);
                Studio studioModel = studioModelOptional.isPresent() ? studioModelOptional.get() : null;
                if (Objects.isNull(studioModel)) {
                    Studio studio = Studio.builder().name(studioName).build();
                    studioModel = repository.save(studio);
                }
                ret.add(studioModel);
            }
        }
        return ret;
    }

    @Override
    public Studio updateStudio(Long id, Studio studio) {
        if (!repository.findById(id).isPresent())
            throw new RecordNotFoundException(id);
        return repository.save(studio);
    }

    @Override
    public void deleteStudio(Long id) {
        if (Objects.nonNull(id)) {
            Optional<Studio> studio = repository.findById(id);
            if (!studio.isPresent())
                throw new RecordNotFoundException(id);
            repository.delete(studio.get());
        }
    }

}
