package com.technlogi.tt.gps;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.technlogi.tt.user.interfaces.LocationChangeLisnter;
import com.technlogi.tt.user.models.LocationModel;

import static android.content.Context.LOCATION_SERVICE;

public class GpsLocation  implements LocationListener {

    private final Activity mContext;
    public boolean isGPSEnabled = false;
    boolean canGetLocation = false;
    Location location;
    double latitude;
    double longitude;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    protected LocationManager locationManager;
    private LocationChangeLisnter locationChangeLisnter;

    public GpsLocation(Activity context) {
        this.mContext = context;
    }
    private FusedLocationProviderClient fusedLocationClient;

    public void getLocation() {
        LocationModel locationModel = new LocationModel();

        try {

            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


            if (!isGPSEnabled) {

                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            } else {
                this.canGetLocation = true;

                if (isGPSEnabled) {

                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS Enabled", "GPS Enabled");

                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if (location != null) {

                            Criteria criteria = new Criteria();
                            criteria.setAccuracy(Criteria.ACCURACY_FINE);
                            criteria.setAltitudeRequired(false);
                            criteria.setBearingRequired(true);
                            criteria.setCostAllowed(true);
                            criteria.setSpeedRequired(false);
                            location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            locationModel.setDblLat(latitude);
                            locationModel.setDblLong(longitude);
                            locationModel.setStrLocationName(location.getProvider());
                            locationChangeLisnter.onLocationChange(locationModel,true);
                        }
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public GpsLocation setLocationChangeLisnter(LocationChangeLisnter locationChangeLisnter) {
        this.locationChangeLisnter = locationChangeLisnter;
        getLocation();
        return  this;
    }

    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GpsLocation.this);
        }
    }

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        return latitude;
    }


    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        return longitude;
    }


    public boolean canGetLocation() {
        return this.canGetLocation;
    }


    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        alertDialog.setTitle("GPS settings");

        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        LocationModel locationModel = new LocationModel();
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        locationModel.setDblLat(latitude);
        locationModel.setDblLong(longitude);
        locationModel.setStrLocationName(location.getProvider());
        locationChangeLisnter.onLocationChange(locationModel,true);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

}
