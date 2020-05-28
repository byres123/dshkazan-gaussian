package ru.burlakov.dshkazan.utill;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ru.burlakov.dshkazan.dto.ExcessDTO;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExportExcess {

    public static void export(String name, List<ExcessDTO> list) throws IOException {
        writeExcel(name, list);
        writeCSV(name, list);
    }

    public static void writeExcel(String name, List<ExcessDTO> list) throws IOException {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("default");
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Row headerRow = sheet.createRow(0);

        Cell time = headerRow.createCell(0);
        time.setCellValue("time");

        Cell industry = headerRow.createCell(1);
        industry.setCellValue("industry");

        Cell parameter = headerRow.createCell(2);
        parameter.setCellValue("parameter");

        Cell pdk = headerRow.createCell(3);
        pdk.setCellValue("pdk");

        Cell value = headerRow.createCell(4);
        value.setCellValue("value");

        Cell lt = headerRow.createCell(5);
        lt.setCellValue("lt");

        Cell ln = headerRow.createCell(6);
        ln.setCellValue("ln");

        Cell zip = headerRow.createCell(7);
        zip.setCellValue("zip");

        Cell watcher = headerRow.createCell(8);
        watcher.setCellValue("watcher");

        Cell calcValue = headerRow.createCell(9);
        calcValue.setCellValue("calcValue");

        int i = 0;
        for (ExcessDTO dto : list) {

            Row row = sheet.createRow(++i);

            Cell dataTime = row.createCell(0);
            dataTime.setCellValue(formatter.format(dto.getTime()));

            Cell dataIndustry = row.createCell(1);
            dataIndustry.setCellValue(dto.getIndustry());

            Cell dataParameter = row.createCell(2);
            dataParameter.setCellValue(dto.getParameter());

            Cell dataPdk = row.createCell(3);
            dataPdk.setCellValue(dto.getPdk().toString());

            Cell dataValue = row.createCell(4);
            dataValue.setCellValue(dto.getValue().toString());

            Cell dataLt = row.createCell(5);
            dataLt.setCellValue(dto.getCoord()[0].toString());

            Cell dataLn = row.createCell(6);
            dataLn.setCellValue(dto.getCoord()[1].toString());

            Cell dataZip = row.createCell(7);
            dataZip.setCellValue("470000");

            Cell dataWatcher = row.createCell(8);
            dataWatcher.setCellValue(dto.getWatcher());

            Cell dataCalcValue = row.createCell(9);
            dataCalcValue.setCellValue(dto.getCalcValue());
        }

        book.write(new FileOutputStream(String.format("export/excess/%s.xls", name)));
        book.close();
    }

    public static void writeCSV(String name, List<ExcessDTO> list) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(String.format("export/excess/%s.csv", name), false));

        String[] header = new String[]{"time", "industry", "parameter", "pdk", "value", "lt", "ln", "zip", "watcher", "calcValue"};

        writer.writeNext(header);

        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (ExcessDTO dto : list) {
            writer.writeNext(new String[]{
                    formatter.format(dto.getTime()),
                    dto.getIndustry(),
                    dto.getParameter(),
                    dto.getPdk().toString(),
                    dto.getValue().toString(),
                    dto.getCoord()[0].toString(),
                    dto.getCoord()[1].toString(),
                    "470000",
                    dto.getWatcher(),
                    dto.getCalcValue().toString()
            });
        }
    }

}
