package com.example.routersever.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/2 10:30.
 * Function :
 */
public class SeverService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
    }
}
