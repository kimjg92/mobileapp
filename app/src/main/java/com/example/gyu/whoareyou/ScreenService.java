package com.example.gyu.whoareyou;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;

/**
 * Created by GYU on 2015-11-25.
 */
public class ScreenService extends Service {

    private ReceiverScreen mReceiver = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mReceiver = new ReceiverScreen();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter);
        registerRestartAlarm(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags, startId);

        //Notification notification = new Notification(R.drawable.whoru, "서비스 실행됨", System.currentTimeMillis());
        //notification.setLatestEventInfo(getApplicationContext(), "Screen Service", "Foreground로 실행됨", null);
        startForeground(1, new Notification());


        if(intent != null){
            if(intent.getAction()==null){
                if(mReceiver==null){
                    mReceiver = new ReceiverScreen();
                    IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
                    registerReceiver(mReceiver, filter);
                }
            }
        }
        return START_REDELIVER_INTENT;
    }

    public void registerRestartAlarm(boolean isOn){
        Intent intent = new Intent(ScreenService.this, ReceiverRestart.class);
        intent.setAction(ReceiverRestart.ACTION_RESTART_SERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        if(isOn){
            am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1000, 10000, sender);
        }else{
            am.cancel(sender);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
//        if(mReceiver != null)
//            mReceiver.reenableKeyguard();
        registerRestartAlarm(false);
        if(mReceiver != null){
            unregisterReceiver(mReceiver);
        }
    }
}