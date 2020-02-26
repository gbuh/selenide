package com.epam.il.selenide;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
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

public class CsvDataProvider {

    @DataProvider(name = "csvdataset")
    public static Iterator<Object[]> provideData(Method method) {
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
            throw new RuntimeException("File " + pathName + " not found.\n" + e.getStackTrace().toString());
        } catch (IOException e) {
            throw new RuntimeException("Could not read " + pathName + " file.\n" + e.getStackTrace().toString());
        }
        return list.iterator();
    }
}
