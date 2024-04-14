package com.example.persistapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.persistapplication.CLocation;
import com.example.persistapplication.R;
import com.example.persistapplication.databinding.ActivityMainBinding;
import com.example.persistapplication.fragments.DashboardFragment;
import com.example.persistapplication.fragments.EVStationsFragment;
import com.example.persistapplication.fragments.ProfileFragment;
import com.example.persistapplication.fragments.SpeedometerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    ActivityMainBinding binding;
    static public BottomNavigationView bottomNavigationView;
    Dialog speedometer;

    TextView speedTextView;
    ProgressBar speedProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initial Setup
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        speedometer = new Dialog(this);
        speedometer.requestWindowFeature(Window.FEATURE_NO_TITLE);
        speedometer.setContentView(R.layout.bottom_sheet_layout);

        speedTextView = speedometer.findViewById(R.id.textView1);
        speedProgressBar = speedometer.findViewById(R.id.progressBar);

        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, new DashboardFragment(), "DashboardFragment")
                .addToBackStack("DashboardFragment")
                .commit();

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.home_btm);

        // Listener Setup for Bottom Navigation Bar
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_btm) {
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new DashboardFragment(), "DashboardFragment")
                        .addToBackStack("DashboardFragment")
                        .commit();
                return true;
            } else if (itemId == R.id.electric_btm) {
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new EVStationsFragment(), "EVStationsFragment")
                        .addToBackStack("EVStationsFragment")
                        .commit();
                return true;
            } else if (itemId == R.id.profile_btm) {
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new ProfileFragment(), "ProfileFragment")
                        .addToBackStack("ProfileFragment")
                        .commit();
                return true;
            } else if (itemId == R.id.speed_btm) {
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new SpeedometerFragment(), "SpeedometerFragment")
                        .addToBackStack("SpeedometerFragment")
                        .commit();
                return true;
            }
            return false;
        });

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.your_action_bar_color)));
        getSupportActionBar().setTitle("");

        // Bottom Sheet Dialog Implementation

        // Check permission
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {
            // Start the program if permission is granted
            doStuff();
        }
        this.updateSpeed(null);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // Check if the current fragment is the Dashboard
        if (getSupportFragmentManager().findFragmentById(R.id.frame) instanceof DashboardFragment) {
            // Exit the app
            finish();
        } else {
            // If not in the Dashboard, handle the back button as usual
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doStuff();
            } else {
                finish();
            }
        }
    }

    private void doStuff() {
        LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (lm != null) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0,this);
        }
        Toast.makeText(getApplicationContext(), "Waiting for GPS connection!", Toast.LENGTH_SHORT).show();
    }

    private void showBottomSheet(String nCurrentSpeed,float speed) {
        if (!speedometer.isShowing()) {
            speedometer.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            speedometer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            speedometer.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            speedometer.getWindow().setGravity(Gravity.BOTTOM);
            speedometer.setCanceledOnTouchOutside(false);

            // Set initial speed value
            if (speedTextView != null && speedProgressBar != null) {
                runOnUiThread(() -> {
                    speedTextView.setText(String.format("%.2f", speed) + " km/h");

                    // Assuming max speed is set to 200 in the ProgressBar
                    int progress = (int) Math.min(speed, 100);
                    speedProgressBar.setProgress(progress);
                });
            }
            speedometer.show();
        } else {
            // Update the speed in the existing dialog
            if (speedTextView != null && speedProgressBar != null) {
                runOnUiThread(() -> {
                    speedTextView.setText(String.format("%.2f", nCurrentSpeed) + " km/h");

                    // Assuming max speed is set to 200 in the ProgressBar
                    int progress = (int) Math.min(speed, 100);
                    speedProgressBar.setProgress(progress);
                });
            }
        }
    }

//    private void updateSpeedUI(float nCurrentSpeed) {
//        if (speedTextView != null && speedProgressBar != null) {
//            runOnUiThread(() -> {
//                speedTextView.setText(String.format("%.2f", nCurrentSpeed) + " km/h");
//
//                // Assuming max speed is set to 200 in the ProgressBar
//                int progress = (int) Math.min(nCurrentSpeed, 100);
//                speedProgressBar.setProgress(progress);
//            });
//        }
//    }
    private void updateSpeed(CLocation location){
        float nCurrentSpeed = 0;
        if(location!=null){
            location.setUseMetricUnits(this.useMetricUnits());
            nCurrentSpeed = location.getSpeed();
        }
        Formatter fmt = new Formatter(new StringBuilder());
        fmt.format(Locale.US,"%5.1f",nCurrentSpeed);
        String strCurrentSpeed = fmt.toString();
        strCurrentSpeed = strCurrentSpeed.replace(" ","0");

        if(this.useMetricUnits()){
            if(nCurrentSpeed>0){
                showBottomSheet(strCurrentSpeed,nCurrentSpeed);
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if(location!=null){
            CLocation myLocation = new CLocation(location,this.useMetricUnits());
            this.updateSpeed(myLocation);
        }
    }

    private boolean useMetricUnits(){
        return true;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

//    @Override
//    public void onLocationChanged(@NonNull Location location) {
//        if (location != null) {
//            float nCurrentSpeed = location.getSpeed() * 3.6f; // Convert m/s to km/h
//            Log.d("myLogs", "Speed : " + nCurrentSpeed + "");
//            if(nCurrentSpeed>0.0) showBottomSheet(nCurrentSpeed);
//            else if(nCurrentSpeed<=0.0&&speedometer!=null&&speedometer.isShowing()) speedometer.dismiss();
//        } else {
//            // Handle the case when location is null
//            // For example, if the location provider is disabled
//        }
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//        LocationListener.super.onStatusChanged(provider, status, extras);
//    }
//
//    @Override
//    public void onProviderEnabled(@NonNull String provider) {
//        LocationListener.super.onProviderEnabled(provider);
//    }
//
//    @Override
//    public void onProviderDisabled(@NonNull String provider) {
//        LocationListener.super.onProviderDisabled(provider);
//    }
}
