package com.example.routersever.controller.cache;

import android.content.Context;

import com.example.routersever.controller.cache.IContext.IContext;

import java.lang.ref.WeakReference;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 10:20.
 * Function :application缓存类，用于路由服务
 */
class ContextImpl implements IContext {

    private volatile WeakReference<Context> mWeakReference;

    private ContextImpl() {
    }

    private static class Factory {
        private static ContextImpl mInstance = new ContextImpl();
    }

    public static IContext getInstance() {
        return ContextImpl.Factory.mInstance;
    }

    @Override
    public void put(Context context) {
        if (mWeakReference == null) {
            mWeakReference = new WeakReference<>(context);
        }
    }

    @Override
    public Context get() {
        if (mWeakReference != null) {
            return mWeakReference.get();
        }
        return null;
    }
}
