package com.example.persistapplication.models;

public class RegistrationModel {
    String UserName, Email, Password;
    int IsActive;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getActive() {
        return IsActive;
    }

    public void setActive(int active) {
        IsActive = active;
    }
}
