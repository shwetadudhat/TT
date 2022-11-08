package com.technlogi.tt.user.models;

public class LocationModel {
    private int intPosition;
    private double dblLat;
    private double dblLong;
    private String strLocationName;

    private boolean seletedForStops = false;

    public LocationModel() {
    }

    public LocationModel(double dblLat, double dblLong, String strLocationName) {
        this.dblLat = dblLat;
        this.dblLong = dblLong;
        this.strLocationName = strLocationName;
    }

    public double getDblLat() {
        return dblLat;
    }

    public void setDblLat(double dblLat) {
        this.dblLat = dblLat;
    }

    public double getDblLong() {
        return dblLong;
    }

    public void setDblLong(double dblLong) {
        this.dblLong = dblLong;
    }

    public String getStrLocationName() {
        return strLocationName;
    }

    public void setStrLocationName(String strLocationName) {
        this.strLocationName = strLocationName;
    }

    public int getIntPosition() {
        return intPosition;
    }

    public void setIntPosition(int intPosition) {
        this.intPosition = intPosition;
    }

    public boolean isSeletedForStops() {
        return seletedForStops;
    }

    public void setSeletedForStops(boolean seletedForStops) {
        this.seletedForStops = seletedForStops;
    }
}
