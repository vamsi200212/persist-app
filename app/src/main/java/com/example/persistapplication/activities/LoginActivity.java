package com.example.persistapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.persistapplication.ApiService;
import com.example.persistapplication.RetrofitClient;
import com.example.persistapplication.databinding.ActivityLoginBinding;
import com.example.persistapplication.models.LoginResponse;
import com.example.persistapplication.models.RegistrationModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    String email, password, username;
    SharedPreferences sharedPreferences;

    public static final String fileName = "login";
    public static final String Email = "email";
    public static final String Password = "password";
    public static final String UserName = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.235.71.201:86/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegistrationApi registrationApi = retrofit.create(RegistrationApi.class);

        binding.btnStartRiding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = binding.edtEmailAddress.getText().toString();
                password = binding.edtPassword.getText().toString();

                sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);

                RegistrationModel registrationModel = new RegistrationModel();
                registrationModel.setUserName("");
                registrationModel.setEmail(email);
                registrationModel.setPassword(password);
                registrationModel.setIsActive(1);

                Call<LoginResponse> call = registrationApi.loginUser(registrationModel);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse result = response.body();

                        if(result!=null&&result.getResponse().equals("Data Found")){
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Email,email);
                            editor.putString(Password,password);
                            editor.putString(UserName,username);
                            editor.apply();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finishAffinity();
                        }else if(result!=null&&result.getResponse().equals("Invalid User")){
                            binding.edtEmailAddress.setError("Invalid User or Password");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this,"Request Failed",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });

    }

    public interface RegistrationApi {
        @POST("api/Registration/login")
        Call<LoginResponse> loginUser(@Body RegistrationModel requestModel);
    }

}