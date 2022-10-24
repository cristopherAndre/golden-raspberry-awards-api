package com.cristopherandre.goldenraspberryawardsapi.service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.cristopherandre.goldenraspberryawardsapi.exceptions.RecordNotFoundException;
import com.cristopherandre.goldenraspberryawardsapi.model.GRAWinner;
import com.cristopherandre.goldenraspberryawardsapi.model.Movie;
import com.cristopherandre.goldenraspberryawardsapi.model.Producer;
import com.cristopherandre.goldenraspberryawardsapi.model.Studio;
import com.cristopherandre.goldenraspberryawardsapi.repository.GRAWinnerRepository;

@Service
public class GRAWinnerServiceImpl implements GRAWinnerService {

    @Autowired
    private Environment env;

    @Autowired
    private GRAWinnerRepository repository;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private StudioService studioService;

    @Autowired
    private MovieService movieService;

    String[] HEADERS = { "year", "title", "studios", "producers", "winner" };

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
    public Collection<GRAWinner> fetchGRAWinners() {
        return repository.findAll();
    }

    @Override
    public void loadGRAWinners() {

        try {

            String csvDataFileLocation = env.getProperty("initial.data.csv.location");
            String csvDataFileName = env.getProperty("initial.data.csv.file");

            Reader csvFile = new FileReader(csvDataFileLocation + csvDataFileName);

            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setDelimiter(";")
                    .setHeader(HEADERS)
                    .setSkipHeaderRecord(true)
                    .setIgnoreEmptyLines(true)
                    .build()
                    .parse(csvFile);

            for (CSVRecord record : records) {

                String producers = record.get("producers");
                String studios = record.get("studios");
                String title = record.get("title");
                Integer year = Integer.parseInt(record.get("year"));
                Boolean isWinner = record.get("winner").trim().equalsIgnoreCase("YES") ? true : false;

                Set<Producer> producersList = producerService.saveProducers(producers);
                Set<Studio> studiosList = studioService.saveStudios(studios);
                Movie movie = Movie.builder().title(title).producers(producersList).studios(studiosList).build();
                movieService.saveMovie(movie);

                if (isWinner) {
                    GRAWinner graWinner = GRAWinner.builder().awardYear(year).movie(movie).build();
                    repository.save(graWinner);
                }
            }

        //TODO tratar erros e implementarLOG4J
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
