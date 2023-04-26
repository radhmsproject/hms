package com.radproject.hms.models;

public class FarmModel {
    private String farmId;
    private String name;
    private int numOfPerch;

    private String Location;
    private double longitude;
    private double latitude;


    public FarmModel(String farmId, String name, int numOfPerch, String location, double longitude, double latitude) {
        this.farmId = farmId;
        this.name = name;
        this.numOfPerch = numOfPerch;
        Location = location;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfPerch() {
        return numOfPerch;
    }

    public void setNumOfPerch(int numOfPerch) {
        this.numOfPerch = numOfPerch;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
