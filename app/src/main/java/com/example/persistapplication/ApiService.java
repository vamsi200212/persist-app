package com.example.persistapplication;

import com.example.persistapplication.models.EVStationModel;
import com.example.persistapplication.models.LoginResponse;
import com.example.persistapplication.models.RegistrationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/Registration/login")
    Call<LoginResponse> login(@Body RegistrationModel registrationModel);

    @GET("api/CStation")
    Call<List<EVStationModel>> getEVStations();
}
