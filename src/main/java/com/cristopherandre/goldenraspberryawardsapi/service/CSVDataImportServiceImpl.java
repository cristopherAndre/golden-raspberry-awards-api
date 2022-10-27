package com.cristopherandre.goldenraspberryawardsapi.service;

import java.io.FileReader;
import java.io.Reader;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.cristopherandre.goldenraspberryawardsapi.model.GRAWinner;
import com.cristopherandre.goldenraspberryawardsapi.model.Movie;
import com.cristopherandre.goldenraspberryawardsapi.model.Producer;
import com.cristopherandre.goldenraspberryawardsapi.model.Studio;
import com.cristopherandre.goldenraspberryawardsapi.repository.GRAWinnerRepository;

@Service
public class CSVDataImportServiceImpl implements CSVDataImportService {

    private static final Logger logger = LogManager.getLogger(CSVDataImportServiceImpl.class.getName());

    @Autowired
    private Environment env;

    @Autowired
    private GRAWinnerRepository graWinnerRepository;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private StudioService studioService;

    @Autowired
    private MovieService movieService;

    private static final String[] HEADERS = { "year", "title", "studios", "producers", "winner" };

    @Override
    public void importCSVData() {

        Reader csvFile = null;
        Duration timeElapsed = null;
        String csvDataFileLocation = env.getProperty("initial.data.csv.location");
        String csvDataFileName = env.getProperty("initial.data.csv.file");

        try {
            Instant start = Instant.now();
            logger.info("---------STARTING CSV IMPORT DATA---------");

            csvFile = new FileReader(csvDataFileLocation + csvDataFileName);
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
                boolean isWinner = record.get("winner").trim().equalsIgnoreCase("YES");

                Set<Producer> producersList = producerService.saveProducers(producers);
                Set<Studio> studiosList = studioService.saveStudios(studios);

                Movie movie = Movie.builder().title(title).producers(producersList).studios(studiosList).build();
                movieService.saveMovie(movie);

                if (isWinner) {
                    GRAWinner graWinner = GRAWinner.builder().awardYear(year).movie(movie).build();
                    graWinnerRepository.save(graWinner);
                }
            }
            Instant end = Instant.now();
            timeElapsed = Duration.between(start, end);
        } catch (Exception e) {
            logger.error("---------ERROR WHILE IMPORTING CSV DATA---------", e);
        } finally {
            logger.info("---------FINISHED CSV IMPORT DATA--------->" + "Time taken (ms): " + timeElapsed.toMillis());
            if (Objects.nonNull(csvFile)) {
                try {
                    csvFile.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
