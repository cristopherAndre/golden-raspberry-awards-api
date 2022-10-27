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

import com.cristopherandre.goldenraspberryawardsapi.model.GRANominee;
import com.cristopherandre.goldenraspberryawardsapi.model.Movie;
import com.cristopherandre.goldenraspberryawardsapi.model.Producer;
import com.cristopherandre.goldenraspberryawardsapi.model.Studio;
import com.cristopherandre.goldenraspberryawardsapi.repository.GRANomineeRepository;

/**
 * @author Cristopher Andre
 */
@Service
public class CSVDataImportServiceImpl implements CSVDataImportService {

    private static final Logger logger = LogManager.getLogger(CSVDataImportServiceImpl.class.getName());

    @Autowired
    private Environment env;

    @Autowired
    private GRANomineeRepository graNomineeRepository;

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

        try {
            Instant start = Instant.now();
            logger.info("---------STARTING CSV IMPORT DATA---------");

            // Le o arquivo de importação CSV
            Iterable<CSVRecord> records = readCSVFile();

            // Percorre os items do arquivo de importação CSV
            if(Objects.nonNull(records)){
                for (CSVRecord record : records) {

                    // Lê os valores de cada coluna
                    String producers = record.get("producers");
                    String studios = record.get("studios");
                    String title = record.get("title");
                    Integer year = Integer.parseInt(record.get("year"));
                    boolean isWinner = record.get("winner").trim().equalsIgnoreCase("YES");

                    // Persiste os producers/studios caso nao existirem na base e retorna a lista
                    Set<Producer> producersList = producerService.saveProducers(producers);
                    Set<Studio> studiosList = studioService.saveStudios(studios);

                    // Constroi o objeto Movie e o persiste no banco
                    Movie movie = Movie.builder().title(title).producers(producersList).studios(studiosList).build();
                    movieService.saveMovie(movie);

                    // Constroi o objeto GRANominee e o persiste no banco
                    GRANominee graWinner = GRANominee.builder().awardYear(year).movie(movie).isWinner(isWinner).build();
                    graNomineeRepository.save(graWinner);
                }
            }
            Instant end = Instant.now();
            timeElapsed = Duration.between(start, end);
        } catch (Exception e) {
            logger.error("---------ERROR WHILE IMPORTING CSV DATA---------", e);
        } finally {
            logger.info("---------FINISHED CSV IMPORT DATA: " + "Time taken (ms): " + timeElapsed.toMillis());
            if (Objects.nonNull(csvFile)) {
                try {
                    csvFile.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public Iterable<CSVRecord> readCSVFile() {
        String csvDataFileLocation = env.getProperty("initial.data.csv.location");
        String csvDataFileName = env.getProperty("initial.data.csv.file");
        Reader csvFile = null;
        Iterable<CSVRecord> records = null;
        try {
            csvFile = new FileReader(csvDataFileLocation + csvDataFileName);
            records = CSVFormat.DEFAULT.builder()
                    .setDelimiter(";")
                    .setHeader(HEADERS)
                    .setSkipHeaderRecord(true)
                    .setIgnoreEmptyLines(true)
                    .build()
                    .parse(csvFile);
        } catch (Exception e) {
            logger.error("---------ERROR WHILE READ CSV DATA---------", e);
        }
        return records;
    }

}
