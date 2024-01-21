package com.example.persistapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.persistapplication.R;
import com.example.persistapplication.activities.LoginActivity;
import com.example.persistapplication.activities.MainActivity;
import com.example.persistapplication.databinding.FragmentProfileBinding;
public class ProfileFragment extends Fragment {

    SharedPreferences.Editor editor;
    AppCompatButton logoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        logoutBtn = view.findViewById(R.id.logout_btn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Deleting Login Info in Shared Preferences
                if(getContext()!=null&&getContext().getSharedPreferences("login", Context.MODE_PRIVATE)!=null){
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                    if(sharedPreferences!=null){
                        editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                        if(getActivity()!=null) getActivity().finishAffinity();
                    }
                }

            }
        });

        return view;
    }
}