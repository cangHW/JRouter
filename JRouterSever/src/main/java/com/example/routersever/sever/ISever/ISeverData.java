package com.example.routersever.sever.ISever;

import com.example.routersever.interfaces.DataCallback;
import com.example.routersever.interfaces.ServiceConnectCallback;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/13 18:13.
 * Function :任何一个方法都会触发检查连接状态的操作，发现未连接，则尝试进行连接
 */
public interface ISeverData {

    /**
     * 保存数据
     */
    <T> void set(T t, String... tag);

    /**
     * 获取数据，可以获取当前进程与其他进程数据（其他进程数据需要保证已经连接成功service，否则返回null）
     */
    <T> T get(Class<T> tClass, String... tag);

    /**
     * 返回数据，异步返回，保证查询数据时一定是连接service成功的状态，未连接就处于等待状态，等待连接
     */
    <T> void getOnConnect(Class<T> tClass, DataCallback<T> callback, String... tag);

    /**
     * 监听连接状态
     */
    void OnConnect(ServiceConnectCallback callback);
}
