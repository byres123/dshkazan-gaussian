package ru.burlakov.dshkazan.utill;

import ru.burlakov.dshkazan.dto.IndustryDTO;
import ru.burlakov.dshkazan.dto.MetricParameterDTO;

public class CalcDischargeTest {
    public static void main(String[] args) {
        IndustryDTO industryDTO = new IndustryDTO();

        industryDTO.setName("INDUSTRY_0");
        industryDTO.setCoord(new Double[]{55.804324,49.066089});
        industryDTO.setHeight(148.11);
        industryDTO.setOutDiameter(2.11);
        industryDTO.setOutSpeed(39.14);
        industryDTO.setAvgDisch(0.61);
        industryDTO.setOutTemperature(30.18);

        MetricParameterDTO metric = new MetricParameterDTO();
        metric.setCoord(new Double[]{55.77095, 49.14431});
        metric.setWindSpeed(5D);
        metric.setAirTemperature(24D);
        metric.setWindDeg(67D);

        Double discharge = CalcDischarge.calc(metric, industryDTO, 1);

        System.out.println(discharge);
    }
}
