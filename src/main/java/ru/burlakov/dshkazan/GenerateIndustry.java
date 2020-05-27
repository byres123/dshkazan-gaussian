package ru.burlakov.dshkazan;

import au.com.bytecode.opencsv.CSVWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.burlakov.dshkazan.dto.IndustryDTO;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.math.BigDecimal.ROUND_HALF_UP;

public class GenerateIndustry {

    public final static String FILE_NAME = "static/industry-points.json";
    public final static String FILE_OUT = "export/industry.csv";

    public static void main(String[] args) throws IOException {
        List<IndustryDTO> dtos = getIndustry();
        saveIndustry(dtos);
    }

    public static void saveIndustry(List<IndustryDTO> industryDTOS) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(FILE_OUT, false));

        String[] header = new String[]{"name", "coord", "height", "diameter", "speed", "avgDisch", "outTemperature"};

        writer.writeNext(header);

        industryDTOS.forEach(dto -> writer.writeNext(createRow(dto)));
        writer.close();
    }

    public static String[] createRow(IndustryDTO industryDTO) {
        String[] row = new String[]{
                industryDTO.getName(),
                Stream.of(industryDTO.getCoord()).map(String::valueOf).collect(Collectors.joining(",")),
                industryDTO.getHeight().toString(),
                industryDTO.getOutDiameter().toString(),
                industryDTO.getOutSpeed().toString(),
                industryDTO.getAvgDisch().toString(),
                industryDTO.getOutTemperature().toString()
        };
        return row;
    }

    public static List<IndustryDTO> getIndustry() throws IOException {
        Stream<String> lines = Files.lines(Paths.get(FILE_NAME));
        String json = lines.collect(Collectors.joining(System.lineSeparator()));

        ObjectMapper objectMapper = new ObjectMapper();

        Double[][] objects = objectMapper.readValue(json, Double[][].class);
        List<IndustryDTO> dtos = new ArrayList<>();

        for (int i=0; i<objects.length; i++) {
            IndustryDTO dto = new IndustryDTO();
            dto.setName("INDUSTRY_" + i);
            dto.setCoord(objects[i]);
            addIndustryParams(dto);
            dtos.add(dto);
        }

        return dtos;
    }

    public static void addIndustryParams(IndustryDTO industryDTO) {
        industryDTO.setHeight(getRandHeight());
        industryDTO.setOutDiameter(getRandDiameter());
        industryDTO.setOutSpeed(getRandSpeed());
        industryDTO.setAvgDisch(getRandAvgDisch());
        industryDTO.setOutTemperature(getRandOutTemperature());
    }

    //· высокие (высота выброса – 50 и более метров);
    //· средние (10–50 м);
    //· низкие (2–10 м);
    //· наземные (до 2 м).
    public static Double getRandHeight() {
        return getRangRandom(2D, 150D);
    }

    public static Double getRandDiameter() {
        return getRangRandom(0.3, 2.5D);
    }

    public static Double getRandSpeed() {
        return getRangRandom(20D, 50D);
    }

    public static Double getRandAvgDisch() {
        return getRangRandom(0.005, 2D);
    }

    public static Double getRandOutTemperature() {
        return getRangRandom(10D, 50D);
    }

    public static Double getRangRandom(Double min, Double max) {
        return BigDecimal.valueOf((Math.random() * ((max - min) + 1)) + min).setScale(2, ROUND_HALF_UP).doubleValue();
    }

}
