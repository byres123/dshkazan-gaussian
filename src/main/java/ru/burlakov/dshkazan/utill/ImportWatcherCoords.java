package ru.burlakov.dshkazan.utill;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ImportWatcherCoords {

    private final static String PATH_TO_FILE = "static/watcher_coords.csv";

    public static Map<String,Double[]> importList() throws IOException {
        CSVReader reader = new CSVReader(new FileReader(PATH_TO_FILE));
        Map<String,Double[]> coords = new HashMap<>();

        List<String[]> lines = reader.readAll();

        for(String[] line : lines) {
            String value = line[1].replaceAll("\\s", "");
            coords.put(
                    line[0],
                    Arrays.stream(value.split(","))
                            .map(item -> Double.parseDouble(item))
                            .collect(Collectors.toList())
                            .toArray(new Double[2])
            );
        }

        return coords;
    }

}
