package com.example.persistapplication.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.persistapplication.R;
import com.example.persistapplication.models.DashboardDataModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    DashboardDataModel model;
    TextView tempVal,latitude,longitude,acx,acy,acz,gyx,gyy,gyz,pitch,roll,yaw;
    CardView allParaLay,allParaCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

//        binding = FragmentDashboardBinding.inflate(getLayoutInflater());
        allParaLay = view.findViewById(R.id.all_para_lay);
        allParaCard = view.findViewById(R.id.all_para_card);
        tempVal = view.findViewById(R.id.temp_val);
        latitude = view.findViewById(R.id.latitude);
        longitude = view.findViewById(R.id.longitude);
        acx = view.findViewById(R.id.acx);
        acy = view.findViewById(R.id.acy);
        acz = view.findViewById(R.id.acz);
        gyx = view.findViewById(R.id.gyx);
        gyy = view.findViewById(R.id.gyy);
        gyz = view.findViewById(R.id.gyz);
        pitch = view.findViewById(R.id.pitch);
        roll = view.findViewById(R.id.roll);
        yaw = view.findViewById(R.id.yaw);

        model = new DashboardDataModel();
        getDataFromDB();

//        model.setLongitude(1.234f);
//        model.setLatitude(1.234f);
//        model.setAcX(23);
//        model.setAcY(12);
//        model.setAcZ(23);
//        model.setGyX(12);
//        model.setGyY(23);
//        model.setGyZ(12);
//        model.setAmbientTemp(123);
//        model.setPitch(456);
//        model.setRoll(678);
//        model.setYaw(2837);

        allParaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(allParaLay.getVisibility()==View.GONE){
                    allParaLay.setVisibility(View.VISIBLE);
                }else{
                    allParaLay.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

    public void getDataFromDB() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8082/")  // Adjusted base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetDataApi api = retrofit.create(GetDataApi.class);

        Call<DashboardDataModel> call = api.getData(1);

        call.enqueue(new Callback<DashboardDataModel>() {
            @Override
            public void onResponse(Call<DashboardDataModel> call, Response<DashboardDataModel> response) {
                if (response.isSuccessful()) {
                    model = response.body();
//                    Log.d("TEST API",model.getDeviceID()+ " " + model.getTransID());
//                    Toast.makeText(getContext(), model.getTransID() + "", Toast.LENGTH_LONG).show();
                    setDataToViews();
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(getContext(), "Request Failed: " + response.code(), Toast.LENGTH_LONG).show();
                    Log.e("API Error", "Code: " + response.code() + ", Message: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<DashboardDataModel> call, Throwable t) {
                // Handle failure
                Toast.makeText(getContext(), "Request Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API Error", "Failure: " + t.getMessage());
            }

        });
    }

    public void setDataToViews(){
        tempVal.setText(model.getAmbientTemp()+"");
        latitude.setText("Latitude : "+model.getLatitude());
        longitude.setText("Longitude : "+model.getLongitude());
        acx.setText("AcX : "+model.getAcX());
        acy.setText("AcY : "+model.getAcY());
        acz.setText("AcZ : "+model.getAcZ());
        gyx.setText("GyX : "+model.getGyX());
        gyy.setText("GyY : "+model.getGyY());
        gyz.setText("GyZ : "+model.getGyZ());
        pitch.setText("Pitch : "+model.getPitch());
        roll.setText("Roll : "+model.getRoll());
        yaw.setText("Yaw : "+model.getYaw());
    }

    public interface GetDataApi {
        @GET("api/VD/TransID")  // Adjusted path
        Call<DashboardDataModel> getData(@Query("TransID") int transid);
    }
}