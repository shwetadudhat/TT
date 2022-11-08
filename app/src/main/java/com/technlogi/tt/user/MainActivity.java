package com.technlogi.tt.user;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.util.Rational;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.technlogi.tt.FloatingWindow;
import com.technlogi.tt.LoadingDialog;
//import com.technlogi.tt.Overlay;
import com.technlogi.tt.PermissionReqActivity;
import com.technlogi.tt.R;
import com.technlogi.tt.common.broadcast.InternetConnectivityChange;
import com.technlogi.tt.common.broadcast.NotificationBroadcast;
import com.technlogi.tt.driver.DriverActivity;
import com.technlogi.tt.driver.services.ForegroundService;
import com.technlogi.tt.user.activities.BookingDetailsActivity;
import com.technlogi.tt.user.activities.EditProfileActivity;
import com.technlogi.tt.user.adapters.SelectedStopsAdapter;
import com.technlogi.tt.user.adapters.SelectedStopsHorizAdapter;
import com.technlogi.tt.user.adapters.TravelViewPagerAdapter;
import com.technlogi.tt.user.adapters.VehicleAdapter;
import com.technlogi.tt.user.adapters.VehicleGoodsAdapter;
import com.technlogi.tt.user.fragment.GoodsVehicleFragment;
import com.technlogi.tt.user.fragment.PasangerVehicleFragment;
import com.technlogi.tt.user.interfaces.LocationChangeLisnter;
import com.technlogi.tt.user.interfaces.LocationDetailsClickLisnter;
import com.technlogi.tt.user.interfaces.OnFragmentChangeLisnter;
import com.technlogi.tt.user.models.LocationModel;
import com.technlogi.tt.user.models.VehicleModel;
import com.technlogi.tt.user.services.GpsLocationService;
import com.technlogi.tt.common.utils.ClickEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import okhttp3.internal.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import static com.technlogi.tt.Constant.AUTO_TYPE_VIEW;
import static com.technlogi.tt.Constant.BIDDING_TYPE_VIEW;
import static com.technlogi.tt.Constant.DESTI_PLACE_REQUEST_CODE;
import static com.technlogi.tt.Constant.DRIVER_DETAILS_VIEW;
import static com.technlogi.tt.Constant.HIDE_VIEW;
import static com.technlogi.tt.Constant.HOME_BACK;
import static com.technlogi.tt.Constant.HOME_VIEW;
import static com.technlogi.tt.Constant.LOCATION_PERMISSION_REQUEST_CODE;
import static com.technlogi.tt.Constant.LOCATION_PIN_FOR_DESTINATION;
import static com.technlogi.tt.Constant.LOCATION_PIN_FOR_PICKUP;
import static com.technlogi.tt.Constant.LOCATION_PIN_FOR_STOP;
import static com.technlogi.tt.Constant.OVERLAY_PERMISSION_REQUEST;
import static com.technlogi.tt.Constant.PHONE_CALL_REQUEST;
import static com.technlogi.tt.Constant.PICKUP_PLACE_REQUEST_CODE;
import static com.technlogi.tt.Constant.PREV_VIEW;
import static com.technlogi.tt.Constant.SELECTED_VIEW;
import static com.technlogi.tt.Constant.STOP_PLACE_REQUEST_CODE;
import static com.technlogi.tt.Constant.VEHICLE_SELECT_VIEW;


public class MainActivity extends AppCompatActivity implements SelectedStopsAdapter.ReceiverDetails {

    private AppBarConfiguration mAppBarConfiguration;
    private BottomSheetBehavior sheetBehavior;
    private BottomSheetBehavior passangerTypsheet;
    private BottomSheetBehavior vehicleAutoListLayout;
    private BottomSheetBehavior paymentOptionLayout;
    private BottomSheetBehavior vehicle_goods_list_view;
    private BottomSheetBehavior driverDetailsLayout;
    private BottomSheetBehavior dateTimeLayout;
    private BottomSheetBehavior bookingCancelReason;
    private BottomSheetBehavior bookingCancelled;
    private BottomSheetBehavior ratingBottomSheet;

    private MaterialTextView etPickUpLocation;
    private MaterialTextView etDestiUpLocation;
    private MaterialTextView etStopLocation;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private AppCompatImageView imgEditProfile;

    private ImageView ivPointCurrentLocation;
    private boolean isFirstTime = true;
    private LocationChangeLisnter locationChangeLisnter;
    private RecyclerView rvSltstopLocation;
    private MaterialButton btnAddStop;

    /*layout*/
    private ConstraintLayout bottom_sheet_home;
    private LinearLayout passangerTypeLayout;

    private LocationModel stopLocation;
    private MaterialButton btnPassenger;
    private LinearLayout llToolbar;

    private ImageView btnback;
    private  boolean toggle = false;
    private boolean vehicleList_toggle = false;
    private boolean vehicleList_good_toggle = false;
    private boolean driverDetails_toggle = false;

    private int selectedPos = 0;

    private int selectedDate;
    private int selectedMonth;
    private int selectedYear;
    private int selectHour;
    private int selectMinute;
    private NavController navController;


    private FragmentManager fragmentManager;
    private Boolean isDriver;

    private Boolean isBidding;


    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    private static RemoteViews contentView;
    private static Notification notification;
    private static NotificationManager notificationManager;
    private static final int NotificationID = 1005;
    private static NotificationCompat.Builder mBuilder;

    private String paymentMethod;
    private MaterialRadioButton googlePay,paytm,phonepe;
    private Button confirmPaymentMethod;

    private LoadingDialog loadingDialog;

    private static final int DRIVER_DETAILS_BOTTOMSHEET_MAX_HEIGHT = 500;
    private static final int DRIVER_DETAILS_BOTTOMSHEET_MIN_HEIGHT = 150;

    private ImageView ivFullScreen;
    private ImageView vehicleGoodsList_ivFullScreen;
    private ImageView vehicleList_ivFullScreen;
    private ImageView driverDetails_ivFullScreen;

    private Dialog filterDialog;

    private int totalHours=0,totalMins=0;
    private float totalDistance=0;

    private View driverDetailsRouteDetails;

    private List<LocationModel> stopList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ivFullScreen =  findViewById(R.id.ivFullScreen);
        vehicleGoodsList_ivFullScreen =  findViewById(R.id.vehicleGoodsList_ivFullScreen);
        vehicleList_ivFullScreen =  findViewById(R.id.vehicleList_ivFullScreen);
        driverDetails_ivFullScreen =  findViewById(R.id.driverDetails_ivFullScreen);
        driverDetailsRouteDetails = findViewById(R.id.driverDetails_routeDetails);

        filterDialog = new Dialog(this);
        filterDialog.setContentView(R.layout.dialog_filter_popup);
        filterDialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);
        filterDialog.findViewById(R.id.filter_done_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.dismiss();
            }
        });

        filterDialog.findViewById(R.id.filter_dialog_cut_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.dismiss();
            }
        });

//        When click on Myself on Top right of mainActiviy
        findViewById(R.id.booking_for).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingForDialogInitialize();
            }
        });

//        Overlay
//        Overlay is used when driver get notification to accept/reject for a booking
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(Settings.canDrawOverlays(MainActivity.this))
                startOverlay();
            else{
                requestPermission(OVERLAY_PERMISSION_REQUEST);
            }
        }
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        FirebaseMessaging.getInstance().subscribeToTopic("Passanger");

//        com.technlogi.tt.Notification notification = new com.technlogi.tt.Notification();
//        notification.runNotification(getApplicationContext());

        gpsLocaitonLisnter();

        loadingDialog = new LoadingDialog(this);
        loadingDialog.showLoadingDialog();

        googlePay = findViewById(R.id.google_pay);
        paytm = findViewById(R.id.paytm);
        phonepe = findViewById(R.id.phonepe);

        confirmPaymentMethod = findViewById(R.id.confirm_payment_method);


/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            askPermission();
        }*/

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions( MainActivity.this, new String[]{Manifest.permission.FOREGROUND_SERVICE}, LOCATION_PERMISSION_REQUEST_CODE);
        }else {
            startService(new Intent(MainActivity.this, ForegroundService.class));
        }
        findViewById(R.id.destiBtnSheetClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((ImageView)findViewById(R.id.destiBtnSheetClose)).setImageResource(sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED ?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);

                sheetBehavior.setState(sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED ?BottomSheetBehavior.STATE_COLLAPSED:BottomSheetBehavior.STATE_EXPANDED);

            }
        });

        findViewById(R.id.vehiclListBtnSheetClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(vehicleAutoListLayout.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    vehicleList_ivFullScreen.setImageResource(R.drawable.ic_baseline_zoom_out_map_24);
                    GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(true);
                }
                else{
                    vehicleList_ivFullScreen.setImageResource(R.drawable.ic_baseline_fullscreen_24);
                    GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(false);
                }
                vehicleList_toggle =! vehicleList_toggle;

                ((ImageView)findViewById(R.id.vehiclListBtnSheetClose)).setImageResource(vehicleAutoListLayout.getState() == BottomSheetBehavior.STATE_EXPANDED ?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);

                vehicleAutoListLayout.setState(vehicleAutoListLayout.getState() == BottomSheetBehavior.STATE_EXPANDED ?BottomSheetBehavior.STATE_COLLAPSED:BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        findViewById(R.id.vehiclGoodsBtnSheetClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(vehicle_goods_list_view.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    vehicleGoodsList_ivFullScreen.setImageResource(R.drawable.ic_baseline_zoom_out_map_24);
                    GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(true);
                }
                else{
                    vehicleGoodsList_ivFullScreen.setImageResource(R.drawable.ic_baseline_fullscreen_24);
                    GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(false);
                }
                vehicleList_good_toggle =! vehicleList_good_toggle;

                ((ImageView)findViewById(R.id.vehiclGoodsBtnSheetClose)).setImageResource(vehicle_goods_list_view.getState() == BottomSheetBehavior.STATE_EXPANDED ?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);

                vehicle_goods_list_view.setState(vehicle_goods_list_view.getState() == BottomSheetBehavior.STATE_EXPANDED ?BottomSheetBehavior.STATE_COLLAPSED:BottomSheetBehavior.STATE_EXPANDED);
            }
        });


        findViewById(R.id.driverDetailsBltnSheetClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                ((ImageView)findViewById(R.id.driverDetailsBltnSheetClose)).setImageResource(driverDetailsLayout.getState() == BottomSheetBehavior.STATE_EXPANDED ?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);

//                driverDetailsLayout.setState(driverDetailsLayout.getState() == BottomSheetBehavior.STATE_EXPANDED ?BottomSheetBehavior.STATE_COLLAPSED:BottomSheetBehavior.STATE_EXPANDED);

                if (driverDetailsLayout.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    driverDetailsLayout.setHideable(false);
                    driverDetailsLayout.setState(BottomSheetBehavior.STATE_EXPANDED);
//                    driverDetailsLayout.setHalfExpandedRatio((float)DRIVER_DETAILS_BOTTOMSHEET_MIN_HEIGHT/findViewById(R.id.driverDetailsBottomSheet).getMeasuredHeight());
//                    driverDetailsLayout.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                    ((ImageView)findViewById(R.id.driverDetailsBltnSheetClose)).setImageResource(R.drawable.ic_baseline_expand_more_24);
                } else {
//                    driverDetailsLayout.setHideable(false);
//                    driverDetailsLayout.setState(BottomSheetBehavior.STATE_EXPANDED);
//                    driverDetailsLayout.setHalfExpandedRatio((float)DRIVER_DETAILS_BOTTOMSHEET_MAX_HEIGHT/findViewById(R.id.driverDetailsBottomSheet).getMeasuredHeight());
//                    driverDetailsLayout.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                    driverDetailsLayout.setHideable(false);
                    driverDetailsLayout.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    driverDetailsLayout.setHalfExpandedRatio((float)DRIVER_DETAILS_BOTTOMSHEET_MAX_HEIGHT/findViewById(R.id.driverDetailsBottomSheet).getMeasuredHeight());
                    driverDetailsLayout.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                    ((ImageView)findViewById(R.id.driverDetailsBltnSheetClose)).setImageResource(R.drawable.ic_baseline_expand_less_24);
                }

            }
        });

        findViewById(R.id.bookingCancelReasonSheetClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView)findViewById(R.id.bookingCancelReasonSheetClose)).setImageResource(bookingCancelReason.getState() == BottomSheetBehavior.STATE_EXPANDED ?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);

                bookingCancelReason.setState(bookingCancelReason.getState() == BottomSheetBehavior.STATE_EXPANDED ?BottomSheetBehavior.STATE_COLLAPSED:BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        findViewById(R.id.bookingCancelledSheetClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((ImageView)findViewById(R.id.bookingCancelledSheetClose)).setImageResource(bookingCancelled.getState() == BottomSheetBehavior.STATE_EXPANDED ?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);
                bookingCancelled.setState(bookingCancelled.getState() == BottomSheetBehavior.STATE_EXPANDED ?BottomSheetBehavior.STATE_COLLAPSED:BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        findViewById(R.id.ratingBottomSheetClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView)findViewById(R.id.ratingBottomSheetClose)).setImageResource(ratingBottomSheet.getState() == BottomSheetBehavior.STATE_EXPANDED ?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);
                ratingBottomSheet.setState(ratingBottomSheet.getState() == BottomSheetBehavior.STATE_EXPANDED ?BottomSheetBehavior.STATE_COLLAPSED:BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        findViewById(R.id.paymentOptionSheetClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeLayout.setHideable(true);
//                ((ImageView)findViewById(R.id.paymentOptionSheetClose)).setImageResource(paymentOptionLayout.getState() == BottomSheetBehavior.STATE_EXPANDED ?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);
                paymentOptionLayout.setState(paymentOptionLayout.getState() == BottomSheetBehavior.STATE_EXPANDED ?BottomSheetBehavior.STATE_HIDDEN:BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        findViewById(R.id.customDateSheetClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeLayout.setHideable(true);
                ((ImageView)findViewById(R.id.customDateSheetClose)).setImageResource(dateTimeLayout.getState() == BottomSheetBehavior.STATE_EXPANDED ?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);
                dateTimeLayout.setState(dateTimeLayout.getState() == BottomSheetBehavior.STATE_EXPANDED ?BottomSheetBehavior.STATE_HIDDEN:BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        findViewById(R.id.vsSheetClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (passangerTypsheet.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(false);
                    ivFullScreen.setImageResource(R.drawable.ic_baseline_fullscreen_24);
                } else {
                    GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(true);
                    ivFullScreen.setImageResource(R.drawable.ic_baseline_zoom_out_map_24);
                }
                toggle =! toggle;

                ((ImageView)findViewById(R.id.vsSheetClose)).setImageResource(passangerTypsheet.getState() == BottomSheetBehavior.STATE_EXPANDED ?R.drawable.ic_baseline_expand_less_24:R.drawable.ic_baseline_expand_more_24);

                passangerTypsheet.setState(passangerTypsheet.getState() == BottomSheetBehavior.STATE_EXPANDED ?BottomSheetBehavior.STATE_COLLAPSED:BottomSheetBehavior.STATE_EXPANDED);
            }
        });


        stopLocation = new LocationModel();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        preferences = getSharedPreferences("main_pref",0);
        isDriver = preferences.getBoolean("driver",false);


        NavigationView navigationView = findViewById(R.id.nav_view);


        imgEditProfile =  navigationView.getHeaderView(0).findViewById(R.id.imgEditProfile);
        imgEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
            }
        });


        navigationView.getHeaderView(0).findViewById(R.id.txtSwitchDriver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                editor = preferences.edit();
                editor.putBoolean("driver",false);
                editor.commit();

                startActivity(new Intent(MainActivity.this, DriverActivity.class));
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });

        etPickUpLocation = findViewById(R.id.etPickUpLocation);
        etDestiUpLocation = findViewById(R.id.etDestiUpLocation);
        etStopLocation = findViewById(R.id.etStopLocation);
        rvSltstopLocation = findViewById(R.id.rvSltstopLocationTop);
        btnback = findViewById(R.id.btnback);

        llToolbar = findViewById(R.id.llToolbar);

        bottom_sheet_home = findViewById(R.id.bottom_sheet_home);
        passangerTypeLayout = findViewById(R.id.passangerTypeLayout);


        sheetBehavior =  BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_home));
        sheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        sheetBehavior.setHideable(true);

        passangerTypsheet =  BottomSheetBehavior.from(findViewById(R.id.passangerTypeLayout));
        passangerTypsheet.setState(BottomSheetBehavior.STATE_EXPANDED);
        passangerTypsheet.setHideable(true);

        vehicleAutoListLayout =  BottomSheetBehavior.from(findViewById(R.id.vehicleAutoListLayout));
        vehicleAutoListLayout.setState(BottomSheetBehavior.STATE_EXPANDED);
        vehicleAutoListLayout.setHideable(true);


        paymentOptionLayout =  BottomSheetBehavior.from(findViewById(R.id.paymentOptionLayout));
        paymentOptionLayout.setHideable(true);
        paymentOptionLayout.setState(BottomSheetBehavior.STATE_HIDDEN);

        vehicle_goods_list_view =  BottomSheetBehavior.from(findViewById(R.id.vehicleGoodsListLayout));
        vehicle_goods_list_view.setHideable(true);
        vehicle_goods_list_view.setState(BottomSheetBehavior.STATE_HIDDEN);

        driverDetailsLayout =  BottomSheetBehavior.from(findViewById(R.id.driverDetailsLayout));
        driverDetailsLayout.setHideable(true);
        driverDetailsLayout.setState(BottomSheetBehavior.STATE_HIDDEN);

        bookingCancelReason =  BottomSheetBehavior.from(findViewById(R.id.bookingCancelReason));
        bookingCancelReason.setHideable(true);
        bookingCancelReason.setState(BottomSheetBehavior.STATE_HIDDEN);

        bookingCancelled =  BottomSheetBehavior.from(findViewById(R.id.bookingCancelled));
        bookingCancelled.setHideable(true);
        bookingCancelled.setState(BottomSheetBehavior.STATE_HIDDEN);

        ratingBottomSheet =  BottomSheetBehavior.from(findViewById(R.id.ratingBottomSheet));
        ratingBottomSheet.setHideable(true);
        ratingBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);

        dateTimeLayout =  BottomSheetBehavior.from(findViewById(R.id.dateTimeLayout));
        dateTimeLayout.setHideable(true);
        dateTimeLayout.setState(BottomSheetBehavior.STATE_HIDDEN);

        /*passangerTypsheet =  BottomSheetBehavior.from(findViewById(R.id.passangerTypeLayout));
        passangerTypsheet.setState(BottomSheetBehavior.STATE_EXPANDED);
        passangerTypsheet.setHideable(true);*/


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (SELECTED_VIEW){
                    case VEHICLE_SELECT_VIEW:
                        switchLayout(HOME_BACK);
                        break;
                    case AUTO_TYPE_VIEW:
                        switchLayout(VEHICLE_SELECT_VIEW);
                        break;
                    case BIDDING_TYPE_VIEW:
                        switchLayout(VEHICLE_SELECT_VIEW);
                        break;
                    case DRIVER_DETAILS_VIEW:
                        switchLayout(PREV_VIEW);
                }


            }
        });
        switchLayout(HOME_VIEW);


        btnPassenger = findViewById(R.id.btnPassenger);
        btnPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.showLoadingDialog();
                totalDistance = 0;
                totalHours = 0;
                totalMins = 0;

                if(!etPickUpLocation.getText().toString().isEmpty() && !etDestiUpLocation.getText().toString().isEmpty()) {
                    GpsLocationService.getInstance(MainActivity.this).setCommonLocationChangeLisnter(new LocationChangeLisnter() {
                        @Override
                        public void onLocationChange(LocationModel locationModel, boolean moveMap) {

                        }
                    });
                    switchLayout(VEHICLE_SELECT_VIEW);

                    GpsLocationService.getInstance(MainActivity.this).getNextButtonClickListner().onClick(v);

                }
            }
        });

        btnAddStop = findViewById(R.id.btnAddStop);


        btnAddStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    stopList = GpsLocationService.getInstance(MainActivity.this).getStopLocationLatLng();

                    List<LocationModel> searchModel = new ArrayList<>();
                    searchModel.addAll(stopList);
                    boolean contain = false;
                    if(searchModel.size() != 0) {
                        for (LocationModel locationModel : searchModel) {
                            if (stopLocation != null) {
                                if (locationModel.getStrLocationName().equals(stopLocation.getStrLocationName())) {
                                        contain = true;
                                }
                            }
                        }
                    }

                    if(!contain && stopLocation != null){
                      stopLocation.setIntPosition(stopList.size()+1);
                      stopList.add(stopLocation);


                        try {
                            stopList.get(stopList.size() - 1).setSeletedForStops(true);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        ImageView imgDelete = findViewById(R.id.imgDelete);

                        rvSltstopLocation =  findViewById(R.id.rvSltstopLocationTop);
                        SelectedStopsHorizAdapter selectedStopsAdapter =  new SelectedStopsHorizAdapter(stopList,GpsLocationService.getInstance(MainActivity.this).getDistance(),GpsLocationService.getInstance(MainActivity.this).getTimes());
                        selectedStopsAdapter.ActionListner(new LocationDetailsClickLisnter() {
                                                                                @Override
                                                                                public void onClick(int position) {
                                                                                    ((MaterialTextView)findViewById(R.id.tvSelectedLocation)).setText(stopList.get(position-1).getStrLocationName());
                                                                                    selectedPos =  position-1;
                                                                                    ((MaterialTextView)findViewById(R.id.tvLocationCircl)).setText(Integer.toString(selectedPos+1));
                                                                                    findViewById(R.id.llLocationDetails).setVisibility(View.VISIBLE);
                                                                                }

                                                                            });
                        rvSltstopLocation.setHasFixedSize(false);
                        rvSltstopLocation.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.HORIZONTAL,false));
                        rvSltstopLocation.setAdapter(selectedStopsAdapter);
                        imgDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                stopList.remove(selectedPos);
                                try {
                                    stopList.get(selectedPos - 1).setSeletedForStops(true);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                selectedStopsAdapter.notifyDataSetChanged();
                                ((MaterialTextView)findViewById(R.id.tvLocationCircl)).setText("");
                                findViewById(R.id.llLocationDetails).setVisibility(View.GONE);

                            }
                        });
                        etStopLocation.setText("");
                        btnAddStop.setEnabled(false);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvSltstopLocation);


        ivPointCurrentLocation = findViewById(R.id.ivPointCurrentLocation);
        ivPointCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GpsLocationService.getInstance(MainActivity.this).getPointChangeListner().pinChange(LOCATION_PIN_FOR_PICKUP);

                GpsLocationService.getInstance(MainActivity.this).getPickupResetLocationChangeLisnter().onLocationChange(null,true);

            }
        });

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_user_bidding_list, R.id.nav_gallery, R.id.nav_slideshow,R.id.userBookingFragment,R.id.refersEarnFragment)
                .setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        getSupportActionBar().setTitle("");
      //  navController.getBackStackEntry(R.id.nav_home);

//        etPickUpLocation.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction() == MotionEvent.ACTION_UP) {
//                    List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
//                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(MainActivity.this);
//
//                    GpsLocationService.getInstance(MainActivity.this).setCommonLocationChangeLisnter(GpsLocationService.getInstance(MainActivity.this).getPickupLcationChangeListner());
//                    startActivityForResult(intent, PICKUP_PLACE_REQUEST_CODE);
//                    GpsLocationService.getInstance(MainActivity.this).getPointChangeListner().pinChange(LOCATION_PIN_FOR_PICKUP);
//
//
//                    return true;
//                }return false;
//            }
//        });


        etPickUpLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                   List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                    //Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, null).build(MainActivity.this);

                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).setTypeFilter(TypeFilter.ESTABLISHMENT).build(MainActivity.this);

                    GpsLocationService.getInstance(MainActivity.this).setCommonLocationChangeLisnter(GpsLocationService.getInstance(MainActivity.this).getPickupLcationChangeListner());
                    startActivityForResult(intent, PICKUP_PLACE_REQUEST_CODE);
                    GpsLocationService.getInstance(MainActivity.this).getPointChangeListner().pinChange(LOCATION_PIN_FOR_PICKUP);


                    return true;
                }return false;
            }
        });


        etDestiUpLocation.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).setTypeFilter(TypeFilter.ESTABLISHMENT).build(MainActivity.this);
                    GpsLocationService.getInstance(MainActivity.this).setCommonLocationChangeLisnter(GpsLocationService.getInstance(MainActivity.this).getDestiLocationChangeLisnter());
                    startActivityForResult(intent, DESTI_PLACE_REQUEST_CODE);
                    GpsLocationService.getInstance(MainActivity.this).getPointChangeListner().pinChange(LOCATION_PIN_FOR_DESTINATION);

                    return true;
                }
                return false;

            }

        });
        etStopLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).setTypeFilter(TypeFilter.ESTABLISHMENT).build(MainActivity.this);
                    GpsLocationService.getInstance(MainActivity.this).setCommonLocationChangeLisnter(GpsLocationService.getInstance(MainActivity.this).getStopLcationChangeListner());
                    startActivityForResult(intent, STOP_PLACE_REQUEST_CODE);
                   GpsLocationService.getInstance(MainActivity.this).getPointChangeListner().pinChange(LOCATION_PIN_FOR_STOP);

                    return true;
                }return false;
            }
        });
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == PICKUP_PLACE_REQUEST_CODE) {
                if (data != null) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    LatLng ltl = place.getLatLng();
                    LocationModel model = new LocationModel();
                    model.setDblLong(ltl.longitude);
                    model.setDblLat(ltl.latitude);
                    model.setStrLocationName(place.getName()!= null? place.getName():""+","+place.getAddress() != null?place.getAddress():"");
                    GpsLocationService.getInstance(MainActivity.this).getCommonLocationChangeLisnter().onLocationChange(model,true);
                }
             }
            else if(requestCode == DESTI_PLACE_REQUEST_CODE) {
                if (data != null) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    LatLng ltl = place.getLatLng();
                    LocationModel model = new LocationModel();
                    model.setDblLong(ltl.longitude);
                    model.setDblLat(ltl.latitude);
                    model.setStrLocationName(place.getName()!= null? place.getName():""+","+place.getAddress() != null?place.getAddress():"");
                    GpsLocationService.getInstance(MainActivity.this).getCommonLocationChangeLisnter().onLocationChange(model,true);
                }

            }else if(requestCode == STOP_PLACE_REQUEST_CODE){
                Place place = Autocomplete.getPlaceFromIntent(data);
                LatLng ltl = place.getLatLng();
                LocationModel model = new LocationModel();
                model.setDblLong(ltl.longitude);
                model.setDblLat(ltl.latitude);
                model.setStrLocationName(place.getName()!= null? place.getName():""+","+place.getAddress() != null?place.getAddress():"");
                GpsLocationService.getInstance(MainActivity.this).getCommonLocationChangeLisnter().onLocationChange(model,true);
            } else if (requestCode==OVERLAY_PERMISSION_REQUEST) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.canDrawOverlays(MainActivity.this)) {
                        startOverlay();
                    }
                }
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

//    private void runNotification() {
//
//        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
//
////        contentView = new RemoteViews(getPackageName(), R.layout.custom_notification_layout);
////        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
////        Intent switchIntent = new Intent(this,MainActivity.class);
////        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(this, 1020, switchIntent, 0);
//
//        contentView = new RemoteViews(getPackageName(),R.layout.notification_layout);
//        contentView.setImageViewResource(R.id.notification_icon,R.mipmap.ic_launcher);
//        Intent intent1 = new Intent(this, NotificationBroadcast.class);
//        intent1.putExtra("id","100");
//        PendingIntent buttonPendingIntent = PendingIntent.getBroadcast(this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
//        contentView.setOnClickPendingIntent(R.id.notification_acceptButton,buttonPendingIntent);
//
//
//
//        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
//        mBuilder.setAutoCancel(false);
//        mBuilder.setOngoing(true);
//        mBuilder.setPriority(Notification.PRIORITY_HIGH);
//        mBuilder.setOnlyAlertOnce(true);
//        mBuilder.build().flags = Notification.FLAG_NO_CLEAR | Notification.PRIORITY_HIGH;
//        mBuilder.setContent(contentView);
////        mBuilder.addAction(R.mipmap.ic_launcher,"Accept",buttonPendingIntent);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            String channelId = "channel_id";
//            NotificationChannel channel = new NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH);
//            channel.enableVibration(true);
//            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//            notificationManager.createNotificationChannel(channel);
//            mBuilder.setChannelId(channelId);
//        }
//
//        notification = mBuilder.build();
//        notificationManager.notify(NotificationID, notification);
//    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
        gpsLocaitonLisnter();
        //GpsLocationService.getInstance(MainActivity.this).setCommonLocationChangeLisnter(GpsLocationService.getInstance(MainActivity.this).getPickupLcationChangeListner()) ;

    }

    private void gpsLocaitonLisnter(){

        GpsLocationService.getInstance(this).setGpsLocationSubsribers(new LocationChangeLisnter() {
            @Override
            public void onLocationChange(LocationModel locationModel,boolean moveMap) {
                if(isFirstTime){
                    etPickUpLocation.setText(locationModel.getStrLocationName());
                    isFirstTime = false;
                    loadingDialog.dismissLoadingDialog();
                }
            }
        });

        GpsLocationService.getInstance(this).setPickupLocationSubsribers(new LocationChangeLisnter() {
            @Override
            public void onLocationChange(LocationModel locationModel,boolean moveMap) {
                etPickUpLocation.setText(locationModel.getStrLocationName());
            }
        });

        GpsLocationService.getInstance(this).setDestiLocationSubsribers(new LocationChangeLisnter() {
            @Override
            public void onLocationChange(LocationModel locationModel,boolean moveMap) {
                etDestiUpLocation.setText(locationModel.getStrLocationName() );
            }
        });
        GpsLocationService.getInstance(MainActivity.this).setStopLocationSubsribers(new LocationChangeLisnter() {
            @Override
            public void onLocationChange(LocationModel locationModel,boolean moveMap) {
                stopLocation = locationModel;
                etStopLocation.setText(stopLocation.getStrLocationName());
                btnAddStop.setEnabled(true);
            }
        });

    }

    private void askPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 151);
    }


    @Override
    public void onBackPressed() {
       // GpsLocationService.clearInstance();
        if(SELECTED_VIEW == HOME_BACK) {
            onDestroy();
            finish();
            System.exit(0);
        }else {
//            if(SELECTED_VIEW == HIDE_VIEW) {
//                navController.popBackStack();
//                switchLayout(HOME_BACK);
//            }else{
//                switchLayout(SELECTED_VIEW);
//            }
            switch (SELECTED_VIEW){
                case VEHICLE_SELECT_VIEW:
                    switchLayout(HOME_BACK);
                    break;
                case AUTO_TYPE_VIEW:
                    switchLayout(VEHICLE_SELECT_VIEW);
                    break;
                case BIDDING_TYPE_VIEW:
                    switchLayout(VEHICLE_SELECT_VIEW);
                    break;
                case DRIVER_DETAILS_VIEW:
                    switchLayout(PREV_VIEW);
            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void switchLayout(int layoutType){

        switch (layoutType){
            case HOME_VIEW:
                sheetBehavior.setHideable(false);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                passangerTypsheet.setHideable(true);
                passangerTypsheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                vehicleAutoListLayout.setHideable(true);
                vehicleAutoListLayout.setState(BottomSheetBehavior.STATE_HIDDEN);

                vehicle_goods_list_view.setHideable(true);
                vehicle_goods_list_view.setState(BottomSheetBehavior.STATE_HIDDEN);


                btnback.animate()
                        .translationYBy(-600.0f)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                btnback.setVisibility(View.GONE);
                            }
                        });

                findViewById(R.id.mainActivity_bookingAlert).setVisibility(View.VISIBLE);

                SELECTED_VIEW = HOME_VIEW;
                break;

            case HOME_BACK:
                sheetBehavior.setHideable(false);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                passangerTypsheet.setHideable(true);
                passangerTypsheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                vehicleAutoListLayout.setHideable(true);
                driverDetailsLayout.setHideable(true);
                driverDetailsLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                vehicleAutoListLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                vehicle_goods_list_view.setHideable(true);
                vehicle_goods_list_view.setState(BottomSheetBehavior.STATE_HIDDEN);

                btnback.animate()
                        .translationYBy(-600.0f)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                btnback.setVisibility(View.GONE);
                            }
                        });
                llToolbar.setVisibility(View.VISIBLE);
                llToolbar.animate()
                        .translationYBy(600.0f)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                            }
                        });

                findViewById(R.id.mainActivity_bookingAlert).setVisibility(View.VISIBLE);
                SELECTED_VIEW = HOME_BACK;
                break;
            case VEHICLE_SELECT_VIEW:
               sheetBehavior.setHideable(true);
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                passangerTypsheet.setHideable(false);
                passangerTypsheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                passangerTypsheet.setDraggable(false);
                vehicleAutoListLayout.setHideable(true);
                vehicleAutoListLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                vehicle_goods_list_view.setHideable(true);
                vehicle_goods_list_view.setState(BottomSheetBehavior.STATE_HIDDEN);
                driverDetailsLayout.setHideable(true);
                driverDetailsLayout.setState(BottomSheetBehavior.STATE_HIDDEN);

                if(SELECTED_VIEW != AUTO_TYPE_VIEW && SELECTED_VIEW != BIDDING_TYPE_VIEW ) {
                    llToolbar.animate()
                            .translationYBy(-600.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    llToolbar.setVisibility(View.GONE);
                                }
                            });
                    btnback.animate()
                            .translationYBy(600.0f)
                            .alpha(1.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    btnback.setVisibility(View.VISIBLE);
                                }
                            });
                }
                GpsLocationService.getInstance(MainActivity.this).setRouteCreateLisnter(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setVehicleList();
                        GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(false);
                    }
                });
                SELECTED_VIEW = VEHICLE_SELECT_VIEW;
                break;

            case AUTO_TYPE_VIEW:
                GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(false);
                passangerTypsheet.setHideable(true);
                passangerTypsheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                vehicleAutoListLayout.setHideable(false);
                driverDetailsLayout.setHideable(true);
                driverDetailsLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                vehicleAutoListLayout.setState(BottomSheetBehavior.STATE_EXPANDED);
                vehicle_goods_list_view.setHideable(true);
                vehicle_goods_list_view.setState(BottomSheetBehavior.STATE_HIDDEN);
                setAutoVehiclelist();
                SELECTED_VIEW = AUTO_TYPE_VIEW;
                break;

            case BIDDING_TYPE_VIEW:
                GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(false);
                vehicle_goods_list_view.setHideable(false);
                vehicle_goods_list_view.setState(BottomSheetBehavior.STATE_EXPANDED);

                driverDetailsLayout.setHideable(true);
                driverDetailsLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                passangerTypsheet.setHideable(true);
                passangerTypsheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                vehicleAutoListLayout.setHideable(true);
                vehicleAutoListLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                setBiddingVehiclelist();
                SELECTED_VIEW = BIDDING_TYPE_VIEW;
                break;
            case DRIVER_DETAILS_VIEW:
                GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(false);
                driverDetailsLayout.setHideable(false);
                driverDetailsLayout.setFitToContents(false);
                driverDetailsLayout.setHalfExpandedRatio((float)DRIVER_DETAILS_BOTTOMSHEET_MAX_HEIGHT/findViewById(R.id.driverDetailsBottomSheet).getMeasuredHeight());
                driverDetailsLayout.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);


                vehicle_goods_list_view.setHideable(true);
                vehicle_goods_list_view.setState(BottomSheetBehavior.STATE_HIDDEN);
                passangerTypsheet.setHideable(true);
                passangerTypsheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                vehicleAutoListLayout.setHideable(true);
                vehicleAutoListLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                finishRide();
                PREV_VIEW = SELECTED_VIEW;
//                SELECTED_VIEW = BIDDING_TYPE_VIEW;
                if (isBidding) {
                    SELECTED_VIEW = BIDDING_TYPE_VIEW;
                } else {
                    SELECTED_VIEW = AUTO_TYPE_VIEW;
                }

                break;
            case HIDE_VIEW:
                sheetBehavior.setHideable(true);
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                driverDetailsLayout.setHideable(true);
                driverDetailsLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                vehicle_goods_list_view.setHideable(true);
                vehicle_goods_list_view.setState(BottomSheetBehavior.STATE_HIDDEN);
                passangerTypsheet.setHideable(true);
                passangerTypsheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                vehicleAutoListLayout.setHideable(true);
                vehicleAutoListLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                llToolbar.animate()
                        .translationYBy(-600.0f)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                llToolbar.setVisibility(View.GONE);
                            }
                        });
                findViewById(R.id.mainActivity_bookingAlert).setVisibility(View.GONE);
                SELECTED_VIEW = HIDE_VIEW;
                break;
        }
    }

    public void setVehicleList(){

        MaterialTextView tvSelectedPickupLocation = findViewById(R.id.tvSelectedPickupLocation) ;
        TextView tvDeLocationDistance =  findViewById(R.id.tvDeLocationDistance);
        TextView tvDeLocationTimes =  findViewById(R.id.tvDeLocationTimes);
        RecyclerView rvSltstopLocation =  findViewById(R.id.rvSltstopLocation);

        MaterialTextView driverDetails_tvSelectedPickupLocation = driverDetailsRouteDetails.findViewById(R.id.tvSelectedPickupLocation) ;
        TextView driverDetails_tvDeLocationDistance =  driverDetailsRouteDetails.findViewById(R.id.tvDeLocationDistance);
        TextView driverDetails_tvDeLocationTimes =  driverDetailsRouteDetails.findViewById(R.id.tvDeLocationTimes);
        RecyclerView driverDetails_rvSltstopLocation =  driverDetailsRouteDetails.findViewById(R.id.rvSltstopLocation);

        findViewById(R.id.rideCofirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLayout(DRIVER_DETAILS_VIEW);
            }
        });

        findViewById(R.id.rideGoodsCofirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLayout(DRIVER_DETAILS_VIEW);
            }
        });

//        findViewById(R.id.vehicleSelectContinueToBookingBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                passangerGoodsDetailsDialog.show();
//            }
//        });

        setupTabbedView();

        MaterialTextView txtTimer =  findViewById(R.id.txtTimer);

        Calendar c = Calendar.getInstance();

         selectedDate =c.get(Calendar.DAY_OF_MONTH);
         selectedMonth = c.get(Calendar.MONTH) ;
         selectedYear = c.get(Calendar.YEAR);

         selectHour =  c.get(Calendar.HOUR_OF_DAY);
         selectMinute = c.get(Calendar.MINUTE);

        txtTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateTimeLayout.setHideable(false);
                dateTimeLayout.setState(BottomSheetBehavior.STATE_EXPANDED);
                ((MaterialTextView)findViewById(R.id.date_picker_actions)).setText((Integer.toString(selectedDate).length() == 1?"0"+Integer.toString(selectedDate):Integer.toString(selectedDate)) +"/"+(Integer.toString(selectedMonth+1).length() == 1?"0"+Integer.toString(selectedMonth+1):Integer.toString(selectedMonth+1)) +"/"+Integer.toString(selectedYear));
                ((MaterialTextView)findViewById(R.id.time_picker_actions)).setText((Integer.toString(selectHour).length() == 1?"0"+Integer.toString(selectHour):Integer.toString(selectHour))+":"+(Integer.toString(selectMinute).length() == 1?"0"+Integer.toString(selectMinute):Integer.toString(selectMinute)));


                findViewById(R.id.timerLayoutClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateTimeLayout.setHideable(true);
                        dateTimeLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                });


                findViewById(R.id.btnDateSelect).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((Switch)findViewById(R.id.schedule_switch_now)).isChecked()){
                            dateTimeLayout.setHideable(true);
                            dateTimeLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                            ((MaterialTextView)findViewById(R.id.txtTimer)).setText("NOW");
                        } else {
                            dateTimeLayout.setHideable(true);
                            dateTimeLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                            ((MaterialTextView)findViewById(R.id.txtTimer)).setText((Integer.toString(selectedDate).length() == 1?"0"+Integer.toString(selectedDate):Integer.toString(selectedDate)) +"/"+(Integer.toString(selectedMonth+1).length() == 1?"0"+Integer.toString(selectedMonth+1):Integer.toString(selectedMonth+1)) +"/"+Integer.toString(selectedYear)+"\n"+
                            (Integer.toString(selectHour).length() == 1?"0"+Integer.toString(selectHour):Integer.toString(selectHour))+":"+(Integer.toString(selectMinute).length() == 1?"0"+Integer.toString(selectMinute):Integer.toString(selectMinute)) );
                        }
                    }
                });

                findViewById(R.id.date_picker_actions).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                selectedDate =  dayOfMonth;
                                selectedMonth = month;
                                selectedYear =  year;
                                ((MaterialTextView)findViewById(R.id.date_picker_actions)).setText(Integer.toString(dayOfMonth)+"/"+Integer.toString(month+1)+"/"+Integer.toString(year));
                            }
                        },selectedYear, selectedMonth,selectedDate);
                        datePickerDialog.show();
                    }
                });
                 findViewById(R.id.time_picker_actions).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimePickerDialog datePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                selectHour =  hourOfDay;
                                selectMinute =  minute;

                                ((MaterialTextView)findViewById(R.id.time_picker_actions)).setText(Integer.toString(hourOfDay)+":"+Integer.toString(minute));
                            }
                        }, selectHour, selectMinute, false);
                        datePickerDialog.show();
                    }
                });

            }
        });


        toggle = false;
        ivFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passangerTypsheet.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    passangerTypsheet.setHideable(false);
                    passangerTypsheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    ivFullScreen.setImageResource(R.drawable.ic_baseline_zoom_out_map_24);
                    GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(true);
                }
                else{
                    passangerTypsheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                    ivFullScreen.setImageResource(R.drawable.ic_baseline_fullscreen_24);
                    GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(false);
                }
                toggle =! toggle;
            }
        });

        vehicleList_good_toggle = false;
        vehicleGoodsList_ivFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vehicle_goods_list_view.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    vehicle_goods_list_view.setHideable(false);
                    vehicle_goods_list_view.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    vehicleGoodsList_ivFullScreen.setImageResource(R.drawable.ic_baseline_zoom_out_map_24);
                    GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(true);
                }
                else{
                    vehicle_goods_list_view.setState(BottomSheetBehavior.STATE_EXPANDED);
                    vehicleGoodsList_ivFullScreen.setImageResource(R.drawable.ic_baseline_fullscreen_24);
                    GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(false);
                }
                vehicleList_good_toggle =! vehicleList_good_toggle;
            }
        });

        vehicleList_toggle = false;
        vehicleList_ivFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vehicleAutoListLayout.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    vehicleAutoListLayout.setHideable(false);
                    vehicleAutoListLayout.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    vehicleList_ivFullScreen.setImageResource(R.drawable.ic_baseline_zoom_out_map_24);
                    GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(true);
                }
                else{
                    vehicleAutoListLayout.setState(BottomSheetBehavior.STATE_EXPANDED);
                    vehicleList_ivFullScreen.setImageResource(R.drawable.ic_baseline_fullscreen_24);
                    GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(false);
                }
                vehicleList_toggle =! vehicleList_toggle;
            }
        });

        driverDetails_toggle = false;
        driverDetails_ivFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView)findViewById(R.id.driverDetailsBltnSheetClose)).setImageResource(R.drawable.ic_baseline_expand_less_24);
                if(driverDetailsLayout.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    driverDetailsLayout.setHideable(false);
                    driverDetailsLayout.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    driverDetailsLayout.setFitToContents(false);
                    driverDetailsLayout.setHalfExpandedRatio((float)DRIVER_DETAILS_BOTTOMSHEET_MIN_HEIGHT/findViewById(R.id.driverDetailsBottomSheet).getMeasuredHeight());
                    driverDetailsLayout.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                    driverDetails_ivFullScreen.setImageResource(R.drawable.ic_baseline_zoom_out_map_24);
                    GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(true);
                }
                else{
                    driverDetailsLayout.setFitToContents(false);
                    driverDetailsLayout.setState(BottomSheetBehavior.STATE_EXPANDED);
                    driverDetailsLayout.setHalfExpandedRatio((float)DRIVER_DETAILS_BOTTOMSHEET_MAX_HEIGHT/findViewById(R.id.driverDetailsBottomSheet).getMeasuredHeight());
                    driverDetailsLayout.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                    driverDetails_ivFullScreen.setImageResource(R.drawable.ic_baseline_fullscreen_24);
                    GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(false);
                }
                driverDetails_toggle =! driverDetails_toggle;
            }
        });

        List<LocationModel> stopList = GpsLocationService.getInstance(MainActivity.this).getStopLocationLatLng();

        SelectedStopsAdapter selectedStopsAdapter =  new SelectedStopsAdapter(stopList,GpsLocationService.getInstance(MainActivity.this).getDistance(),GpsLocationService.getInstance(MainActivity.this).getTimes(),this);
        rvSltstopLocation.setHasFixedSize(false);
        rvSltstopLocation.setLayoutManager(new LinearLayoutManager(this));
        rvSltstopLocation.setAdapter(selectedStopsAdapter);

        driverDetails_rvSltstopLocation.setHasFixedSize(false);
        driverDetails_rvSltstopLocation.setLayoutManager(new LinearLayoutManager(this));
        driverDetails_rvSltstopLocation.setAdapter(selectedStopsAdapter);

        for (int i = 0;i<GpsLocationService.getInstance(MainActivity.this).getDistance().size();i++) {
            CalculateTotalDistance(GpsLocationService.getInstance(MainActivity.this).getDistance().get(i).toString());
        }

        for (int j = 0;j<GpsLocationService.getInstance(MainActivity.this).getTimes().size();j++) {
            CalculateTotalTime(GpsLocationService.getInstance(MainActivity.this).getTimes().get(j));
        }

        try {
            String time = GpsLocationService.getInstance(MainActivity.this).getTimes().get(GpsLocationService.getInstance(MainActivity.this).getTimes().size() - 1);
            String arrTime[] = time.split(" ");

            try {
                time = "";
                for (int i = 0; i < arrTime.length; i++) {
                    if (i % 2 != 0) {
                        if (arrTime[i].equals("hour") || arrTime[i].equals("hours")) {
                            time += " h ";
                        } else {
                            time += " m ";
                        }
                    } else {
                        time += arrTime[i];
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            tvDeLocationDistance.setText(GpsLocationService.getInstance(MainActivity.this).getDistance().get(GpsLocationService.getInstance(MainActivity.this).getDistance().size()-1));
            tvDeLocationTimes.setText(time);

            driverDetails_tvDeLocationDistance.setText(GpsLocationService.getInstance(MainActivity.this).getDistance().get(GpsLocationService.getInstance(MainActivity.this).getDistance().size()-1));
            driverDetails_tvDeLocationTimes.setText(time);
            loadingDialog.dismissLoadingDialog();
        }catch (Exception e){
            e.printStackTrace();
        }

        MaterialTextView tvSelectedDestLocation =  findViewById(R.id.tvSelectedDestLocation);
        MaterialTextView driverDetails_tvSelectedDestLocation =  driverDetailsRouteDetails.findViewById(R.id.tvSelectedDestLocation);
        LocationModel pickup =  GpsLocationService.getInstance(this).getPickupLocationLatLng();
        LocationModel desti =  GpsLocationService.getInstance(this).getDestiLocationLatLng();
        List<LocationModel> listway =  GpsLocationService.getInstance(this).getStopLocationLatLng();

        tvSelectedPickupLocation.setText(pickup.getStrLocationName());
        driverDetails_tvSelectedPickupLocation.setText(pickup.getStrLocationName());


        tvSelectedDestLocation.setText(desti.getStrLocationName());
        driverDetails_tvSelectedDestLocation.setText(desti.getStrLocationName());

        findViewById(R.id.destAddReceiverDetailIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiverDetailsDialogInitialize(findViewById(R.id.destAddReceiverDetailIcon));
            }
        });

    }

    public void drawableToBitMap(Context context, int drawable, int widthPixels, int heightPixels) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/", "drawable.png");
            FileOutputStream fOut = new FileOutputStream(file);
            Drawable drw = ResourcesCompat.getDrawable(context.getResources(), drawable, null);
            if (drw != null) {
                convertToBitmap(drw, widthPixels, heightPixels).compress(Bitmap.CompressFormat.PNG, 100, fOut);
            }
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap bitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);
        return bitmap;
    }

    public void setupTabbedView(){
        TabLayout travelTypeTabbed = findViewById(R.id.travelTypeTabbed);
        ViewPager  viewPager = (ViewPager) findViewById(R.id.travelypeViewPager);
        travelTypeTabbed.setupWithViewPager(viewPager);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        TravelViewPagerAdapter adapter = new TravelViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PasangerVehicleFragment(), "Passanger");
        adapter.addFragment(new GoodsVehicleFragment(), "Goods");
        viewPager.setAdapter(adapter);

        MaterialButton btnpsngrAutoFind =  findViewById(R.id.btnpsngrAutoFind);
        btnpsngrAutoFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBidding = false;
                switchLayout(AUTO_TYPE_VIEW);
            }
        });

        findViewById(R.id.btnAutoFind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBidding = false;
                switchLayout(BIDDING_TYPE_VIEW);
            }
        });

        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_confirm_layout);
        ((TextView)dialog.findViewById(R.id.dialog_confirm_msg)).setText("Are you sure you want to put this booking on bid?");
        dialog.findViewById(R.id.confirmDialog_cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        MaterialButton btnpsngrBiddingFind =  findViewById(R.id.btnpsngrBiddingFind);
        btnpsngrBiddingFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                dialog.findViewById(R.id.confirmDialog_okButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        isBidding = true;
                        switchLayout(AUTO_TYPE_VIEW);
                    }
                });

            }
        });
        findViewById(R.id.btnBiddingFind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                dialog.findViewById(R.id.confirmDialog_okButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        isBidding = true;
                        switchLayout(BIDDING_TYPE_VIEW);
                    }
                });
            }
        });

        findViewById(R.id.iv_goods_unit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_unit_popup);
                dialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);
                dialog.show();

                dialog.findViewById(R.id.unit_dialog_cut_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.unit_select_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((MaterialRadioButton)dialog.findViewById(R.id.unit_kg)).isChecked()) {
                            ((MaterialTextView)findViewById(R.id.mtv_unit)).setText("KG");
                        } else if (((MaterialRadioButton)dialog.findViewById(R.id.unit_l)).isChecked()) {
                            ((MaterialTextView)findViewById(R.id.mtv_unit)).setText("L");
                        } else if (((MaterialRadioButton)dialog.findViewById(R.id.unit_ton)).isChecked()) {
                            ((MaterialTextView)findViewById(R.id.mtv_unit)).setText("TON");
                        }
                        dialog.dismiss();
                    }
                });
            }
        });
    }


    public void setAutoVehiclelist(){

//        LinearLayout llPaymentOption =  findViewById(R.id.llPaymentOption);
        MaterialTextView llPaymentOption =  findViewById(R.id.llPaymentOption);
        llPaymentOption.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                paymentOptionLayout.setHideable(true);
                paymentOptionLayout.setState(BottomSheetBehavior.STATE_EXPANDED);
                findViewById(R.id.paymentClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        paymentOptionLayout.setHideable(true);
                        paymentOptionLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                });
                return false;
            }
        });

        findViewById(R.id.vehicleList_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.show();
            }
        });

        findViewById(R.id.apply_coupon_option).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.tv_coupon_apply).setVisibility(View.VISIBLE);
                findViewById(R.id.couponEditText).setVisibility(View.VISIBLE);
                findViewById(R.id.apply_coupon_cut_btn).setVisibility(View.VISIBLE);

                findViewById(R.id.apply_coupon_cut_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.tv_coupon_apply).setVisibility(View.GONE);
                        findViewById(R.id.couponEditText).setVisibility(View.GONE);
                        findViewById(R.id.apply_coupon_cut_btn).setVisibility(View.GONE);
                    }
                });
            }
        });



        confirmPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                paymentOptionLayout.setHideable(true);
                paymentOptionLayout.setState(BottomSheetBehavior.STATE_HIDDEN);

                if (googlePay.isChecked()) {
                    paymentMethod = "GooglePay";
                    llPaymentOption.setText(paymentMethod);
                } else if (paytm.isChecked()) {
                    paymentMethod = "Paytm";
                    llPaymentOption.setText(paymentMethod);
                } else if (phonepe.isChecked()) {
                    paymentMethod = "PhonePe";
                    llPaymentOption.setText(paymentMethod);
                }
            }
        });

        findViewById(R.id.llBike).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoVehicleListTabSelect(findViewById(R.id.llBike),findViewById(R.id.tvBike));
            }
        });

        findViewById(R.id.llTaktak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoVehicleListTabSelect(findViewById(R.id.llTaktak),findViewById(R.id.tvTaktak));
            }
        });

        findViewById(R.id.llTaxi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoVehicleListTabSelect(findViewById(R.id.llTaxi),findViewById(R.id.tvTaxi));
            }
        });

        findViewById(R.id.llPickup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoVehicleListTabSelect(findViewById(R.id.llPickup),findViewById(R.id.tvPickup));
            }
        });

        findViewById(R.id.llMiniBus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoVehicleListTabSelect(findViewById(R.id.llMiniBus),findViewById(R.id.tvMiniBus));
            }
        });

        findViewById(R.id.llSuv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoVehicleListTabSelect(findViewById(R.id.llSuv),findViewById(R.id.tvSuv));
            }
        });

        RecyclerView rvVehicleList =  findViewById(R.id.rvVehicleList);

        VehicleModel model =  new VehicleModel();
        model.setIntId(1);
        model.setIntMaxLoad(1000);
        model.setStrVehicleName("Maruti Car");
        model.setStrVehicleType("Car");
        ArrayList<VehicleModel> models = new ArrayList<>();
        models.add(model);

        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);

        VehicleAdapter adapter = new VehicleAdapter(models,isBidding);
        rvVehicleList.setLayoutManager(new LinearLayoutManager(this));
        rvVehicleList.setHasFixedSize(false);
        rvVehicleList.setAdapter(adapter);
    }

    public void setBiddingVehiclelist(){

//        LinearLayout llPaymentOption =  findViewById(R.id.llGoodsPaymentOption);
        MaterialTextView llPaymentOption =  findViewById(R.id.llGoodsPaymentOption);
        llPaymentOption.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                paymentOptionLayout.setHideable(true);
                paymentOptionLayout.setState(BottomSheetBehavior.STATE_EXPANDED);
                findViewById(R.id.paymentClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        paymentOptionLayout.setHideable(true);
                        paymentOptionLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                });
                return false;
            }
        });

        findViewById(R.id.vehicleGoodList_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.show();
            }
        });

        findViewById(R.id.apply_coupon_option).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.tv_coupon_apply).setVisibility(View.VISIBLE);
                findViewById(R.id.couponEditText).setVisibility(View.VISIBLE);
                findViewById(R.id.apply_coupon_cut_btn).setVisibility(View.VISIBLE);

                findViewById(R.id.apply_coupon_cut_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.tv_coupon_apply).setVisibility(View.GONE);
                        findViewById(R.id.couponEditText).setVisibility(View.GONE);
                        findViewById(R.id.apply_coupon_cut_btn).setVisibility(View.GONE);
                    }
                });
            }
        });

        confirmPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                paymentOptionLayout.setHideable(true);
                paymentOptionLayout.setState(BottomSheetBehavior.STATE_HIDDEN);

                if (googlePay.isChecked()) {
                    paymentMethod = "GooglePay";
                    llPaymentOption.setText(paymentMethod);
                } else if (paytm.isChecked()) {
                    paymentMethod = "Paytm";
                    llPaymentOption.setText(paymentMethod);
                } else if (phonepe.isChecked()) {
                    paymentMethod = "PhonePe";
                    llPaymentOption.setText(paymentMethod);
                }
            }
        });

        findViewById(R.id.goods_llBike).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BiddingVehicleListTabSelect(findViewById(R.id.goods_llBike),findViewById(R.id.goods_tvBike));
            }
        });

        findViewById(R.id.goods_llTaktak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BiddingVehicleListTabSelect(findViewById(R.id.goods_llTaktak),findViewById(R.id.goods_tvTaktak));
            }
        });

        findViewById(R.id.goods_llTaxi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BiddingVehicleListTabSelect(findViewById(R.id.goods_llTaxi),findViewById(R.id.goods_tvTaxi));
            }
        });

        findViewById(R.id.goods_llPickup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BiddingVehicleListTabSelect(findViewById(R.id.goods_llPickup),findViewById(R.id.goods_tvPickup));
            }
        });

        findViewById(R.id.goods_llMiniBus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BiddingVehicleListTabSelect(findViewById(R.id.goods_llMiniBus),findViewById(R.id.goods_tvMiniBus));
            }
        });

        findViewById(R.id.goods_llSuv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BiddingVehicleListTabSelect(findViewById(R.id.goods_llSuv),findViewById(R.id.goods_tvSuv));
            }
        });

        RecyclerView rvVehicleList =  findViewById(R.id.rvGoodsVehicleList);
        VehicleModel model =  new VehicleModel();
        model.setIntId(1);
        model.setIntMaxLoad(1000);
        model.setStrVehicleName("Maruti Car");
        model.setStrVehicleType("Car");
        ArrayList<VehicleModel> models = new ArrayList<>();
        models.add(model);

        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);

        VehicleGoodsAdapter adapter = new VehicleGoodsAdapter(models,isBidding);
        rvVehicleList.setLayoutManager(new LinearLayoutManager(this));
        rvVehicleList.setHasFixedSize(false);
        rvVehicleList.setAdapter(adapter);

    }

    public void finishRide(){
//        findViewById(R.id.btnFinishRide).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dialog dialog =  new Dialog(MainActivity.this);
//                dialog.setContentView(R.layout.dialog_rating);
//                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                dialog.show();
//
//            }
//        });

        findViewById(R.id.driverDetails_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},PHONE_CALL_REQUEST);
                } else {
                    startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"1234567890")));
                }
            }
        });

        findViewById(R.id.driverDetails_landMark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverDetailsLayout.setHideable(true);
                driverDetailsLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                ratingBottomSheet.setHideable(false);
                ratingBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);

                findViewById(R.id.ratingBottomSheetCutBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ratingBottomSheet.setHideable(true);
                        ratingBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                        switchLayout(DRIVER_DETAILS_VIEW);
                    }
                });

                findViewById(R.id.rating_tvViewInvoice).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, BookingDetailsActivity.class));
                    }
                });

            }
        });

        findViewById(R.id.driverDetails_cancelTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingCancelReason.setHideable(true);
                bookingCancelReason.setState(BottomSheetBehavior.STATE_EXPANDED);

                findViewById(R.id.bookingCancelReasonClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bookingCancelReason.setHideable(true);
                        bookingCancelReason.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                });

                findViewById(R.id.bookingCancelReason_dontCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bookingCancelReason.setHideable(true);
                        bookingCancelReason.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                });

                findViewById(R.id.bookingCancelReason_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bookingCancelReason.setHideable(true);
                        bookingCancelReason.setState(BottomSheetBehavior.STATE_HIDDEN);

                        bookingCancelled.setHideable(true);
                        bookingCancelled.setState(BottomSheetBehavior.STATE_EXPANDED);

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

        findViewById(R.id.driverDetails_changePaymentMethod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentOptionLayout.setHideable(true);
                paymentOptionLayout.setState(BottomSheetBehavior.STATE_EXPANDED);
                findViewById(R.id.paymentClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        paymentOptionLayout.setHideable(true);
                        paymentOptionLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                });
            }
        });

    }

    public void AutoVehicleListTabSelect(LinearLayout linearLayout,TextView textView) {
        findViewById(R.id.llBike).setBackgroundResource(0);
        findViewById(R.id.llTaktak).setBackgroundResource(0);
        findViewById(R.id.llTaxi).setBackgroundResource(0);
        findViewById(R.id.llPickup).setBackgroundResource(0);
        findViewById(R.id.llMiniBus).setBackgroundResource(0);
        findViewById(R.id.llSuv).setBackgroundResource(0);

        ((TextView)findViewById(R.id.tvBike)).setTextColor(getResources().getColor(R.color.heading_color));
        ((TextView)findViewById(R.id.tvTaktak)).setTextColor(getResources().getColor(R.color.heading_color));
        ((TextView)findViewById(R.id.tvTaxi)).setTextColor(getResources().getColor(R.color.heading_color));
        ((TextView)findViewById(R.id.tvPickup)).setTextColor(getResources().getColor(R.color.heading_color));
        ((TextView)findViewById(R.id.tvMiniBus)).setTextColor(getResources().getColor(R.color.heading_color));
        ((TextView)findViewById(R.id.tvSuv)).setTextColor(getResources().getColor(R.color.heading_color));

        linearLayout.setBackgroundResource(R.drawable.primary_color_bottom_border);
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    public void BiddingVehicleListTabSelect(LinearLayout linearLayout,TextView textView) {
        findViewById(R.id.goods_llBike).setBackgroundResource(0);
        findViewById(R.id.goods_llTaktak).setBackgroundResource(0);
        findViewById(R.id.goods_llTaxi).setBackgroundResource(0);
        findViewById(R.id.goods_llPickup).setBackgroundResource(0);
        findViewById(R.id.goods_llMiniBus).setBackgroundResource(0);
        findViewById(R.id.goods_llSuv).setBackgroundResource(0);

        ((TextView)findViewById(R.id.goods_tvBike)).setTextColor(getResources().getColor(R.color.heading_color));
        ((TextView)findViewById(R.id.goods_tvTaktak)).setTextColor(getResources().getColor(R.color.heading_color));
        ((TextView)findViewById(R.id.goods_tvTaxi)).setTextColor(getResources().getColor(R.color.heading_color));
        ((TextView)findViewById(R.id.goods_tvPickup)).setTextColor(getResources().getColor(R.color.heading_color));
        ((TextView)findViewById(R.id.goods_tvMiniBus)).setTextColor(getResources().getColor(R.color.heading_color));
        ((TextView)findViewById(R.id.goods_tvSuv)).setTextColor(getResources().getColor(R.color.heading_color));

        linearLayout.setBackgroundResource(R.drawable.primary_color_bottom_border);
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    public void startOverlay() {
        startService(new Intent(MainActivity.this, FloatingWindow.class));
    }

    private void requestPermission(int requestCode){
        Intent overlayIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        overlayIntent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(overlayIntent, requestCode);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (driverDetailsLayout.getState() != BottomSheetBehavior.STATE_HIDDEN) {
            driverDetailsLayout.setHideable(true);
            driverDetailsLayout.setState(BottomSheetBehavior.STATE_HIDDEN);
            ratingBottomSheet.setHideable(true);
            ratingBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
            bookingCancelReason.setHideable(true);
            bookingCancelReason.setState(BottomSheetBehavior.STATE_HIDDEN);
            bookingCancelled.setHideable(true);
            bookingCancelled.setState(BottomSheetBehavior.STATE_HIDDEN);
            findViewById(R.id.btnback).setVisibility(View.GONE);


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
                GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(true);
            }
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        if (!isInPictureInPictureMode) {
            driverDetailsLayout.setHideable(false);
            driverDetailsLayout.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
            findViewById(R.id.btnback).setVisibility(View.VISIBLE);
//            GpsLocationService.getInstance(MainActivity.this).getZoomLinster().zoom(false);
        }
    }

    public void CalculateTotalTime(String time) {
        int hour=0,min=0,temp=0; /* "6 hours 29 min"*/

        Log.d("{shweta}", "CalculateTotalTime: "+time);
        time=time.replace("hours","h");
        temp = time.indexOf('h') == -1 ? 0 : time.indexOf('h')+1;

        Log.d("{shweta}", "CalculateTotalTime temp: "+temp);
        time=time.replace("mins","m");
        if (temp > 0) {
            Log.d("{shweta}", "CalculateTotalTime: "+time.substring(0,time.indexOf('h')));
            hour = Integer.parseInt(time.substring(0,time.indexOf('h')).trim());

        }

        min = Integer.parseInt(time.substring(temp,time.indexOf('m')).trim());
        Log.d("{shweta}", "CalculateTotalTimemin: "+min);

        totalHours = (totalHours + hour + ((totalMins + min)/60));
        totalMins = (totalMins + min) % 60;

        ((MaterialTextView)findViewById(R.id.tvTotalTimes)).setText(totalHours+" h "+totalMins+" m");
        ((MaterialTextView)driverDetailsRouteDetails.findViewById(R.id.tvTotalTimes)).setText(totalHours+" h "+totalMins+" m");
    }

    public void CalculateTotalDistance(String distance) {
        float dis = 0;

        dis = Float.parseFloat(distance.substring(0,distance.indexOf('k')).trim());

        totalDistance = totalDistance + dis;

        ((MaterialTextView)findViewById(R.id.tvTotalDistance)).setText(totalDistance+" km");
        ((MaterialTextView)driverDetailsRouteDetails.findViewById(R.id.tvTotalDistance)).setText(totalDistance+" km");
    }


    @Override
    public void ReceiverDetailsDialogInitialize(ImageView addReceiverDetailsIcon) {
        Dialog receiverDetailsDialog = new Dialog(MainActivity.this);
        receiverDetailsDialog.setContentView(R.layout.dialog_receiver_details_layout);
        receiverDetailsDialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);
        receiverDetailsDialog.show();

        receiverDetailsDialog.findViewById(R.id.receiverDetailsCutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiverDetailsDialog.dismiss();
            }
        });

        receiverDetailsDialog.findViewById(R.id.receiverDetails_SubmitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((TextInputEditText)receiverDetailsDialog.findViewById(R.id.receiverDetails_name)).getText().toString().trim().equals("")) {
                    ((TextInputEditText)receiverDetailsDialog.findViewById(R.id.receiverDetails_name)).setError("Mandatory");
                } else if (((TextInputEditText)receiverDetailsDialog.findViewById(R.id.receiverDetails_mobileNumber)).getText().toString().trim().length() != 10) {
                    ((TextInputEditText)receiverDetailsDialog.findViewById(R.id.receiverDetails_mobileNumber)).requestFocus();
                    ((TextInputEditText)receiverDetailsDialog.findViewById(R.id.receiverDetails_mobileNumber)).setError("Invalid Number");
                } else {
                    receiverDetailsDialog.dismiss();
                    addReceiverDetailsIcon.setImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                }
            }
        });
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.START | ItemTouchHelper.END |ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT , 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(stopList,fromPosition,toPosition);
            rvSltstopLocation.getAdapter().notifyItemMoved(fromPosition,toPosition);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    public void BookingForDialogInitialize() {
        Dialog bookingForDialog = new Dialog(MainActivity.this);
        bookingForDialog.setContentView(R.layout.dialog_booking_for_layout);
        bookingForDialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);
        bookingForDialog.show();

        bookingForDialog.findViewById(R.id.bookingForCutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingForDialog.dismiss();
            }
        });

        bookingForDialog.findViewById(R.id.bookingForSomeoneElse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingForDialog.findViewById(R.id.bookingFor_nameMobileNoLayout).setVisibility(View.VISIBLE);
            }
        });

        bookingForDialog.findViewById(R.id.bookingFor_SubmitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((CheckBox)bookingForDialog.findViewById(R.id.bookingForMyself)).isChecked()
                    && (((TextInputEditText)bookingForDialog.findViewById(R.id.nameMobileNo_name)).getText().toString().trim().equals("")
                        && ((TextInputEditText)bookingForDialog.findViewById(R.id.nameMobileNo_mobileNumber)).getText().toString().length() == 0)) {
                    ((Button)bookingForDialog.findViewById(R.id.bookingFor_SubmitBtn)).requestFocus();
                    ((Button)bookingForDialog.findViewById(R.id.bookingFor_SubmitBtn)).setError("");
                    Toast.makeText(MainActivity.this, "Please Choose Atleast One Option", Toast.LENGTH_LONG).show();
                } else if (!((CheckBox)bookingForDialog.findViewById(R.id.bookingForMyself)).isChecked()
                        && (((TextInputEditText)bookingForDialog.findViewById(R.id.nameMobileNo_name)).getText().toString().trim().equals(""))) {
                    ((TextInputEditText)bookingForDialog.findViewById(R.id.nameMobileNo_name)).requestFocus();
                    ((TextInputEditText)bookingForDialog.findViewById(R.id.nameMobileNo_name)).setError("Mandatory");
                } else if (!((CheckBox)bookingForDialog.findViewById(R.id.bookingForMyself)).isChecked()
                        && ((TextInputEditText)bookingForDialog.findViewById(R.id.nameMobileNo_mobileNumber)).getText().toString().length() != 10) {
                    ((TextInputEditText)bookingForDialog.findViewById(R.id.nameMobileNo_mobileNumber)).requestFocus();
                    ((TextInputEditText)bookingForDialog.findViewById(R.id.nameMobileNo_mobileNumber)).setError("Invalid Mobile Number");
                } else {
                    bookingForDialog.dismiss();
                    if (((CheckBox)bookingForDialog.findViewById(R.id.bookingForMyself)).isChecked()) {
                        ((TextView)findViewById(R.id.booking_for)).setText("Myself");
                    } else {
                        ((TextView)findViewById(R.id.booking_for)).setText("Other");
                    }
                }
            }
        });
    }
}