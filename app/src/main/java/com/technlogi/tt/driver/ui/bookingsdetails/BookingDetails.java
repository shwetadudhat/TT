package com.technlogi.tt.driver.ui.bookingsdetails;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.technlogi.tt.R;
import com.technlogi.tt.common.utils.ClickEvent;
import com.technlogi.tt.user.adapters.BookingDetailsAdapter;
import com.technlogi.tt.user.models.BookingDetailsModel;
import com.technlogi.tt.user.models.DriversDetailsModel;
import com.technlogi.tt.user.models.LocationModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.technlogi.tt.Constant.HIDE_VIEW;

public class BookingDetails extends Fragment {
    private RecyclerView rvBookingHistory;
    private ImageView rideDetailsBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvBookingHistory =  view.findViewById(R.id.rvRideHistory);
        rideDetailsBack =  view.findViewById(R.id.rideDetailsBack);
        rideDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickEvent.getInstance().getClickEvent().onBackFrag();
            }
        });

        DriversDetailsModel detailsModel = new DriversDetailsModel();
        detailsModel.setStrVehicleNo("DL01H0123");
        BookingDetailsModel bookingDetailsModel =  new BookingDetailsModel();
        bookingDetailsModel.setStrDateTime("02/02/2021 04:20");
        bookingDetailsModel.setStrCost("Rs.1000");
        bookingDetailsModel.setDriverDetails(detailsModel);
        LocationModel pickup = new LocationModel();
        pickup.setStrLocationName("Rourkela");
        LocationModel destination =  new LocationModel();
        destination.setStrLocationName("Lathikata");
        bookingDetailsModel.setPickup(pickup);
        bookingDetailsModel.setDestination(destination);
        ArrayList<BookingDetailsModel> history = new  ArrayList();
        history.add(bookingDetailsModel);
        BookingDetailsAdapter adapter = new BookingDetailsAdapter(history);
        rvBookingHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBookingHistory.setHasFixedSize(false);
        rvBookingHistory.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ClickEvent.getInstance().getClickEvent().onChangeFrag(HIDE_VIEW);
        View v = inflater.inflate(R.layout.fragment_ride_detials, container, false);

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    if (keyCode == KeyEvent.KEYCODE_BACK){
                        return true;
                    }
                }
                return false;
            }
        });

        return v;
    }

}
