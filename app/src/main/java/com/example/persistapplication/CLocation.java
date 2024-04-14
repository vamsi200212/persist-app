package com.example.persistapplication;

import android.location.Location;

import androidx.annotation.NonNull;

public class CLocation extends Location {
    private boolean bUseMetricUnits = false;

    public CLocation(Location location){
        this(location,true);
    }

    public CLocation(Location location,boolean bUseMetricUnits){
        super(location);
        this.bUseMetricUnits = bUseMetricUnits;
    }

    public boolean getUseMetricUnits(){
        return this.bUseMetricUnits;
    }

    public void setUseMetricUnits(boolean bUseMetricUnits){
        this.bUseMetricUnits = bUseMetricUnits;
    }

    @Override
    public float distanceTo(@NonNull Location dest) {
        float nDistance = super.distanceTo(dest);
        if(!this.getUseMetricUnits()){
            nDistance = nDistance * 3.28083989501312f;
        }
        return nDistance;
    }

    @Override
    public float getAccuracy() {
        float nAccuracy = super.getAccuracy();
        if(!this.getUseMetricUnits()){
            nAccuracy = nAccuracy * 3.28083989501312f;
        }
        return nAccuracy;
    }

    @Override
    public double getAltitude() {
        double nAltitude = super.getAltitude();
        if(!this.getUseMetricUnits()){
            nAltitude = nAltitude * 3.28083989501312d;
        }
        return nAltitude;
    }

    @Override
    public float getSpeed() {
        float nSpeed = super.getSpeed() * 3.6f;
        if(!this.getUseMetricUnits()){
            //Convert meters/second to Miles/Hour
//            nSpeed = super.getSpeed() * 2.23693629f;
        }
        return nSpeed;
    }
}
