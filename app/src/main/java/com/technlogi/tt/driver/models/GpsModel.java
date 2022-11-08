package com.technlogi.tt.driver.models;

public class GpsModel {

    private String socketId;
    private String placeId;
    private String formatedAddress;
    private long lat;
    private long lng;


    public GpsModel() {
    }

    public GpsModel(String socketId, String placeId, String formatedAddress, long lat, long lng) {
        this.socketId = socketId;
        this.placeId = placeId;
        this.formatedAddress = formatedAddress;
        this.lat = lat;
        this.lng = lng;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getFormatedAddress() {
        return formatedAddress;
    }

    public void setFormatedAddress(String formatedAddress) {
        this.formatedAddress = formatedAddress;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLng() {
        return lng;
    }

    public void setLng(long lng) {
        this.lng = lng;
    }
}
