package com.example.persistapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.persistapplication.R;
import com.example.persistapplication.databinding.ActivityMainBinding;
import com.example.persistapplication.fragments.DashboardFragment;
import com.example.persistapplication.fragments.EVStationsFragment;
import com.example.persistapplication.fragments.ProfileFragment;
import com.example.persistapplication.fragments.SpeedometerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    static BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setElevation(0);
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame,new DashboardFragment(),"DashboardFragment")
                .addToBackStack("DashboardFragment")
                .commit();

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.home_btm);
        navigationView = binding.navView;

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

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout,R.string.open,R.string.close);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.your_action_bar_color)));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.navView.setCheckedItem(R.id.home_mn);
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home_mn) {
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame, new DashboardFragment(), "DashboardFragment")
                            .addToBackStack("DashboardFragment")
                            .commit();
                    return true;
                } else if (itemId == R.id.ev_stations_mn) {
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame, new EVStationsFragment(), "EVStationsFragment")
                            .addToBackStack("EVStationsFragment")
                            .commit();
                    return true;
                } else if (itemId == R.id.profile_mn) {
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame, new ProfileFragment(), "ProfileFragment")
                            .addToBackStack("ProfileFragment")
                            .commit();
                    return true;
                } else if (itemId == R.id.speed_mn) {
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame, new SpeedometerFragment(), "SpeedometerFragment")
                            .addToBackStack("SpeedometerFragment")
                            .commit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
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

}