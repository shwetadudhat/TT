package com.technlogi.tt.user.models;

public class DriversDetailsModel {
    private String strDriverName;
    private String strDriverMobile;
    private String strDriverDl;
    private String strVehicleName;
    private String strVehicleNo;
    private String strVehicleModel;


    public DriversDetailsModel() {
    }

    public DriversDetailsModel(String strDriverName, String strDriverMobile, String strDriverDl, String strVehicleName, String strVehicleNo, String strVehicleModel) {
        this.strDriverName = strDriverName;
        this.strDriverMobile = strDriverMobile;
        this.strDriverDl = strDriverDl;
        this.strVehicleName = strVehicleName;
        this.strVehicleNo = strVehicleNo;
        this.strVehicleModel = strVehicleModel;
    }

    public String getStrDriverName() {
        return strDriverName;
    }

    public void setStrDriverName(String strDriverName) {
        this.strDriverName = strDriverName;
    }

    public String getStrDriverMobile() {
        return strDriverMobile;
    }

    public void setStrDriverMobile(String strDriverMobile) {
        this.strDriverMobile = strDriverMobile;
    }

    public String getStrDriverDl() {
        return strDriverDl;
    }

    public void setStrDriverDl(String strDriverDl) {
        this.strDriverDl = strDriverDl;
    }

    public String getStrVehicleName() {
        return strVehicleName;
    }

    public void setStrVehicleName(String strVehicleName) {
        this.strVehicleName = strVehicleName;
    }

    public String getStrVehicleNo() {
        return strVehicleNo;
    }

    public void setStrVehicleNo(String strVehicleNo) {
        this.strVehicleNo = strVehicleNo;
    }

    public String getStrVehicleModel() {
        return strVehicleModel;
    }

    public void setStrVehicleModel(String strVehicleModel) {
        this.strVehicleModel = strVehicleModel;
    }
}
