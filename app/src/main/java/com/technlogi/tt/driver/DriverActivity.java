package com.technlogi.tt.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.PictureInPictureParams;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Rational;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.technlogi.tt.R;
import com.technlogi.tt.common.utils.ClickEvent;
import com.technlogi.tt.driver.adapter.BiddingListAdapter;
import com.technlogi.tt.driver.adapter.DriverVehicleListAdapter;
import com.technlogi.tt.driver.services.GpsLocationService;
import com.technlogi.tt.driver.ui.vehiclelist.VehicleList;
import com.technlogi.tt.user.MainActivity;
import com.technlogi.tt.user.activities.BookingDetailsActivity;
import com.technlogi.tt.user.activities.EditProfileActivity;
import com.technlogi.tt.user.interfaces.LocationChangeLisnter;
import com.technlogi.tt.user.interfaces.OnFragmentChangeLisnter;
import com.technlogi.tt.user.models.BookingDetailsModel;
import com.technlogi.tt.user.models.DriversDetailsModel;
import com.technlogi.tt.user.models.LocationModel;
import com.technlogi.tt.user.ui.home.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.technlogi.tt.Constant.AUTO_TYPE_VIEW;
import static com.technlogi.tt.Constant.BIDDING_TYPE_VIEW;
import static com.technlogi.tt.Constant.DRIVER_DETAILS_VIEW;
import static com.technlogi.tt.Constant.HIDE_VIEW;
import static com.technlogi.tt.Constant.HOME_BACK;
import static com.technlogi.tt.Constant.HOME_VIEW;
import static com.technlogi.tt.Constant.PASSANGER_DETAILS_VIEW;
import static com.technlogi.tt.Constant.PHONE_CALL_REQUEST;
import static com.technlogi.tt.Constant.PREV_VIEW;
import static com.technlogi.tt.Constant.SELECTED_VIEW;
import static com.technlogi.tt.Constant.VEHICLE_SELECT_VIEW;

public class DriverActivity extends AppCompatActivity implements OnMapReadyCallback , DriverVehicleListAdapter.vhMinorUpdate {

    private BottomSheetBehavior bottomSheetHome;
    private BottomSheetBehavior passangerDetailsLayout;
    private BottomSheetBehavior driverBookingCancelReason;
    private BottomSheetBehavior bookingCancelled;
    private BottomSheetBehavior passangerRatingBottomSheet;
    private BottomSheetBehavior driverPaymentWaiting;

    private LinearLayout llDriverToolbar;
    private  Toolbar toolbar;
    private ImageView mapIcon;
    private TextView destination,todayEarning;

    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;

    private GoogleMap googleMap;
    private GpsLocationService gpsLocationService;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private AppCompatImageView imgEditProfile;

    private RecyclerView rvDriverVehicleList;

    private static final int PASSANGER_DETAILS_BOTTOMSHEET_MAX_HEIGHT = 550;
    private static final int PASSANGER_DETAILS_BOTTOMSHEET_MIN_HEIGHT = 150;

    private ImageView passangerDetails_ivFullScreen;
    private TextView passangerDetials_liveTime;

    Timer timer;
    String currentTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        timer = new Timer();

        passangerDetails_ivFullScreen =  findViewById(R.id.passangerDetails_ivFullScreen);
        passangerDetials_liveTime = findViewById(R.id.psnDetail_liveTime);

        findViewById(R.id.driverLayoutClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView)findViewById(R.id.driverLayoutClose)).setImageResource(bottomSheetHome.getState() == BottomSheetBehavior.STATE_EXPANDED ?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);
                bottomSheetHome.setState(bottomSheetHome.getState() == BottomSheetBehavior.STATE_EXPANDED ?BottomSheetBehavior.STATE_COLLAPSED:BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        findViewById(R.id.passangerDetailsBltnSheetClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                ((ImageView)findViewById(R.id.driverDetailsBltnSheetClose)).setImageResource(driverDetailsLayout.getState() == BottomSheetBehavior.STATE_EXPANDED ?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);

//                driverDetailsLayout.setState(driverDetailsLayout.getState() == BottomSheetBehavior.STATE_EXPANDED ?BottomSheetBehavior.STATE_COLLAPSED:BottomSheetBehavior.STATE_EXPANDED);

                if (passangerDetailsLayout.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    passangerDetailsLayout.setHideable(false);
                    passangerDetailsLayout.setState(BottomSheetBehavior.STATE_EXPANDED);
//                    driverDetailsLayout.setHalfExpandedRatio((float)DRIVER_DETAILS_BOTTOMSHEET_MIN_HEIGHT/findViewById(R.id.driverDetailsBottomSheet).getMeasuredHeight());
//                    driverDetailsLayout.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                    ((ImageView)findViewById(R.id.passangerDetailsBltnSheetClose)).setImageResource(R.drawable.ic_baseline_expand_more_24);
                } else {
//                    driverDetailsLayout.setHideable(false);
//                    driverDetailsLayout.setState(BottomSheetBehavior.STATE_EXPANDED);
//                    driverDetailsLayout.setHalfExpandedRatio((float)DRIVER_DETAILS_BOTTOMSHEET_MAX_HEIGHT/findViewById(R.id.driverDetailsBottomSheet).getMeasuredHeight());
//                    driverDetailsLayout.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                    passangerDetailsLayout.setHideable(false);
                    passangerDetailsLayout.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    passangerDetailsLayout.setHalfExpandedRatio((float)PASSANGER_DETAILS_BOTTOMSHEET_MAX_HEIGHT/findViewById(R.id.passangerDetailsBottomSheet).getMeasuredHeight());
                    passangerDetailsLayout.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                    ((ImageView)findViewById(R.id.passangerDetailsBltnSheetClose)).setImageResource(R.drawable.ic_baseline_expand_less_24);
                }

            }
        });

        findViewById(R.id.driverBookingCancelReasonSheetClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView)findViewById(R.id.driverBookingCancelReasonSheetClose)).setImageResource(driverBookingCancelReason.getState() == BottomSheetBehavior.STATE_EXPANDED ?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);

                driverBookingCancelReason.setState(driverBookingCancelReason.getState() == BottomSheetBehavior.STATE_EXPANDED ?BottomSheetBehavior.STATE_COLLAPSED:BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        findViewById(R.id.bookingCancelledSheetClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((ImageView)findViewById(R.id.bookingCancelledSheetClose)).setImageResource(bookingCancelled.getState() == BottomSheetBehavior.STATE_EXPANDED ?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);
                bookingCancelled.setState(bookingCancelled.getState() == BottomSheetBehavior.STATE_EXPANDED ?BottomSheetBehavior.STATE_COLLAPSED:BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        findViewById(R.id.passangerRatingBottomSheetClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView)findViewById(R.id.passangerRatingBottomSheetClose)).setImageResource(passangerRatingBottomSheet.getState() == BottomSheetBehavior.STATE_EXPANDED ?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);
                passangerRatingBottomSheet.setState(passangerRatingBottomSheet.getState() == BottomSheetBehavior.STATE_EXPANDED ?BottomSheetBehavior.STATE_COLLAPSED:BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        findViewById(R.id.driverPaymentWaitingSheetClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((ImageView)findViewById(R.id.driverPaymentWaitingSheetClose)).setImageResource(driverPaymentWaiting.getState() == BottomSheetBehavior.STATE_EXPANDED ?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);
                driverPaymentWaiting.setState(driverPaymentWaiting.getState() == BottomSheetBehavior.STATE_EXPANDED ?BottomSheetBehavior.STATE_COLLAPSED:BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        llDriverToolbar = (LinearLayout) findViewById(R.id.llDriverToolbar) ;
        mapIcon = (ImageView) findViewById(R.id.driver_panel_map_icon);
        destination = findViewById(R.id.driver_panel_destination);
        todayEarning = findViewById(R.id.driver_panel_todayEarning);

        mapIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mapUri = Uri.parse("google.navigation:q=Mehdi+Hasan+chowk");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW,mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        bottomSheetHome = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_home));
        bottomSheetHome.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        bottomSheetHome.setHideable(true);


        passangerDetailsLayout =  BottomSheetBehavior.from(findViewById(R.id.passangerDetailsLayout));
        passangerDetailsLayout.setHideable(true);
        passangerDetailsLayout.setState(BottomSheetBehavior.STATE_HIDDEN);

        driverBookingCancelReason =  BottomSheetBehavior.from(findViewById(R.id.driverBookingCancelReason));
        driverBookingCancelReason.setHideable(true);
        driverBookingCancelReason.setState(BottomSheetBehavior.STATE_HIDDEN);

        bookingCancelled =  BottomSheetBehavior.from(findViewById(R.id.bookingCancelled));
        bookingCancelled.setHideable(true);
        bookingCancelled.setState(BottomSheetBehavior.STATE_HIDDEN);

        passangerRatingBottomSheet =  BottomSheetBehavior.from(findViewById(R.id.passangerRatingBottomSheet));
        passangerRatingBottomSheet.setHideable(true);
        passangerRatingBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);

        driverPaymentWaiting =  BottomSheetBehavior.from(findViewById(R.id.driverPaymentWaitingLayout));
        driverPaymentWaiting.setHideable(true);
        driverPaymentWaiting.setState(BottomSheetBehavior.STATE_HIDDEN);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.driverMap);
//        mapFragment.getMapAsync(this);

        gpsLocationService = new GpsLocationService(this).setGpsLocationListner(new LocationChangeLisnter() {
            @Override
            public void onLocationChange(LocationModel locationModel, boolean moveMap) {

                if (ActivityCompat.checkSelfPermission(DriverActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(DriverActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    googleMap.setMyLocationEnabled(true);

                LatLng center = new LatLng(locationModel.getDblLat(),locationModel.getDblLong());
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center,15), 1000, null);
            }
        });

        toolbar = findViewById(R.id.driverToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Driver");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


//        Driver edit profile Button Onclick
        imgEditProfile =  navigationView.getHeaderView(0).findViewById(R.id.imgEditProfile);
        imgEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverActivity.this, OwnerProfileActivity.class));
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return true;
            }
        });

        preferences = getSharedPreferences("main_pref",0);


        navigationView.getHeaderView(0).findViewById(R.id.txtSwitchPassanger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor = preferences.edit();
                editor.putBoolean("driver",false);
                editor.commit();


                startActivity(new Intent(DriverActivity.this, MainActivity.class));
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.driverVehicleList,R.id.nav_driver_driver_list,R.id.nav_user_bidding_list,R.id.driverBookingFragement,R.id.refersEarnFragment)
                .setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.driverMap);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        getSupportActionBar().setTitle("");

        ClickEvent.getInstance().setClickEvent(new OnFragmentChangeLisnter() {
            @Override
            public void onChangeFrag(int fragmentId) {
                switchLayout(fragmentId);
            }

            @Override
            public void onBackFrag() {
                //navController.navigate(R.id.nav_home);
                navController.popBackStack();
                switchLayout(HOME_BACK);
            }
        });

        rvDriverVehicleList = findViewById(R.id.driver_rvdriverVehicleList);

//        DriversDetailsModel detailsModel = new DriversDetailsModel();
//        detailsModel.setStrVehicleNo("DL01H0123");
//        detailsModel.setStrVehicleName("Tesla Cyber Truck");
//        detailsModel.setStrDriverName("DriverName");
//        ArrayList<DriversDetailsModel> list = new  ArrayList();
//        list.add(detailsModel);
//
//        list.add(detailsModel);
//        list.add(detailsModel);
//        list.add(detailsModel);
//        list.add(detailsModel);
//
//        DriverVehicleListAdapter adapter = new DriverVehicleListAdapter(this,list);
//        rvDriverVehicleList.setLayoutManager(new LinearLayoutManager(this));
//        rvDriverVehicleList.setHasFixedSize(false);
//        rvDriverVehicleList.setAdapter(adapter);

        DriversDetailsModel detailsModel = new DriversDetailsModel();
        detailsModel.setStrVehicleNo("DL01H0123");
        detailsModel.setStrVehicleName("Tesla Cyber Truck");
        detailsModel.setStrDriverName("DriverName");
        ArrayList<DriversDetailsModel> list = new  ArrayList();
        list.add(detailsModel);

        list.add(detailsModel);
        list.add(detailsModel);
        list.add(detailsModel);
        list.add(detailsModel);

        DriverVehicleListAdapter adapter = new DriverVehicleListAdapter(DriverActivity.this,list,this);

        findViewById(R.id.driverLayout_noList_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.driverLayout_noList_icon).setVisibility(View.GONE);
                findViewById(R.id.driverLayout_noList_content).setVisibility(View.GONE);
                findViewById(R.id.driverLayout_noList_addVehilceTv).setVisibility(View.GONE);

                rvDriverVehicleList.setLayoutManager(new LinearLayoutManager(DriverActivity.this));
                rvDriverVehicleList.setHasFixedSize(false);
                rvDriverVehicleList.setAdapter(adapter);

                findViewById(R.id.driver_dashboard_search).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.driverLayout_noList_addVehilceTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationMenuItemView vehicleAdd = findViewById(R.id.driverVehicleList);
                vehicleAdd.callOnClick();
            }
        });

        passangerDetailsSetup();

    }

    private void passangerDetailsSetup(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currentTime = simpleDateFormat.format(new Date()).toString();
                        passangerDetials_liveTime.setText(currentTime);
                    }
                });

            }
        },0,1000);

        passangerDetails_ivFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView)findViewById(R.id.passangerDetailsBltnSheetClose)).setImageResource(R.drawable.ic_baseline_expand_less_24);
                if(passangerDetailsLayout.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    passangerDetailsLayout.setHideable(false);
                    passangerDetailsLayout.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    passangerDetailsLayout.setFitToContents(false);
                    passangerDetailsLayout.setHalfExpandedRatio((float)PASSANGER_DETAILS_BOTTOMSHEET_MIN_HEIGHT/findViewById(R.id.passangerDetailsBottomSheet).getMeasuredHeight());
                    passangerDetailsLayout.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                    passangerDetails_ivFullScreen.setImageResource(R.drawable.ic_baseline_zoom_out_map_24);
//                    com.technlogi.tt.user.services.GpsLocationService.getInstance(DriverActivity.this).getZoomLinster().zoom(true);
                }
                else{
                    passangerDetailsLayout.setFitToContents(false);
                    passangerDetailsLayout.setState(BottomSheetBehavior.STATE_EXPANDED);
                    passangerDetailsLayout.setHalfExpandedRatio((float)PASSANGER_DETAILS_BOTTOMSHEET_MAX_HEIGHT/findViewById(R.id.passangerDetailsBottomSheet).getMeasuredHeight());
                    passangerDetailsLayout.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                    passangerDetails_ivFullScreen.setImageResource(R.drawable.ic_baseline_fullscreen_24);
//                    com.technlogi.tt.user.services.GpsLocationService.getInstance(DriverActivity.this).getZoomLinster().zoom(false);
                }
            }
        });

        findViewById(R.id.passangerDetailsBottomSheetClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passangerDetailsLayout.setHideable(true);
                passangerDetailsLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                switchLayout(HOME_VIEW);
            }
        });

        findViewById(R.id.driver_dashboard_search_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLayout(PASSANGER_DETAILS_VIEW);
            }
        });

        findViewById(R.id.bookingOTP_submitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.bookingOTP).setEnabled(false);
                findViewById(R.id.bookingOTP_submitBtn).setEnabled(false);
                findViewById(R.id.passangerDetails_cancelTv).setEnabled(false);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.driverMap);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public void switchLayout(int layoutType){

        switch (layoutType){
            case HOME_VIEW:
                bottomSheetHome.setHideable(false);
                bottomSheetHome.setState(BottomSheetBehavior.STATE_EXPANDED);
                passangerDetailsLayout.setHideable(true);
                passangerDetailsLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                driverBookingCancelReason.setHideable(true);
                driverBookingCancelReason.setState(BottomSheetBehavior.STATE_HIDDEN);
                bookingCancelled.setHideable(true);
                bookingCancelled.setState(BottomSheetBehavior.STATE_HIDDEN);
                passangerRatingBottomSheet.setHideable(true);
                passangerRatingBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                driverPaymentWaiting.setHideable(true);
                driverPaymentWaiting.setState(BottomSheetBehavior.STATE_HIDDEN);
//                llDriverToolbar.animate()
//                        .translationYBy(600.0f)
//                        .setListener(new AnimatorListenerAdapter() {
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                super.onAnimationEnd(animation);
//                                llDriverToolbar.setVisibility(View.VISIBLE);
//                            }
//                        });
                toolbar.setVisibility(View.VISIBLE);
                todayEarning.setVisibility(View.VISIBLE);
                mapIcon.setVisibility(View.GONE);
                destination.setVisibility(View.GONE);
                SELECTED_VIEW = HOME_VIEW;
                break;

            case HOME_BACK:
                bottomSheetHome.setHideable(false);
                bottomSheetHome.setState(BottomSheetBehavior.STATE_EXPANDED);
                passangerDetailsLayout.setHideable(true);
                passangerDetailsLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                driverBookingCancelReason.setHideable(true);
                driverBookingCancelReason.setState(BottomSheetBehavior.STATE_HIDDEN);
                bookingCancelled.setHideable(true);
                bookingCancelled.setState(BottomSheetBehavior.STATE_HIDDEN);
                passangerRatingBottomSheet.setHideable(true);
                passangerRatingBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                driverPaymentWaiting.setHideable(true);
                driverPaymentWaiting.setState(BottomSheetBehavior.STATE_HIDDEN);
                llDriverToolbar.setVisibility(View.VISIBLE);
//                llDriverToolbar.animate()
//                        .translationYBy(600.0f)
//                        .setListener(new AnimatorListenerAdapter() {
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                super.onAnimationEnd(animation);
//                                llDriverToolbar.setVisibility(View.VISIBLE);
//                            }
//                        });
                toolbar.setVisibility(View.VISIBLE);
                todayEarning.setVisibility(View.VISIBLE);
                mapIcon.setVisibility(View.GONE);
                destination.setVisibility(View.GONE);
                SELECTED_VIEW = HOME_BACK;
                break;
            case PASSANGER_DETAILS_VIEW:
//                com.technlogi.tt.user.services.GpsLocationService.getInstance(DriverActivity.this).getZoomLinster().zoom(false);
                bottomSheetHome.setHideable(true);
                bottomSheetHome.setState(BottomSheetBehavior.STATE_HIDDEN);
                passangerDetailsLayout.setHideable(false);
                passangerDetailsLayout.setFitToContents(false);
                passangerDetailsLayout.setHalfExpandedRatio((float)PASSANGER_DETAILS_BOTTOMSHEET_MAX_HEIGHT/findViewById(R.id.passangerDetailsBottomSheet).getMeasuredHeight());
                passangerDetailsLayout.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
//                llDriverToolbar.animate()
//                        .translationYBy(-600.0f)
//                        .setListener(new AnimatorListenerAdapter() {
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                super.onAnimationEnd(animation);
//                                llDriverToolbar.setVisibility(View.GONE);
//                            }
//                        });
                toolbar.setVisibility(View.GONE);
                todayEarning.setVisibility(View.GONE);
                mapIcon.setVisibility(View.VISIBLE);
                destination.setVisibility(View.VISIBLE);

                finishRide();
                PREV_VIEW = SELECTED_VIEW;
                SELECTED_VIEW = PASSANGER_DETAILS_VIEW;
                break;
            case HIDE_VIEW:
                bottomSheetHome.setHideable(true);
                bottomSheetHome.setState(BottomSheetBehavior.STATE_HIDDEN);
                passangerDetailsLayout.setHideable(true);
                passangerDetailsLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                driverBookingCancelReason.setHideable(true);
                driverBookingCancelReason.setState(BottomSheetBehavior.STATE_HIDDEN);
                bookingCancelled.setHideable(true);
                bookingCancelled.setState(BottomSheetBehavior.STATE_HIDDEN);
                passangerRatingBottomSheet.setHideable(true);
                passangerRatingBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                driverPaymentWaiting.setHideable(true);
                driverPaymentWaiting.setState(BottomSheetBehavior.STATE_HIDDEN);
//                llDriverToolbar.animate()
//                        .translationYBy(-600.0f)
//                        .setListener(new AnimatorListenerAdapter() {
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                super.onAnimationEnd(animation);
//                                llDriverToolbar.setVisibility(View.GONE);
//                            }
//                        });
                toolbar.setVisibility(View.GONE);
                todayEarning.setVisibility(View.GONE);
                mapIcon.setVisibility(View.GONE);
                destination.setVisibility(View.GONE);
                SELECTED_VIEW = HIDE_VIEW;
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        if(SELECTED_VIEW == HOME_BACK) {
//            onDestroy();
//            finish();
//            System.exit(0);
//        }else {
//            if(SELECTED_VIEW == HIDE_VIEW) {
//                navController.popBackStack();
//                switchLayout(HOME_BACK);
//            }else{
//                switchLayout(SELECTED_VIEW);
//            }
//            switch (PREV_VIEW){
//                case HOME_VIEW:
//                    switchLayout(HOME_VIEW);
//            }
//        }
    }

    public void finishRide(){
        findViewById(R.id.passangerDetails_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(DriverActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DriverActivity.this,new String[]{Manifest.permission.CALL_PHONE},PHONE_CALL_REQUEST);
                } else {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"1234567890")));
                }
            }
        });

        findViewById(R.id.driver_endRideBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DriverActivity.this, "PassangerLandMark", Toast.LENGTH_SHORT).show();
                passangerRatingBottomSheet.setHideable(true);
                passangerRatingBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);

                RatingBottomSheetSetup();
            }
        });

        findViewById(R.id.passangerDetails_cancelTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DriverActivity.this, "PassangerDetailsCancel", Toast.LENGTH_SHORT).show();
                driverBookingCancelReason.setHideable(true);
                driverBookingCancelReason.setState(BottomSheetBehavior.STATE_EXPANDED);

                findViewById(R.id.driverBookingCancelReasonClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        driverBookingCancelReason.setHideable(true);
                        driverBookingCancelReason.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                });

                findViewById(R.id.driverBookingCancelReason_dontCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        driverBookingCancelReason.setHideable(true);
                        driverBookingCancelReason.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                });

                findViewById(R.id.driverBookingCancelReason_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        driverBookingCancelReason.setHideable(true);
                        driverBookingCancelReason.setState(BottomSheetBehavior.STATE_HIDDEN);

                        bookingCancelled.setHideable(true);
                        bookingCancelled.setState(BottomSheetBehavior.STATE_EXPANDED);
                        ((MaterialTextView)findViewById(R.id.alertBottomSheetTitle)).setText("Booking Cancelled");
                        ((MaterialTextView)findViewById(R.id.alertBottomSheetMsg)).setText("Your Booking with BID1234564 has been cancelled successfully");

                        findViewById(R.id.bookingCancelledClose).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bookingCancelled.setHideable(true);
                                bookingCancelled.setState(BottomSheetBehavior.STATE_HIDDEN);
                            }
                        });
                    }
                });
            }
        });
    }

    private void RatingBottomSheetSetup() {
        findViewById(R.id.passangerRatingBottomSheetCutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passangerRatingBottomSheet.setHideable(true);
                passangerRatingBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        findViewById(R.id.passangerRating_tvViewInvoice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverActivity.this, BookingDetailsActivity.class));
            }
        });

        findViewById(R.id.passangerRatingDoneBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passangerRatingBottomSheet.setHideable(true);
                passangerRatingBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                driverPaymentWaiting.setHideable(true);
                driverPaymentWaiting.setState(BottomSheetBehavior.STATE_EXPANDED);

                PaymentWaitingSetup();
            }
        });
    }

    private void PaymentWaitingSetup() {
        findViewById(R.id.driverPaymentWaitingClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverPaymentWaiting.setHideable(true);
                driverPaymentWaiting.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        findViewById(R.id.driverPaymentWaitingCashRecievedBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingCancelled.setHideable(true);
                bookingCancelled.setState(BottomSheetBehavior.STATE_EXPANDED);
                ((MaterialTextView)findViewById(R.id.alertBottomSheetTitle)).setText("Cash Recieved Confirmation");
                ((MaterialTextView)findViewById(R.id.alertBottomSheetMsg)).setText("Make sure you recieved cash of Rs 1234 for BID123456!");

                findViewById(R.id.bookingCancelledClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bookingCancelled.setHideable(true);
                        bookingCancelled.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                });
            }
        });
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (passangerDetailsLayout.getState() != BottomSheetBehavior.STATE_HIDDEN) {
            passangerDetailsLayout.setHideable(true);
            passangerDetailsLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
            passangerRatingBottomSheet.setHideable(true);
            passangerRatingBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
            driverBookingCancelReason.setHideable(true);
            driverBookingCancelReason.setState(BottomSheetBehavior.STATE_HIDDEN);
            bookingCancelled.setHideable(true);
            bookingCancelled.setState(BottomSheetBehavior.STATE_HIDDEN);
            driverPaymentWaiting.setHideable(true);
            driverPaymentWaiting.setState(BottomSheetBehavior.STATE_HIDDEN);
            mapIcon.setVisibility(View.GONE);
            destination.setVisibility(View.GONE);


            Display d = getWindowManager().getDefaultDisplay();
            Point p = new Point();
            d.getSize(p);
            int width = p.x;
            int height = p.y;

            Rational ratio = new Rational(width,height);
            PictureInPictureParams.Builder pip_Builder = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                pip_Builder = new PictureInPictureParams.Builder();
                pip_Builder.setAspectRatio(ratio).build();
                enterPictureInPictureMode(pip_Builder.build());
//                com.technlogi.tt.user.services.GpsLocationService.getInstance(DriverActivity.this).getZoomLinster().zoom(true);
            }
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        if (!isInPictureInPictureMode) {
            passangerDetailsLayout.setHideable(false);
            passangerDetailsLayout.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
            mapIcon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void JumpToDriverList() {
        NavigationMenuItemView driverAdd = findViewById(R.id.nav_driver_driver_list);
        driverAdd.callOnClick();
    }
}