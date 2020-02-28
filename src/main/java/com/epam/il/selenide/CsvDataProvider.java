package com.epam.il.selenide;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class CsvDataProvider {
    private static final Logger LOGGER = Logger.getLogger(CsvDataProvider.class);

    private CsvDataProvider() { }

    @DataProvider(name = "csvdataset")
    public static Iterator<Object[]> provideData(final Method method) {
        List<Object[]> list = new ArrayList<>();
        String pathName = "src" + File.separator + "test" + File.separator + "resources" + File.separator + method
                .getDeclaringClass().getSimpleName() + "_" + method.getName() + ".csv";
        File file = new File(pathName);
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
            }
        } catch (CsvValidationException e) {
            LOGGER.info("File " + pathName + " not found.\n" + Arrays.toString(e.getStackTrace()));
        } catch (IOException e) {
            LOGGER.info("Could not read " + pathName + " file.\n" + Arrays.toString(e.getStackTrace()));
        }
        return list.iterator();
    }
}
