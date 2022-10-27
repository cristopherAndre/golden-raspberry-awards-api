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

import com.cristopherandre.goldenraspberryawardsapi.dto.ProducerDTO;
import com.cristopherandre.goldenraspberryawardsapi.mapper.ProducerMapper;
import com.cristopherandre.goldenraspberryawardsapi.model.Producer;
import com.cristopherandre.goldenraspberryawardsapi.service.ProducerService;

@RestController
@RequestMapping("/api/v1/producers")
public class ProducerController {

    @Autowired
    private ProducerService service;

    @Autowired
    private ProducerMapper mapper;

    // List all Producers
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<ProducerDTO>> getAllMovies() {
        Collection<Producer> producers = service.findAll();
        if (producers.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(producers.stream().map(p -> mapper.toProducerDTO(p)).toList());
    }

    // List Producer by Id
    @GetMapping(value = "/getById/{id}")
    public ResponseEntity<ProducerDTO> getMovieById(@PathVariable(value = "id") String id) {
        Producer producer = service.findById(Long.valueOf(id));
        if (Objects.isNull(producer)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toProducerDTO(producer));
    }

    // List Producer by Name
    @GetMapping(value = "/getByName/{name}")
    public ResponseEntity<ProducerDTO> getMovieByName(@PathVariable(value = "title") String name) {
        Producer producer = service.findByName(name);
        if (Objects.isNull(producer)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toProducerDTO(producer));
    }

    // Save Producer
    @PostMapping(value = "/createProducer")
    public ResponseEntity<ProducerDTO> createMovie(@RequestBody ProducerDTO producerDTO) {
        Producer producer = service.saveProducer(mapper.toProducer(producerDTO));
        if (Objects.isNull(producer)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toProducerDTO(producer));
    }

    // Update Producer
    @PutMapping(value = "/updateProducer/{id}")
    public ResponseEntity<ProducerDTO> updateProducer(@PathVariable(value = "id") String id,
            @RequestBody ProducerDTO producerDTO) {
        Producer producer = service.updateProducer(Long.valueOf(id, 0), mapper.toProducer(producerDTO));
        if (Objects.isNull(producer)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toProducerDTO(producer));
    }

    // Delete Producer
    @DeleteMapping(value = "/deleteProducer/{id}")
    public void deleteProducer(@PathVariable(value = "id") String id) {
        service.deleteProducer(Long.valueOf(id));
    }

}
