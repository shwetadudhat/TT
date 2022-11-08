package com.technlogi.tt.user.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.technlogi.tt.R;
import com.technlogi.tt.user.models.LocationModel;

import java.util.ArrayList;
import java.util.List;

public class SelectedStopsAdapter extends RecyclerView.Adapter<SelectedStopsAdapter.SelectedStopsViewHolder> {
    private List<LocationModel> locationModels =  new ArrayList<>();
    private ArrayList<String> distances = new ArrayList<>();
    private ArrayList<String> times = new ArrayList<>();

    private ReceiverDetails receiverDetails;

    public interface ReceiverDetails {
//        public void CalculateTotalTime(String time);
//        public void CalculateTotalDistance(String distance);
        public void ReceiverDetailsDialogInitialize(ImageView addReceiverDetailsIcon);
    }

    public SelectedStopsAdapter(List<LocationModel> locationModels,ArrayList<String> distances,
    ArrayList<String> times,ReceiverDetails receiverDetails) {
        this.locationModels = locationModels;
        this.distances =  distances;
        this.times = times;
        this.receiverDetails = receiverDetails;
    }

    @NonNull
    @Override
    public SelectedStopsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_selecter_stops,parent,false);
        return new SelectedStopsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedStopsViewHolder holder, int position) {
            holder.tvAdLocationName.setText(locationModels.get(position).getStrLocationName());
//            holder.tvLocationPosition.setText(Integer.toString(locationModels.get(position).getIntPosition()));
        holder.tvLocationPosition.setText(Integer.toString(position+1));
            try {
                holder.tvAdLocationDistance.setText(distances.get(position));
//                bookingTimeAndDistance.CalculateTotalDistance(distances.get(position).toString());
                String time = times.get(position);
                String arrTime[] = time.split(" ");

                try {
                    time = "";
                    for (int i = 0; i < arrTime.length; i++) {
                        if (i % 2 != 0) {
                            if (arrTime[i].equals("hour") || arrTime[i].equals("hours")) {
                                time += " h ";
                            } else {
                                time += " m ";
                            }
                        } else {
                            time += arrTime[i];
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                holder.tvAdLocationTimes.setText(time);
//                bookingTimeAndDistance.CalculateTotalTime(time);
            }catch (Exception e){
                e.printStackTrace();
            }

            holder.addReceiverDetailsIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    receiverDetails.ReceiverDetailsDialogInitialize(holder.addReceiverDetailsIcon);
                }
            });

    }

    @Override
    public int getItemCount() {
        return locationModels.size();
    }

    class SelectedStopsViewHolder extends RecyclerView.ViewHolder{
        private MaterialTextView tvLocationPosition;
        private TextView tvAdLocationName;
        private TextView tvAdLocationDistance;
        private TextView tvAdLocationTimes;
        private ImageView addReceiverDetailsIcon;

        public SelectedStopsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocationPosition =  itemView.findViewById(R.id.tvLocationPosition);
            tvAdLocationName =  itemView.findViewById(R.id.tvAdLocationName);
            tvAdLocationDistance =  itemView.findViewById(R.id.tvAdLocationDistance);
            tvAdLocationTimes =  itemView.findViewById(R.id.tvAdLocationTimes);
            addReceiverDetailsIcon = itemView.findViewById(R.id.stopAddReceiverDetailIcon);
        }
    }
}
