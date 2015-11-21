package com.example.gyu.whoareyou;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Action_log_Content extends AppCompatActivity {

    Action_log_Object action_log_object;
    GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //SupportMapFragment mapFragment = (SupportMapFragment)findViewById(R.id.mapView);
        setContentView(R.layout.activity_action_log_content);
        Intent intent = getIntent();
        action_log_object = (Action_log_Object)intent.getExtras().getSerializable("Content");
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageBitmap(loadPicture(action_log_object.picture_object.get_Path()));

        map = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.mapView)).getMap();
        setMarker(action_log_object.location_object.getLatitude(),action_log_object.location_object.getLongitude());

    }

    public void setMarker (double latitude, double longitude) {
        map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
        CameraPosition INIT =
                new CameraPosition.Builder()
                        .target(new LatLng(latitude, longitude))
                        .zoom( 17.5F )
                        .bearing( 300F) // orientation
                                //.tilt( 50F) // viewing angle z축 회전
                        .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(INIT));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action_log_content, menu);

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
    private Bitmap loadPicture (String path){
        Bitmap picture = BitmapFactory.decodeFile(path);
        return picture;
    }
}
