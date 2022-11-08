package com.technlogi.tt.driver.ui.booking;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.technlogi.tt.R;
import com.technlogi.tt.common.utils.ClickEvent;
import com.technlogi.tt.driver.adapter.BookingFragmentAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import static com.technlogi.tt.Constant.HIDE_VIEW;

public class Booking extends Fragment {

    private ImageView backBtn;

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private BookingFragmentAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backBtn = (ImageView) view.findViewById(R.id.dr_bookingBackBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickEvent.getInstance().getClickEvent().onBackFrag();
            }
        });

        tabLayout = (TabLayout) view.findViewById(R.id.dr_booking_TabLayout);
        viewPager2 = (ViewPager2) view.findViewById(R.id.dr_booking_ViewPager);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        adapter = new BookingFragmentAdapter(fm,getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Bidding"));
        tabLayout.addTab(tabLayout.newTab().setText("Pending"));
        tabLayout.addTab(tabLayout.newTab().setText("Cancelled"));
        tabLayout.addTab(tabLayout.newTab().setText("Completed"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ClickEvent.getInstance().getClickEvent().onChangeFrag(HIDE_VIEW);
        View v = inflater.inflate(R.layout.fragment_driver_booking_fragement, container, false);

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    if (keyCode == KeyEvent.KEYCODE_BACK){
                        ClickEvent.getInstance().getClickEvent().onBackFrag();
                        return true;
                    }
                }
                return false;
            }
        });

        return v;
    }
}
