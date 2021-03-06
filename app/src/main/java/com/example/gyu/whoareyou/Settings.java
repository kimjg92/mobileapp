package com.example.gyu.whoareyou;

import android.app.TabActivity;

import java.io.Serializable;

public class Settings extends TabActivity implements Serializable{
/*
    Settings_Object settings_object;
    int passwordCount = -1;
    Switch Emailswitch;
    boolean ActivateApp;
    boolean sendEmail;
    boolean setPasswordEnable = false;

    boolean saveCondition_passwordCount = true;
    boolean saveCondition_Email = true;
    boolean saveCondition_crntPassword = false;
    boolean saveCondition_Password = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        loadSettingsFromFile();
        printSettings();

        setSettingsOnView();

        setActivateApplicationSwitchListener();
        setRadioGroupListener();
        setSwitchListener();
        setPasswordChangeEnableListener();
        setApplyButtonListener();

       // Intent intent=getIntent();
       // settings_object = (Settings_Object)intent.getExtras().getSerializable("Settings");

    }
    private void ActivateApplication(){
        if(ActivateApp == true){
            settings_object.setUseApp(ActivateApp);
            Intent intent = new Intent(Settings.this, ScreenService.class);
            startService(intent);
        } else if(ActivateApp == false){
            settings_object.setUseApp(ActivateApp);
            Intent intent = new Intent(Settings.this, ScreenService.class);
            stopService(intent);
        }
    }
    private void setActivateApplicationSwitchListener(){
        final Switch ActivateAppSwitch = (Switch)findViewById(R.id.useApp);
        ActivateAppSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ActivateApp = true;
                    //setEmail.setEnabled(true);
                    Toast.makeText(getApplication(), "Application Enabled " , Toast.LENGTH_SHORT).show();
                } else {
                    ActivateApp = false;
                    //setEmail.setEnabled(false);
                    Toast.makeText(getApplication(), "Application Disabled ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void setActivateAppSwitchMark () {
        Switch ActivateAppSwitch = (Switch)findViewById(R.id.useApp);
        if(settings_object.getUseApp()  == true) {
            ActivateAppSwitch.setChecked(true);
        } else if (settings_object.getUseApp() == false){
            ActivateAppSwitch.setChecked(false);
        }

    }
    private void setApplyButtonListener (){
        Button Apply = (Button)findViewById(R.id.Apply);
        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveSettings(); // 여기안의 기능들을 모듈화 하여 스위치, 체크박스 들이 클릭되어있을때 나누어서 해야할 듯
                //rintSettings();

                if(setPasswordEnable == true){
                    currentPasswordCheck();
                    if(saveCondition_passwordCount && saveCondition_Email && saveCondition_Password && saveCondition_crntPassword){
                        //setActivateApplication();
                        ActivateApplication();
                        setPasswordCnt();
                        setEmail();
                        setPassword();
                        printSettings();

                        saveSettingsToFile();

                        Toast.makeText(getApplication(), "적용되었습니다.", Toast.LENGTH_SHORT).show();

                    }
                } else if (setPasswordEnable == false){
                    if(saveCondition_passwordCount && saveCondition_Email) {
                        //setActivateApplication();
                        ActivateApplication();
                        setPasswordCnt();
                        setEmail();
                        printSettings();

                        saveSettingsToFile();

                        Toast.makeText(getApplication(), "적용되었습니다.", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }
    private void currentPasswordCheck(){
        EditText currentPassword = (EditText)findViewById(R.id.currrentPassword);
        String crntPassword = currentPassword.getText().toString();
        if(settings_object.getPassword().toString().equals(crntPassword)){
            saveCondition_crntPassword = true;
        } else {
            saveCondition_crntPassword = false;
            Toast.makeText(getApplication(), "current Password incorrect", Toast.LENGTH_SHORT).show();
        }
    }
    private void loadSettingsFromFile() {
        try {
            Settings_Object temp;
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getExternalFilesDir(null) + File.separator + "settings.WRY"));
            temp = (Settings_Object)ois.readObject();
            settings_object = temp;
            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("Password_chk Class - loadVectorObject Method : FileNotFoundException, create File (call CreateSaveData Method)");

        } catch (Exception e) {
            System.out.println("Password_chk Class - loadVectorObject Method : Exception, print Stack Trace");
            e.printStackTrace();
        }
    }

    private void setSettingsOnView(){
        passwordCountMark();
        passwordCount = settings_object.getPasswordCount();
        sendEmailSwitchMark();
        sendEmail = settings_object.getEmailEnable();
        setEmailMark();
        setActivateAppSwitchMark();
        ActivateApp = settings_object.getUseApp();

        EditText currentPassword = (EditText)findViewById(R.id.currrentPassword);
        EditText newPassword = (EditText)findViewById(R.id.newPassword);
        EditText newPasswordChk = (EditText)findViewById(R.id.newPasswordChk);
        currentPassword.setEnabled(false);
        newPassword.setEnabled(false);
        newPasswordChk.setEnabled(false);
    }

    private void passwordCountMark(){
        RadioButton PasswordOption1 = (RadioButton)findViewById(R.id.PasswordOption1);
        RadioButton PasswordOption2 = (RadioButton)findViewById(R.id.PasswordOption2);
        RadioButton PasswordOption3 = (RadioButton)findViewById(R.id.PasswordOption3);
        RadioButton PasswordOption4 = (RadioButton)findViewById(R.id.PasswordOption4);

        switch (settings_object.getPasswordCount()){
            case 1 :
                PasswordOption1.setChecked(true);
                settings_object.setPasswordCount(1);
                break;
            case 2:
                PasswordOption2.setChecked(true);
                settings_object.setPasswordCount(2);
                break;
            case 3:
                PasswordOption3.setChecked(true);
                settings_object.setPasswordCount(3);
                break;
            case 4:
                PasswordOption4.setChecked(true);
                settings_object.setPasswordCount(4);
                break;
        }
    }

    private void sendEmailSwitchMark () {
        Switch EmailSwitch = (Switch)findViewById(R.id.EmailSwitch);
        EditText setEmail = (EditText)findViewById(R.id.setEmail);
        if(settings_object.getEmailEnable() == true) {
            EmailSwitch.setChecked(true);
            setEmail.setEnabled(true);
        } else if (settings_object.getEmailEnable() == false){
            EmailSwitch.setChecked(false);
            setEmail.setEnabled(false);
        }

    }

    private void setEmailMark (){
        EditText setEmail = (EditText)findViewById(R.id.setEmail);
        setEmail.setText(settings_object.getEmail());
    }


    private void printSettings(){
        System.out.println("Activation App : " + settings_object.getUseApp());
        System.out.println("Password Count : " + settings_object.getPasswordCount());
        System.out.println("Email : " + settings_object.getEmail());
        System.out.println("Email Send Enable : " + settings_object.getEmailEnable());
        System.out.println("Password : " + settings_object.getPassword());
        System.out.println("New Password : " + getNewPassword());
        System.out.println("New Password Check : " + getNewPasswordChk());
        System.out.println("Save Condition Password Count : " + saveCondition_passwordCount);
        System.out.println("Save Condition Email : " + saveCondition_Email);
        System.out.println("Save Condition crntPassword : " + saveCondition_crntPassword);
        System.out.println("Save Condition newPassword : " + saveCondition_Password);


    }

    private void saveSettings(){
        //settings_object = new Settings_Object();

        //setPasswordcnt
        setPasswordCnt();

        //setEmail
        setEmail();

        //setPassword
        setPassword();

    }
    private void setPassword(){
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

    private void setEmail(){
        settings_object.setEmailEnable(sendEmail);
        if(isEmailValid()) {
            settings_object.setEmail(getEmail());
            saveCondition_Email = true;
        } else {
            saveCondition_Email = false;
        }
    }

    private void setPasswordCnt(){
        if (passwordCount == -1){
            Toast.makeText(getApplication(), "Click the Password Count", Toast.LENGTH_SHORT).show();
            saveCondition_passwordCount = false;
        } else {
            settings_object.setPasswordCount(passwordCount);
            saveCondition_passwordCount = true;
        }
    }

    private String getEmail(){
        EditText editText = (EditText) findViewById(R.id.setEmail);
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

    private String getCurrentPassword(){
        EditText editText = (EditText)findViewById(R.id.currrentPassword);
        return editText.getText().toString();
    }

    private String getNewPassword(){
        EditText editText = (EditText)findViewById(R.id.newPassword);
        return editText.getText().toString();
    }

    private String getNewPasswordChk(){
        EditText editText = (EditText)findViewById(R.id.newPasswordChk);
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
        Emailswitch = (Switch) findViewById(R.id.EmailSwitch);
        Emailswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EditText setEmail = (EditText)findViewById(R.id.setEmail);
                if (isChecked) {
                    sendEmail = true;
                    setEmail.setEnabled(true);
                    Toast.makeText(getApplication(), "Send Email Enabled " + sendEmail, Toast.LENGTH_SHORT).show();
                } else {
                    sendEmail = false;
                    setEmail.setEnabled(false);
                    Toast.makeText(getApplication(), "Send Email Disabled " + sendEmail, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void setRadioGroupListener (){
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.PasswordOption1:
                        passwordCount = 1;
                        break;

                    case R.id.PasswordOption2:
                        passwordCount = 2;
                        break;

                    case R.id.PasswordOption3:
                        passwordCount = 3;
                        break;

                    case R.id.PasswordOption4:
                        passwordCount = 4;
                        break;
                }
            }
        });
    }

    private void setPasswordChangeEnableListener() {
        final CheckBox passwordSettingEnable = (CheckBox) findViewById(R.id.passwordSettingEnable);
        passwordSettingEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EditText currentPassword = (EditText)findViewById(R.id.currrentPassword);
                EditText newPassword = (EditText)findViewById(R.id.newPassword);
                EditText newPasswordChk = (EditText)findViewById(R.id.newPasswordChk);
                if (isChecked == true) {
                    setPasswordEnable = true;
                    currentPassword.setEnabled(true);
                    newPassword.setEnabled(true);
                    newPasswordChk.setEnabled(true);
                } else {
                    setPasswordEnable = false;
                    currentPassword.setEnabled(false);
                    newPassword.setEnabled(false);
                    newPasswordChk.setEnabled(false);
                }
            }
        });
    }
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
