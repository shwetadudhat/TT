package com.technlogi.tt.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technlogi.tt.R;
import com.technlogi.tt.user.models.DriversDetailsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DriverAllotmentAdapter extends RecyclerView.Adapter<DriverAllotmentAdapter.DriverAllotmentViewHolder> {

    private Context context;
    private ArrayList<DriversDetailsModel> list = new ArrayList<>();

    public DriverAllotmentAdapter (Context context,ArrayList<DriversDetailsModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DriverAllotmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_allotment_layout,parent,false);
        return new DriverAllotmentAdapter.DriverAllotmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverAllotmentViewHolder holder, int position) {
        holder.driverName.setText(list.get(position).getStrDriverName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DriverAllotmentViewHolder extends RecyclerView.ViewHolder {

        private TextView driverName;

        public DriverAllotmentViewHolder(@NonNull View itemView) {
            super(itemView);

            driverName = itemView.findViewById(R.id.drAllotment_driverName);
        }
    }
}
