package com.example.routersever.dispatcher;

import android.os.RemoteException;

import com.example.routersever.DataAIDLInterface;
import com.example.routersever.DataAIDLMessage;
import com.example.routersever.IMessageInterface;
import com.example.routersever.controller.cache.CacheFactoryImpl;
import com.example.routersever.controller.data.DataFactoryImpl;
import com.example.routersever.controller.data.DataImpl;
import com.example.routersever.dispatcher.IDispacher.IDispacherData;
import com.example.routersever.interfaces.ServiceConnectCallback;
import com.example.routersever.service.SeverConnection;
import com.google.gson.Gson;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/13 11:36.
 * Function :
 */
class DispacherDataImpl implements IDispacherData {

    private ConnectCallback mCallback;

    private DispacherDataImpl() {
    }

    private static class Factory {
        private static DispacherDataImpl mInstance = new DispacherDataImpl();
    }

    public static IDispacherData getInstance() {
        return Factory.mInstance;
    }

    @Override
    public <T> void sendMessage(T t, String tag) {
        checkConnect();
        DataFactoryImpl.getFactory().getDatas().setData(tag, t);
    }

    @Override
    public <T> T receiveMessage(Class<T> tClass, String tag) {
        T t = DataFactoryImpl.getFactory().getDatas().getData(tag, tClass);
        if (t != null) {
            return t;
        }
        try {
            int pid = CacheFactoryImpl.getFactory().getCache().getPid();
            DataAIDLMessage message = SeverConnection.getInstance().getDataAIDLInterface().getMessage(tag, pid);
            if (message.getObject() != null) {
                if (message.isString() && tClass != String.class) {
                    Gson gson = CacheFactoryImpl.getFactory().getCache().getGson();
                    t = gson.fromJson(message.getString(), tClass);
                    return t;
                } else {
                    return tClass.cast(message.getObject());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onConnect(final ServiceConnectCallback callback) {
        checkConnect();
        if (mCallback.isConnect()){
            callback.onConnect();
        }else {
            mCallback.setConnect(new ServiceConnectCallback() {
                @Override
                public void onConnect() {
                    callback.onConnect();
                }
            });
        }
    }

    private void checkConnect() {
        if (mCallback==null){
            mCallback=new ConnectCallback();
        }
        SeverConnection.getInstance().addConnect(mCallback);
    }

    private class ConnectCallback implements SeverConnection.ConnectCallback{

        private boolean isConnect=false;
        private ServiceConnectCallback mConnect;

        ConnectCallback(){}

        void setConnect(ServiceConnectCallback connect){
            this.mConnect=connect;
        }

        boolean isConnect(){
            return isConnect;
        }

        @Override
        public void onServiceConnected(DataAIDLInterface anInterface) {
            try {
                int pid = CacheFactoryImpl.getFactory().getCache().getPid();
                anInterface.Register(new JRouterMessage(),null, pid);
                isConnect=true;
                if (mConnect!=null){
                    mConnect.onConnect();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(DataAIDLInterface anInterface) {
            isConnect=false;
        }
    }

    private class JRouterMessage extends IMessageInterface.Stub {

        @Override
        public DataAIDLMessage getMessage(String messageTag, int pid) throws RemoteException {
            DataAIDLMessage message = new DataAIDLMessage();
            Object object = DataImpl.getInstance().getData(messageTag);
            saveDataToMessage(message, object);
            return message;
        }

        private void saveDataToMessage(DataAIDLMessage message, Object o) {
            if (o instanceof Integer) {
                message.setInt((Integer) o);
            } else if (o instanceof Long) {
                message.setLong((Long) o);
            } else if (o instanceof Boolean) {
                message.setBoolean((Boolean) o);
            } else if (o instanceof Float) {
                message.setFloat((Float) o);
            } else if (o instanceof Double) {
                message.setDouble((Double) o);
            } else if (o instanceof String) {
                message.setString((String) o);
            }
        }
    }

}
