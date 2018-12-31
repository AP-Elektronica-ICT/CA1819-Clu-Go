package com.example.arno.cluego.Objects;

import java.io.Serializable;

public class Location implements Serializable {
    private double locLat;
    private double loclong;
    private String locDescription;
    private String locName;
    private int locId;

    public double getLocLat() {
        return locLat;
    }

    public double getLoclong() {
        return loclong;
    }

    public int getLocId() {
        return locId;
    }

    public String getLocDescription() {
        return locDescription;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocDescription(String locDescription) {
        this.locDescription = locDescription;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    public void setLocLat(double locLat) {
        this.locLat = locLat;
    }

    public void setLoclong(double loclong) {
        this.loclong = loclong;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }
}

