package com.example.gyu.whoareyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

public class Action_log extends AppCompatActivity implements Serializable{
    Vector<Action_log_Object> action_log_object = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_log);
        ImageButton btn_settings;
        btn_settings=(ImageButton)findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_settings = new Intent(Action_log.this, Settings.class);
                startActivity(intent_settings);
            }
        });


        loadVectorObject();
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.action_log);
        Button button_List[] = new Button[action_log_object.size()];
        createButtons(button_List, layout);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action_log, menu);
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

    private void createButtons(final Button[] button_List, RelativeLayout layout){
        for (int i = 0 ; i < action_log_object.size() ; i ++){
            final int button_number = i;
            button_List[i] = new Button(this);
            button_List[i].setText(action_log_object.elementAt(i).picture_object.get_Path());
            button_List[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(action_log_object.elementAt(button_number).picture_object.get_Path());
                }
            });
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = 100 + i*150;
            params.rightMargin = 1001+ i*150;
            button_List[i].setLayoutParams(params);

            layout.addView(button_List[i]);
        }
    }

    private void savePicturePath(String path){
        Action_log_Object temp = new Action_log_Object();
        temp.picture_object.set_Path(path);
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
            Vector<Action_log_Object> temp;
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getExternalFilesDir(null) + File.separator + "save_data.WRY"));
            temp = (Vector<Action_log_Object>) ois.readObject();
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
}
