package com.cristopherandre.goldenraspberryawardsapi.service;

import java.util.Collection;
import java.util.Set;

import com.cristopherandre.goldenraspberryawardsapi.model.Studio;

public interface StudioService {

    public abstract Studio findById(Long id);

    public abstract Studio findByName(String name);

    public abstract Collection<Studio> findAll();

    public abstract Studio saveStudio(Studio studio);

    public abstract Set<Studio> saveStudios(String studios);

    public abstract Studio updateStudio(Long id, Studio studio);

    public abstract void deleteStudio(Long id);

}
