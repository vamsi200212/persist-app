package com.example.persistapplication.models;

import com.google.gson.annotations.SerializedName;

public class RegistrationModel {

    @SerializedName("ID")
    private int ID;
    @SerializedName("UserName")
    private String UserName;
    @SerializedName("Password")
    private String Password;
    @SerializedName("Email")
    private String Email;
    @SerializedName("IsActive")
    private int IsActive;

    public RegistrationModel(){

    }

    public RegistrationModel(int id, String userName, String password, String email, int isActive) {
        this.ID = id;
        this.UserName = userName;
        this.Password = password;
        this.Email = email;
        this.IsActive = isActive;
    }
    public RegistrationModel( String userName, String password, String email, int isActive) {
        this.UserName = userName;
        this.Password = password;
        this.Email = email;
        this.IsActive = isActive;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getIsActive() {
        return IsActive;
    }

    public void setIsActive(int isActive) {
        IsActive = isActive;
    }
}
