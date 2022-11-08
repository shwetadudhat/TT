package com.technlogi.tt.user.adapters;

import com.technlogi.tt.user.ui.biddinglist.UserBiddingList;
import com.technlogi.tt.user.ui.cancelledlist.UserCancelledList;
import com.technlogi.tt.user.ui.completedlist.UserCompletedList;
import com.technlogi.tt.user.ui.pendinglist.UserPendingList;

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
                return new UserPendingList();
            case 2:
                return new UserCancelledList();
            case 3:
                return new UserCompletedList();
        }

        return new UserBiddingList();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
