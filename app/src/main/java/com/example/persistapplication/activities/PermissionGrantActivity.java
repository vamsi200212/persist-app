//package com.example.persistapplication.activities;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.view.View;
//import android.widget.Toast;
//
//
//
//import com.example.persistapplication.databinding.ActivityPermissionGrantBinding;
//
//public class PermissionGrantActivity extends AppCompatActivity {
//    ActivityPermissionGrantBinding binding;
//    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityPermissionGrantBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        if(getSupportActionBar()!=null){
//            getSupportActionBar().hide();
//        }
//
//        // Check if the app has location permissions
//        if (ContextCompat.checkSelfPermission(PermissionGrantActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            // If permissions are granted, start MainActivity
//
//            turnOnLocationServices();
//        } else {
//            // If permissions are not granted, request for them
//            ActivityCompat.requestPermissions(PermissionGrantActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
//        }
//
//        binding.givePermissionBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(binding.givePermissionBtn.getText().equals("Enable Location Services")) turnOnLocationServices();
//                else ActivityCompat.requestPermissions(PermissionGrantActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
//            }
//        });
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // If permission is granted, start MainActivity
//                turnOnLocationServices();
//            } else {
//                // If permission is denied, handle it accordingly (e.g., show a message or request again)
//                // You can show an explanation to the user here if needed
//                Toast.makeText(getApplicationContext(),"Give Necessary Permissions!",Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    public void turnOnLocationServices(){
//        // Check if location services are enabled
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//            // Location services are enabled, proceed with your logic
//            // For example, start MainActivity
//            startActivity(new Intent(PermissionGrantActivity.this, MainActivity.class));
//            finishAffinity();
//        } else {
//            // Location services are not enabled, prompt the user to enable them
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//            alertDialogBuilder.setMessage("Location services are disabled. Please enable location services to proceed.");
//            alertDialogBuilder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    // Open location settings
//                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                    startActivity(intent);
//                    ActivityCompat.requestPermissions(PermissionGrantActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
//                }
//            });
//            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    // Do nothing or handle the cancellation
//                    Toast.makeText(getApplicationContext(),"Turn on the Location Service!",Toast.LENGTH_LONG).show();
//                    binding.givePermissionBtn.setText("Enable Location Services");
//
//                }
//            });
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            alertDialog.show();
//        }
//
//    }
//}
package com.example.persistapplication.activities;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.example.persistapplication.databinding.ActivityPermissionGrantBinding;

public class PermissionGrantActivity extends AppCompatActivity {
    private ActivityPermissionGrantBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initial Setup
        binding = ActivityPermissionGrantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)&&(locationManager != null && (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)))) {
            startActivity(new Intent(PermissionGrantActivity.this,MainActivity.class));
            finishAffinity();
        }

        // Permissions Button
        binding.givePermissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
                    Toast.makeText(PermissionGrantActivity.this,"Locations Permissions are already given.",Toast.LENGTH_SHORT).show();
                }else{
                    ActivityCompat.requestPermissions(PermissionGrantActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                }
            }
        });

        // Locations Services Button
        binding.locServBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if location services are enabled
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if((locationManager != null && (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)))){
                    Toast.makeText(PermissionGrantActivity.this,"Location services are already turned on.",Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PermissionGrantActivity.this);
                    alertDialogBuilder.setMessage("Location services are disabled. Please enable location services to proceed.");
                    alertDialogBuilder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Open location settings
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    });
                    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing or handle the cancellation
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

        // Proceed Button
        binding.proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if ((ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)&&(locationManager != null && (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)))) {
                    startActivity(new Intent(PermissionGrantActivity.this,MainActivity.class));
                    finishAffinity();
                } else if((ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
                    Toast.makeText(PermissionGrantActivity.this,"Turn on location services.",Toast.LENGTH_SHORT).show();
                } else if((locationManager != null && (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)))){
                    Toast.makeText(PermissionGrantActivity.this,"Give Location Permissions.",Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(PermissionGrantActivity.this,"Turn on location services and give location permissions.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
