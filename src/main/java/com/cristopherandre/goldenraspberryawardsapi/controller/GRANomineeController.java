package com.cristopherandre.goldenraspberryawardsapi.controller;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristopherandre.goldenraspberryawardsapi.dto.GRANomineeDTO;
import com.cristopherandre.goldenraspberryawardsapi.dto.GRAWinnersMinMaxDTO;
import com.cristopherandre.goldenraspberryawardsapi.mapper.GRANomineeMapper;
import com.cristopherandre.goldenraspberryawardsapi.model.GRANominee;
import com.cristopherandre.goldenraspberryawardsapi.service.GRANomineeService;

/**
 * @author Cristopher Andre
 */
@RestController
@RequestMapping("/granominees")
public class GRANomineeController {

    @Autowired
    private GRANomineeService service;

    @Autowired
    private GRANomineeMapper mapper;

    // List all GRANominees
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<GRANomineeDTO>> getAllGRANominees() {
        Collection<GRANominee> graNominees = service.findAll();
        if (graNominees.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        return ResponseEntity.status(HttpStatus.OK)
                .body(graNominees.stream().map(m -> mapper.toGRANomineeDTO(m)).toList());
    }

    // List GRANominee by Id
    @GetMapping(value = "/getById/{id}")
    public ResponseEntity<GRANomineeDTO> getGRANomineeById(@PathVariable(value = "id") String id) {
        GRANominee graNominee = service.findById(Long.valueOf(id));
        if (Objects.isNull(graNominee)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toGRANomineeDTO(graNominee));
    }

    // Save GRANominee
    @PostMapping(value = "/createGRANominee")
    public ResponseEntity<GRANomineeDTO> createGRANominee(@RequestBody GRANomineeDTO graNomineeDTO) {
        GRANominee graNominee = service.saveGRANominee(mapper.toGRANominee(graNomineeDTO));
        if (Objects.isNull(graNominee)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toGRANomineeDTO(graNominee));
    }

    // Update GRANominee
    @PutMapping(value = "/updateGRANominee/{id}")
    public ResponseEntity<GRANomineeDTO> updateGRANominee(@PathVariable(value = "id") String id,
            @RequestBody GRANomineeDTO graNomineeDTO) {
        GRANominee graNominee = service.updateGRANominee(Long.valueOf(id, 0), mapper.toGRANominee(graNomineeDTO));
        if (Objects.isNull(graNominee)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toGRANomineeDTO(graNominee));
    }

    // Delete GRANominee
    @DeleteMapping(value = "/deleteGRANominee/{id}")
    public void deleteGRANominee(@PathVariable(value = "id") String id) {
        service.deleteGRANominee(Long.valueOf(id));
    }

    // List all Min and Max GRA Winners
    @GetMapping(value = "/minMaxGRAWinnersInterval")
    public ResponseEntity<GRAWinnersMinMaxDTO> getGRAWinnersMinMaxInterval() {
        GRAWinnersMinMaxDTO graWinnersMinMax = service.findGRAWinnersMinMaxInterval();
        if (Objects.isNull(graWinnersMinMax)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(graWinnersMinMax);
    }

}
