package com.example.gyu.whoareyou;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GYU on 2015-11-25.
 */
public class ScreenLock extends AppCompatActivity implements Serializable{

    private frontCamera f_camera;
    private Settings_Object settings;

    private int password_error_count;
    private int password_error_max; // 후에 설정 값으로 수정할 것
    private boolean SendEmail;
    private String Email;
    private String my_password;// 후에 설정한 비밀번호로 수정할 것
    private String filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_chk);
        final EditText password = (EditText)findViewById(R.id.password);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        password_error_count = 1;


        loadSettingsFromFile();
        getSettings();

        //////////////////////////////
        //이전 인텐트에서 파일 경로 받아와 메일 보내는 부분
        filePath = getIntent().getStringExtra("Path");
        if(filePath != null) {
            if (!filePath.equals("null")) {
                sendGmail(filePath);
            }
        }
        ////////////////////////////////
        ///////////////////////////////////
        TextView settingPassword = (TextView)findViewById(R.id.showCurrentPassword);
        settingPassword.setText(my_password);
        //////////////////////////////////

        Button login = (Button)findViewById(R.id.login);
        Button.OnClickListener listener = new Button.OnClickListener() {

            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.login:
                        if(password_error_max == password_error_count && !my_password.equals(password.getText().toString())) {
                            f_camera  = new frontCamera(ScreenLock.this);
                            f_camera.captureCamera();
                            loadVectorObject();
                            saveInfo(f_camera.getPath());
                            saveVectorObject();
                            //password_error_count = 1;<- 계속틀리는경우 계속 카메라 촬영을 할것인가?
                            System.out.println("Password_chk class : action_log_object size : " + action_log_object.size());
                            Intent intent = new Intent(ScreenLock.this , ScreenLock.class);
                            intent.putExtra("Path", f_camera.getPath());
                            startActivity(intent);
                            //sendGmail(f_camera.getPath());
                        }
                        if(my_password.equals(password.getText().toString())) {
                            finish();
                        } else {
                            password_error_count++;
                        }
                        break;
                }
            }
        };
        login.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_password_chk, menu);
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

    private void sendGmail( String filePath ){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        GMailSender sender = new GMailSender(settings.getSenderEmail(), settings.getSenderEmailPassword()); // 설정파일에서 불러오기로 고치기
        System.out.println("지메일 센더 실행");

        GPS gps = new GPS(this);
        Location location = gps.getLocation();

        double lat = location.getLatitude();
        double lng = location.getLongitude();
        String mailContent = "위치정보 : https://www.google.com/maps?q="+lat+","+lng+"&hl=ko&gl=kr&shorturl=1";


        File file = new File (filePath);


        System.out.println("sendGmail Method - get File : " + file);
        try {
            sender.sendMail("Who Are You", mailContent, settings.getSenderEmail()+"@gmail.com", settings.getEmail(), file);

            System.out.println("메일 센더 호출");
        } catch (Exception e) {
            System.out.println("오류 파일을 찾을 수 없음 : " + file);
            e.printStackTrace();
        }

    }
    private void loadSettingsFromFile() {
        try {
            Settings_Object temp;
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getExternalFilesDir(null) + File.separator + "settings.WRY"));
            temp = (Settings_Object)ois.readObject();
            settings = temp;
            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("Password_chk Class - loadVectorObject Method : FileNotFoundException, create File (call CreateSaveData Method)");

        } catch (Exception e) {
            System.out.println("Password_chk Class - loadVectorObject Method : Exception, print Stack Trace");
            e.printStackTrace();
        }
    }
    private void getSettings (){
        // Intent intent = getIntent();
        // settings = (Settings_Object)intent.getExtras().getSerializable("Settings");
        password_error_max = settings.getPasswordCount();
        SendEmail = settings.getEmailEnable();
        if(SendEmail) {
            Email = settings.getEmail();
        }
        my_password = settings.getPassword();
    }

    ArrayList<Action_log_Object> action_log_object = new ArrayList<>();

    private void saveInfo(String path){
        Action_log_Object temp = new Action_log_Object();
        temp.picture_object.set_Path(path);

        GPS gps = new GPS(this);
        Location location = gps.getLocation();
        temp.location_object.setLocation(location.getLatitude(), location.getLongitude());
        action_log_object.add(temp);
    }

    private void saveVectorObject(){
        try{
            ObjectOutputStream oos =new ObjectOutputStream(new FileOutputStream(getExternalFilesDir(null) + File.separator + "save_data.WRY"));
            oos.writeObject(action_log_object);
            oos.close();
        } catch (Exception e) {
            System.out.println("Password_chk Class - saveVectorObject Method : Exception");
        }
    }

    private void loadVectorObject() {

        try {
            ArrayList<Action_log_Object> temp;
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getExternalFilesDir(null) + File.separator + "save_data.WRY"));
            temp = (ArrayList<Action_log_Object>) ois.readObject();
            action_log_object = temp;
            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("Password_chk Class - loadVectorObject Method : FileNotFoundException, create File (call CreateSaveData Method)");
            createSaveData();
        } catch (Exception e) {
            System.out.println("Password_chk Class - loadVectorObject Method : Exception, print Stack Trace");
            createSaveData();
            e.printStackTrace();
        }
    }
    private void createSaveData(){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getExternalFilesDir(null) + File.separator + "save_data.WRY"));
            oos.writeObject(action_log_object);
            oos.close();
        } catch (Exception e){
            System.out.println("Password_chk Class - createSaveData Method : Exception");
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        // 보통 안쓰는 객체는 onDestroy에서 해제 되지만 카메라는 확실히 제거해주는게 안전하다.
        if(f_camera != null){
            f_camera.releaseCamera();
            f_camera = null;
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if(f_camera != null){
            f_camera.releaseCamera();
            f_camera = null;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(f_camera != null){
            f_camera.releaseCamera();
            f_camera = null;
        }
    }
}
