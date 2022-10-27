package com.cristopherandre.goldenraspberryawardsapi.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristopherandre.goldenraspberryawardsapi.dto.GRAWinnerIntervalDTO;
import com.cristopherandre.goldenraspberryawardsapi.dto.GRAWinnersMinMaxDTO;
import com.cristopherandre.goldenraspberryawardsapi.exceptions.RecordNotFoundException;
import com.cristopherandre.goldenraspberryawardsapi.model.GRAWinner;
import com.cristopherandre.goldenraspberryawardsapi.repository.GRAWinnerRepository;

@Service
public class GRAWinnerServiceImpl implements GRAWinnerService {

    @Autowired
    private GRAWinnerRepository repository;

    @Override
    public GRAWinner saveGRAWinner(GRAWinner graWinner) {
        return repository.save(graWinner);
    }

    @Override
    public GRAWinner updateGRAWinner(Long id, GRAWinner graWinner) {
        if (!repository.findById(id).isPresent())
            throw new RecordNotFoundException(id);
        return repository.save(graWinner);
    }

    @Override
    public void deleteGRAWinner(Long id) {
        Optional<GRAWinner> graWinner = repository.findById(id);
        if (!graWinner.isPresent())
            throw new RecordNotFoundException(id);
        repository.delete(graWinner.get());

    }

    @Override
    public List<GRAWinner> fetchGRAWinners() {
        return repository.findAll();
    }

    @Override
    public List<GRAWinnerIntervalDTO> fetchGRAWinnersInterval() {
        return repository.findGRAWinnersInterval();

    }

    @Override
    public GRAWinnersMinMaxDTO findGRAWinnersMinMax() {

        GRAWinnersMinMaxDTO graWinnersMinMaxDTO = new GRAWinnersMinMaxDTO();
        List<GRAWinnerIntervalDTO> graWinnersInterval = fetchGRAWinnersInterval();

        graWinnersInterval.sort(Comparator.comparing(GRAWinnerIntervalDTO::getInterval));

        int min = graWinnersInterval.stream().min(Comparator.comparing(GRAWinnerIntervalDTO::getInterval)).get()
                .getInterval();

        List<GRAWinnerIntervalDTO> graMinWinnersInterval = graWinnersInterval.stream()
                .filter(g -> g.getInterval() == min)
                .collect(Collectors.toList());

        graWinnersMinMaxDTO.setMin(graMinWinnersInterval);

        int max = graWinnersInterval.stream().max(Comparator.comparing(GRAWinnerIntervalDTO::getInterval)).get()
                .getInterval();

        List<GRAWinnerIntervalDTO> graMaxWinnersInterval = graWinnersInterval.stream()
                .filter(g -> g.getInterval() == max)
                .collect(Collectors.toList());

        graWinnersMinMaxDTO.setMax(graMaxWinnersInterval);

        return graWinnersMinMaxDTO;
    }

}
