package ru.burlakov.dshkazan;

import ru.burlakov.dshkazan.dto.ExcessDTO;
import ru.burlakov.dshkazan.dto.IndustryDTO;
import ru.burlakov.dshkazan.dto.MetricParameterDTO;
import ru.burlakov.dshkazan.utill.*;

import java.io.IOException;
import java.util.*;

public class Application {

    private final static Map<String,String> watchers = new HashMap<String,String>(){
        {
            put("АСКЗА-1", "static/data_all/2016/АСКЗА-1/АСКЗА-1 04.2016.xlsx");
            put("АСКЗА-2", "static/data_all/2016/АСКЗА-2/АСКЗА-2 04.2016.xlsx");
            put("АСКЗА-3", "static/data_all/2016/АСКЗА-3/АСКЗА-3 04.2016.xlsx");
            put("АСКЗА-4", "static/data_all/2016/АСКЗА-4/АСКЗА-4 04.2016.xlsx");
        }
    };

    public static void main(String[] args) throws IOException {

        List<ExcessDTO> excessList = new ArrayList<>();

        Map<String,Double[]> allCoords = ImportWatcherCoords.importList();

        for(String watcher : watchers.keySet()) {
            List<MetricParameterDTO> metrics = ImportExcel.importExcel(watcher, watchers.get(watcher), allCoords.get(watcher));

            List<IndustryDTO> industryList = ImportIndustry.importIndustry();

            excessList.addAll(ExcessList.getList(metrics, industryList));
        }

        ExportExcess.export("АСКЗА", excessList);

    }
}
