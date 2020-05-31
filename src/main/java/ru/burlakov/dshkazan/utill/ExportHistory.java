package ru.burlakov.dshkazan.utill;

import au.com.bytecode.opencsv.CSVWriter;
import ru.burlakov.dshkazan.dto.MetricParameterDTO;

import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExportHistory {

    private final static String OUT_PATH = "export/history/%s.csv";

    public static void exportHistory(String watcher, List<MetricParameterDTO> metrics) throws IOException {

        String currentParam = null;

        List<MetricParameterDTO> list = new ArrayList<>();

        for(MetricParameterDTO metric : metrics) {
            if(currentParam == null) currentParam = metric.getParameter();
            if(!currentParam.equals(metric.getParameter())) {
                export(metric.getParameter(), list, watcher);
                list = new ArrayList<>();
                currentParam = metric.getParameter();
            }
            list.add(metric);
        }
    }

    public static void export(String paramName, List<MetricParameterDTO> metrics, String watcher) throws IOException {

        String outPath = String.format(OUT_PATH, paramName + "_" + watcher);

        CSVWriter writer = new CSVWriter(new FileWriter(outPath));

        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        writer.writeNext(new String[] {
            "time",
            "speed",
            "direction"
        });

        for(MetricParameterDTO metric : metrics) {
            writer.writeNext(new String[] {
                formatter.format(metric.getTime()),
                metric.getWindSpeed().toString(),
                metric.getWindDeg().toString()
            });
        }

        int i = 0;
    }

}
