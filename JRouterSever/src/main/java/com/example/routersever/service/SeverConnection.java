package com.example.routersever.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.routersever.controller.cache.CacheFactoryImpl;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/25 14:01.
 * Function :
 */
public class SeverConnection implements ServiceConnection {

    public interface ConnectCallback{
        void onServiceConnected(ComponentName name, IBinder service);
        void onServiceDisconnected(ComponentName name);
    }

    private boolean isConnect=false;

    private SeverConnection(){
        init();
    }

    private void init(){
        Context context = CacheFactoryImpl.getFactory().getContext().get();
        Intent intent = new Intent(context, SeverService.class);
        intent.putExtra(SeverService.KEY, SeverService.VALUE);
        context.startService(intent);
        context.bindService(intent, SeverConnection.this, Context.BIND_AUTO_CREATE);
    }

    private static class Factory {
        private static SeverConnection mInstance = new SeverConnection();
    }

    public static SeverConnection getInstance() {
        return Factory.mInstance;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        isConnect=true;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        isConnect=false;
    }

    @Override
    public void onBindingDied(ComponentName name) {
        isConnect=false;
        init();
    }
}
