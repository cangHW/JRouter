package com.example.routersever.controller.cache.ICacheFactory;

import com.example.routersever.controller.cache.IContext.IContext;
import com.example.routersever.controller.cache.ICache.ICache;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 10:23.
 * Function :
 */
public interface ICacheFactory {

    IContext getContext();

    ICache getCache();
}
