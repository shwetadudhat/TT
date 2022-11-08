package com.technlogi.tt.user.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.technlogi.tt.R;


public class PasangerVehicleFragment extends Fragment {



    public PasangerVehicleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PasangerVehicleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PasangerVehicleFragment newInstance(String param1, String param2) {
        PasangerVehicleFragment fragment = new PasangerVehicleFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pasanger_vehicle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView tvPassangerMinus =  view.findViewById(R.id.tvPassangerMinus);
        tvPassangerMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(((TextInputEditText)view.findViewById(R.id.etNoOfPassanger)).getText().toString())>1){
                    ((TextInputEditText)view.findViewById(R.id.etNoOfPassanger)).setText (Integer.toString(Integer.parseInt(((TextInputEditText)view.findViewById(R.id.etNoOfPassanger)).getText().toString())-1));
                }
            }
        });


        ImageView tvPassangerPlus =  view.findViewById(R.id.tvPassangerPlus);
        tvPassangerPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextInputEditText)view.findViewById(R.id.etNoOfPassanger)).setText (Integer.toString(Integer.parseInt(((TextInputEditText)view.findViewById(R.id.etNoOfPassanger)).getText().toString())+1));
            }
        });


        ImageView tvBagsMinus =  view.findViewById(R.id.tvBagsMinus);
        tvBagsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(((TextInputEditText)view.findViewById(R.id.etNoOfBags)).getText().toString())>0){
                    ((TextInputEditText)view.findViewById(R.id.etNoOfBags)).setText (Integer.toString(Integer.parseInt(((TextInputEditText)view.findViewById(R.id.etNoOfBags)).getText().toString())-1));
                }
            }
        });

        ImageView tvBagsPlus =  view.findViewById(R.id.tvBagsPlus);
        tvBagsPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextInputEditText)view.findViewById(R.id.etNoOfBags)).setText (Integer.toString(Integer.parseInt(((TextInputEditText)view.findViewById(R.id.etNoOfBags)).getText().toString())+1));
            }
        });

    }
}