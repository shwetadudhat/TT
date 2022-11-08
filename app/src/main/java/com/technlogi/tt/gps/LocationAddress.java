package com.technlogi.tt.gps;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationAddress {

    private static final String TAG = "LocationAddress";

    public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                Address address1 = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        /*for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)).append("\n");
                        }*/
                        sb.append(address.getLocality()).append("\n");
                        /*sb.append(address.getPostalCode()).append("\n");
                        sb.append(address.getCountryName());*/
                        result = sb.toString();
                        address1 = address;
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        try {
                            message.what = 1;
                            Bundle bundle = new Bundle();
                        /*result = "Latitude: " + latitude + " Longitude: " + longitude +
                                "\n\nAddress:\n" + result;*/
                            String addline[] = address1.getAddressLine(0).split(",");
                            ArrayList<String> stringArrayList = new ArrayList<>();
                            for (String data : addline) {
                                stringArrayList.add(data);
                            }

                            stringArrayList.remove(stringArrayList.size() - 1);
                            stringArrayList.remove(stringArrayList.size() - 1);
                            String finalAddresslien = "";
                            for (String data : stringArrayList) {
                                finalAddresslien += data;
                            }

                            bundle.putString("addressLine", finalAddresslien);
                            bundle.putString("city", address1.getLocality());
                            bundle.putString("state", address1.getAdminArea());
                            bundle.putString("country", address1.getCountryName());
                            bundle.putString("area", address1.getSubLocality());
                            bundle.putDouble("lat", latitude);
                            bundle.putDouble("lon",longitude);
                            message.setData(bundle);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Latitude: " + latitude + " Longitude: " + longitude +
                                "";
                        bundle.putString("city", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }

}
