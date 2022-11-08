package com.technlogi.tt.gps;

import android.content.Context;
import android.util.Log;

import com.technlogi.tt.R;
import com.technlogi.tt.user.interfaces.GeocodingLocationLisnter;
import com.technlogi.tt.user.models.LocationModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.technlogi.tt.Constant.GOOGLE_MAP_API;

public class GeocodingLocationDetails {
    private Context context;
    private GeocodingLocationLisnter locationChangeLisnter;
    private String spiUrl = null;
    private String strLocationDetails;
    private double lat;
    private double lng;

    public GeocodingLocationDetails(Context context) {
        this.context = context;
    }

    public  GeocodingLocationDetails  setHandler(GeocodingLocationLisnter locationChangeLisnter){
        this.locationChangeLisnter = locationChangeLisnter;
        return this;
    }
    public GeocodingLocationDetails build(double lat, double lng){
        this.lat =  lat;
        this.lng = lng;

        this.spiUrl = GOOGLE_MAP_API+"geocode/json?latlng="+Double.toString(lat)+","+Double.toString(lng)
                    +"&key="+context.getResources().getString(R.string.google_map_api_key);
        return this;
    }
    public void getLocationDetaisl() {
         Observable<String> todoObservable = Observable.create(new ObservableOnSubscribe<String>() {
           @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
               try {
                   String locationDetails = downloadUrl(spiUrl);
                   emitter.onNext(locationDetails);
                   emitter.onComplete();
               } catch (Exception e) {
                   emitter.onError(e);
               }
           }
        });
        DisposableObserver<String> disposableObserver = todoObservable
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribeWith(new  DisposableObserver<String>() {

            @Override
            public void onNext(String strLocationDetails) {
                GeocodingLocationDetails.this.strLocationDetails =  strLocationDetails;
                convertJSON();
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        });


    }
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            Log.d("mylog", "Downloaded URL: " + data.toString());
            br.close();
        } catch (Exception e) {
            Log.d("mylog", "Exception downloading URL: " + e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    public void convertJSON(){
        try {
            JSONObject obj = new JSONObject(strLocationDetails);
            JSONArray results = obj.getJSONArray("results");

            JSONObject location = results.getJSONObject(0);
            String strFormatedAddress = location.getString("formatted_address");
            LocationModel locationModel =  new LocationModel();
            locationModel.setDblLat(lat);//Double.parseDouble(results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat")));
            locationModel.setDblLong(lng);//Double.parseDouble(results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng")));
            locationModel.setStrLocationName(strFormatedAddress);
            locationChangeLisnter.setLocation(locationModel);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
