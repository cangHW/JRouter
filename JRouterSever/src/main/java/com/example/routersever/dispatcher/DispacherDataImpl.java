package com.example.routersever.dispatcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.routersever.DataAIDLInterface;
import com.example.routersever.DataAIDLMessage;
import com.example.routersever.IMessageInterface;
import com.example.routersever.constant.Constants;
import com.example.routersever.controller.cache.CacheFactoryImpl;
import com.example.routersever.controller.data.DataFactoryImpl;
import com.example.routersever.controller.data.DataImpl;
import com.example.routersever.dispatcher.IDispacher.IDispacherData;
import com.example.routersever.interfaces.ServiceConnectCallback;
import com.example.routersever.service.SeverService;
import com.google.gson.Gson;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/13 11:36.
 * Function :
 */
class DispacherDataImpl implements IDispacherData {

    private JRouterServiceConnection mConnection;

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
            DataAIDLMessage message = mConnection.getDataAIDLInterface().getMessage(tag, pid);
            if (message.getObject() != null) {
                if (message.isString() && tClass != String.class) {
                    Gson gson = CacheFactoryImpl.getFactory().getCache().getGson();
                    t = gson.fromJson(message.getString(), tClass);
                    return t;
                } else {
                    return tClass.cast(message.getObject());
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onConnect(final ServiceConnectCallback callback) {
        checkConnect();
        if (mConnection.isConnect()) {
            callback.onConnect();
        } else {
            mConnection.setConnectCallback(new ConnectCallback() {
                @Override
                public void callback() {
                    callback.onConnect();
                }
            });
        }
    }

    private void checkConnect() {
        if (mConnection == null) {
            JRouterMessage jRouterMessage = new JRouterMessage();
            mConnection = new JRouterServiceConnection();
            mConnection.setJRouterMessage(jRouterMessage);
        }
        if (!mConnection.isConnect()) {
            Context context = CacheFactoryImpl.getFactory().getContext().get();
            Intent intent = new Intent(context, SeverService.class);
            intent.putExtra(SeverService.KEY, SeverService.VALUE);
            context.startService(intent);
            context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private class JRouterServiceConnection implements ServiceConnection {

        private boolean isConnect = false;
        private JRouterMessage mJRouterMessage;
        private DataAIDLInterface mDataAIDLInterface;
        private ConnectCallback mCallback;

        boolean isConnect() {
            return isConnect;
        }

        void setJRouterMessage(JRouterMessage jRouterMessage) {
            this.mJRouterMessage = jRouterMessage;
        }

        void setConnectCallback(ConnectCallback callback) {
            this.mCallback = callback;
        }

        DataAIDLInterface getDataAIDLInterface() {
            return mDataAIDLInterface;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                if (service==null){
                    return;
                }
                int pid = CacheFactoryImpl.getFactory().getCache().getPid();
                mDataAIDLInterface = DataAIDLInterface.Stub.asInterface(service);
                mDataAIDLInterface.Register(mJRouterMessage, pid);
                isConnect = true;
                if (mCallback != null) {
                    mCallback.callback();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            try {
                mDataAIDLInterface.unRegister(mJRouterMessage);
                isConnect = false;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onBindingDied(ComponentName name) {
            isConnect = false;
            checkConnect();
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

    private interface ConnectCallback {
        void callback();
    }
}
