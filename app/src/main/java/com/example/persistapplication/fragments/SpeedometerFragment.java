package com.example.persistapplication.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.persistapplication.R;

public class SpeedometerFragment extends Fragment implements LocationListener{

    private TextView speedTextView;
    private ProgressBar speedProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_speedometer, container, false);
//
        speedTextView = view.findViewById(R.id.textView1);
        speedProgressBar = view.findViewById(R.id.progressBar);

        // Check permission
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {
            // Start the program if permission is granted
            doStuff();
        }


        return view;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) {
            updateSpeedUI(0f);
        } else {
            float nCurrentSpeed = location.getSpeed() * 3.6f; // Convert m/s to km/h
            updateSpeedUI(nCurrentSpeed);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doStuff();
            } else {
                getActivity().finish();
            }
        }
    }

    private void doStuff() {
        LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (lm != null) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
        }
        Toast.makeText(getContext(), "Waiting for GPS connection!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    private void updateSpeedUI(float speed) {
        speedTextView.setText(String.format("%.2f", speed) + " km/h");

        // Assuming max speed is set to 200 in the ProgressBar
        int progress = (int) Math.min(speed, 100);
        speedProgressBar.setProgress(progress);
    }
}