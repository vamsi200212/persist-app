package com.example.persistapplication.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("response")
    String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
