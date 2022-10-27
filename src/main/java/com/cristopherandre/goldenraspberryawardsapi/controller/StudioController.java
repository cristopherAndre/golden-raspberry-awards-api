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

import com.cristopherandre.goldenraspberryawardsapi.dto.StudioDTO;
import com.cristopherandre.goldenraspberryawardsapi.mapper.StudioMapper;
import com.cristopherandre.goldenraspberryawardsapi.model.Studio;
import com.cristopherandre.goldenraspberryawardsapi.service.StudioService;

/**
 * @author Cristopher Andre
 */
@RestController
@RequestMapping("/api/v1/studios")
public class StudioController {

    @Autowired
    private StudioService service;

    @Autowired
    private StudioMapper mapper;

    // List all Studios
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<StudioDTO>> getAllMovies() {
        Collection<Studio> studios = service.findAll();
        if (studios.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(studios.stream().map(p -> mapper.toStudioDTO(p)).toList());
    }

    // List Studio by Id
    @GetMapping(value = "/getById/{id}")
    public ResponseEntity<StudioDTO> getMovieById(@PathVariable(value = "id") String id) {
        Studio studio = service.findById(Long.valueOf(id));
        if (Objects.isNull(studio)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toStudioDTO(studio));
    }

    // List Studio by Name
    @GetMapping(value = "/getByName/{name}")
    public ResponseEntity<StudioDTO> getMovieByName(@PathVariable(value = "title") String name) {
        Studio studio = service.findByName(name);
        if (Objects.isNull(studio)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toStudioDTO(studio));
    }

    // Save Studio
    @PostMapping(value = "/createStudio")
    public ResponseEntity<StudioDTO> createMovie(@RequestBody StudioDTO studioDTO) {
        Studio studio = service.saveStudio(mapper.toStudio(studioDTO));
        if (Objects.isNull(studio)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toStudioDTO(studio));
    }

    // Update Studio
    @PutMapping(value = "/updateStudio/{id}")
    public ResponseEntity<StudioDTO> updateStudio(@PathVariable(value = "id") String id,
            @RequestBody StudioDTO studioDTO) {
        Studio studio = service.updateStudio(Long.valueOf(id, 0), mapper.toStudio(studioDTO));
        if (Objects.isNull(studio)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toStudioDTO(studio));
    }

    // Delete Studio
    @DeleteMapping(value = "/deleteStudio/{id}")
    public void deleteStudio(@PathVariable(value = "id") String id) {
        service.deleteStudio(Long.valueOf(id));
    }

}
