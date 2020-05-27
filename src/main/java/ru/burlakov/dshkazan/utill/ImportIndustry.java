package ru.burlakov.dshkazan.utill;

import au.com.bytecode.opencsv.CSVReader;
import ru.burlakov.dshkazan.GenerateIndustry;
import ru.burlakov.dshkazan.dto.IndustryDTO;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ImportIndustry {

    public static List<IndustryDTO> importIndustry() throws IOException {
        CSVReader reader = new CSVReader(new FileReader(GenerateIndustry.FILE_OUT));
        List<IndustryDTO> list = new ArrayList<>();

        reader.readNext();
        String[] row = reader.readNext();

        while (row != null) {
            IndustryDTO dto = new IndustryDTO();
            dto.setName(row[0]);
            dto.setCoord(Arrays.stream(row[1].split(",")).map(item -> Double.valueOf(item)).collect(Collectors.toList()).toArray(new Double[2]));
            dto.setHeight(Double.valueOf(row[2]));
            dto.setOutDiameter(Double.valueOf(row[3]));
            dto.setOutSpeed(Double.valueOf(row[4]));
            dto.setAvgDisch(Double.valueOf(row[5]));
            dto.setOutTemperature(Double.valueOf(row[6]));
            list.add(dto);
            row = reader.readNext();
        }

        return list;
    }

}
