package com.cristopherandre.goldenraspberryawardsapi.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristopherandre.goldenraspberryawardsapi.dto.GRAWinnersMinMaxDTO;
import com.cristopherandre.goldenraspberryawardsapi.service.GRAWinnerService;

@RestController
@RequestMapping("/grawinners")
public class GRAWinnerController {

    @Autowired
    private GRAWinnerService graWinnerService;

    @GetMapping(value = "/minmax")
    public ResponseEntity<GRAWinnersMinMaxDTO> getMethodName() {
        GRAWinnersMinMaxDTO graWinnersMinMax = graWinnerService.findGRAWinnersMinMax();

        if (Objects.isNull(graWinnersMinMax)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(graWinnersMinMax);
    }

}
