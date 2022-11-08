package com.technlogi.tt.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.technlogi.tt.R;
import com.technlogi.tt.user.models.DriversDetailsModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DriverAllotmentSpinnerAdapter extends ArrayAdapter<DriversDetailsModel> {

    private Context context;
    private List<DriversDetailsModel> list;

    private TextView driverName;

    public DriverAllotmentSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<DriversDetailsModel> objects) {
        super(context, resource, objects);

        this.context = context;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return DriverAllotmentSpinnerView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return DriverAllotmentSpinnerView(position, convertView, parent);
    }

    private View DriverAllotmentSpinnerView (int position, @Nullable View myView, @Nullable ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.driver_allotment_layout,parent,false);

//        driverName = customView.findViewById(R.id.drSpinner_driverName);
        driverName.setText(list.get(position).getStrDriverName());

        return customView;
    }
}
