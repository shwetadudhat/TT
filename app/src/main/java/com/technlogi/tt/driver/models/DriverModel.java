package com.technlogi.tt.driver.models;

public class DriverModel {
    private String socketId;
    private String userId;
    private String driverName;
    private String driverLicenceNumber;
    private String driverMobileNumber;
    private String driverEmail;
    private String driverRating;
    private String driverAddress;

    public DriverModel() {
    }

    public DriverModel(String socketId, String userId, String driverName, String driverLicenceNumber, String driverMobileNumber, String driverEmail, String driverRating, String driverAddress) {
        this.socketId = socketId;
        this.userId = userId;
        this.driverName = driverName;
        this.driverLicenceNumber = driverLicenceNumber;
        this.driverMobileNumber = driverMobileNumber;
        this.driverEmail = driverEmail;
        this.driverRating = driverRating;
        this.driverAddress = driverAddress;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverLicenceNumber() {
        return driverLicenceNumber;
    }

    public void setDriverLicenceNumber(String driverLicenceNumber) {
        this.driverLicenceNumber = driverLicenceNumber;
    }

    public String getDriverMobileNumber() {
        return driverMobileNumber;
    }

    public void setDriverMobileNumber(String driverMobileNumber) {
        this.driverMobileNumber = driverMobileNumber;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(String driverRating) {
        this.driverRating = driverRating;
    }

    public String getDriverAddress() {
        return driverAddress;
    }

    public void setDriverAddress(String driverAddress) {
        this.driverAddress = driverAddress;
    }
}
