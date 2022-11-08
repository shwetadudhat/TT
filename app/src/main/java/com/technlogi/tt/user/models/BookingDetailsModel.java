package com.technlogi.tt.user.models;

public class BookingDetailsModel {
    private String strUserName;
    private String strMobileNo;
    private String strDateTime;
    private String strCost;
    private LocationModel destination;
    private LocationModel pickup;
    private DriversDetailsModel driverDetails;

    public BookingDetailsModel() {
    }

    public BookingDetailsModel(String strUserName, String strMobileNo, String strDateTime, String strCost, LocationModel destination, LocationModel pickup, DriversDetailsModel driverDetails) {
        this.strUserName = strUserName;
        this.strMobileNo = strMobileNo;
        this.strDateTime = strDateTime;
        this.strCost = strCost;
        this.destination = destination;
        this.pickup = pickup;
        this.driverDetails = driverDetails;
    }

    public String getStrUserName() {
        return strUserName;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    public String getStrMobileNo() {
        return strMobileNo;
    }

    public void setStrMobileNo(String strMobileNo) {
        this.strMobileNo = strMobileNo;
    }

    public String getStrDateTime() {
        return strDateTime;
    }

    public void setStrDateTime(String strDateTime) {
        this.strDateTime = strDateTime;
    }

    public String getStrCost() {
        return strCost;
    }

    public void setStrCost(String strCost) {
        this.strCost = strCost;
    }

    public LocationModel getDestination() {
        return destination;
    }

    public void setDestination(LocationModel destination) {
        this.destination = destination;
    }

    public LocationModel getPickup() {
        return pickup;
    }

    public void setPickup(LocationModel pickup) {
        this.pickup = pickup;
    }

    public DriversDetailsModel getDriverDetails() {
        return driverDetails;
    }

    public void setDriverDetails(DriversDetailsModel driverDetails) {
        this.driverDetails = driverDetails;
    }
}
