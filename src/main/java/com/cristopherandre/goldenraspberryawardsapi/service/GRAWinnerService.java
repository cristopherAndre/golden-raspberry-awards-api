package com.cristopherandre.goldenraspberryawardsapi.service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class GRAWinnerService {

    @Autowired
    private Environment env;

    String[] HEADERS = { "year", "title", "studios", "producers", "winner"};

    public void loadGRAWinners() throws IOException {

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
            String year = record.get("year");
            String title = record.get("title");
            String studios = record.get("studios");
            String producers = record.get("producers");
            String winner = record.get("winner");
        }

        

    

    }

}
