package com.technlogi.tt.driver.services;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.technlogi.tt.R;
import com.technlogi.tt.common.utils.NetworkUtil;
import com.technlogi.tt.gps.GeocodingLocationDetails;
import com.technlogi.tt.user.interfaces.GeocodingLocationLisnter;
import com.technlogi.tt.user.interfaces.GpsEnableListner;
import com.technlogi.tt.user.interfaces.LocationChangeLisnter;
import com.technlogi.tt.user.interfaces.PinPointChangeListner;
import com.technlogi.tt.user.interfaces.ZoomLinster;
import com.technlogi.tt.user.models.LocationModel;

import java.util.ArrayList;
import java.util.List;

import static com.technlogi.tt.Constant.snackMessage;

public class GpsLocationService {

    private Activity context;


    private FusedLocationProviderClient fusedLocationClient;

    private LocationChangeLisnter gpsLocationListner;


    public GpsLocationService(Activity context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }


    public GpsLocationService setGpsLocationListner(LocationChangeLisnter gpsLocationListner) {
        this.gpsLocationListner = gpsLocationListner;
        getGpsLocation();
        return this;
    }

    private void getGpsLocation(){
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)

            fusedLocationClient.getLastLocation()
                .addOnSuccessListener(context, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location locationModel) {

                        int status = NetworkUtil.getConnectivityStatusString(context);


                        if (status != NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {

                            if (locationModel != null) {

                                new GeocodingLocationDetails(context)
                                        .setHandler(new GeocodingLocationLisnter() {
                                            @Override
                                            public void setLocation(LocationModel model) {
                                                if (model != null) {
                                                   gpsLocationListner.onLocationChange(model,true);
                                                }
                                            }
                                        })
                                        .build(locationModel.getLatitude(),locationModel.getLongitude())
                                        .getLocationDetaisl();

                            }
                        }else snackMessage(context.getResources().getString(R.string.no_internet_connection),context.getWindow().getDecorView());
                    }
                }
                );

    }

}
