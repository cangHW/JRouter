package com.example.routersever;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.routersever.controller.cache.ICacheFactory.ICacheFactory;
import com.example.routersever.util.ExceptionUtil;
import com.example.routersever.controller.cache.CacheFactoryImpl;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/2 11:55.
 * Function :路由
 */
public class JRouter {

    private static boolean isReady;

    static {
        isReady = false;
    }

    /**
     * 路由初始化
     */
    public static void init(@NonNull Context context) {
        if (context instanceof Application) {
            ICacheFactory factory = new CacheFactoryImpl();
            factory.getContext().put(context);
            isReady = true;
        } else {
            ExceptionUtil.ClassCast("Are you sure this context is Application ?");
        }
    }

    public static boolean isReady() {
        return isReady;
    }
}
