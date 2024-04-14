package com.example.persistapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.persistapplication.R;
import com.example.persistapplication.activities.LoginActivity;
import com.example.persistapplication.activities.MainActivity;
import com.example.persistapplication.databinding.FragmentProfileBinding;
import com.example.persistapplication.models.LoginResponse;
import com.example.persistapplication.models.RegistrationModel;

import java.util.function.ObjIntConsumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class ProfileFragment extends Fragment {

    SharedPreferences.Editor editor;
    AppCompatButton logoutBtn;
    SharedPreferences sharedPreferences;

    public static final String fileName = "login";
    public static final String Email = "email";
    public static final String Password = "password";
    public static final String UserName = "username";
    TextView nameTxt, emailTxt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sharedPreferences = getActivity().getSharedPreferences(fileName,Context.MODE_PRIVATE);

        String name = sharedPreferences.getString(UserName,"");
        String email = sharedPreferences.getString(Email,"");
        String password = sharedPreferences.getString(Password,"");

        nameTxt = view.findViewById(R.id.name);
        nameTxt.setText(name);

        emailTxt = view.findViewById(R.id.mail);
        emailTxt.setText(email);



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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.235.71.201:86/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserDataApi getDataApi = retrofit.create(UserDataApi.class);

        Call<RegistrationModel> call = getDataApi.getData(email);

        call.enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {
                RegistrationModel result = response.body();

                if(result!=null){
                    nameTxt.setText(result.getUserName());
                }
            }

            @Override
            public void onFailure(Call<RegistrationModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Request Failed",Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    public interface UserDataApi {
        @GET("api/UserData/getData")
        Call<RegistrationModel> getData(@Query("email") String email);
    }
}