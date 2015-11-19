package com.example.gyu.whoareyou;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import java.io.Serializable;

/**
 * Created by GYU on 2015-11-08.
 */
public class Screen_Lock extends Activity implements Serializable{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }

    public class ScreenReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Intent i = new Intent(context, login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }

    public class ScreenService extends Service {

        private ScreenReceiver mReceiver = null;

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();

            mReceiver = new ScreenReceiver();
            IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
            registerReceiver(mReceiver, filter);
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId){
            super.onStartCommand(intent, flags, startId);

            if(intent != null){
                if(intent.getAction()==null){
                    if(mReceiver==null){
                        mReceiver = new ScreenReceiver();
                        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
                        registerReceiver(mReceiver, filter);
                    }
                }
            }
            return START_REDELIVER_INTENT;
        }

        @Override
        public void onDestroy(){
            super.onDestroy();

            if(mReceiver != null){
                unregisterReceiver(mReceiver);
            }
        }
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
}
