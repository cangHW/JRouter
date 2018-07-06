package com.example.routersever.controller.cache;

import android.os.Process;

import com.example.routersever.controller.cache.ICache.ICache;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/13 17:56.
 * Function :
 */
public class CacheImpl implements ICache {

    private volatile WeakReference<Gson> mGsonWeakReference;
    private volatile WeakReference<Integer> mPidWeakReference;

    private CacheImpl() {
    }

    private static class Factory {
        private static final CacheImpl mInstance = new CacheImpl();
    }

    public static ICache getInstance() {
        return Factory.mInstance;
    }

    @Override
    public Gson getGson() {
        if (mGsonWeakReference == null || mGsonWeakReference.get() == null) {
            Gson gson = new Gson();
            mGsonWeakReference = new WeakReference<>(gson);
        }
        return mGsonWeakReference.get();
    }

    @Override
    public int getPid() {
        if (mPidWeakReference == null || mPidWeakReference.get() == null) {
            int pid=Process.myPid();
            mPidWeakReference = new WeakReference<>(pid);
        }
        return mPidWeakReference.get();
    }
}
