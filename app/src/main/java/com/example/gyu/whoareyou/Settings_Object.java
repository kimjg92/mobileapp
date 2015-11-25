package com.example.gyu.whoareyou;

import java.io.Serializable;

/**
 * Created by GYU on 2015-11-22.
 */
public class Settings_Object implements Serializable{
    private boolean useApp;
    private int PasswordCount;
    private String Email;
    private String SenderEmail;
    private String SenderEmailPassword;
    private boolean SendEmail;
    private String Password;

    public void setUseApp (boolean use){
        useApp = use;
    }
    public boolean getUseApp(){
        return useApp;
    }
    public void setSenderEmail(String email){
        SenderEmail = email;
    }
    public String getSenderEmail(){
        return SenderEmail;
    }
    public void setSenderEmailPassword(String Password){
        SenderEmailPassword = Password;
    }
    public String getSenderEmailPassword(){
        return SenderEmailPassword;
    }

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
