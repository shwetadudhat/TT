package com.technlogi.tt.user.models;

public class VehicleModel {
    private int intId;
    private String strVehicleName;
    private String strVehicleType;
    private String strVehicleImgUrl;
    private int intMaxLoad;

    public VehicleModel() {
    }

    public VehicleModel(int intId, String strVehicleName, String strVehicleType, String strVehicleImgUrl, int intMaxLoad) {
        this.intId = intId;
        this.strVehicleName = strVehicleName;
        this.strVehicleType = strVehicleType;
        this.strVehicleImgUrl = strVehicleImgUrl;
        this.intMaxLoad = intMaxLoad;
    }

    public int getIntId() {
        return intId;
    }

    public void setIntId(int intId) {
        this.intId = intId;
    }

    public String getStrVehicleName() {
        return strVehicleName;
    }

    public void setStrVehicleName(String strVehicleName) {
        this.strVehicleName = strVehicleName;
    }

    public String getStrVehicleType() {
        return strVehicleType;
    }

    public void setStrVehicleType(String strVehicleType) {
        this.strVehicleType = strVehicleType;
    }

    public String getStrVehicleImgUrl() {
        return strVehicleImgUrl;
    }

    public void setStrVehicleImgUrl(String strVehicleImgUrl) {
        this.strVehicleImgUrl = strVehicleImgUrl;
    }

    public int getIntMaxLoad() {
        return intMaxLoad;
    }

    public void setIntMaxLoad(int intMaxLoad) {
        this.intMaxLoad = intMaxLoad;
    }
}
