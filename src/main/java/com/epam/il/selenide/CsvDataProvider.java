package com.epam.il.selenide;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.validators.LineValidator;
import com.opencsv.validators.LineValidatorAggregator;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Csv data reader implementation.
 */
public final class CsvDataProvider {
    private CsvDataProvider() { }

    /**
     * Creating an iterator for the csv data file.
     *
     * @param method the reflected method
     * @return Iterator/<Object[]/>
     * @throws IOException            if the file does not exist
     * @throws CsvValidationException if the file format is not correct
     */
    @DataProvider(name = "csvdataset")
    public static Iterator<Object[]> provideData(final Method method)
            throws IOException, CsvValidationException {
        List<Object[]> list = new ArrayList<>();
        String pathName = "src" + File.separator + "test" + File.separator + "resources"
                + File.separator + method.getDeclaringClass().getSimpleName() + "_" + method
                .getName() + ".csv";
        File file = new File(pathName);
        LineValidatorAggregator lineValidatorAggregator = new LineValidatorAggregator();
        lineValidatorAggregator.addValidator(new LineValidator() {
            @Override
            public boolean isValid(final String line) {
                return true;
            }

            @Override
            public void validate(final String line) throws CsvValidationException {
                if (line == null || "".equals(line)) {
                    throw new CsvValidationException("Csv data file cannot be empty or null.");
                }
            }
        });
        try (CSVReader reader = new CSVReader(new FileReader(file));) {
            String[] keys = reader.readNext();
            if (keys != null) {
                String[] dataParts;
                while ((dataParts = reader.readNext()) != null) {
                    Map<String, String> testData = new HashMap<>();
                    for (int i = 0; i < keys.length; i++) {
                        testData.put(keys[i], dataParts[i]);
                    }
                    list.add(new Object[] {testData});
                }
                lineValidatorAggregator.validate(keys[0]);
            } else if (lineValidatorAggregator.isValid(null)) {
                lineValidatorAggregator.validate(null);
            }
        }
        return list.iterator();
    }
}
