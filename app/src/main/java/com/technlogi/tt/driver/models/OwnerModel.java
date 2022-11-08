package com.technlogi.tt.driver.models;

import java.util.ArrayList;

public class OwnerModel {

    private String socketId;
    private String ownerId;
    private String ownerName;
    private String ownerMobileNumber;
    private String owerEmail;
    private String ownerAddress;
    private Integer numberOfVehicles;
    private ArrayList<VehicleModel> vehicleModels;

    public OwnerModel() {
    }

    public OwnerModel(String socketId, String ownerId, String ownerName, String ownerMobileNumber, String owerEmail, String ownerAddress, Integer numberOfVehicles, ArrayList<VehicleModel> vehicleModels) {
        this.socketId = socketId;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerMobileNumber = ownerMobileNumber;
        this.owerEmail = owerEmail;
        this.ownerAddress = ownerAddress;
        this.numberOfVehicles = numberOfVehicles;
        this.vehicleModels = vehicleModels;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerMobileNumber() {
        return ownerMobileNumber;
    }

    public void setOwnerMobileNumber(String ownerMobileNumber) {
        this.ownerMobileNumber = ownerMobileNumber;
    }

    public String getOwerEmail() {
        return owerEmail;
    }

    public void setOwerEmail(String owerEmail) {
        this.owerEmail = owerEmail;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public Integer getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public void setNumberOfVehicles(Integer numberOfVehicles) {
        this.numberOfVehicles = numberOfVehicles;
    }

    public ArrayList<VehicleModel> getVehicleModels() {
        return vehicleModels;
    }

    public void setVehicleModels(ArrayList<VehicleModel> vehicleModels) {
        this.vehicleModels = vehicleModels;
    }
}
