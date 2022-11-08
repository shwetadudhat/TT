package com.technlogi.tt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InvitesListAdapter extends RecyclerView.Adapter<InvitesListAdapter.InvitesListViewHolder> {

    ArrayList<InviteModel> list = new ArrayList<>();

    public InvitesListAdapter (ArrayList<InviteModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public InvitesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_invites_card_layout,parent,false);
        return new InvitesListAdapter.InvitesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvitesListViewHolder holder, int position) {

        holder.name.setText(list.get(position).getName());
        holder.details.setText(list.get(position).getDetails());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class InvitesListViewHolder extends RecyclerView.ViewHolder {

        private TextView name,details;

        public InvitesListViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.invites_name);
            details = itemView.findViewById(R.id.invites_details);
        }
    }
}
