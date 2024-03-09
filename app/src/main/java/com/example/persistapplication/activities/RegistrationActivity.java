package com.example.persistapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.persistapplication.R;
import com.example.persistapplication.models.LoginResponse;
import com.example.persistapplication.models.RegistrationModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class RegistrationActivity extends AppCompatActivity {

    ImageView left;
    TextView already;
    Button signup;
    EditText edt_email,edt_pass,edt_confirm,edt_name;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    String username;

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

        setContentView(R.layout.activity_registration);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        already = findViewById(R.id.already_user_txt);
        signup = findViewById(R.id.appCompatButton);
        edt_name = findViewById(R.id.edtName);
        edt_email = findViewById(R.id.edtEmailAddress);
        edt_pass = findViewById(R.id.edtPassword);
        edt_confirm = findViewById(R.id.edtConfirmPassword);

        progressDialog = new ProgressDialog(this);
        sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();
            }
        });

//        left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });

        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }

    private void performAuth(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.235.71.201:86/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegistrationApi registrationApi = retrofit.create(RegistrationApi.class);

        String userName = edt_name.getText().toString();
        String email = edt_email.getText().toString();
        String password = edt_pass.getText().toString();
        String confirmPassword = edt_confirm.getText().toString();

        if(!email.matches(emailPattern)){
            edt_email.setError("Enter correct email!");
        }else if(password.isEmpty()||password.length()<6){
            edt_pass.setError("Enter proper password!");
        }else if(!password.equals(confirmPassword)){
            edt_confirm.setError("Password not matched!");
        }else{
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            RegistrationModel registrationModel = new RegistrationModel();
            registrationModel.setUserName(userName);
            registrationModel.setEmail(email);
            registrationModel.setPassword(password);
            registrationModel.setIsActive(1);

            Call<LoginResponse> call = registrationApi.loginUser(registrationModel);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse result = response.body();

                    if(result.getResponse().equals("Data Inserted")){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Email,email);
                        editor.putString(Password,password);
                        editor.putString(UserName,username);
                        editor.apply();
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                    }else if(result.getResponse().equals("Error")){
//                        edtEmailAddress.setError("Invalid User or Password");
                        progressDialog.dismiss();
                        Toast.makeText(RegistrationActivity.this,"Error while registering!",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(RegistrationActivity.this,"Request Failed",Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finishAffinity();
    }

    public interface RegistrationApi {
        @POST("api/Registration/registration")
        Call<LoginResponse> loginUser(@Body RegistrationModel requestModel);
    }
}