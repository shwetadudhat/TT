package com.technlogi.tt.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.technlogi.tt.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.FacilityViewHolder> {

    private Context context;
    private ArrayList<String> facilityList;

    public FacilityAdapter (Context context,ArrayList<String> facilityList) {
        this.context = context;
        this.facilityList = facilityList;
    }

    @NonNull
    @Override
    public FacilityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_facility_card_layout,parent,false);
        return new FacilityAdapter.FacilityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacilityViewHolder holder, int position) {
        holder.facilityCardViewCheckbox.setText(facilityList.get(position).toString());
//        holder.facilityCardViewCheckbox.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_local_gas_station_24,0,?android:attr/listChoiceIndicatorMultiple,0);
    }

    @Override
    public int getItemCount() {
        return facilityList.size();
    }

    class FacilityViewHolder extends RecyclerView.ViewHolder {

        private CheckBox facilityCardViewCheckbox;

        public FacilityViewHolder(@NonNull View itemView) {
            super(itemView);

            facilityCardViewCheckbox = itemView.findViewById(R.id.facilityCardViewCheckbox);
        }
    }
}
