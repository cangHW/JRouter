package com.example.routersever.sever.ISever;

import android.support.annotation.NonNull;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 10:45.
 * Function :自定义功能服务接口
 */
public interface ISeverFunction {

    void RegisterFunction(@NonNull Class<?> c, @NonNull Object o);

    void unRegisterFunction(@NonNull Class<?> c);

    <T> T getFunction(Class<T> c);
}
