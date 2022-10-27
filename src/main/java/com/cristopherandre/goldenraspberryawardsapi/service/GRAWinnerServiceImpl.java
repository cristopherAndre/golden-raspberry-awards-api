package com.cristopherandre.goldenraspberryawardsapi.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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
    public Collection<GRAWinner> findAll() {
        return repository.findAll();
    }

    @Override
    public GRAWinner saveGRAWinner(GRAWinner graWinner) {
        GRAWinner newGRAWinner = null;
        if (Objects.nonNull(graWinner)) {
            newGRAWinner = repository.save(graWinner);
        }
        return newGRAWinner;
    }

    @Override
    public GRAWinner updateGRAWinner(Long id, GRAWinner graWinner) {
        if (!repository.findById(id).isPresent())
            throw new RecordNotFoundException(id);
        return repository.save(graWinner);
    }

    @Override
    public void deleteGRAWinner(Long id) {
        if (Objects.nonNull(id)) {
            Optional<GRAWinner> graStudio = repository.findById(id);
            if (!graStudio.isPresent())
                throw new RecordNotFoundException(id);
            repository.delete(graStudio.get());
        }
    }

    @Override
    public List<GRAWinnerIntervalDTO> findGRAWinnersInterval() {
        return repository.findGRAWinnersInterval();
    }

    @Override
    public GRAWinnersMinMaxDTO findGRAWinnersMinMax() {

        GRAWinnersMinMaxDTO graWinnersMinMaxDTO = new GRAWinnersMinMaxDTO();

        // Busca todos ganhadores com mais de um premio
        List<GRAWinnerIntervalDTO> graWinnersInterval = findGRAWinnersInterval();

        // Como pode existir mais de um ganhador com o mesmo intervalo Minimo de premios
        // Retorno a lista de ganahdores que tenham o mesmo intervalo Minimo de premios
        Integer min = getMinInterval(graWinnersInterval);
        List<GRAWinnerIntervalDTO> graMinWinnersInterval = graWinnersInterval.stream()
                .filter(g -> g.getInterval().equals(min))
                .collect(Collectors.toList());
        graWinnersMinMaxDTO.setMin(graMinWinnersInterval);

        // Como pode existir mais de um ganhador com o mesmo intervalo Maximo de premios
        // Retorno a lista de ganahdores que tenham o mesmo intervalo Maximo de premios
        Integer max = getMaxInterval(graWinnersInterval);
        List<GRAWinnerIntervalDTO> graMaxWinnersInterval = graWinnersInterval.stream()
                .filter(g -> g.getInterval().equals(max))
                .collect(Collectors.toList());
        graWinnersMinMaxDTO.setMax(graMaxWinnersInterval);

        return graWinnersMinMaxDTO;
    }

    private Integer getMinInterval(List<GRAWinnerIntervalDTO> graWinnersInterval) {
        Integer minInterval = null;
        Optional<GRAWinnerIntervalDTO> min = graWinnersInterval.stream()
                .min(Comparator.comparing(GRAWinnerIntervalDTO::getInterval));
        if (min.isPresent()) {
            minInterval = min.get().getInterval();
        }
        return minInterval;
    }

    private Integer getMaxInterval(List<GRAWinnerIntervalDTO> graWinnersInterval) {
        Integer maxInterval = null;
        Optional<GRAWinnerIntervalDTO> max = graWinnersInterval.stream()
                .max(Comparator.comparing(GRAWinnerIntervalDTO::getInterval));
        if (max.isPresent()) {
            maxInterval = max.get().getInterval();
        }
        return maxInterval;
    }

}
