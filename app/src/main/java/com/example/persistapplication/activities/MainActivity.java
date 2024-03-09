package com.example.persistapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
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

import com.example.persistapplication.R;
import com.example.persistapplication.databinding.ActivityMainBinding;
import com.example.persistapplication.fragments.DashboardFragment;
import com.example.persistapplication.fragments.EVStationsFragment;
import com.example.persistapplication.fragments.ProfileFragment;
import com.example.persistapplication.fragments.SpeedometerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {

    ActivityMainBinding binding;
    static BottomNavigationView bottomNavigationView;
    Dialog speedometer;

    TextView speedTextView;
    ProgressBar speedProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                .replace(R.id.frame,new DashboardFragment(),"DashboardFragment")
                .addToBackStack("DashboardFragment")
                .commit();

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.home_btm);

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

    static int flag = 0;
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
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
        }
        Toast.makeText(getApplicationContext(), "Waiting for GPS connection!", Toast.LENGTH_SHORT).show();
    }



    private void showBottomSheet(float nCurrentSpeed) {
        if (!speedometer.isShowing()) {
            speedometer.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            speedometer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            speedometer.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            speedometer.getWindow().setGravity(Gravity.BOTTOM);
            speedometer.setCanceledOnTouchOutside(false);

            // Set initial speed value
            updateSpeedUI(nCurrentSpeed);

            speedometer.show();
        } else {
            // Update the speed in the existing dialog
            updateSpeedUI(nCurrentSpeed);
        }
    }

    private void updateSpeedUI(float nCurrentSpeed) {
        if (speedTextView != null && speedProgressBar != null) {
            runOnUiThread(() -> {
                speedTextView.setText(String.format("%.2f", nCurrentSpeed) + " km/h");

                // Assuming max speed is set to 200 in the ProgressBar
                int progress = (int) Math.min(nCurrentSpeed, 100);
                speedProgressBar.setProgress(progress);
            });
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (location != null) {
            float nCurrentSpeed = location.getSpeed() * 3.6f; // Convert m/s to km/h
            Log.d("myLogs ","Speed : "+nCurrentSpeed+"");
            showBottomSheet(nCurrentSpeed);
        } else {
            // Handle the case when location is null
            // For example, if the location provider is disabled
        }
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);

    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
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
}