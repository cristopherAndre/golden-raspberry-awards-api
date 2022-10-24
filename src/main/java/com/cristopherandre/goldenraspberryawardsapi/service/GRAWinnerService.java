package com.cristopherandre.goldenraspberryawardsapi.service;

import java.io.IOException;
import java.util.Collection;

import com.cristopherandre.goldenraspberryawardsapi.model.GRAWinner;

public interface GRAWinnerService {
    
    public abstract GRAWinner saveGRAWinner(GRAWinner graWinner);

    public abstract GRAWinner updateGRAWinner(Long id, GRAWinner graWinner);

    public abstract void deleteGRAWinner(Long id);

    public abstract Collection<GRAWinner> fetchGRAWinners();

    public abstract void loadGRAWinners();

}
