package com.example.routersever.sever;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.routersever.controller.cache.CacheFactoryImpl;
import com.example.routersever.dispatcher.DispacherFactoryImpl;
import com.example.routersever.interfaces.DataCallback;
import com.example.routersever.interfaces.ServiceConnectCallback;
import com.example.routersever.sever.ISever.ISeverData;
import com.example.routersever.util.DataUtil;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/13 18:15.
 * Function :
 */
public class SeverDataImpl implements ISeverData {

    private SeverDataImpl() {
    }

    private static class Factory {
        private static final SeverDataImpl mInstance = new SeverDataImpl();
    }

    public static ISeverData getInstance() {
        return Factory.mInstance;
    }

    @Override
    public <T> void set(T t, String... tag) {
        if (DataUtil.checkDataType(t)) {
            DispacherFactoryImpl.getFactory().getDispacheData().sendMessage(t,getTag(t.getClass(),tag));
            return;
        }
        String string=DataUtil.isJavaBean(t);
        if (!TextUtils.isEmpty(string)){
            DispacherFactoryImpl.getFactory().getDispacheData().sendMessage(string,getTag(t.getClass(),tag));
            return;
        }
        Context context= CacheFactoryImpl.getFactory().getContext().get();
        Toast.makeText(context,"data type error",Toast.LENGTH_SHORT).show();
    }

    @Override
    public <T> T get(Class<T> tClass, String... tag) {
        return DispacherFactoryImpl.getFactory().getDispacheData().receiveMessage(tClass,getTag(tClass,tag));
    }

    @Override
    public <T> void getOnConnect(final Class<T> tClass, final DataCallback<T> callback, final String... tag) {
        DispacherFactoryImpl.getFactory().getDispacheData().onConnect(new ServiceConnectCallback() {
            @Override
            public void onConnect() {
                callback.onCallback(DispacherFactoryImpl.getFactory().getDispacheData().receiveMessage(tClass,getTag(tClass,tag)));
            }
        });
    }

    @Override
    public void OnConnect(ServiceConnectCallback callback) {
        DispacherFactoryImpl.getFactory().getDispacheData().onConnect(callback);
    }

    private <T> String getTag(Class<T> tClass, String... tag) {
        StringBuilder messageTag = new StringBuilder();
        if (tag != null && tag.length > 0) {
            for (String s : tag) {
                if (messageTag.length() > 0) {
                    messageTag.append(",");
                }
                messageTag.append(s);
            }
        } else {
            messageTag.append(tClass.getName());
        }
        return messageTag.toString();
    }
}
