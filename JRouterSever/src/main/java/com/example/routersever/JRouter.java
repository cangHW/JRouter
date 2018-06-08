package com.example.routersever;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.routersever.controller.context.IContextFactory.IContextFactory;
import com.example.routersever.util.ExceptionUtil;
import com.example.routersever.controller.context.ContextFactoryImpl;

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
            IContextFactory factory = new ContextFactoryImpl();
            factory.getContextCache().put(context);
            isReady = true;
        } else {
            ExceptionUtil.ClassCast("Are you sure this context is Application ?");
        }
    }

    public static boolean isReady() {
        return isReady;
    }
}
