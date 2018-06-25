package com.example.routersever.dispatcher;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.routersever.dispatcher.IDispacher.IDispacherEvent;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/25 13:46.
 * Function :
 */
class DispacherEventImpl implements IDispacherEvent {

    private JRouterServiceConnection mConnection;

    private DispacherEventImpl() {
    }

    private static class Factory {
        private static DispacherEventImpl mInstance = new DispacherEventImpl();
    }

    public static IDispacherEvent getInstance() {
        return Factory.mInstance;
    }

    @Override
    public void post(Object o) {

    }

    private class JRouterServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onBindingDied(ComponentName name) {

        }
    }
}
