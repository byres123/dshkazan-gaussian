package ru.burlakov.dshkazan.utill;

import ru.burlakov.dshkazan.dto.MetricParameterDTO;

import java.util.*;

public class GetHistory {

    private final static int HISTORY_COUNT = 20;

    public static List<MetricParameterDTO> getList(List<MetricParameterDTO> metrics) {
        List<MetricParameterDTO> list = new ArrayList<>();

        for(int i=0; i<metrics.size(); i++) {
            MetricParameterDTO metric = metrics.get(i);
            if(metric.getValue() > metric.getPdk())
                list.addAll(getHistory(metrics, i, metric.getParameter()));
        }

        Collections.sort(list, new Comparator<MetricParameterDTO>() {
            public int compare(MetricParameterDTO o1, MetricParameterDTO o2) {
                return o2.getTime().compareTo(o1.getTime());
            }
        });

        return list;
    }

    private static List<MetricParameterDTO> getHistory(List<MetricParameterDTO> metrics, int index, String prameter) {
        List<MetricParameterDTO> list = new ArrayList<>();
        int minIndex = index - HISTORY_COUNT;
        minIndex = minIndex < 0 ? 0 : minIndex;

        while (index > minIndex) {
            MetricParameterDTO metricParameterDTO = metrics.get(index--);
            metricParameterDTO.setParameter(prameter);
            list.add(metricParameterDTO);
        }

        return list;
    }

}
