package ru.burlakov.dshkazan.utill;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.burlakov.dshkazan.dto.MetricParameterDTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ImportExcel {

    public static List<MetricParameterDTO> importExcel(String pointName, String path, Double[] coords) throws IOException {
        List<MetricParameterDTO> list = new ArrayList<>();

        FileInputStream file = new FileInputStream(new File(path));
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);

        Row header1 = sheet.getRow(0);
        Row header2 = sheet.getRow(1);

        List<Map<String, Object>> params = new ArrayList<>();

        int i = -1;
        for (Cell cell : header1) {
            if(++i == 0) continue;
            String paramName = cell.getStringCellValue();

            if(!paramName.contains("Параметр")) continue;

            paramName = paramName.replace("Параметр", "Parameter").trim();

            HashMap<String, Object> param = new HashMap<>();
            String value = header2.getCell(i).getStringCellValue();
            value = value.replace("ПДК: ", "").replace(",", ".");
            Double pdk = value.equals("") ? null : Double.valueOf(value);

            param.put("name", paramName);
            param.put("value", pdk);
            params.add(param);
        }

        i = -1;
        for (Row row: sheet) {
            if(++i < 3) continue;

            try {
                Date time = row.getCell(1).getDateCellValue();
                Double deg = row.getCell(1 + params.size() * 2 + 3).getNumericCellValue();
                Double speed = row.getCell(1 + params.size() * 2 + 6).getNumericCellValue();
                Double temperature = row.getCell(1 + params.size() * 2 + 8).getNumericCellValue();

                int j = 2;
                for (Map<String,Object> param : params) {
                    MetricParameterDTO metric = new MetricParameterDTO();
                    metric.setWatcher(pointName);
                    metric.setParameter((String) param.get("name"));
                    metric.setCoord(coords);
                    metric.setTime(time);
                    metric.setValue(row.getCell(j).getNumericCellValue());
                    metric.setWindSpeed(speed);
                    metric.setAirTemperature(temperature);
                    metric.setWindDeg(deg < 0 ? deg + 360 : deg);
                    metric.setPdk((Double) param.get("value"));
                    list.add(metric);
                    j += 2;
                }
            } catch (Exception e) {
                continue;
            }

        }

//        for (Row row : sheet) {
//            data.put(i, new ArrayList<String>());
//            for (Cell cell : row) {
//                switch (cell.getCellTypeEnum()) {
//                    case STRING: ... break;
//                    case NUMERIC: ... break;
//                    case BOOLEAN: ... break;
//                    case FORMULA: ... break;
//                    default: data.get(new Integer(i)).add(" ");
//                }
//            }
//            i++;
//        }

        return list;
    }

}
