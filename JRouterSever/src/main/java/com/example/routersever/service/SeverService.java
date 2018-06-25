package com.example.routersever.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.routersever.DataAIDLInterface;
import com.example.routersever.DataAIDLMessage;
import com.example.routersever.IMessageInterface;
import com.example.routersever.constant.Constants;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/2 10:30.
 * Function :
 */
public class SeverService extends Service {
    public static final String KEY="JRouter";
    public static final String VALUE="true";

    private RemoteCallbackList<IMessageInterface> mCallbackList=new RemoteCallbackList<>();

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
        if (VALUE.equals(intent.getStringExtra(KEY))) {
            return mStub;
        }
        Log.d(Constants.TAG,"the user message is error");
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

    @Override
    public void onDestroy() {
        mCallbackList.kill();
        super.onDestroy();
    }

    private DataAIDLInterface.Stub mStub=new DataAIDLInterface.Stub() {
        @Override
        public void Register(IMessageInterface inter,int pid) throws RemoteException {
            mCallbackList.register(inter,pid);
        }

        @Override
        public void unRegister(IMessageInterface inter) throws RemoteException {
            mCallbackList.unregister(inter);
        }

        @Override
        public DataAIDLMessage getMessage(String messageTag, int pid) throws RemoteException {
            int len=mCallbackList.beginBroadcast();
            for (int i=0;i<len;i++){
                int p= (int) mCallbackList.getBroadcastCookie(i);
                if (p!=pid) {
                    try {
                        DataAIDLMessage message = mCallbackList.getBroadcastItem(i).getMessage(messageTag, p);
                        if (message.getObject()!=null){
                            mCallbackList.finishBroadcast();
                            return message;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            mCallbackList.finishBroadcast();
            return new DataAIDLMessage();
        }

        @Override
        public void notifyAll(String message, String type, int pid) throws RemoteException {

        }
    };

}
