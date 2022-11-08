package com.technlogi.tt.user.adapters;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.technlogi.tt.R;
import com.technlogi.tt.gps.directionhelpers.DeleteListner;
import com.technlogi.tt.user.interfaces.LocationDetailsClickLisnter;
import com.technlogi.tt.user.models.LocationModel;

import java.util.ArrayList;
import java.util.List;

public class SelectedStopsHorizAdapter extends RecyclerView.Adapter<SelectedStopsHorizAdapter.SelectedStopsViewHolder> {
    private List<LocationModel> locationModels =  new ArrayList<>();
    private ArrayList<String> distances = new ArrayList<>();
    private ArrayList<String> times = new ArrayList<>();
    private LocationDetailsClickLisnter actionListner;
    private SelectedStopsViewHolder selectedStopsViewHolder;
    private DeleteListner deleteListner;

    public SelectedStopsHorizAdapter(List<LocationModel> locationModels, ArrayList<String> distances,
                                     ArrayList<String> times) {
        this.locationModels = locationModels;
        this.distances =  distances;
        this.times = times;
    }
    public SelectedStopsHorizAdapter ActionListner(LocationDetailsClickLisnter actionListner){
        this.actionListner  = actionListner;
        return this;
    }

    @NonNull
    @Override
    public SelectedStopsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_selecter_horizon_stops,parent,false);
        return new SelectedStopsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedStopsViewHolder holder, int position) {

            holder.tvLocationHorizPosition.setText(Integer.toString(position+1));
            if(locationModels.get(position).isSeletedForStops()){
                actionListner.onClick(position+1);
                if(selectedStopsViewHolder != null){
                    selectedStopsViewHolder.imgLocationHorizPosition.setBackgroundResource(R.drawable.stop_icon_circle);
                }
                selectedStopsViewHolder = holder;
                selectedStopsViewHolder.imgLocationHorizPosition.setBackgroundResource(R.drawable.stops_icon_image);
            }
            holder.tvLocationHorizPosition.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    actionListner.onClick(position+1);
                    if(selectedStopsViewHolder != null){
                        selectedStopsViewHolder.imgLocationHorizPosition.setBackgroundResource(R.drawable.stop_icon_circle);
                    }
                    selectedStopsViewHolder = holder;
                    selectedStopsViewHolder.imgLocationHorizPosition.setBackgroundResource(R.drawable.stops_icon_image);
                    return false;
                }
            });

    }

    @Override
    public int getItemCount() {
        return locationModels.size();
    }

    class SelectedStopsViewHolder extends RecyclerView.ViewHolder{
        private MaterialTextView tvLocationHorizPosition;
        private ImageView imgLocationHorizPosition;

        public SelectedStopsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocationHorizPosition = itemView.findViewById(R.id.tvLocationHorizPosition);
            imgLocationHorizPosition = itemView.findViewById(R.id.imgLocationHorizPosition);

        }
    }
}
