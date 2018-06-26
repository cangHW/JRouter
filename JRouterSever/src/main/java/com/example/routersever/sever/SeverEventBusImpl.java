package com.example.routersever.sever;

import android.support.annotation.NonNull;

import com.example.routersever.controller.cache.CacheFactoryImpl;
import com.example.routersever.controller.eventbus.EventBusFactory;
import com.example.routersever.controller.eventbus.IEventBus.IEventBus;
import com.example.routersever.dispatcher.DispacherFactoryImpl;
import com.example.routersever.sever.ISever.ISeverEventBus;
import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/25 12:33.
 * Function :
 */
public class SeverEventBusImpl implements ISeverEventBus {

    private SeverEventBusImpl() {
    }

    private static class Factory {
        private static SeverEventBusImpl mInstance = new SeverEventBusImpl();
    }

    public static ISeverEventBus getInstance() {
        return Factory.mInstance;
    }

    @Override
    public void register(@NonNull Object o) {
        DispacherFactoryImpl.getFactory().getDispacherEvent().init();
        EventBusFactory.getFactory().getEventBus().register(o);
    }

    @Override
    public void unRegister(@NonNull Object o) {
        EventBusFactory.getFactory().getEventBus().unRegister(o);
    }

    @Override
    public <T> void post(@NonNull T t) {
        Gson gson= CacheFactoryImpl.getFactory().getCache().getGson();
        String message=gson.toJson(t);
        String type=t.getClass().getName();
        DispacherFactoryImpl.getFactory().getDispacherEvent().post(t,message,type);
    }
}
