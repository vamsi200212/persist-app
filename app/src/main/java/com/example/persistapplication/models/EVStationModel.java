package com.example.persistapplication.models;

import com.google.gson.annotations.SerializedName;

public class EVStationModel {
    @SerializedName("id")
    int Id;

    @SerializedName("name")
    String Name;

    @SerializedName("latitude")
    double Latitude;

    @SerializedName("longitude")
    double Longitude;

    @SerializedName("address")
    String Address;

    @SerializedName("city")
    String City;

    @SerializedName("state")
    String State;

    @SerializedName("country")
    String Country;

    @SerializedName("imageLink")
    String ImageLink;

    public EVStationModel() {

    }

    public EVStationModel(int id, String name, double latitude, double longitude, String address, String city, String state, String country, String imageLink) {
        Id = id;
        Name = name;
        Latitude = latitude;
        Longitude = longitude;
        Address = address;
        City = city;
        State = state;
        Country = country;
        ImageLink = imageLink;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getImageLink() {
        return ImageLink;
    }

    public void setImageLink(String imageLink) {
        ImageLink = imageLink;
    }
}
