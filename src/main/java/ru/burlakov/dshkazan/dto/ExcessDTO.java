package ru.burlakov.dshkazan.dto;

import java.util.Date;

public class ExcessDTO {

    Date time;
    String industry;
    String parameter;
    Double pdk;
    Double value;
    Double[] coord;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Double getPdk() {
        return pdk;
    }

    public void setPdk(Double pdk) {
        this.pdk = pdk;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double[] getCoord() {
        return coord;
    }

    public void setCoord(Double[] coord) {
        this.coord = coord;
    }
}
