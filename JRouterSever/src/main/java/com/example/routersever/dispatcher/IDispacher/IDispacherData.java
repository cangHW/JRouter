package com.example.routersever.dispatcher.IDispacher;

import android.content.Context;
import android.os.Bundle;

import com.example.routersever.interfaces.ServiceConnectCallback;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/7 10:42.
 * Function :
 */
public interface IDispacherData {

    <T> void sendMessage(T t,String tag);

    <T> T receiveMessage(Class<T> t,String tag);

    void onConnect(ServiceConnectCallback callback);
}
