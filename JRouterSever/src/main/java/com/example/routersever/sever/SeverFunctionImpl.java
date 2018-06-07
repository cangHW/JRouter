package com.example.routersever.sever;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.routersever.constant.Constants;
import com.example.routersever.sever.ISever.ISeverFunction;
import com.example.routersever.util.ExceptionUtil;

import java.util.HashMap;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 10:46.
 * Function :自定义功能服务
 */
class SeverFunctionImpl implements ISeverFunction {

    private HashMap<String, Object> mSever = new HashMap<>();

    private SeverFunctionImpl() {
    }

    private static class Factory {
        private static SeverFunctionImpl mInstance = new SeverFunctionImpl();
    }

    public static ISeverFunction getInstance() {
        return Factory.mInstance;
    }

    @Override
    public void RegisterFunction(@NonNull Class<?> c, @NonNull Object o) {
        if (mSever.containsKey(c.getName())) {
            ExceptionUtil.Runtime("the " + c.getName() + " is Registered");
            return;
        }
        mSever.put(c.getName(), o);
    }

    @Override
    public void unRegisterFunction(@NonNull Class<?> c) {
        if (mSever.containsKey(c.getName())) {
            mSever.remove(c.getName());
        } else {
            ExceptionUtil.Runtime("first you need to register " + c.getName());
        }
    }

    @Override
    public <T> T getFunction(Class<T> c) {
        if (mSever.containsKey(c.getName())) {
            Object o = mSever.get(c.getName());
            return c.cast(o);
        }
        Log.d(Constants.TAG, "first you need to register " + c.getName());
        return null;
    }
}
