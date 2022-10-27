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
import com.cristopherandre.goldenraspberryawardsapi.model.GRANominee;
import com.cristopherandre.goldenraspberryawardsapi.repository.GRANomineeRepository;

/**
 * @author Cristopher Andre
 */
@Service
public class GRANomineeServiceImpl implements GRANomineeService {

    @Autowired
    private GRANomineeRepository repository;

    @Override
    public GRANominee findById(Long id) {
        Optional<GRANominee> graNominee = Optional.empty();
        if (Objects.nonNull(id)) {
            graNominee = repository.findById(id);
            if (!graNominee.isPresent())
                throw new RecordNotFoundException(id);
        }
        return graNominee.isPresent() ? graNominee.get() : null;
    }

    @Override
    public Collection<GRANominee> findAll() {
        return repository.findAll();
    }

    @Override
    public GRANominee saveGRANominee(GRANominee graNominee) {
        GRANominee newGRAWinner = null;
        if (Objects.nonNull(graNominee)) {
            newGRAWinner = repository.save(graNominee);
        }
        return newGRAWinner;
    }

    @Override
    public GRANominee updateGRANominee(Long id, GRANominee graNominee) {
        if (!repository.findById(id).isPresent())
            throw new RecordNotFoundException(id);
        return repository.save(graNominee);
    }

    @Override
    public void deleteGRANominee(Long id) {
        if (Objects.nonNull(id)) {
            Optional<GRANominee> graStudio = repository.findById(id);
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
    public GRAWinnersMinMaxDTO findGRAWinnersMinMaxInterval() {

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
