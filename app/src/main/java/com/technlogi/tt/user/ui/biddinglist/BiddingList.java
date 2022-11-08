package com.technlogi.tt.user.ui.biddinglist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.technlogi.tt.R;
import com.technlogi.tt.common.utils.ClickEvent;
import com.technlogi.tt.user.adapters.BiddingListAdapter;
import com.technlogi.tt.user.models.BookingDetailsModel;
import com.technlogi.tt.user.models.LocationModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.technlogi.tt.Constant.HIDE_VIEW;

public class BiddingList extends Fragment {

    private RecyclerView rvBiddingList;
    private ImageView biddinglistBack;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvBiddingList =  view.findViewById(R.id.rvBiddingList);
        biddinglistBack =  view.findViewById(R.id.biddingListBack);

        biddinglistBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickEvent.getInstance().getClickEvent().onBackFrag();
            }
        });

        BookingDetailsModel bookingDetailsModel = new BookingDetailsModel();
        bookingDetailsModel.setStrDateTime("02/02/2021 04:20");
        LocationModel pickup = new LocationModel();
        pickup.setStrLocationName("Rourkela");
        LocationModel destination =  new LocationModel();
        destination.setStrLocationName("Lathikata");
        bookingDetailsModel.setPickup(pickup);
        bookingDetailsModel.setDestination(destination);
        ArrayList<BookingDetailsModel> list = new ArrayList<>();
        list.add(bookingDetailsModel);
        BiddingListAdapter biddingListAdapter = new BiddingListAdapter(getContext(),list);
        rvBiddingList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBiddingList.setHasFixedSize(false);
        rvBiddingList.setAdapter(biddingListAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ClickEvent.getInstance().getClickEvent().onChangeFrag(HIDE_VIEW);
        return inflater.inflate(R.layout.fragment_bidding_list, container, false);
    }


}
