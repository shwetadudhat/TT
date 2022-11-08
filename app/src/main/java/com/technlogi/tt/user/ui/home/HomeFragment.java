
package com.technlogi.tt.user.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.technlogi.tt.R;
import com.technlogi.tt.gps.GpsLocation;
import com.technlogi.tt.gps.directionhelpers.FetchURL;
import com.technlogi.tt.gps.directionhelpers.TaskLoadedCallback;
import com.technlogi.tt.user.interfaces.DistanceTimeLisnter;
import com.technlogi.tt.user.interfaces.LocationChangeLisnter;
import com.technlogi.tt.user.interfaces.PinPointChangeListner;
import com.technlogi.tt.user.interfaces.ZoomLinster;
import com.technlogi.tt.user.models.LocationModel;
import com.technlogi.tt.user.services.GpsLocationService;

import java.util.ArrayList;
import java.util.List;

import static com.technlogi.tt.Constant.GOOGLE_MAP_API;
import static com.technlogi.tt.Constant.LOCATION_PIN_FOR_DESTINATION;
import static com.technlogi.tt.Constant.LOCATION_PIN_FOR_PICKUP;
import static com.technlogi.tt.Constant.LOCATION_PIN_FOR_STOP;


public class HomeFragment extends Fragment implements OnMapReadyCallback, TaskLoadedCallback {

    private HomeViewModel homeViewModel;
    private  TextView textView;
    private GoogleMap googleMap;

    private ImageView pinPoint;
    private Marker currenMarker;
    private static final int MIN_ZOOM = 2;
    private static final int MAX_ZOOM = 18;

    private GpsLocation glFirstTime = null;
    private Polyline currentPolyline;
    private GoogleMap addPolyline;
    private List< LatLng> listway;
    private FrameLayout flMap;
    Marker markerdest;
    Marker markerPick;
    private ArrayList<Marker> stopsMarker =new ArrayList<>();
    private FusedLocationProviderClient fusedLocationClient;
    private boolean fromGps =false;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        flMap =  root.findViewById(R.id.flMap);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        textView = view.findViewById(R.id.text_home);
        pinPoint  =view.findViewById(R.id.pinPoint);
        View markerPickup = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        TextView numTxtp = (TextView) markerPickup.findViewById(R.id.tvStopSl);
        numTxtp.setVisibility(View.GONE);
        ((ImageView)markerPickup.findViewById(R.id.imgMarker)).setImageResource(R.drawable.marker_pickup);

        pinPoint.setImageBitmap(createDrawableFromView(getActivity(), markerPickup));

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setCurrentLocation(){
        GpsLocationService.getInstance(getActivity()).getLocation().setGpsLocationSubsribers(new LocationChangeLisnter() {
            @Override
            public void onLocationChange(LocationModel locationModel,boolean moveMap) {

               //* currenMarker = googleMap.addMarker( new MarkerOptions().position(center)
                    //    .title(locationModel.getStrLocationName()));*//*

                //googleMap.moveCamera(CameraUpdateFactory.newLatLng(center));
              //  googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(locationModel.getDblLat(),locationModel.getDblLong())));


                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    googleMap.setMyLocationEnabled(true);

                LatLng center = new LatLng(locationModel.getDblLat(),locationModel.getDblLong());//new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude());
               // LocationModel locationModel1 =  new LocationModel();
               /// locationModel1.setDblLat(center.latitude);
                //locationModel1.setDblLong(center.longitude);

               // GpsLocationService.getInstance(getActivity()).setGpsLocation(locationModel1);
                //GpsLocationService.getInstance(getActivity()).setPickupLocation(locationModel1);


                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center,17), 1000, null);
                flMap.setVisibility(View.VISIBLE);
               // googleMap.setTrafficEnabled(true);


                googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {

                        LatLng center = googleMap.getCameraPosition().target;
                        LocationModel locationModel1 =  new LocationModel();
                        locationModel1.setDblLat(center.latitude);
                        locationModel1.setDblLong(center.longitude);
                        try {
                           // if(!fromGps)
                                GpsLocationService.getInstance(getActivity()).getCommonLocationChangeLisnter().onLocationChange(locationModel1,false);
                            //fromGps = false;

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

        GpsLocationService.getInstance(getActivity()).setPickupLocationSubsribers(new LocationChangeLisnter() {
            @Override
            public void onLocationChange(LocationModel locationModel,boolean moveMap) {
               if(moveMap)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(locationModel.getDblLat(),locationModel.getDblLong())));

            }
        });


        GpsLocationService.getInstance(getActivity()).setPickupResetLocationChangeLisnter(new LocationChangeLisnter() {
            @Override
            public void onLocationChange(LocationModel locationModel, boolean moveMap) {

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(GpsLocationService.getInstance(getActivity()).getGpsLocationLatLng().getDblLat(), GpsLocationService.getInstance(getActivity()).getGpsLocationLatLng().getDblLong())));

            }
        });
        GpsLocationService.getInstance(getActivity()).setStopLocationSubsribers(new LocationChangeLisnter() {
            @Override
            public void onLocationChange(LocationModel locationModel, boolean moveMap) {
                if(moveMap)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(locationModel.getDblLat(),locationModel.getDblLong())));

            }
        });
        GpsLocationService.getInstance(getActivity()).setPointChangeListner(new PinPointChangeListner() {
            @Override
            public void pinChange(int pinFor) {
                switch (pinFor){
                    case LOCATION_PIN_FOR_PICKUP:

                        View markerPickup = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
                        TextView numTxtp = (TextView) markerPickup.findViewById(R.id.tvStopSl);
                        numTxtp.setVisibility(View.GONE);
                        ((ImageView)markerPickup.findViewById(R.id.imgMarker)).setImageResource(R.drawable.marker_pickup);

                        pinPoint.setImageBitmap(createDrawableFromView(getActivity(), markerPickup));

                        break;
                    case LOCATION_PIN_FOR_STOP:
                        View markerstop = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
                        TextView numTxts = (TextView) markerstop.findViewById(R.id.tvStopSl);
                        numTxts.setVisibility(View.GONE);
                        ((ImageView)markerstop.findViewById(R.id.imgMarker)).setImageResource(R.drawable.marker_stops);

                        pinPoint.setImageBitmap(createDrawableFromView(getActivity(), markerstop));
                        break;
                    case LOCATION_PIN_FOR_DESTINATION:
                        View markerdes = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
                        TextView numTxtd = (TextView) markerdes.findViewById(R.id.tvStopSl);
                        numTxtd.setVisibility(View.GONE);
                        ((ImageView)markerdes.findViewById(R.id.imgMarker)).setImageResource(R.drawable.marker_destination);
                        pinPoint.setImageBitmap(createDrawableFromView(getActivity(), markerdes));
                        break;
                }

            }
        });
        GpsLocationService.getInstance(getActivity()).setDestiLocationSubsribers(new LocationChangeLisnter() {
            @Override
            public void onLocationChange(LocationModel locationModel,boolean moveMap) {
                if(moveMap)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(locationModel.getDblLat(),locationModel.getDblLong())));

                }
        });
        GpsLocationService.getInstance(getActivity()).setNextButtonClickListner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationModel pickup =  GpsLocationService.getInstance(getActivity()).getPickupLocationLatLng();
                LocationModel desti =  GpsLocationService.getInstance(getActivity()).getDestiLocationLatLng();
                List<LocationModel> listway =  GpsLocationService.getInstance(getActivity()).getStopLocationLatLng();

              /*  if(pickup != null && desti != null)*/
                    new FetchURL(getContext(), HomeFragment.this, new DistanceTimeLisnter() {
                        @Override
                        public void distanceTime(ArrayList<String> strDistance, ArrayList<String> strTime) {
                            GpsLocationService.getInstance(getActivity()).setDistance(strDistance);
                            GpsLocationService.getInstance(getActivity()).setTimes(strTime);
                             GpsLocationService.getInstance(getActivity()).getRouteCreateLisnter().onClick(v);
                        }
                    }).execute(getUrl(new LatLng(pickup.getDblLat(),pickup.getDblLong()),new LatLng(desti.getDblLat(),desti.getDblLong()), "driving",listway), "driving");

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(new LatLng(pickup.getDblLat(),pickup.getDblLong()));

                LatLngBounds bounds = builder.build();

                int padding = 100; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                googleMap.moveCamera(cu);
                GpsLocationService.getInstance(getActivity()).setZoomLinster(new ZoomLinster() {
                    @Override
                    public  void zoom(boolean isZoom) {
                            if(isZoom){
                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                builder.include(new LatLng(pickup.getDblLat(),pickup.getDblLong()));
                                for (LocationModel locationModel : listway) {
                                    builder.include(new LatLng(locationModel.getDblLat(),locationModel.getDblLong()));
                                }
                                builder.include(new LatLng(desti.getDblLat(),desti.getDblLong()));
                                LatLngBounds bounds = builder.build();

                                int padding = 100; // offset from edges of the map in pixels
                                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                                googleMap.setPadding(0,0,0,0);
                                googleMap.moveCamera(cu);
                            }else{
                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                builder.include(new LatLng(pickup.getDblLat(),pickup.getDblLong()));

                                LatLngBounds bounds = builder.build();

                                int padding = 100; // offset from edges of the map in pixels
                                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                                int height = (int)(getResources().getDisplayMetrics().heightPixels * 0.65);
                                googleMap.setPadding(100,0,100,height);
                                googleMap.moveCamera(cu);

                                float zoomLevel = 16.0f;
                                cu = CameraUpdateFactory.newLatLngZoom(new LatLng(pickup.getDblLat(),pickup.getDblLong()),zoomLevel);
                                googleMap.moveCamera(cu);

                            }
                    }
                });

               /* CameraPosition cameraPosition =  new CameraPosition.Builder()
                                                   .target(getCenterCoordinate(new LatLng(pickup.getDblLat(),pickup.getDblLong()),new LatLng(pickup.getDblLat(),pickup.getDblLong())))
                                                 .zoom(getBoundsZoomLevel(new LatLng(pickup.getDblLat(),pickup.getDblLong()),new LatLng(pickup.getDblLat(),pickup.getDblLong()),100,100,256,21))
                                                  .build();

                CameraUpdate factory =  CameraUpdateFactory.newCameraPosition(cameraPosition);

                googleMap.animateCamera(factory);*/

            }
        });
    }

    public LatLng getCenterCoordinate(LatLng start, LatLng stop){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(start);
        builder.include(stop);
        LatLngBounds bounds = builder.build();
        return bounds.getCenter();
    }

    public static int getBoundsZoomLevel(LatLng northeast,LatLng southwest,
                                         int width, int height,int GLOBE_WIDTH,int ZOOM_MAX) {
        double latFraction = (latRad(northeast.latitude) - latRad(southwest.latitude)) / Math.PI;
        double lngDiff = northeast.longitude - southwest.longitude;
        double lngFraction = ((lngDiff < 0) ? (lngDiff + 360) : lngDiff) / 360;
        double latZoom = zoom(height, GLOBE_WIDTH, latFraction);
        double lngZoom = zoom(width, GLOBE_WIDTH, lngFraction);
        double zoom = Math.min(Math.min(latZoom, lngZoom),ZOOM_MAX);
        return (int)(zoom);
    }
    private static double latRad(double lat) {
        double sin = Math.sin(lat * Math.PI / 180);
        double radX2 = Math.log((1 + sin) / (1 - sin)) / 2;
        return Math.max(Math.min(radX2, Math.PI), -Math.PI) / 2;
    }
    private static double zoom(double mapPx, double worldPx, double fraction) {
        final double LN2 = .693147180559945309417;
        return (Math.log(mapPx / worldPx / fraction) / LN2);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        HomeFragment.this.googleMap = googleMap;
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);


            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)

                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                try {
                           /* if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                                googleMap.setMyLocationEnabled(true);
*/

                                    LatLng center = new LatLng(location.getLatitude(), location.getLongitude());//new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude());

                                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                    googleMap.getUiSettings().setCompassEnabled(true);
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 17), 1000, null);
                                    flMap.setVisibility(View.VISIBLE);
                                    // googleMap.setTrafficEnabled(true);
                                }catch (Exception e){
                                    e.printStackTrace();
                                    glFirstTime =    new GpsLocation(getActivity()).setLocationChangeLisnter(new LocationChangeLisnter() {
                                        @Override
                                        public void onLocationChange(LocationModel locationModel, boolean moveMap) {

                                            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                                                googleMap.setMyLocationEnabled(true);

                                            LatLng center = new LatLng(locationModel.getDblLat(), locationModel.getDblLong());//new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude());
                                            GpsLocationService.getInstance(getActivity()).setGpsLocation(locationModel);
                                            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                            googleMap.getUiSettings().setCompassEnabled(true);
                                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 17), 1000, null);
                                            flMap.setVisibility(View.VISIBLE);
                                            glFirstTime.setLocationChangeLisnter(new LocationChangeLisnter() {
                                                @Override
                                                public void onLocationChange(LocationModel locationModel, boolean moveMap) {

                                                }
                                            });
                                        }
                                    });
                                    glFirstTime.getLocation();
                                }
                            }
                        });


        setCurrentLocation();
        /*googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {

                GpsLocationService.getInstance(getActivity()).setCommonLocationChangeLisnter(new LocationChangeLisnter() {
                    @Override
                    public void onLocationChange(LocationModel locationModel, boolean moveMap) {

                    }
                });
                if(googleMap.getCameraPosition().zoom<16){
                    pinPoint.setImageResource(0);
                    createMarker();
                }
            }
        });*/

    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        pinPoint.setImageResource(0);

        currentPolyline = googleMap.addPolyline((PolylineOptions) values[0]);
        currentPolyline.setWidth(5.0f);
        currentPolyline.setColor(Color.BLACK);
        createMarker();

    }
    public void createMarker(){
        if(markerPick != null) markerPick.remove();

        MarkerOptions markerPickOptions =  new MarkerOptions();
        LocationModel pickup =  GpsLocationService.getInstance(getActivity()).getPickupLocationLatLng();
        markerPickOptions.position(new LatLng(pickup.getDblLat(),pickup.getDblLong()));

        View markerPickup = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        TextView numTxtp = (TextView) markerPickup.findViewById(R.id.tvStopSl);
        numTxtp.setVisibility(View.GONE);
        ((ImageView)markerPickup.findViewById(R.id.imgMarker)).setImageResource(R.drawable.marker_pickup);

        markerPickOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), markerPickup)));


        markerPickOptions.position(new LatLng(pickup.getDblLat(),pickup.getDblLong()));

        // Setting titile of the infowindow of the marker
        markerPickOptions.title(pickup.getStrLocationName());
        markerPick =googleMap.addMarker(markerPickOptions);



        List<LocationModel> listway =  GpsLocationService.getInstance(getActivity()).getStopLocationLatLng();

        for(Marker options:stopsMarker){
            options.remove();
        }
        int markerCount = 0;
        for(LocationModel latLng:listway){

            View marker = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
            TextView numTxt = (TextView) marker.findViewById(R.id.tvStopSl);
            numTxt.setText(Integer.toString(++markerCount));

            MarkerOptions markerOptions = new MarkerOptions();

            // Setting latitude and longitude of the marker position
            markerOptions.position(new LatLng(latLng.getDblLat(),latLng.getDblLong()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), marker)));
            // Setting titile of the infowindow of the marker
            markerOptions.title(latLng.getStrLocationName());
            markerOptions.snippet("1");
            stopsMarker.add(googleMap.addMarker(markerOptions));

        }



        if(markerdest != null) markerdest.remove();

        MarkerOptions markerdestOptions = new MarkerOptions();
        LocationModel desti =  GpsLocationService.getInstance(getActivity()).getDestiLocationLatLng();
        markerdestOptions.position(new LatLng(desti.getDblLat(),desti.getDblLong()));


        View markerDestin = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        TextView numTxt = (TextView) markerDestin.findViewById(R.id.tvStopSl);
        numTxt.setVisibility(View.GONE);
        ((ImageView)markerDestin.findViewById(R.id.imgMarker)).setImageResource(R.drawable.marker_destination);

        markerdestOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), markerDestin)));

        // Setting titile of the infowindow of the marker
        markerdestOptions.title(desti.getStrLocationName());

        markerdest =  googleMap.addMarker(markerdestOptions);

    }
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private String getUrl(LatLng origin,LatLng dest, String directionMode,List<LocationModel>way) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route

        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        int ii=0;
        String waypoints="waypoints=";
        if(way.size()>0) {
            for (LocationModel s : way) {
                waypoints += (ii == 0 ? "" : "|") + s.getDblLat() + "," + s.getDblLong();
                ii += 1;
            }
        }
        // Building the parameters to the web service
        String parameters = str_origin +"&"+ str_dest + "&" +waypoints+"&"+ mode;
        // Output format
        String pp="destination=Madrid,Spain&origin=Barcelona,Spain&waypoints=Zaragoza|Huesca";
        String output = "json";
        // Building the url to the web service
        String url = GOOGLE_MAP_API+"directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_map_api_key);
        return url;
    }
}