package com.cristopherandre.goldenraspberryawardsapi.service;

import java.util.Collection;
import java.util.Set;

import com.cristopherandre.goldenraspberryawardsapi.model.Studio;


public interface StudioService {

    public abstract void saveStudio(Studio studio);

    public abstract Set<Studio> saveStudios(String studios);

    public abstract void updateStudio(Long id, Studio studio);

    public abstract void deleteStudio(Long id);

    public abstract Collection<Studio> fetchStudios();

}
