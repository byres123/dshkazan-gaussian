package ru.burlakov.dshkazan.utill;

import ru.burlakov.dshkazan.dto.ExcessDTO;
import ru.burlakov.dshkazan.dto.IndustryDTO;
import ru.burlakov.dshkazan.dto.MetricParameterDTO;

import java.util.ArrayList;
import java.util.List;

public class ExcessList {
    public static List<ExcessDTO> getList(List<MetricParameterDTO> metrics, List<IndustryDTO> industryList) {
        List<ExcessDTO> excessList = new ArrayList<>();

        for (MetricParameterDTO metrica : metrics) {
            if(metrica.getPdk() == null) continue;
            if(metrica.getValue() == null) continue;
            if(metrica.getValue() < metrica.getPdk()) continue;
            for (IndustryDTO industry : industryList) {
                Double discharge = CalcDischarge.calc(metrica, industry, 1);
                if(discharge > metrica.getPdk()) {
                    ExcessDTO excess = new ExcessDTO();
                    excess.setTime(metrica.getTime());
                    excess.setIndustry(industry.getName());
                    excess.setParameter(metrica.getParameter());
                    excess.setPdk(metrica.getPdk());
                    excess.setValue(metrica.getValue());
                    excess.setCoord(industry.getCoord());
                    excess.setWatcher(metrica.getWatcher());
                    excess.setCalcValue(discharge);
                    excessList.add(excess);
                } else {
                    System.out.println("");
                    System.out.println("====================================");
                    System.out.println(String.format("ПДК: %s", metrica.getPdk()));
                    System.out.println(String.format("discharge: %s",discharge));
                    System.out.println(String.format("value: %s", metrica.getValue()));
                    System.out.println("====================================");
                }
            }
        }

        return excessList;
    }
}
