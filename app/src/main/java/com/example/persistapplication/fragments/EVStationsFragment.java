package com.example.persistapplication.fragments;

import static com.example.persistapplication.RetrofitClient.BASE_URL;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.persistapplication.ApiService;
import com.example.persistapplication.EVStationsAdapter;
import com.example.persistapplication.R;
import com.example.persistapplication.RetrofitClient;
import com.example.persistapplication.models.DashboardDataModel;
import com.example.persistapplication.models.EVStationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ObjIntConsumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class EVStationsFragment extends Fragment {


    RecyclerView recyclerView;
    List<EVStationModel> list;
    EVStationsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_e_v_stations, container, false);

        list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rec_view);

        getEVStations();
        return view;
    }

    public void getEVStations() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.235.71.201:86/")  // Adjusted base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetDataApi api = retrofit.create(GetDataApi.class);

        Call<List<EVStationModel>> call = api.getData();

        call.enqueue(new Callback<List<EVStationModel>>() {
            @Override
            public void onResponse(Call<List<EVStationModel>> call, Response<List<EVStationModel>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    adapter = new EVStationsAdapter(list,getContext());

                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setItemAnimator(null);

                    Log.d("myTag",list+"");
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(getContext(), "Request Failed: " + response.code(), Toast.LENGTH_LONG).show();
                    Log.e("API Error", "Code: " + response.code() + ", Message: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<EVStationModel>> call, Throwable t) {
                // Handle failure
                Toast.makeText(getContext(), "Request Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API Error", "Failure: " + t.getMessage());
            }

        });
    }

    public interface GetDataApi {
        @GET("api/CStation")  // Adjusted path
        Call<List<EVStationModel>> getData();
    }
}