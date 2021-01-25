package com.example.demineur_aurejac_montoya;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.sql.Time;

public class TimerService extends Service {

    private Handler handler;
    private Runnable runnable;

    public class MyBinder extends Binder {
        TimerService getService(){
            return TimerService.this;
        }
    }

    private final MyBinder myBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        handler = new Handler();
        runnable = new Runnable(){
            public void run(){
                handler.postDelayed(this, 1000);
                Intent intent = new Intent(GameActivity.BROADCAST);
                sendBroadcast(intent);
            }
        };

        handler.post(runnable);
        return START_STICKY;
    }
}
