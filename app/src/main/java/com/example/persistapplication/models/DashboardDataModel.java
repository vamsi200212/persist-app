package com.example.persistapplication.models;

import com.google.gson.annotations.SerializedName;

public class DashboardDataModel {

    @SerializedName("transID")
    private int transID;

    @SerializedName("transactionID")
    private String transactionID;

    @SerializedName("vehicleID")
    private String vehicleID;

    @SerializedName("deviceID")
    private String deviceID;

    @SerializedName("ambientTemp")
    private int ambientTemp;

    @SerializedName("latitude")
    private int latitude;

    @SerializedName("longitude")
    private int longitude;

    @SerializedName("acX")
    private int acX;

    @SerializedName("acY")
    private int acY;

    @SerializedName("acZ")
    private int acZ;

    @SerializedName("gyX")
    private int gyX;

    @SerializedName("gyY")
    private int gyY;

    @SerializedName("gyZ")
    private int gyZ;

    @SerializedName("pitch")
    private int pitch;

    @SerializedName("roll")
    private int roll;

    @SerializedName("yaw")
    private int yaw;

    // Add getters and setters if needed

    public int getTransID() {
        return transID;
    }

    public void setTransID(int transID) {
        this.transID = transID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public int getAmbientTemp() {
        return ambientTemp;
    }

    public void setAmbientTemp(int ambientTemp) {
        this.ambientTemp = ambientTemp;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getAcX() {
        return acX;
    }

    public void setAcX(int acX) {
        this.acX = acX;
    }

    public int getAcY() {
        return acY;
    }

    public void setAcY(int acY) {
        this.acY = acY;
    }

    public int getAcZ() {
        return acZ;
    }

    public void setAcZ(int acZ) {
        this.acZ = acZ;
    }

    public int getGyX() {
        return gyX;
    }

    public void setGyX(int gyX) {
        this.gyX = gyX;
    }

    public int getGyY() {
        return gyY;
    }

    public void setGyY(int gyY) {
        this.gyY = gyY;
    }

    public int getGyZ() {
        return gyZ;
    }

    public void setGyZ(int gyZ) {
        this.gyZ = gyZ;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public int getYaw() {
        return yaw;
    }

    public void setYaw(int yaw) {
        this.yaw = yaw;
    }


    // Add other getters and setters for other fields
}
