package com.example.routersever.controller.eventbus.IEventBus;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/22 14:06.
 * Function :
 */
public interface IEventBus {

    String METHOD_MAIN="onEventThreadMain";

    void register(@NonNull Object o);

    void unRegister(@NonNull Object o);

    List<Object> getObject(String methodType,String parameterType);
}
