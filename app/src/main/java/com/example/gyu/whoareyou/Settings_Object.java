package com.example.gyu.whoareyou;

import java.io.Serializable;

/**
 * Created by GYU on 2015-11-22.
 */
public class Settings_Object implements Serializable{
    private int PasswordCount;
    private String Email;
    private boolean SendEmail;
    private String Password;


    public void setPasswordCount (int count){
        PasswordCount = count;
    }

    public int getPasswordCount (){
        return  PasswordCount;
    }

    public void setEmail (String email){
        Email = email;
    }

    public String getEmail(){
        return Email;
    }

    public void setEmailEnable (Boolean enable){
        SendEmail = enable;
    }

    public boolean getEmailEnable (){
        return SendEmail;
    }

    public void setPassword(String password){
        Password = password;
    }

    public String getPassword(){
        return Password;
    }

}
