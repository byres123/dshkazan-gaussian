package ru.burlakov.dshkazan;

import ru.burlakov.dshkazan.dto.ExcessDTO;
import ru.burlakov.dshkazan.dto.IndustryDTO;
import ru.burlakov.dshkazan.dto.MetricParameterDTO;
import ru.burlakov.dshkazan.utill.CalcDischarge;
import ru.burlakov.dshkazan.utill.ExportExcess;
import ru.burlakov.dshkazan.utill.ImportExcel;
import ru.burlakov.dshkazan.utill.ImportIndustry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {

        List<MetricParameterDTO> metrics = ImportExcel.importExcel("АСКЗА-1", "static/data_all/2016/АСКЗА-1/АСКЗА-1 01.2016.xlsx", new Double[]{55.871370,48.903096});

        List<IndustryDTO> industryList = ImportIndustry.importIndustry();

        List<ExcessDTO> excessList = new ArrayList<>();

        int i = 0;
        for (MetricParameterDTO metrica : metrics) {
            for (IndustryDTO industry : industryList) {
                i++;
                if(metrica.getPdk() == null) continue;
                if(metrica.getValue() == null) continue;
                Double discharge = CalcDischarge.calc(metrica, industry, 1);
                if(discharge > metrica.getPdk()) {
                    ExcessDTO excess = new ExcessDTO();
                    excess.setTime(metrica.getTime());
                    excess.setIndustry(industry.getName());
                    excess.setParameter(metrica.getParameter());
                    excess.setPdk(metrica.getPdk());
                    excess.setValue(discharge);
                    excess.setCoord(industry.getCoord());
                    excessList.add(excess);
                }
            }
        }

        ExportExcess.export("АСКЗА-1", excessList);


        int a = i;

        //        CalcDischarge.calc(new MetricParameterDTO(), new IndustryDTO(), 1);

    }
}
