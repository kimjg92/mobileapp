package com.example.gyu.whoareyou;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Action_log extends AppCompatActivity implements Serializable{
    ArrayList<Action_log_Object> action_log_object = new ArrayList<>();
    ArrayAdapter<String> action_log_adapter;
    ListView action_log_ListView;
    Settings_Object settings_object;

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
        createListView();
        //String Path = getIntent().getStringExtra("Path");
        //sendGmail(Path);  <- 다른 액티비티에서 파일경로를 인텐트로 받아와서 보낸느건 잘됨.
    }
    private void sendGmail(String filePath ){


        GMailSender sender = new GMailSender("kimjdms", "kiki3301"); // 설정파일에서 불러오기로 고치기
        System.out.println("지메일 센더 실행");

        GPS gps = new GPS(this);
        Location location = gps.getLocation();

        double lat = location.getLatitude();
        double lng = location.getLongitude();
        String mailContent = "위치정보 : https://www.google.com/maps?q="+lat+","+lng+"&hl=ko&gl=kr&shorturl=1";

        /////////////////////////////////////////////////////////////////////////////////////


/*            String[] proj = { MediaStore.Images.Media.DATA };
            Cursor cursor = managedQuery(filePath, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String testPath = cursor.getString(column_index);

*/
        /////////////////////////////////
        try {
            FileInputStream ois  = new FileInputStream(filePath);
            //dFile file = ois.read();
            ois.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        File file = new File(filePath);//getOutputMediaFile(filePath);
        try {
            sender.sendMail("Who Are You", mailContent, "kimjdms@mgmail.com", "kim_jg92@naver.com", file);

            System.out.println("메일 센더 호출");
        } catch (Exception e) {
            System.out.println("오류 파일을 찾을 수 없음 : " + file);
            e.printStackTrace();
        }
        /*if(file.exists() == false){
            System.out.println ("파일 없음");
        } else {

        }*/

        //Bitmap img = BitmapFactory.decodeFile(testPath);
        //ImageView test = (ImageView)findViewById(R.id.imageViewTest);
        //test.setImageBitmap(img);

        //System.out.println("sendGmail Method - get File : " + testPath);
        ///////////////////////////////////////////////////////////
        // img.pat
        //file = new File(file.toURI());
        //iv.setImageBitmap(BitmapFactory.decodeFile(filePath));//여기서 오류나느거 보니 String으로 보내면안되나봄
        //whoareyou E/BitmapFactory﹕ Unable to decode stream: java.io.FileNotFoundException: /storage/emulated/0/Pictures/MyCameraApp/IMG_20151124_134227.jpg: open failed: ENOENT (No such file or directory
        //http://stackoverflow.com/questions/24524809/bitmapfactory-decodefile-filenotfoundexception
        //이 방법으로 해볼 것
        //System.out.println("sendGmail Method - get File : " + file);


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


    private void createListView (){
        ArrayList<String> fortest = new ArrayList<>();


        for (int i = 0 ; i < action_log_object.size() ; i ++){
            fortest.add(action_log_object.get(i).picture_object.get_Path());
        }
        action_log_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, fortest);
        AdapterView.OnItemClickListener ListViewClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long l_position) {
                //position 이 순번 , 위에서부터 0,1,2...
                Intent intent = new Intent(Action_log.this,Action_log_Content.class);
                intent.putExtra("Content", action_log_object.get(position));
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        };

        action_log_ListView = (ListView) findViewById(R.id.listView);
        action_log_ListView.setAdapter(action_log_adapter);
        action_log_ListView.setOnItemClickListener(ListViewClickListener);
    }


    private void createButtons(final Button[] button_List, RelativeLayout layout){
        for (int i = 0 ; i < action_log_object.size() ; i ++){
            final int button_number = i;
            button_List[i] = new Button(this);
            button_List[i].setText(action_log_object.get(i).picture_object.get_Path());
            button_List[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(action_log_object.get(button_number).picture_object.get_Path());
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
}
