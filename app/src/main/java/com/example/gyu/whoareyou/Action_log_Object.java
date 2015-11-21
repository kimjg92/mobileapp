package com.example.gyu.whoareyou;

import android.app.Activity;

import java.io.Serializable;

/**
 * Created by GYU on 2015-11-08.
 */
public class Action_log_Object extends Activity implements Serializable {
    Picture_Object picture_object;
    Location_Object location_object;

    public Action_log_Object (){
        picture_object = new Picture_Object();
        location_object = new Location_Object();
    }

}


