package com.technlogi.tt.gps;

import android.os.Bundle;
import android.os.Handler;

import com.technlogi.tt.user.interfaces.LocationChangeLisnter;
import com.technlogi.tt.user.models.LocationModel;

public class GeocoderHandler extends Handler {
        private LocationChangeLisnter locationChangeLisnter;

    public GeocoderHandler setLocationChangeLisnter(LocationChangeLisnter locationChangeLisnter) {
        this.locationChangeLisnter = locationChangeLisnter;
        return this;
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
                    locationChangeLisnter.onLocationChange(model,false);
                }
            }catch (Exception e){

            }
        }
    }