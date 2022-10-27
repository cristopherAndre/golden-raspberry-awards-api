package com.cristopherandre.goldenraspberryawardsapi.service;

import java.util.Collection;
import java.util.List;

import com.cristopherandre.goldenraspberryawardsapi.dto.GRAWinnerIntervalDTO;
import com.cristopherandre.goldenraspberryawardsapi.dto.GRAWinnersMinMaxDTO;
import com.cristopherandre.goldenraspberryawardsapi.model.GRANominee;

/**
 * @author Cristopher Andre
 */
public interface GRANomineeService {

    public abstract GRANominee findById(Long id);

    public abstract Collection<GRANominee> findAll();

    public abstract GRANominee saveGRANominee(GRANominee graWinner);

    public abstract GRANominee updateGRANominee(Long id, GRANominee graWinner);

    public abstract void deleteGRANominee(Long id);

    public abstract List<GRAWinnerIntervalDTO> findGRAWinnersInterval();

    public abstract GRAWinnersMinMaxDTO findGRAWinnersMinMaxInterval();

}
