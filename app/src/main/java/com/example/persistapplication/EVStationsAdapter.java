package com.example.persistapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.BinderThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.persistapplication.databinding.ItemEvStationsBinding;
import com.example.persistapplication.fragments.MapsFragment;
import com.example.persistapplication.models.EVStationModel;

import java.util.ArrayList;
import java.util.List;

public class EVStationsAdapter extends RecyclerView.Adapter<EVStationsAdapter.MyViewHolder> {

    List<EVStationModel> list;
    Context context;

    public EVStationsAdapter(List<EVStationModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public EVStationsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ev_stations,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EVStationsAdapter.MyViewHolder holder, int position) {
        EVStationModel model = list.get(position);

        String add = model.getAddress()+", "+model.getCity()+", "+model.getState()+", "+model.getCountry();

        holder.name.setText(model.getName());
        holder.address.setText(add);

        Glide.with(context).load(model.getImageLink()).into(holder.pic);

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                        Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
//                startActivity(intent);
                Bundle bundle = new Bundle();
                bundle.putString("latKey",model.getLatitude()+"");
                bundle.putString("lngKey",model.getLongitude()+"");
                MapsFragment mapsFragment = new MapsFragment();
                mapsFragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame,mapsFragment,"MapsFragment").addToBackStack("MapsFragment").commit();
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                progressDialog.setMessage("Maps Loading...");
//                progressDialog.setTitle();
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                },2000);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,address;
        ImageView pic;
        AppCompatButton btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ev_name);
            address = itemView.findViewById(R.id.address);
            pic = itemView.findViewById(R.id.station_img);
            btn = itemView.findViewById(R.id.get_dir_btn);
        }
    }
}
