package com.technlogi.tt.driver.adapter;

import com.technlogi.tt.driver.ui.biddinglist.DriverBiddingList;
import com.technlogi.tt.driver.ui.cancelledlist.DriverCancelledList;
import com.technlogi.tt.driver.ui.completedlist.DriverCompleteList;
import com.technlogi.tt.driver.ui.pendinglist.DriverPendingList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class BookingFragmentAdapter extends FragmentStateAdapter {
    public BookingFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1:
                return new DriverPendingList();
            case 2:
                return new DriverCancelledList();
            case 3:
                return new DriverCompleteList();
        }

        return new DriverBiddingList();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
