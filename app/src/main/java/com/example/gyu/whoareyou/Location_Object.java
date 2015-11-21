package com.example.gyu.whoareyou;

import android.support.v4.app.FragmentActivity;

import java.io.Serializable;

/**
 * Created by GYU on 2015-11-19.
 */

public class Location_Object extends FragmentActivity implements Serializable{
    private double latitude;
    private double longitude;
    public void setLocation(double lat, double lng){
        latitude = lat;
        longitude = lng;
    }
    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }

}
