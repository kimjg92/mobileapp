package com.example.gyu.whoareyou;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by GYU on 2015-11-25.
 */
public class ReceiverScreen extends BroadcastReceiver {
    private KeyguardManager km = null;
    private KeyguardManager.KeyguardLock keyLock = null;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            System.out.println("화면꺼졌다");
            if (km == null)
                km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

            if (keyLock == null)
                keyLock = km.newKeyguardLock(Context.KEYGUARD_SERVICE);


            disableKeyguard();

            Intent i = new Intent(context, ScreenLock.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public void reenableKeyguard() {
        keyLock.reenableKeyguard();
        System.out.println("ScreenReceiver Class - reEnableKeyguard Method call");
    }

    public void disableKeyguard() {
        keyLock.disableKeyguard();
        System.out.println("ScreenRecevier Class - disableKeyguard method call");
    }
}