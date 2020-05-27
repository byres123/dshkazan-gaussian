package ru.burlakov.dshkazan.dto;

public class IndustryDTO {

    private String name;
    private Double[] coord = new Double[]{55.798551,49.106324};
    private Double height = 116.43;
    private Double outDiameter = 1.0;
    private Double outSpeed = 24.07;
    private Double avgDisch = 2.82;
    private Double outTemperature = 15.75;

    public String getName() {
        return name;
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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getOutDiameter() {
        return outDiameter;
    }

    public void setOutDiameter(Double outDiameter) {
        this.outDiameter = outDiameter;
    }

    public Double getOutSpeed() {
        return outSpeed;
    }

    public void setOutSpeed(Double outSpeed) {
        this.outSpeed = outSpeed;
    }

    public Double getAvgDisch() {
        return avgDisch;
    }

    public void setAvgDisch(Double avgDisch) {
        this.avgDisch = avgDisch;
    }

    public Double getOutTemperature() {
        return outTemperature;
    }

    public void setOutTemperature(Double outTemperature) {
        this.outTemperature = outTemperature;
    }
}
