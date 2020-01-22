package org.metastringfoundation.healthheatmap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class CSVReader {
    private String path;

    CSVReader(String path) {
        this.path = path;
    }

    private Reader getFileReader(String path) throws DatasetIntegrityError {
        try {
            return new FileReader(path);
        } catch (Exception e) {
            throw new DatasetIntegrityError(e);
        }
    }

    private List<CSVRecord> getCSVRecords(Reader in) throws DatasetIntegrityError {
        try {
            CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT);
            return parser.getRecords();
        } catch (IOException e) {
            throw new DatasetIntegrityError(e);
        }
    }

    public void readCSV() throws DatasetIntegrityError {
        Reader in = getFileReader(path);
        List<CSVRecord> list = getCSVRecords(in);
        for (CSVRecord record: list) {
            Iterator recordIterator = record.iterator();
            while (recordIterator.hasNext()) {
                System.out.println(recordIterator.next());
            }
        }
    }
}
