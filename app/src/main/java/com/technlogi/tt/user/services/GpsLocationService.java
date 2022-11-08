package com.technlogi.tt.user.services;

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
import com.technlogi.tt.gps.GeocodingLocationDetails;
import com.technlogi.tt.user.interfaces.GeocodingLocationLisnter;
import com.technlogi.tt.user.interfaces.GpsEnableListner;
import com.technlogi.tt.user.interfaces.LocationChangeLisnter;
import com.technlogi.tt.user.interfaces.PinPointChangeListner;
import com.technlogi.tt.user.interfaces.ZoomLinster;
import com.technlogi.tt.user.models.LocationModel;
import com.technlogi.tt.common.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import static com.technlogi.tt.Constant.snackMessage;

public class GpsLocationService {

    private Activity context;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationModel gpsLocation = new LocationModel();
    private LocationModel pickupLocation =  new LocationModel();
    private List<LocationModel> stopLocation =  new ArrayList<>();
    private LocationModel destiLocation =  new LocationModel();

    private ZoomLinster zoomLinster;

    private ArrayList<String> distance = new ArrayList<>();
    private ArrayList<String> times = new ArrayList<>();

    private int intfor = 0;

    private LocationChangeLisnter pickupLocationChangeLisnter = null;
    private LocationChangeLisnter pickupResetLocationChangeLisnter = null;
    private LocationChangeLisnter stopLocationChangeLisnter = null;
    private LocationChangeLisnter destiLocationChangeLisnter = null;

    private View.OnClickListener nextButtonClickListner = null;
    private View.OnClickListener routeCreateLisnter = null;


    private PinPointChangeListner pointChangeListner;

    private ArrayList<LocationChangeLisnter> gpsLocationSubsribers = new ArrayList<>();
    private ArrayList<LocationChangeLisnter> pickupLocationSubsribers = new ArrayList<>();
    private ArrayList<LocationChangeLisnter> stopLocationSubsribers = new ArrayList<>();
    private ArrayList<LocationChangeLisnter> destiLocationSubsribers = new ArrayList<>();


    private LocationChangeLisnter commonLocationChangeLisnter = null;
    private GpsEnableListner gpsEnableListner = null;


    private static  GpsLocationService locationService;

    public static  GpsLocationService getInstance(Activity context){
            if(locationService == null){
                return   locationService = new GpsLocationService(context);

            }else{
                return locationService;
            }
    }
    public static  void clearInstance(){
        locationService = null;
    }

    public GpsLocationService(Activity context ) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }
    public  GpsLocationService getLocation(){
        getGpsLocation();
        getDestiLocation();
        getPickupLocation();
        getStopLocation();
        this.setCommonLocationChangeLisnter(getPickupLcationChangeListner());
        return this;
    }

    public LocationChangeLisnter getPickupLcationChangeListner(){
        return  this.pickupLocationChangeLisnter;
    }

    public LocationChangeLisnter getStopLcationChangeListner(){
       return this.stopLocationChangeLisnter;
    }

    public ZoomLinster getZoomLinster() {
        return zoomLinster;
    }

    public void setZoomLinster(ZoomLinster zoomLinster) {
        this.zoomLinster = zoomLinster;
    }

    public LocationChangeLisnter getDestiLocationChangeLisnter() {
        return destiLocationChangeLisnter;
    }


    public void setCommonLocationChangeLisnter(LocationChangeLisnter commonLocationChangeLisnter) {
        this.commonLocationChangeLisnter = commonLocationChangeLisnter;
    }


    public View.OnClickListener getRouteCreateLisnter() {
        return routeCreateLisnter;
    }

    public void setRouteCreateLisnter(View.OnClickListener routeCreateLisnter) {
        this.routeCreateLisnter = routeCreateLisnter;
    }

    public LocationChangeLisnter getCommonLocationChangeLisnter() {

        return commonLocationChangeLisnter;
    }

    public PinPointChangeListner getPointChangeListner() {
        return pointChangeListner;
    }

    public void setPointChangeListner(PinPointChangeListner pointChangeListner) {
        this.pointChangeListner = pointChangeListner;
    }

    public LocationChangeLisnter getPickupResetLocationChangeLisnter() {
        return pickupResetLocationChangeLisnter;
    }

    public ArrayList<String> getDistance() {
        return distance;
    }

    public void setDistance(ArrayList<String> distance) {
        this.distance = distance;
    }

    public ArrayList<String> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<String> times) {
        this.times = times;
    }

    public void setPickupResetLocationChangeLisnter(LocationChangeLisnter pickupResetLocationChangeLisnter) {
        this.pickupResetLocationChangeLisnter = pickupResetLocationChangeLisnter;
    }

    public GpsEnableListner getGpsEnableListner() {
        return gpsEnableListner;
    }


    public View.OnClickListener getNextButtonClickListner() {
        return nextButtonClickListner;
    }

    public void setNextButtonClickListner(View.OnClickListener nextButtonClickListner) {
        this.nextButtonClickListner = nextButtonClickListner;
    }

    public void setGpsLocationSubsribers(LocationChangeLisnter gpsLocationSubsriber) {
        this.gpsLocationSubsribers.add(gpsLocationSubsriber);
    }


    public void setPickupLocationSubsribers(LocationChangeLisnter pickupLocationSubsriber) {
        this.pickupLocationSubsribers.add(pickupLocationSubsriber);
    }

    public void setStopLocationSubsribers(LocationChangeLisnter stopLocationSubsriber) {
        this.stopLocationSubsribers .add(stopLocationSubsriber);
    }

    public void setDestiLocationSubsribers(LocationChangeLisnter destiLocationSubsriber) {
        this.destiLocationSubsribers.add(destiLocationSubsriber);
    }


    public LocationModel getGpsLocationLatLng() {
        return gpsLocation;
    }

    public LocationModel getDestiLocationLatLng() {
        return destiLocation;
    }

    public LocationModel getPickupLocationLatLng() {
        return pickupLocation;
    }
    public List<LocationModel> getStopLocationLatLng() {
        return stopLocation;
    }


    public void setGpsLocation(LocationModel gpsLocation) {
        this.gpsLocation = gpsLocation;
        if(pickupLocation == null)
            pickupLocation =gpsLocation;

    }

    public void setPickupLocation(LocationModel pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public void setStopLocation(List<LocationModel> stopLocation) {
        this.stopLocation = stopLocation;
    }

    public void setDestiLocation(LocationModel destiLocation) {
        this.destiLocation = destiLocation;
    }

    public ArrayList<LocationChangeLisnter> getPickupLocationSubsribers() {
        return pickupLocationSubsribers;
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
                                                    gpsLocation = model;
                                                    for (LocationChangeLisnter locationChangeLisnter : gpsLocationSubsribers)
                                                        locationChangeLisnter.onLocationChange(model, true);
                                                }
                                            }
                                        })
                                        .build(locationModel.getLatitude(),locationModel.getLongitude())
                                        .getLocationDetaisl();
                               /* locationModel.setDblLat(location.getLatitude());
                                locationModel.setDblLong(location.getLongitude());
                                LocationAddress locationAddress = new LocationAddress();
                                locationAddress.getAddressFromLocation(locationModel.getDblLat(), locationModel.getDblLong(),
                                        context, new GeocoderGpsHandler(true));*/
                            }
                        }else snackMessage(context.getResources().getString(R.string.no_internet_connection),context.getWindow().getDecorView());
                    }
                }
                );


    }


    private void getPickupLocation(){


            pickupLocationChangeLisnter =  new LocationChangeLisnter() {
                @Override
                public void onLocationChange(LocationModel locationModel,boolean moveMap) {
                    int status = NetworkUtil.getConnectivityStatusString(context);



                    if (status != NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
                        if (moveMap) {
                            pickupLocation = locationModel;
                            for (LocationChangeLisnter locationChangeLisnter : pickupLocationSubsribers)
                                locationChangeLisnter.onLocationChange(locationModel, moveMap);
                        } else {

                            new GeocodingLocationDetails(context)
                                    .setHandler(new GeocodingLocationLisnter() {
                                        @Override
                                        public void setLocation(LocationModel model) {
                                            if (model != null) {
                                                pickupLocation = model;
                                                for(LocationChangeLisnter locationChangeLisnter:pickupLocationSubsribers)locationChangeLisnter.onLocationChange(model,moveMap);
                                            }
                                            if(pickupLocationSubsribers.size() == 0){
                                                getGpsLocation();
                                            }
                                        }
                                    })
                                    .build(locationModel.getDblLat(),locationModel.getDblLong())
                                    .getLocationDetaisl();
                          /*  LocationAddress locationAddress = new LocationAddress();
                            locationAddress.getAddressFromLocation(locationModel.getDblLat(), locationModel.getDblLong(),
                                    context, new GeocoderPickupHandler(moveMap));*/
                        }
                    }else snackMessage(context.getResources().getString(R.string.no_internet_connection),context.getWindow().getDecorView());
                }
            };
    }

    private void getStopLocation(){

            stopLocationChangeLisnter = new LocationChangeLisnter() {
                @Override
                public void onLocationChange(LocationModel locationModel,boolean moveMap) {
                    int status = NetworkUtil.getConnectivityStatusString(context);


                    if (status != NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
                        if (moveMap) {
                            for (LocationChangeLisnter locationChangeLisnter : stopLocationSubsribers)
                                locationChangeLisnter.onLocationChange(locationModel, moveMap);
                        } else {

                            new GeocodingLocationDetails(context)
                                    .setHandler(new GeocodingLocationLisnter() {
                                        @Override
                                        public void setLocation(LocationModel model) {
                                            if (model != null) {

                                                for (LocationChangeLisnter locationChangeLisnter : stopLocationSubsribers)
                                                    locationChangeLisnter.onLocationChange(model, moveMap);
                                            }
                                        }
                                    })
                                    .build(locationModel.getDblLat(),locationModel.getDblLong())
                                    .getLocationDetaisl();
                            /*LocationAddress locationAddress = new LocationAddress();
                            locationAddress.getAddressFromLocation(locationModel.getDblLat(), locationModel.getDblLong(),
                                    context, new GeocoderStopHandler(moveMap));*/
                        }
                    }else snackMessage(context.getResources().getString(R.string.no_internet_connection),context.getWindow().getDecorView());
                }
            };
    }




    private void getDestiLocation(){

            destiLocationChangeLisnter = new LocationChangeLisnter() {
                @Override
                public void onLocationChange(LocationModel locationModel,boolean moveMap) {
                    int status = NetworkUtil.getConnectivityStatusString(context);


                    if (status != NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
                        if (moveMap) {
                            destiLocation = locationModel;
                            for (LocationChangeLisnter locationChangeLisnter : destiLocationSubsribers)
                                locationChangeLisnter.onLocationChange(locationModel, moveMap);
                        } else {

                            new GeocodingLocationDetails(context)
                                    .setHandler(new GeocodingLocationLisnter() {
                                        @Override
                                        public void setLocation(LocationModel model) {
                                            if (model != null) {
                                                destiLocation = model;
                                                for (LocationChangeLisnter locationChangeLisnter : destiLocationSubsribers)
                                                    locationChangeLisnter.onLocationChange(model, moveMap);
                                            }
                                        }
                                    })
                                    .build(locationModel.getDblLat(),locationModel.getDblLong())
                                    .getLocationDetaisl();
                            /*LocationAddress locationAddress = new LocationAddress();
                            locationAddress.getAddressFromLocation(locationModel.getDblLat(), locationModel.getDblLong(),
                                    context, new GeocoderDestiHandler(moveMap));*/
                        }
                    }else snackMessage(context.getResources().getString(R.string.no_internet_connection),context.getWindow().getDecorView());
                }
            };


    }

    private class GeocoderGpsHandler extends Handler {
        private boolean moveMap;

        public GeocoderGpsHandler(boolean moveMap) {
            this.moveMap = moveMap;
        }

        @Override
        public void handleMessage(android.os.Message message) {
            LocationModel model = new LocationModel();
            try {
                switch (message.what) {
                    case 1:
                        Bundle bundle = message.getData();
                        model.setStrLocationName(bundle.getString("addressLine") != null? bundle.getString("addressLine"):""+","+bundle.getString("city") != null?bundle.getString("city"):""+","+
                                bundle.getString("area") != null?bundle.getString("area"):"");
                        model.setDblLat(bundle.getDouble("lat"));
                        model.setDblLong(bundle.getDouble("lon"));
                        break;
                    default:
                }

                if (model != null) {
                    gpsLocation = model;
                    for(LocationChangeLisnter locationChangeLisnter:gpsLocationSubsribers)locationChangeLisnter.onLocationChange(model,moveMap);
                }
            }catch (Exception e){
                 e.printStackTrace();
            }
        }
    }

    private class GeocoderPickupHandler extends Handler {
        private boolean moveMap;

        public GeocoderPickupHandler(boolean moveMap) {
            this.moveMap = moveMap;
        }

        @Override
        public void handleMessage(android.os.Message message) {
            LocationModel model = new LocationModel();
            try {
                switch (message.what) {
                    case 1:
                        Bundle bundle = message.getData();
                        model.setStrLocationName(bundle.getString("addressLine") != null? bundle.getString("addressLine"):""+","+bundle.getString("city") != null?bundle.getString("city"):""+","+
                                bundle.getString("area") != null?bundle.getString("area"):"");
                        model.setDblLat(bundle.getDouble("lat"));
                        model.setDblLong(bundle.getDouble("lon"));
                        break;
                    default:
                }

                if (model != null) {
                    pickupLocation = model;
                    for(LocationChangeLisnter locationChangeLisnter:pickupLocationSubsribers)locationChangeLisnter.onLocationChange(model,moveMap);
                }
                if(pickupLocationSubsribers.size() == 0){
                    getGpsLocation();
                }
            }catch (Exception e){

            }
        }
    }



    private class GeocoderStopHandler extends Handler {
        private boolean moveMap;

        public GeocoderStopHandler(boolean moveMap) {
            this.moveMap = moveMap;
        }
        @Override
        public void handleMessage(android.os.Message message) {
            LocationModel model = new LocationModel();
            try {
                switch (message.what) {
                    case 1:
                        Bundle bundle = message.getData();
                        model.setStrLocationName(bundle.getString("addressLine") != null? bundle.getString("addressLine"):""+","+bundle.getString("city") != null?bundle.getString("city"):""+","+
                                bundle.getString("area") != null?bundle.getString("area"):"");
                        model.setDblLat(bundle.getDouble("lat"));
                        model.setDblLong(bundle.getDouble("lon"));
                        break;
                    default:
                }

                if (model != null) {

                    for(LocationChangeLisnter locationChangeLisnter:stopLocationSubsribers)locationChangeLisnter.onLocationChange(model,moveMap);
                }
            }catch (Exception e){

            }
        }
    }

    private class GeocoderDestiHandler extends Handler {
        private boolean moveMap;

        public GeocoderDestiHandler(boolean moveMap) {
            this.moveMap = moveMap;
        }

        @Override
        public void handleMessage(android.os.Message message) {
            LocationModel model = new LocationModel();
            try {
                switch (message.what) {
                    case 1:
                        Bundle bundle = message.getData();
                        model.setStrLocationName(bundle.getString("addressLine") != null? bundle.getString("addressLine"):""+","+bundle.getString("city") != null?bundle.getString("city"):""+","+
                                bundle.getString("area") != null?bundle.getString("area"):"");
                        model.setDblLat(bundle.getDouble("lat"));
                        model.setDblLong(bundle.getDouble("lon"));
                        break;
                    default:
                }

                if (model != null) {
                    destiLocation = model;
                    for(LocationChangeLisnter locationChangeLisnter:destiLocationSubsribers)locationChangeLisnter.onLocationChange(model,moveMap);
                }
            }catch (Exception e){

            }
        }
    }

}
