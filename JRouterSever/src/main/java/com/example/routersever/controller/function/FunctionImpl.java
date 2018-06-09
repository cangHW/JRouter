package com.example.routersever.controller.function;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.routersever.constant.Constants;
import com.example.routersever.controller.function.IFunction.IFunction;
import com.example.routersever.util.ExceptionUtil;

import java.util.HashMap;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 10:46.
 * Function :自定义功能服务
 */
class FunctionImpl implements IFunction {

    private HashMap<String, Object> mSeverWapper = new HashMap<>();

    private FunctionImpl() {
    }

    private static class Factory {
        private static FunctionImpl mInstance = new FunctionImpl();
    }

    public static IFunction getInstance() {
        return Factory.mInstance;
    }

    @Override
    public void RegisterFunction(@NonNull Class<?> c, @NonNull Object o) {
        if (mSeverWapper.containsKey(c.getName())) {
            ExceptionUtil.Runtime("the " + c.getName() + " is Registered");
            return;
        }
        mSeverWapper.put(c.getName(), o);
    }

    @Override
    public void unRegisterFunction(@NonNull Class<?> c) {
        if (mSeverWapper.containsKey(c.getName())) {
            mSeverWapper.remove(c.getName());
        } else {
            ExceptionUtil.Runtime("first you need to register " + c.getName());
        }
    }

    @Override
    public <T> T getFunction(Class<T> c) {
        if (mSeverWapper.containsKey(c.getName())) {
            Object o = mSeverWapper.get(c.getName());
            return c.cast(o);
        }
        Log.d(Constants.TAG, "first you need to register " + c.getName());
        return null;
    }
}
