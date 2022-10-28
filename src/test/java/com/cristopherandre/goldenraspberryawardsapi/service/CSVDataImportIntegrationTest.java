package com.cristopherandre.goldenraspberryawardsapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cristopherandre.goldenraspberryawardsapi.GoldenRaspberryAwardsApiApplication;
import com.cristopherandre.goldenraspberryawardsapi.dto.GRANomineeDTO;
import com.cristopherandre.goldenraspberryawardsapi.dto.MovieDTO;
import com.cristopherandre.goldenraspberryawardsapi.dto.ProducerDTO;
import com.cristopherandre.goldenraspberryawardsapi.dto.StudioDTO;
import com.cristopherandre.goldenraspberryawardsapi.mapper.GRANomineeMapper;
import com.cristopherandre.goldenraspberryawardsapi.model.GRANominee;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = GoldenRaspberryAwardsApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CSVDataImportIntegrationTest {

    private static final String HOST = "http://localhost:";
    private static final String MOVIES_PATH = "/api/v1/movies";
    private static final String GET_ALL_MOVIES_PATH = "/getAll";

    private String path;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CSVDataImportService csvDataImportService;

    @Autowired
    private GRANomineeService graNomineeService;

    @Autowired
    private GRANomineeMapper graNomineeMapper;

    @BeforeEach
    private void init() {
        this.path = HOST + port + MOVIES_PATH;
    }

    @Test
    void getAllMovies() {

        // Busca todos Movies da API, importados no Startup da aplicação
        ResponseEntity<MovieDTO[]> response = testRestTemplate.getForEntity(this.path + GET_ALL_MOVIES_PATH,
                MovieDTO[].class);

        // Le o arquivo de importação CSV
        Iterable<CSVRecord> csvDataRecords = csvDataImportService.readCSVFile();

        // Compara se os valores retornados pela API se são iguais aos valores contidos no arquivo de importação CSV
        if (Objects.nonNull(response.getBody()) && Objects.nonNull(csvDataRecords)) {
            List<MovieDTO> movies = Arrays.asList(response.getBody());

            // Percorre os items do arquivo de importação CSV
            for (CSVRecord record : csvDataRecords) {

                // Lê os valores de cada coluna
                String title = record.get("title").trim();
                ArrayList<String> producers = new ArrayList<>(Arrays.asList(Arrays
                        .stream(record.get("producers").split(",|\\ and ")).map(String::trim).toArray(String[]::new)));
                ArrayList<String> studios = new ArrayList<>(Arrays.asList(Arrays
                        .stream(record.get("studios").split(",|\\ and ")).map(String::trim).toArray(String[]::new)));
                Integer year = Integer.parseInt(record.get("year"));
                boolean isWinner = record.get("winner").trim().equalsIgnoreCase("YES");

                // Filtra o Movie da API pelo item da iteração da leitura do arquivo de importação CSV
                MovieDTO movieDTO = movies.stream().filter(m -> m.getTitle().equalsIgnoreCase(title)).findFirst()
                        .orElse(null);

                // Realiza os Asserts
                if (Objects.nonNull(movies)) {

                    assertEquals(title, movieDTO.getTitle());

                    // Verifica se o valor de uma lista de objetos existe em outra lista de Strings
                    boolean foundProducer = movieDTO.getProducers()
                            .stream()
                            .map(ProducerDTO::getName)
                            .anyMatch(
                                    producers
                                            .stream()
                                            .map(p -> p)
                                            .collect(Collectors.toSet())::contains);
                    assertTrue(foundProducer);

                    // Verifica se o valor de uma lista de objetos existe em outra lista de Strings
                    boolean foundStudio = movieDTO.getStudios()
                            .stream()
                            .map(StudioDTO::getName)
                            .anyMatch(
                                    studios
                                            .stream()
                                            .map(p -> p)
                                            .collect(Collectors.toSet())::contains);
                    assertTrue(foundStudio);

                    // Busca o GRANominee que é uma Inner Relation
                    GRANominee graNominee = graNomineeService.findByMovieId(movieDTO.getId());
                    if (Objects.nonNull(graNominee)) {
                        // Realiza os Asserts
                        GRANomineeDTO graNomineeDTO = graNomineeMapper.toGRANomineeDTO(graNominee);
                        assertEquals(year, graNomineeDTO.getAwardYear());
                        assertEquals(isWinner, graNomineeDTO.getIsWinner());
                    }
                }
            }
        }
    }
}
