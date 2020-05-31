package ru.burlakov.dshkazan;

import ru.burlakov.dshkazan.dto.ExcessDTO;
import ru.burlakov.dshkazan.dto.IndustryDTO;
import ru.burlakov.dshkazan.dto.MetricParameterDTO;
import ru.burlakov.dshkazan.utill.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HitoryDischarge {

    private final static Map<String,String> watchers = new HashMap<String,String>(){
        {
            put("АСКЗА-1", "static/data_all/2016/АСКЗА-1/АСКЗА-1 10.2016.xlsx");
            put("АСКЗА-2", "static/data_all/2016/АСКЗА-2/АСКЗА-2 10.2016.xlsx");
            put("АСКЗА-3", "static/data_all/2016/АСКЗА-3/АСКЗА-3 10.2016.xlsx");
            put("АСКЗА-4", "static/data_all/2016/АСКЗА-4/АСКЗА-4 10.2016.xlsx");
        }
    };

    private final static LocalDate currentDate = null;

    public static void main(String[] args) throws IOException {

        Map<String,Double[]> allCoords = ImportWatcherCoords.importList();

        for(String watcher : watchers.keySet()) {
            List<MetricParameterDTO> metrics = ImportExcel.importExcel(watcher, watchers.get(watcher), allCoords.get(watcher), currentDate);
            metrics = GetHistory.getList(metrics);
            ExportHistory.exportHistory(watcher, metrics);
        }
    }

}
