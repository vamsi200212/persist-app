package com.example.persistapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.example.persistapplication.R;
import com.example.persistapplication.databinding.ActivityLauncherBinding;

public class LauncherActivity extends AppCompatActivity {

    ActivityLauncherBinding binding;
    SharedPreferences sharedPreferences;

    public static final String fileName = "login";
    public static final String Email = "email";
    public static final String Password = "password";
    public static final String UserName = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initial Setup
        if(getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }

        binding = ActivityLauncherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.your_action_bar_color)));
        getSupportActionBar().setTitle("");

        sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);

        //Listener to Next Activity
        binding.btnStartRiding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = sharedPreferences.getString(Email, "");
                String password = sharedPreferences.getString(Password, "");
                String username = sharedPreferences.getString(UserName, "");

                if(email.equals("")&&password.equals("")&&username.equals("")){
                    startActivity(new Intent(LauncherActivity.this,LoginActivity.class));
                    finishAffinity();
                }
                else{
                    startActivity(new Intent(LauncherActivity.this, PermissionGrantActivity.class));
                    finishAffinity();
                }

            }
        });

    }
}