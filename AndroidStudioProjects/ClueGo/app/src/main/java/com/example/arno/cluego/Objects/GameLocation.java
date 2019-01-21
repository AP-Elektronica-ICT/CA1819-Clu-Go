package com.example.arno.cluego.Objects;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class GameLocation implements Serializable {
    private double locLat;
    private double loclong;
    private String locDescription;
    private String locName;
    private int locId;
    private boolean visited;
    private LatLng locLtLng;
    private String clueType;

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

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

    public LatLng getLocLtLng() {
        return locLtLng;
    }

    public void setLocLtLng(double _locLat, double _locLong) {
        this.locLtLng = new LatLng(_locLat, _locLong);
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getClueType() {
        return clueType;
    }

    public void setClueType(String clueType) {
        this.clueType = clueType;
    }
}

