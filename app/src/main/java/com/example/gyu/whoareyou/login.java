package com.example.gyu.whoareyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class login extends AppCompatActivity implements Serializable{
    Settings_Object settings_object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Intent intent=new Intent(login.this,Password_chk.class);
        //startActivity(intent);
        //setContentView(R.layout.activity_settings);
        loadSettings();
        if(settings_object != null){
            Intent intent = new Intent(login.this, Password_chk.class);
            intent.putExtra("Settings", settings_object);
            startActivity(intent);
        } else if (settings_object == null){
            Intent intent = new Intent(login.this, new_settings.class);
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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


    private void loadSettings() {

        try {
            Settings_Object temp;
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getExternalFilesDir(null) + File.separator + "settings.WRY"));
            temp = (Settings_Object) ois.readObject();
            settings_object = temp;
            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("login Class - saveSettings method call : FileNotFoundException, Sweep Settings Activity");
            //Intent intent=new Intent(login.this,Settings.class);
            //Settings_Object nullSettings = null;
            //intent.putExtra("Settings", nullSettings);
            //startActivity(intent);
        } catch (Exception e) {
            System.out.println("login Class - saveSettings method call : Exception, print Stack Trace");
        }
    }
}
