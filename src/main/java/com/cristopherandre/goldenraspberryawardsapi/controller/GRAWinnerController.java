package com.cristopherandre.goldenraspberryawardsapi.controller;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristopherandre.goldenraspberryawardsapi.dto.GRAWinnerDTO;
import com.cristopherandre.goldenraspberryawardsapi.dto.GRAWinnersMinMaxDTO;
import com.cristopherandre.goldenraspberryawardsapi.mapper.GRAWinnerMapper;
import com.cristopherandre.goldenraspberryawardsapi.model.GRAWinner;
import com.cristopherandre.goldenraspberryawardsapi.service.GRAWinnerService;

/**
 * @author Cristopher Andre
 */
@RestController
@RequestMapping("/api/v1/grawinners")
public class GRAWinnerController {

    @Autowired
    private GRAWinnerService service;

    @Autowired
    private GRAWinnerMapper mapper;

    // List all GRAWinners
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<GRAWinnerDTO>> getAllGRAWinners() {
        Collection<GRAWinner> graWinners = service.findAll();
        if (graWinners.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        return ResponseEntity.status(HttpStatus.OK)
                .body(graWinners.stream().map(m -> mapper.toGRAWinnerDTO(m)).toList());
    }

    // List all Min and Max Award Interval
    @GetMapping(value = "/minMaxAwardInterval")
    public ResponseEntity<GRAWinnersMinMaxDTO> getMethodName() {
        GRAWinnersMinMaxDTO graWinnersMinMax = service.findGRAWinnersMinMax();
        if (Objects.isNull(graWinnersMinMax)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(graWinnersMinMax);
    }

}
