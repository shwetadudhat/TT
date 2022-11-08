package com.technlogi.tt.user.ui.pendinglist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technlogi.tt.R;
import com.technlogi.tt.driver.adapter.CancelledListAdapter;
import com.technlogi.tt.user.adapters.PendingListAdapter;
import com.technlogi.tt.user.models.BookingDetailsModel;
import com.technlogi.tt.user.models.DriversDetailsModel;
import com.technlogi.tt.user.models.LocationModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserPendingList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserPendingList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rvPendingList;

    public UserPendingList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserPendingList.
     */
    // TODO: Rename and change types and number of parameters
    public static UserPendingList newInstance(String param1, String param2) {
        UserPendingList fragment = new UserPendingList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPendingList =  view.findViewById(R.id.user_rvPendingList);

        DriversDetailsModel detailsModel = new DriversDetailsModel();
        detailsModel.setStrVehicleNo("BID1234");
        BookingDetailsModel bookingDetailsModel =  new BookingDetailsModel();
        bookingDetailsModel.setStrDateTime("02/02/2021 04:20");
        bookingDetailsModel.setStrCost("");
        bookingDetailsModel.setDriverDetails(detailsModel);
        LocationModel pickup = new LocationModel();
        pickup.setStrLocationName("Rourkela");
        LocationModel destination =  new LocationModel();
        destination.setStrLocationName("Lathikata");
        bookingDetailsModel.setPickup(pickup);
        bookingDetailsModel.setDestination(destination);
        ArrayList<BookingDetailsModel> history = new  ArrayList();
        history.add(bookingDetailsModel);
        PendingListAdapter adapter = new PendingListAdapter(getContext(),history);
        rvPendingList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPendingList.setHasFixedSize(false);
        rvPendingList.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_pending_list, container, false);
    }
}