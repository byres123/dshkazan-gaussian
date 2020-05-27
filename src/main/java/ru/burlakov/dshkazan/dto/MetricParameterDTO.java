package ru.burlakov.dshkazan.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class MetricParameterDTO {

    private String name = "АСКЗА-1";
    private String parameter = "Параметр 1";
    private Double[] coord = new Double[]{55.74309, 49.17876};
    private Date time;
    private Double value;
    private Double windSpeed = 2.5;
    private Double airTemperature = -17D;
    private Double windDeg = 65D;
    private Double pdk = 0.4;

    public String getName() {
        return name;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double[] getCoord() {
        return coord;
    }

    public void setCoord(Double[] coord) {
        this.coord = coord;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(Double airTemperature) {
        this.airTemperature = airTemperature;
    }

    public Double getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(Double windDeg) {
        this.windDeg = windDeg;
    }

    public Double getPdk() {
        return pdk;
    }

    public void setPdk(Double pdk) {
        this.pdk = pdk;
    }
}
