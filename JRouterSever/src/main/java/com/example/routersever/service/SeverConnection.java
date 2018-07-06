package com.example.routersever.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.routersever.DataAIDLInterface;
import com.example.routersever.controller.cache.CacheFactoryImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/25 14:01.
 * Function :
 */
public class SeverConnection implements ServiceConnection {

    public interface ConnectCallback {
        void onServiceConnected(DataAIDLInterface anInterface);
        void onServiceDisconnected(DataAIDLInterface anInterface);
    }

    private DataAIDLInterface mDataAIDLInterface;
    private HashSet<ConnectCallback> mConnectCallbacks=new HashSet<>();

    private SeverConnection() {
        init();
    }

    private void init() {
        Context context = CacheFactoryImpl.getFactory().getContext().get();
        Intent intent = new Intent(context, SeverService.class);
        intent.putExtra(SeverService.KEY, SeverService.VALUE);
        context.startService(intent);
        context.bindService(intent, SeverConnection.this, Context.BIND_AUTO_CREATE);
    }

    private static class Factory {
        private static final SeverConnection mInstance = new SeverConnection();
    }

    public static SeverConnection getInstance() {
        return Factory.mInstance;
    }

    public void addConnect(ConnectCallback callback){
        try {
            if (mDataAIDLInterface!=null){
                callback.onServiceConnected(mDataAIDLInterface);
            }
            mConnectCallbacks.add(callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public DataAIDLInterface getDataAIDLInterface() {
        return mDataAIDLInterface;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        if (service == null) {
            return;
        }
        mDataAIDLInterface = DataAIDLInterface.Stub.asInterface(service);
        for (ConnectCallback callback:mConnectCallbacks){
            callback.onServiceConnected(mDataAIDLInterface);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        for (ConnectCallback callback:mConnectCallbacks){
            callback.onServiceDisconnected(mDataAIDLInterface);
        }
        mDataAIDLInterface = null;
    }

    @Override
    public void onBindingDied(ComponentName name) {
        for (ConnectCallback callback:mConnectCallbacks){
            callback.onServiceDisconnected(mDataAIDLInterface);
        }
        mDataAIDLInterface = null;
        init();
    }
}
