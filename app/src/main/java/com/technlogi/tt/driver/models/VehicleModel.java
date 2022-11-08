package com.technlogi.tt.driver.models;

public class VehicleModel {

    private String socketId;
    private String vehicleRegNumber;
    private String vehicleName;
    private String vehicleType;
    private String vehicleModel;
    private String vehicleMaxLoad;//goods type vehicle
    private String vehicleMaxSeat;//passanger type vehicle
    private GpsModel gpsModel;

    public VehicleModel() {
    }

    public VehicleModel(String socketId, String vehicleRegNumber, String vehicleName, String vehicleType, String vehicleModel, String vehicleMaxLoad, String vehicleMaxSeat, GpsModel gpsModel) {
        this.socketId = socketId;
        this.vehicleRegNumber = vehicleRegNumber;
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
        this.vehicleModel = vehicleModel;
        this.vehicleMaxLoad = vehicleMaxLoad;
        this.vehicleMaxSeat = vehicleMaxSeat;
        this.gpsModel = gpsModel;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleMaxLoad() {
        return vehicleMaxLoad;
    }

    public void setVehicleMaxLoad(String vehicleMaxLoad) {
        this.vehicleMaxLoad = vehicleMaxLoad;
    }

    public String getVehicleMaxSeat() {
        return vehicleMaxSeat;
    }

    public void setVehicleMaxSeat(String vehicleMaxSeat) {
        this.vehicleMaxSeat = vehicleMaxSeat;
    }

    public GpsModel getGpsModel() {
        return gpsModel;
    }

    public void setGpsModel(GpsModel gpsModel) {
        this.gpsModel = gpsModel;
    }
}
