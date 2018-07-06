package com.example.routersever.dispatcher;

import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;

import com.example.routersever.DataAIDLInterface;
import com.example.routersever.IEventInterface;
import com.example.routersever.controller.cache.CacheFactoryImpl;
import com.example.routersever.controller.eventbus.EventBusFactory;
import com.example.routersever.controller.eventbus.IEventBus.IEventBus;
import com.example.routersever.dispatcher.IDispacher.IDispacherEvent;
import com.example.routersever.interfaces.ServiceConnectCallback;
import com.example.routersever.service.SeverConnection;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/25 13:46.
 * Function :
 */
class DispacherEventImpl implements IDispacherEvent {

    private ConnectCallback mCallback;
    private Handler mHandler;

    private DispacherEventImpl() {
        mHandler=new Handler(Looper.getMainLooper());
    }

    private static class Factory {
        private static final DispacherEventImpl mInstance = new DispacherEventImpl();
    }

    public static IDispacherEvent getInstance() {
        return Factory.mInstance;
    }

    @Override
    public void init() {
        checkConnect();
    }

    @Override
    public void post(Object o, final String message, final String type) {
        notifyAllCallback(o);
        checkConnect();
        if (mCallback.isConnect()){
            try {
                int pid = CacheFactoryImpl.getFactory().getCache().getPid();
                SeverConnection.getInstance().getDataAIDLInterface().notifyAll(message,type,pid);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }else {
            mCallback.setCallback(new ServiceConnectCallback() {
                @Override
                public void onConnect() {
                    try {
                        int pid = CacheFactoryImpl.getFactory().getCache().getPid();
                        SeverConnection.getInstance().getDataAIDLInterface().notifyAll(message,type,pid);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void checkConnect(){
        if (mCallback==null){
            mCallback=new ConnectCallback();
        }
        SeverConnection.getInstance().addConnect(mCallback);
    }

    private class ConnectCallback implements SeverConnection.ConnectCallback{

        private boolean isConnect=false;
        private ServiceConnectCallback mCallback;

        ConnectCallback(){
        }

        void setCallback(ServiceConnectCallback callback){
            this.mCallback=callback;
        }

        boolean isConnect(){
            return isConnect;
        }

        @Override
        public void onServiceConnected(DataAIDLInterface anInterface) {
            try {
                int pid = CacheFactoryImpl.getFactory().getCache().getPid();
                anInterface.Register(null,new Event(DispacherEventImpl.this),pid);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            isConnect=true;
            if (mCallback!=null){
                mCallback.onConnect();
            }
        }

        @Override
        public void onServiceDisconnected(DataAIDLInterface anInterface) {
            isConnect=false;
        }
    }

    private class Event extends IEventInterface.Stub {

        private WeakReference<DispacherEventImpl> reference;

        Event(DispacherEventImpl dispacherEvent){
            reference=new WeakReference<>(dispacherEvent);
        }

        @Override
        public void notifyAll(String message, String type, int pid) throws RemoteException {
            Gson gson=CacheFactoryImpl.getFactory().getCache().getGson();
            try {
                Object o=gson.fromJson(message,Class.forName(type));
                if (reference!=null&&reference.get()!=null){
                    reference.get().notifyAllCallback(o);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void notifyAllCallback(final Object o){
        List<Object> list = EventBusFactory.getFactory().getEventBus().getObject(IEventBus.METHOD_MAIN, o.getClass().getName());
        if (list != null && !list.isEmpty()) {
            for (final Object object : list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Class<?> aClass = object.getClass();
                            Method method = aClass.getDeclaredMethod(IEventBus.METHOD_MAIN, o.getClass());
                            method.setAccessible(true);
                            method.invoke(object, o);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
