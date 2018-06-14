package com.example.routersever.sever.ISever;

import com.example.routersever.interfaces.DataCallback;
import com.example.routersever.interfaces.ServiceConnectCallback;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/13 18:13.
 * Function :
 */
public interface ISeverData {

    <T> void set(T t, String... tag);

    <T> T get(Class<T> tClass, String... tag);

    <T> void getOnConnect(Class<T> tClass, DataCallback<T> callback, String... tag);

    void OnConnect(ServiceConnectCallback callback);
}
