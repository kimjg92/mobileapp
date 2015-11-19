package com.example.gyu.whoareyou;

import java.io.Serializable;

/**
 * Created by GYU on 2015-11-08.
 */
public class Picture_Object implements Serializable{
    String Picture_Path;

    public Picture_Object (){
        Picture_Path = "def";
    }

    public void set_Path(String string){
        Picture_Path = string;
        System.out.println("Picture_Object Class : Picture_Path Saved / at " + Picture_Path);
    }

    public String get_Path(){
        return Picture_Path;
    }
}
