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

    private volatile WeakReference<Context> mContextWeakReference;

    private ContextImpl() {
    }

    private static class Factory {
        private static final ContextImpl mInstance = new ContextImpl();
    }

    public static IContext getInstance() {
        return ContextImpl.Factory.mInstance;
    }

    @Override
    public void put(Context context) {
        if (mContextWeakReference == null||mContextWeakReference.get()==null) {
            mContextWeakReference = new WeakReference<>(context);
        }
    }

    @Override
    public Context get() {
        if (mContextWeakReference != null) {
            return mContextWeakReference.get();
        }
        return null;
    }
}
