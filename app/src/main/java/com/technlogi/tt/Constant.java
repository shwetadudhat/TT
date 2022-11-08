package com.technlogi.tt;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

public class Constant {
    public  static  final String NOTIFICATION_CHANNEL_ID = "FCM Notification";

    public  static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    public  static final int PICKUP_PLACE_REQUEST_CODE = 101;
    public  static final int DESTI_PLACE_REQUEST_CODE = 102;
    public  static final int STOP_PLACE_REQUEST_CODE = 104;
    public  static final int REQUEST_CHECK_SETTINGS = 103;
    public static final int PHONE_CALL_REQUEST = 105;
    public static int OVERLAY_PERMISSION_REQUEST = 106;
    public static final int PICK_IMAGE = 107;
    public static final int PICK_IMAGE_OR_PDF=108;

    public static  int REQUEST_PHONE_STATE_CODE = 1001;
    public static  int SELECTED_VIEW = 41000;
    public static final int HOME_VIEW = 41000;
    public static final int HOME_BACK = 41003;
    public static final int VEHICLE_SELECT_VIEW = 4101;
    public static final int AUTO_TYPE_VIEW = 4102;
    public static final int BIDDING_TYPE_VIEW = 4104;
    public static final int DRIVER_DETAILS_VIEW = 4105;
    public static  int PREV_VIEW = 10;
    public static  int DATE_TIME_VIEW = 4107;
    public static final int HIDE_VIEW = 4113;

    public static final int PASSANGER_DETAILS_VIEW = 5101;

    public  static  final int LOCATION_PIN_FOR_PICKUP = 0;
    public  static  final int LOCATION_PIN_FOR_DESTINATION = 1;
    public  static  final int LOCATION_PIN_FOR_STOP = 2;

    public static  final String GOOGLE_MAP_API = "https://maps.googleapis.com/maps/api/";

    public static final int RESEND_OTP_TIME_DURATION = 45000;
    public static final int RESEND_OTP_TIME_INTERVAL = 1000;

    public static final int DRIVER_PANEL = 201;
    public static final int PASSANGER_PANEL = 202;

    public static final int INSURANCE_DOC = 301;
    public static final int POLLUTION_DOC = 302;
    public static final int CONDITION_DOC = 303;
    public static final int LICENSE_DOC = 304;
    public static final int AADHAAR_DOC = 305;


    public static  boolean  locationPermissionRequest(Activity activity){
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) return true;
        else return false;
    }
    public static  void snackMessage( String msg, View parent){
        Snackbar snackbar = Snackbar
                .make(parent, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
