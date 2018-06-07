package com.example.routersever.dispatcher.intent;

import com.example.routersever.dispatcher.intent.IDispacher.IDispacherIntent;
import com.example.routersever.dispatcher.intent.IDispacherFactory.IDispacherFactory;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/7 10:43.
 * Function :获取UI跳转事件分发处理服务
 */
public class DispacherFactoryImpl implements IDispacherFactory {


    private DispacherFactoryImpl() {
    }

    private static class Factory {
        private static DispacherFactoryImpl mInstance = new DispacherFactoryImpl();
    }

    public static IDispacherFactory getFactory() {
        return Factory.mInstance;
    }

    @Override
    public IDispacherIntent getDispacheIntent() {
        return DispacherIntentImpl.getInstance();
    }
}
