package com.example.gyu.whoareyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class new_settings extends AppCompatActivity implements Serializable {

    Settings_Object settings_object;
    int passwordCount = -1;
    Switch Emailswitch;
    boolean sendEmail;
    boolean saveCondition_passwordCount = true;
    boolean saveCondition_Email = true;
    boolean saveCondition_Password = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_settings);
        setSwitchListener();
        setRadioGroupListener();
        setApplyButtonListener();

    }

    private void setApplyButtonListener (){
        Button Apply = (Button)findViewById(R.id.Apply_new);
        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
                printSettings();
                if(saveCondition_passwordCount && saveCondition_Password && saveCondition_Email) {
                    saveSettingsToFile();
                    Intent intent = new Intent (new_settings.this, Password_chk.class);
                    intent.putExtra("Settings", settings_object);
                    startActivity(intent);
                }
            }
        });
    }

    private void printSettings(){
        System.out.println("Password Count : " + settings_object.getPasswordCount());
        System.out.println("Email : " + settings_object.getEmail());
        System.out.println("Email Send Enable : " + settings_object.getEmailEnable());
        System.out.println("Password : " + settings_object.getPassword());
        System.out.println("Save Condition Password Count : " + saveCondition_passwordCount);
        System.out.println("Save Condition Email : " + saveCondition_Email);
        System.out.println("Save Condition Password : " + saveCondition_Password);

    }

    private void saveSettings(){
        settings_object = new Settings_Object();

        //setPasswordcnt
        if (passwordCount == -1){
            Toast.makeText(getApplication(), "Click the Password Count", Toast.LENGTH_SHORT).show();
            saveCondition_passwordCount = false;
        } else {
            settings_object.setPasswordCount(passwordCount);
            saveCondition_passwordCount = true;
        }

        //setEmail
        settings_object.setEmailEnable(sendEmail);
        if(isEmailValid()) {
            settings_object.setEmail(getEmail());
            saveCondition_Email = true;
        } else {
            saveCondition_Email = false;
        }

        //setPassword
        if(!getNewPassword().toString().equals("") && !getNewPasswordChk().toString().equals("")) {
            if (isNewPasswordEqual()) {
                settings_object.setPassword(getNewPassword());
                saveCondition_Password = true;
            } else {
                Toast.makeText(getApplication(), "new Password is not Equal", Toast.LENGTH_SHORT).show();
                saveCondition_Password = false;
            }
        } else {
            Toast.makeText(getApplication(), "Enter the Password", Toast.LENGTH_SHORT).show();
            saveCondition_Password = false;
        }


    }
    private String getEmail(){
        EditText editText = (EditText) findViewById(R.id.setEmail_new);
        return editText.getText().toString();
    }

    private Boolean isEmailValid(){
        if(sendEmail == true && getEmail().toString().equals("")){
            //Toast.makeText(getApplication(), "Email check Condition 1", Toast.LENGTH_SHORT).show();
            return false;
        } else if (sendEmail == true && !getEmail().toString().equals("")){
           // Toast.makeText(getApplication(), "Email check Condition 2", Toast.LENGTH_SHORT).show();
            return true;
        } else if (sendEmail == false && !getEmail().toString().equals("")){
           // Toast.makeText(getApplication(), "Email check Condition 3", Toast.LENGTH_SHORT).show();
            return true;
        } else if (sendEmail == false && getEmail().toString().equals("")){
          //  Toast.makeText(getApplication(), "Email check Condition 4", Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(getApplication(), "Email check error", Toast.LENGTH_SHORT).show();
        return false;
    }

    private String getNewPassword(){
        EditText editText = (EditText)findViewById(R.id.newPassword_new);
        return editText.getText().toString();
    }

    private String getNewPasswordChk(){
        EditText editText = (EditText)findViewById(R.id.newPasswordChk_new);
        return editText.getText().toString();
    }

    private boolean isNewPasswordEqual(){
        return getNewPassword().equals(getNewPasswordChk());
    }

    private void saveSettingsToFile(){
        try{
            ObjectOutputStream oos =new ObjectOutputStream(new FileOutputStream(getExternalFilesDir(null) + File.separator + "settings.WRY"));
            oos.writeObject(settings_object);
            oos.close();
            System.out.println("new_Settings Class - saveSettingsToFile method call : Save Setting to File Success");
        } catch (Exception e) {
            System.out.println("new Settings Class - saveSettingsToFile method call : Exception");
        }
    }
    private void setSwitchListener (){
        Emailswitch = (Switch) findViewById(R.id.EmailSwitch_new);
        Emailswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sendEmail = true;
                    Toast.makeText(getApplication(), "Send Email Enabled " + sendEmail, Toast.LENGTH_SHORT).show();
                } else {
                    sendEmail = false;
                    Toast.makeText(getApplication(),"Send Email Disabled "+ sendEmail, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void setRadioGroupListener (){
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup_new);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.PasswordOption1_new:
                        passwordCount = 1;
                        break;

                    case R.id.PasswordOption2_new:
                        passwordCount = 2;
                        break;

                    case R.id.PasswordOption3_new:
                        passwordCount = 3;
                        break;

                    case R.id.PasswordOption4_new:
                        passwordCount = 4;
                        break;
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
