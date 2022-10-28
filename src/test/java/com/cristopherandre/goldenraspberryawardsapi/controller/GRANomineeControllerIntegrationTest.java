package com.cristopherandre.goldenraspberryawardsapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;

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
import com.cristopherandre.goldenraspberryawardsapi.dto.GRAWinnersMinMaxDTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = GoldenRaspberryAwardsApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GRANomineeControllerIntegrationTest {

    private static final String HOST = "http://localhost:";
    private static final String MOVIES_PATH = "/api/v1/granominees";
    private static final String GET_ALL_MOVIES_PATH = "/minMaxGRAWinnersInterval";

    private String path;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    private void init() {
        this.path = HOST + port + MOVIES_PATH;
    }

    @Test
    public void getGRAWinnersMinMaxInterval() {
        ResponseEntity<GRAWinnersMinMaxDTO> response = testRestTemplate.getForEntity(this.path + GET_ALL_MOVIES_PATH,
                GRAWinnersMinMaxDTO.class);
        if (Objects.nonNull(response.getBody())) {
            GRAWinnersMinMaxDTO graWinnersMinMaxDTO = response.getBody();
            if (Objects.nonNull(graWinnersMinMaxDTO)) {
                // Min interval Winner
                assertEquals("Joel Silver", graWinnersMinMaxDTO.getMin().get(0).getProducer());
                assertEquals(1, graWinnersMinMaxDTO.getMin().get(0).getInterval());
                assertEquals(1990, graWinnersMinMaxDTO.getMin().get(0).getPreviousWin());
                assertEquals(1991, graWinnersMinMaxDTO.getMin().get(0).getFollowingWin());

                // Max interval Winner
                assertEquals("Matthew Vaughn", graWinnersMinMaxDTO.getMax().get(0).getProducer());
                assertEquals(13, graWinnersMinMaxDTO.getMax().get(0).getInterval());
                assertEquals(2002, graWinnersMinMaxDTO.getMax().get(0).getPreviousWin());
                assertEquals(2015, graWinnersMinMaxDTO.getMax().get(0).getFollowingWin());
            }
        }
    }
}
