package com.example.routersever.sever;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.routersever.constant.Constants;
import com.example.routersever.controller.function.FunctionFactoryImpl;
import com.example.routersever.sever.ISever.ISeverFunction;
import com.example.routersever.util.ExceptionUtil;

import java.util.HashMap;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 10:46.
 * Function :自定义功能服务
 */
class SeverFunctionImpl implements ISeverFunction {

    private SeverFunctionImpl() {
    }

    private static class Factory {
        private static final SeverFunctionImpl mInstance = new SeverFunctionImpl();
    }

    public static ISeverFunction getInstance() {
        return Factory.mInstance;
    }

    @Override
    public void RegisterFunction(@NonNull Class<?> c, @NonNull Object o) {
        FunctionFactoryImpl.getInstance().getFunction().RegisterFunction(c, o);
    }

    @Override
    public void unRegisterFunction(@NonNull Class<?> c) {
        FunctionFactoryImpl.getInstance().getFunction().unRegisterFunction(c);
    }

    @Override
    public <T> T getFunction(Class<T> c) {
        return FunctionFactoryImpl.getInstance().getFunction().getFunction(c);
    }
}
