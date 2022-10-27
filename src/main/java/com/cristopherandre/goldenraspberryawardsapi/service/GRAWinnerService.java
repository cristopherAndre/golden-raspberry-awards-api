package com.cristopherandre.goldenraspberryawardsapi.service;

import java.util.List;

import com.cristopherandre.goldenraspberryawardsapi.dto.GRAWinnerIntervalDTO;
import com.cristopherandre.goldenraspberryawardsapi.dto.GRAWinnersMinMaxDTO;
import com.cristopherandre.goldenraspberryawardsapi.model.GRAWinner;

public interface GRAWinnerService {

    public abstract GRAWinner saveGRAWinner(GRAWinner graWinner);

    public abstract GRAWinner updateGRAWinner(Long id, GRAWinner graWinner);

    public abstract void deleteGRAWinner(Long id);

    public abstract List<GRAWinner> fetchGRAWinners();

    public abstract List<GRAWinnerIntervalDTO> fetchGRAWinnersInterval();

    public abstract GRAWinnersMinMaxDTO findGRAWinnersMinMax();

}
