package com.cristopherandre.goldenraspberryawardsapi.service;

import org.apache.commons.csv.CSVRecord;

/**
 * @author Cristopher Andre
 */
public interface CSVDataImportService {

    public abstract void importCSVData();

    public abstract Iterable<CSVRecord> readCSVFile();

}
