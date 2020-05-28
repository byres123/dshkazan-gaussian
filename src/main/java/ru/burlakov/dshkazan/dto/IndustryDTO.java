package ru.burlakov.dshkazan.dto;

public class IndustryDTO {

    private String name;
    private Double[] coord;
    private Double height;
    private Double outDiameter;
    private Double outSpeed;
    private Double avgDisch;
    private Double outTemperature;

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
