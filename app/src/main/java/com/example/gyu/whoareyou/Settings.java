package com.example.gyu.whoareyou;

import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;

import java.io.Serializable;

public class Settings extends AppCompatActivity implements Serializable{

    Settings_Object settings_object;
    Switch Emailswitch;

    private boolean sendEmail;
    private String Email;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setWitchListener();
        ///
         - 기존 설정 내용이 있을경우 저장된 설정 객체를 불러옴. action log 액티비티 에서 버튼 클릭시 settings 객체를 불러오고 인텐트에 extra를 추가하여 넘기면 될 듯
         - 기존 설정 내용이 없을경우(Settings_Object에 비밀번호가 없을경우) 기본 설정값을 불러옴
         ////
        Intent intent=getIntent();
        settings_object = (Settings_Object)intent.getExtras().getSerializable("Settings");
        Button apply = (Button) findViewById(R.id.Apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (settings_object == null){
                    settings_object = new Settings_Object();
                    //createNewSettings 호출전에 이메일이 비었는데 이메일이 enable되어있는경우 reject시키기
                    if(createNewSettingsConditionChk()){
                        createNewSettings();
                    }
                } else if (settings_object != null){

                } else {

                }
            }
        });

    }

    public void createNewSettings(){


        settings_object.setEmail(getEmail());
        settings_object.setEmailEnable(sendEmail);
        // settings_object.changePassword();

    }
    private String getEmail (){
        if (sendEmail){
            EditText EditEmail = (EditText) findViewById(R.id.setEmail);
            return EditEmail.getText().toString();
        } else {
            return "";
        }
    }
    private boolean EmailCheck (){
        boolean EmailInputChk;
        if(sendEmail == true && getEmail().equals("")){
            EmailInputChk = false;
            Toast.makeText(getApplication(), "Enter the Email", Toast.LENGTH_SHORT).show();
        } else {
            EmailInputChk = true;
        }
        return EmailInputChk;
    }
    private boolean AllPasswordEqual(){
        if(IsNewPasswordEqual() && IsCurrentPasswordEqual()){
            return true;
        } else {
            Toast.makeText(getApplication(), "Password incorrect", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean IsCurrentPasswordEqual(){
        if(settings_object.getCurrentPassword().equals(this.getCurrentPassword())){
            return true;
        } else {
            return false;
        }
    }
    private boolean IsNewPasswordEqual(){
        if(getNewPassword().equals(getNewPasswordChk())){
            return true;
        } else {
            return false;
        }
    }
    private String getCurrentPassword(){
        EditText currentPassword = (EditText)findViewById(R.id.currrentPassword);
        return currentPassword.getText().toString();
    }
    private String getNewPassword(){
        EditText EditNewPassword = (EditText) findViewById(R.id.newPassword);
        return EditNewPassword.getText().toString();
    }
    private String getNewPasswordChk(){
        EditText EditNewPasswordChk = (EditText) findViewById(R.id.newPasswordChk);
        return EditNewPasswordChk.getText().toString();
    }
    private void setWitchListener (){
        Emailswitch = (Switch) findViewById(R.id.EmailSwitch);
        Emailswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sendEmail = true;
                    Toast.makeText(getApplication(), "Send Email Enabled", Toast.LENGTH_SHORT).show();
                } else {
                    sendEmail = false;
                    Toast.makeText(getApplication(),"Send Email Disabled", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
///////
    private void setEmail (){
        if(SendEmail == true) {
            EditText EditEmail = (EditText) findViewById(R.id.setEmail);
            Email = EditEmail.getText().toString();
        } else {
            Email = "null";
        }
    }

    private void setWitchListener (){
        Emailswitch = (Switch) findViewById(R.id.EmailSwitch);
        Emailswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SendEmail = true;
                    Toast.makeText(getApplication(), "Send Email Enabled", Toast.LENGTH_SHORT).show();
                } else {
                    SendEmail = false;
                    Toast.makeText(getApplication(),"Send Email Disabled", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void saveSettings(){
        try{
            ObjectOutputStream oos =new ObjectOutputStream(new FileOutputStream(getExternalFilesDir(null) + File.separator + "settings.WRY"));
            oos.writeObject(settings_object);
            oos.close();
        } catch (Exception e) {
            System.out.println("Settings Class - saveSettings method call : Exception");
        }
    }
/////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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

*/
}
