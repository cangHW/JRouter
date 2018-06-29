package com.example.routersever.controller.cache;

import com.example.routersever.controller.cache.IContext.IContext;
import com.example.routersever.controller.cache.ICacheFactory.ICacheFactory;
import com.example.routersever.controller.cache.ICache.ICache;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/2 10:13.
 * Function :
 */
public class CacheFactoryImpl implements ICacheFactory {

    private CacheFactoryImpl(){}

    private static class Factory {
        private static CacheFactoryImpl mInstance = new CacheFactoryImpl();
    }

    public static ICacheFactory getFactory() {
        return Factory.mInstance;
    }

    @Override
    public IContext getContext() {
        return ContextImpl.getInstance();
    }

    @Override
    public ICache getCache() {
        return CacheImpl.getInstance();
    }
}
